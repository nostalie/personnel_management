package cn.edu.jlu.personnel.management.service.auth;

import cn.edu.jlu.personnel.management.enums.Auth;
import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by nostalie on 17-5-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class AuthGroupServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthGroupServiceTest.class);

    @Resource
    private AuthGroupService authGroupService;

    @Test
    public void testAdd(){
        AuthGroup authGroup = new AuthGroup();
        authGroup.setType(Auth.blend(Auth.SUPER_LEADER,Auth.GROUP_AUTH,Auth.USER_AUTH,Auth.ORDINARY,Auth.BU_LEADER));
        authGroup.setGroupName("root");
        int count = authGroupService.addGroup(authGroup);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testUpdate(){
        AuthGroup authGroup = authGroupService.queryAllGroup().get(0);
        authGroup.setType(Auth.blend(Auth.USER_AUTH,Auth.GROUP_AUTH));
        authGroup.setGroupName("user");
        int count = authGroupService.updateGroup(authGroup);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testDelete(){
        AuthGroup authGroup = authGroupService.queryAllGroup().get(0);
        int count = authGroupService.deleteGroup(authGroup);
        Assert.assertEquals(count,1);
    }
}