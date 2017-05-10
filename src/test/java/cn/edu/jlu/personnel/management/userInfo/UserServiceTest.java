package cn.edu.jlu.personnel.management.userInfo;

import cn.edu.jlu.personnel.management.vo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nostalie on 17-5-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class UserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);
    @Resource
    private UserService userService;
    @Test
    public void testQuery(){
        User user = userService.queryUserInfo("nostalie").get();
        LOGGER.debug("user is: {}",user);
        List<User> userList = userService.queryUsers(user);
        LOGGER.debug("userList is: {}",userList);
    }

    @Test
    public void testUpdate(){
        User user = userService.queryUserInfo("nostalie").get();
        int count = userService.updateUser(user);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testAdd(){
        User user = userService.queryUserInfo("nos").get();
        user.setUserName("nostalie");
        int count = userService.addUser(user);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testDelete(){
        int count = userService.deleteUser("nos");
        Assert.assertEquals(count,1);
    }
}