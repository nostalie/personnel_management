package cn.edu.jlu.personnel.management.sign;

import cn.edu.jlu.personnel.management.dao.UserDao;
import cn.edu.jlu.personnel.management.support.Read;
import cn.edu.jlu.personnel.management.util.EncryptUtil;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.google.common.base.Objects;
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
public class SignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignService.class);
    private static final String NULL_ERROR = "用户名或密码不能为空";
    private static final String NAME_OR_PASSWORD_ERROR = "用户名或密码错误";
    public static final String SUCCESS = "success";
    public static final String COOKIE_ERROR = "cookie不合法";
    @Resource
    private UserDao userDao;

    @Read
    public String signIn(Cookie[] cookies,User user){
        try {
            //用户名密码登录
            if(user != null){
                String userName = user.getUserName();
                String password = user.getPassword();
                if(userName == null || password == null){
                    return NULL_ERROR;
                }
                user.setPassword(EncryptUtil.encode(password));
                LOGGER.debug("user is: {}",user);
                List<User> users = userDao.selectUsers(user);
                if(users.isEmpty()){
                    return NAME_OR_PASSWORD_ERROR;
                }
                return SUCCESS;
            }else{
                //没有用户名密码时，进行cookie校验
                List<String> values = Lists.newArrayList();
                for(Cookie cookie : cookies){
                    if(Objects.equal(cookie.getName(),CookieService.COOKIE_NAME)){
                        values = CookieService.VERTICAL_LINE_SPLITTER.splitToList(cookie.getValue());
                        break;
                    }
                }
                if(values.size() == 3) {
                    String userName = EncryptUtil.decode(values.get(0));
                    User userCheck = new User();
                    userCheck.setUserName(userName);
                    List<User> users = userDao.selectUsers(userCheck);
                    Long time = Long.parseLong(EncryptUtil.decode(values.get(1)));
                    String ip = IdentityInterceptor.IP.get();
                    String ipCheck = EncryptUtil.decode(values.get(2));
                    if(time>System.currentTimeMillis() && users.size()==1 && Objects.equal(ip,ipCheck)){
                        return SUCCESS;
                    }
                }
                return COOKIE_ERROR;
            }
        } catch (Exception e) {
            LOGGER.error("登录失败",e);
            throw new RuntimeException(e);
        }
    }
}
