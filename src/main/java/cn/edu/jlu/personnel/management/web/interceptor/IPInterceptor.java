package cn.edu.jlu.personnel.management.web.interceptor;

import cn.edu.jlu.personnel.management.util.NetworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nostalie on 17-5-13.
 */
public class IPInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityInterceptor.class);

    public static final ThreadLocal<String> IP = new ThreadLocal<String>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = NetworkUtil.getIpAddress(request);
        IP.set(ip);
        LOGGER.debug("登录ip为: {}",ip);
        return true;
    }
}
