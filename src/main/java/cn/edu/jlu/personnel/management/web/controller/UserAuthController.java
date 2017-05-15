package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.service.auth.UserAuthService;
import cn.edu.jlu.personnel.management.vo.model.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-15.
 */
@Controller
public class UserAuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthController.class);
    @Resource
    private UserAuthService userAuthService;

    @RequestMapping("/allot/query/user/group")
    public List<UserAuth> queryUserByGroup(Integer groupId){
        return userAuthService.queryUserInAuth(groupId);
    }

    @RequestMapping("/allot/auth/delete")
    public void deleteAuthForUser(UserAuth userAuth){
        userAuthService.deleteAuthForUser(userAuth);
    }

    @RequestMapping("/allot/auth/add")
    public void addAuthForUser(UserAuth userAuth){
        userAuthService.addAuthForUser(userAuth);
    }
}
