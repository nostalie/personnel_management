package cn.edu.jlu.personnel.management.web.interceptor;

import cn.edu.jlu.personnel.management.util.NetworkUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nostalie on 17-5-9.
 */
public class IdentityInterceptor extends HandlerInterceptorAdapter{

    public static final ThreadLocal<String> IP = new ThreadLocal<String>();
    public static final ThreadLocal<String> USRE_NAME = new ThreadLocal<String>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = NetworkUtil.getIpAddress(request);
        IP.set(ip);
        return true;
    }
}
