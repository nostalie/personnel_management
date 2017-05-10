package cn.edu.jlu.personnel.management.service.sign;

import cn.edu.jlu.personnel.management.util.EncryptUtil;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Created by nostalie on 17-5-9.
 */
@Service
public class CookieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieService.class);
    static final String COOKIE_NAME = "userName";
    private static final Joiner VERTICAL_LINE_JOINER = Joiner.on("|");
    static final Splitter VERTICAL_LINE_SPLITTER = Splitter.on("|").omitEmptyStrings().trimResults();

    /**
     * cookie value 为 userName|过期时间|ip 进行加密的值
     * @param userName
     * @return
     */
    public Cookie createCookie(String userName){
        try {
            Long current = System.currentTimeMillis();
            Long overdue = current + 60 * 1000;
            String ip = IdentityInterceptor.IP.get();
            String value = VERTICAL_LINE_JOINER.join(userName,overdue,ip);
            return new Cookie(COOKIE_NAME, EncryptUtil.encode(value));
        } catch (Exception e) {
            LOGGER.error("创建cookie失败,userName is:{}",userName,e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 清除cookie
     * @param cookies
     * @return
     */
    public Cookie[] clearCookies(Cookie[] cookies){
        for(Cookie cookie : cookies){
            if(Objects.equal(cookie.getName(),COOKIE_NAME)){
                cookie.setMaxAge(0);
            }
        }
        return cookies;
    }

    public Optional<String> getUserName(Cookie[] cookies){
        try {
            String value = "";
            String userName = null;
            for(Cookie cookie : cookies){
                if(Objects.equal(cookie.getName(),COOKIE_NAME)){
                    value = EncryptUtil.decode(cookie.getValue());
                    break;
                }
            }
            LOGGER.debug("value is: {}",value);
            if(!Objects.equal(value,"")){
                List<String> values = VERTICAL_LINE_SPLITTER.splitToList(value);
                if(! values.isEmpty()){
                    userName = values.get(0);
                }
            }
            return Optional.fromNullable(userName);
        } catch (Exception e) {
            LOGGER.error("获取用户名失败",e);
            throw new RuntimeException(e);
        }
    }
}
