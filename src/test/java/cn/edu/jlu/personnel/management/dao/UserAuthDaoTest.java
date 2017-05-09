package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.UserAuth;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nostalie on 17-5-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class UserAuthDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthDaoTest.class);

    @Resource
    private UserAuthDao userAuthDao;

    @Test
    public void testInsert(){
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1);
        userAuth.setAuthId(1);
        int count = userAuthDao.insertUserAuth(userAuth);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testSelectByUser(){
        List<UserAuth> userAuth = userAuthDao.selectUserAuthByUserId(1);
        LOGGER.debug("userAuth is: {}",userAuth);
    }

    @Test
    public void testSelectByAuth(){
        List<UserAuth> userAuth = userAuthDao.selectUserAuthByAuthGroupId(1);
        LOGGER.debug("userAuth is: {}",userAuth);
    }

    @Test
    public void testDelete(){
        int count = userAuthDao.deleteUserAuth(1,1);
        Assert.assertEquals(count,1);
    }
}