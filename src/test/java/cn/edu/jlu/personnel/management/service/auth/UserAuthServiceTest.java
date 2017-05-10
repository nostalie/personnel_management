package cn.edu.jlu.personnel.management.service.auth;

import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
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
 * Created by nostalie on 17-5-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class UserAuthServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthServiceTest.class);
    @Resource
    private UserAuthService userAuthService;

    @Test
    public void testAdd(){
        UserAuth userAuth = new UserAuth();
        userAuth.setAuthId(22);
        userAuth.setUserId(11);
        int count = userAuthService.addAuthForUser(userAuth);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testQuery(){
        List<UserAuth> result = userAuthService.queryAuthForUser(3);
        LOGGER.debug("result is: {}",result);
    }

    @Test
    public void testQuery2(){
        List<UserAuth> result = userAuthService.queryUserInAuth(2);
        LOGGER.debug("result is: {}",result);
    }

    @Test
    public void testDelete(){
        UserAuth userAuth = userAuthService.queryUserInAuth(2).get(0);
        int count = userAuthService.deleteAuthForUser(userAuth);
        Assert.assertEquals(count,1);
    }
}