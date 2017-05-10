package cn.edu.jlu.personnel.management.web.interceptor;

import cn.edu.jlu.personnel.management.service.sign.CookieService;
import cn.edu.jlu.personnel.management.util.NetworkUtil;
import com.google.common.base.Optional;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nostalie on 17-5-9.
 */
public class IdentityInterceptor extends HandlerInterceptorAdapter{

    public static final ThreadLocal<String> IP = new ThreadLocal<String>();
    public static final ThreadLocal<String> USER_NAME = new ThreadLocal<String>();
    @Resource
    private CookieService cookieService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = NetworkUtil.getIpAddress(request);
        IP.set(ip);
        Optional<String> userName = cookieService.getUserName(request.getCookies());
        if(userName.isPresent()){
            USER_NAME.set(userName.get());
        }else{
            String name = request.getParameter("userName");
            USER_NAME.set(name);
        }
        return true;
    }
}
