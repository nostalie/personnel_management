package cn.edu.jlu.personnel.management.service.info;

import cn.edu.jlu.personnel.management.vo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
        User user = userService.queryUserInfo("nos").get();
        LOGGER.debug("user is: {}",user);
        User user2 = userService.queryUserById(3).get();
        LOGGER.debug("user2 is: {}",user2);
        List<User> userList = userService.queryUsers(user, RowBounds.DEFAULT);
        LOGGER.debug("userList is: {}",userList);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(4);
        user.setUserName("111");
        user.setRealName("jjj");
        String time = "2017-05-13 21:24:22";
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormat.parseDateTime(time);
        user.setUpdateTime(dateTime.toDate());
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