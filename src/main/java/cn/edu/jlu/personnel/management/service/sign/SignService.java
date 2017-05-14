package cn.edu.jlu.personnel.management.service.sign;

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
    public static final String ERROR = "failed";
    @Resource
    private UserDao userDao;

    @Read
    public String signIn(User user){
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
            }
            return ERROR;
        } catch (Exception e) {
            LOGGER.error("登录失败",e);
            throw new RuntimeException(e);
        }
    }
}
