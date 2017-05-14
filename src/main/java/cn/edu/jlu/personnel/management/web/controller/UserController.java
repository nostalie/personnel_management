package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.service.info.UserService;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.google.common.base.Optional;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-12.
 */
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/ordinary/query/user/info")
    public User queryUserInfo(){
        String userName = IdentityInterceptor.USER_NAME.get();
        Optional<User> userOptional = userService.queryUserInfo(userName);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        return null;
    }

    @RequestMapping("/bu/super/user/query/id")
    public User queryUserById(Integer id){
        Optional<User> userOptional = userService.queryUserById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        return null;
    }

    @RequestMapping("/bu/super/user/query/all")
    public List<User> queryAllUser(User user, RowBounds rowBounds){
        LOGGER.debug("user is: {},rowBounds is: {}",user,rowBounds);
        List<User> users = userService.queryUsers(user,rowBounds);
        LOGGER.debug("users is: {}",users);
        return userService.queryUsers(user,rowBounds);
    }

    @RequestMapping("/bu/super/user/add")
    public void addUser(User user){
        userService.addUser(user);
    }

    @RequestMapping("/bu/super/user/delete")
    public void deleteUser(String userName){
        userService.deleteUser(userName);
    }

    @RequestMapping("/bu/super/user/update")
    public void updateUser(User user){
        userService.updateUser(user);
    }
}
