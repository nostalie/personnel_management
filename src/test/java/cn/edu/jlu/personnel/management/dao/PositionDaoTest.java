package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.Position;
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
public class PositionDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionDaoTest.class);

    @Resource
    private PositionDao positionDao;

    @Test
    public void testInsert(){
        Position position = new Position();
        position.setName("主管");
        int count = positionDao.insertPosition(position);
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testSelect(){
        List<Position> positions = positionDao.selectPositions();
        LOGGER.debug("positions is: {}",positions);
    }

    @Test
    public void testDelete(){
        int count = positionDao.deletePosition("经理");
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testUpdate(){
        Position position = positionDao.selectPositions().get(0);
        position.setName("经理");
        int count = positionDao.updatePosition(position);
        LOGGER.debug("count is: {}",count);
    }
}