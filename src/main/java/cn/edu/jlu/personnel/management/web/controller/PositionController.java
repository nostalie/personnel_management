package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.service.info.PositionService;
import cn.edu.jlu.personnel.management.vo.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-13.
 */
@Controller
public class PositionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

    @Resource
    private PositionService positionService;

    @RequestMapping("/ordinary/position/query/all")
    public List<Position> queryAllPositions(){
        return positionService.queryPositions();
    }

    @RequestMapping("/super/position/add")
    public void addPosition(Position position){
        positionService.addPosition(position);
    }
}
