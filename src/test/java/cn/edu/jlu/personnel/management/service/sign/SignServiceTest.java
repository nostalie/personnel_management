package cn.edu.jlu.personnel.management.service.sign;

import cn.edu.jlu.personnel.management.vo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by nostalie on 17-5-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class SignServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignServiceTest.class);
    @Resource
    private SignService signService;

    @Test
    public void testSign(){
        User user = new User();
        user.setUserName("nostalie");
        user.setPassword("12345");
        String result = signService.signIn(null,user);
        LOGGER.debug("result is: {}", result);
    }

}