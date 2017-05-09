package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.datasource.DataSourceContext;
import cn.edu.jlu.personnel.management.enums.DataSourceType;
import cn.edu.jlu.personnel.management.enums.Gender;
import cn.edu.jlu.personnel.management.util.EncryptUtil;
import cn.edu.jlu.personnel.management.vo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nostalie on 17-5-5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class UserDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

    @Resource
    private UserDao userDao;

    @Test
    public void testSelect(){
        User user = new User();
        //user.setId(1);
        //User result = userDao.selectUsers(user).get(0);
        List<User> result = userDao.selectUsers(user);
        LOGGER.debug("result is: {}",result);
    }

    @Test
    public void testDelete(){
        int count = userDao.deleteUserByUserName("nostalie");
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testInsert() throws InterruptedException {
        //DataSourceContext.setDataSourceContext(DataSourceType.SLAVE.toString());
        User user = new User();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1994,6,12);
        user.setBirthday(calendar.getTime());
        user.setGender(Gender.MEN);
        user.setPassword(EncryptUtil.encode("123456"));
        user.setRealName("张俭");
        user.setUserName("nostalie");
        int count = userDao.insertUser(user);
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testInsert2() throws InterruptedException {
        DataSourceContext.setDataSourceContext(DataSourceType.MASTER.toString());
    }
}