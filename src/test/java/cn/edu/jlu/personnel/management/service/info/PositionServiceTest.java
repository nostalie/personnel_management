package cn.edu.jlu.personnel.management.service.info;

import cn.edu.jlu.personnel.management.vo.model.Position;
import javafx.geometry.Pos;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nostalie on 17-5-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class PositionServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceTest.class);

    @Resource
    private PositionService positionService;

    @Test
    public void testAdd(){
        Position position = new Position();
        position.setName("经理");
        int count = positionService.addPosition(position);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testQuery(){
        Position position = positionService.queryPositionById(1).get();
        LOGGER.debug("position is: {}",position);
    }

    @Test
    public void testQuery2(){
        List<Position> positionList = positionService.queryPositions();
        LOGGER.debug("positionList is: {}",positionList);
    }

    @Test
    public void testUpdate(){
        Position position = positionService.queryPositionById(1).get();
        position.setName("dada");
        int count = positionService.updatePosition(position);
        Assert.assertEquals(count,1);
    }

    @Test
    public void testDelete(){
        int count = positionService.deletePosition(1);
        Assert.assertEquals(count,1);
    }

}