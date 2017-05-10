package cn.edu.jlu.personnel.management.web.interceptor;

import cn.edu.jlu.personnel.management.datasource.DataSourceContext;
import cn.edu.jlu.personnel.management.enums.Auth;
import cn.edu.jlu.personnel.management.service.auth.AuthGroupService;
import cn.edu.jlu.personnel.management.service.auth.UserAuthService;
import cn.edu.jlu.personnel.management.service.info.UserService;
import cn.edu.jlu.personnel.management.service.sign.CookieService;
import cn.edu.jlu.personnel.management.util.NetworkUtil;
import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.vo.model.UserAuth;
import com.google.common.base.Optional;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by nostalie on 17-5-9.
 */
public class IdentityInterceptor extends HandlerInterceptorAdapter{

    public static final ThreadLocal<String> IP = new ThreadLocal<String>();
    public static final ThreadLocal<String> USER_NAME = new ThreadLocal<String>();
    public static final ThreadLocal<Integer> AUTH = new ThreadLocal<Integer>();

    private static final String ORDINARY = "ordinary";
    private static final String BU_LEADER = "bu";
    private static final String SUPER_LEADER = "super";
    private static final String GROUP_AUTH = "group";
    private static final String USER_AUTH = "allot";

    @Resource
    private CookieService cookieService;
    @Resource
    private UserService userService;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private AuthGroupService authGroupService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        DataSourceContext.salve();
        String ip = NetworkUtil.getIpAddress(request);
        IP.set(ip);
        Optional<String> userName = cookieService.getUserName(request.getCookies());
        if(userName.isPresent()){
            USER_NAME.set(userName.get());
        }else{
            String name = request.getParameter("userName");
            USER_NAME.set(name);
        }

        String url = request.getRequestURI();
        DataSourceContext.master();
        return isPermission(url);
    }

    private boolean isPermission(String url){
        DataSourceContext.salve();
        Optional<User> userOptional = userService.queryUserInfo(USER_NAME.get());
        if(!userOptional.isPresent()){
            return false;
        }
        User user = userOptional.get();

        //获取该登录用户的权限值
        Integer auth = 0;
        List<UserAuth> userAuths = userAuthService.queryAuthForUser(user.getId());
        for(UserAuth userAuth : userAuths){
            AuthGroup authGroup = authGroupService.queryGroupById(userAuth.getAuthId());
            auth = auth | authGroup.getType();
        }
        AUTH.set(auth);
        DataSourceContext.master();
        return url.contains(BU_LEADER) && Auth.isContain(auth,Auth.BU_LEADER) ||
                url.contains(SUPER_LEADER) && Auth.isContain(auth,Auth.SUPER_LEADER) ||
                url.contains(GROUP_AUTH) && Auth.isContain(auth,Auth.GROUP_AUTH) ||
                url.contains(USER_AUTH) && Auth.isContain(auth,Auth.USER_AUTH) ||
                url.contains(ORDINARY) && Auth.isContain(auth,Auth.ORDINARY);

    }
}
