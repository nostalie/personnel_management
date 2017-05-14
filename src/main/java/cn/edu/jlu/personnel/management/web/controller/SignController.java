package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.service.info.UserService;
import cn.edu.jlu.personnel.management.service.sign.CookieService;
import cn.edu.jlu.personnel.management.service.sign.SignService;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nostalie on 17-5-12.
 */
@Controller
public class SignController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);

    @Resource
    private SignService signService;
    @Resource
    private UserService userService;
    @Resource
    private CookieService cookieService;

    @RequestMapping("/ordinary/sign/in")
    public String signIn(String password, String userName, HttpServletResponse response){
        Optional<User> userOptional = userService.queryUserInfo(userName);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(password);
            String result = signService.signIn(user);
            if(result.equals(SignService.SUCCESS)){
                Cookie cookie = cookieService.createCookie(userName);
                response.addCookie(cookie);
            }
            return result;
        }
        return "非法用户";
    }
}
