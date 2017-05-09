package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by nostalie on 17-5-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class AuthGroupDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthGroupDaoTest.class);

    @Resource
    private AuthGroupDao authGroupDao;

    @Test
    public void testInsert(){
        AuthGroup authGroup = new AuthGroup();
        authGroup.setGroupName("部leader");
        authGroup.setType(2);
        int count = authGroupDao.insertAuthGroup(authGroup);
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testUpdate(){
        AuthGroup authGroup = authGroupDao.selectAuthGroups().get(0);
        LOGGER.debug("authGroup is: {}",authGroup);
        authGroup.setGroupName("leader");
        authGroup.setType(22);
        int count = authGroupDao.updateAuthGroup(authGroup);
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testDelete(){
        int count = authGroupDao.deleteAuthGroup("leader");
        LOGGER.debug("count is: {}",count);
        Assert.assertEquals(count,1);
    }
}