package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.enums.OperationType;
import cn.edu.jlu.personnel.management.vo.model.Log;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nostalie on 17-5-28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class LogDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDaoTest.class);

    @Resource
    private LogDao logDao;

    @Test
    public void testInsert(){
        Log log = new Log();
        log.setUserName("nostalie");
        log.setOperationTime(new Date());
        log.setOperationType(OperationType.UPDATE);
        log.setOldContent("nnnnnnnnn");
        log.setNewContent("yyyyyyyyy");
        int count = logDao.insertLog(log);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testSelect(){
        List<Log> logs = logDao.selectLogs();
        LOGGER.info("logs are: {}",logs);
    }
}