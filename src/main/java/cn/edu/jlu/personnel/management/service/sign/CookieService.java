package cn.edu.jlu.personnel.management.service.sign;

import cn.edu.jlu.personnel.management.dao.UserDao;
import cn.edu.jlu.personnel.management.util.EncryptUtil;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.web.interceptor.IPInterceptor;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.google.common.base.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Created by nostalie on 17-5-9.
 */
@Service
public class CookieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieService.class);
    private static final String COOKIE_NAME = "userName";
    private static final Joiner VERTICAL_LINE_JOINER = Joiner.on("|");
    private static final Splitter VERTICAL_LINE_SPLITTER = Splitter.on("|").omitEmptyStrings().trimResults();

    @Resource
    private UserDao userDao;
    /**
     * cookie value 为 userName|过期时间|ip 进行加密的值
     * @param userName
     * @return
     */
    public Cookie createCookie(String userName){
        try {
            Long current = System.currentTimeMillis();
            Long overdue = current + 60 * 60 * 1000;
            String ip = IPInterceptor.IP.get();
            String value = VERTICAL_LINE_JOINER.join(userName,overdue,ip);
            Cookie cookie = new Cookie(COOKIE_NAME, EncryptUtil.encode(value));
            cookie.setMaxAge(60 * 60);
            cookie.setPath("/");
            return cookie;
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

    /**
     * 校验cookie是否合法，过期时间一小时
     * @param cookies
     * @return
     */
    public boolean isEffective(Cookie[] cookies){
        List<String> values = Lists.newArrayList();
        for(Cookie cookie : cookies){
            if(Objects.equal(cookie.getName(),CookieService.COOKIE_NAME)){
                values = CookieService.VERTICAL_LINE_SPLITTER.splitToList(EncryptUtil.decode(cookie.getValue()));
                break;
            }
        }
        if(values.size() == 3) {
            String userName = values.get(0);
            User userCheck = new User();
            userCheck.setUserName(userName);
            List<User> users = userDao.selectUsers(userCheck);
            Long time = Long.parseLong(values.get(1));
            String ip = IPInterceptor.IP.get();
            String ipCheck = values.get(2);
            if(time>System.currentTimeMillis() && users.size()==1 && Objects.equal(ip,ipCheck)){
                return true;
            }
        }
        return false;
    }

    public Optional<String> getUserName(Cookie[] cookies){
        try {
            String value = "";
            String userName = null;
            if(cookies == null){
                return Optional.absent();
            }
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
