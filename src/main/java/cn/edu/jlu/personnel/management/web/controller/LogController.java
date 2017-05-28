package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.dao.LogDao;
import cn.edu.jlu.personnel.management.vo.model.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-28.
 */
@Controller
public class LogController {

    @Resource
    private LogDao logDao;

    @RequestMapping("/super/log/query")
    public List<Log> queryLogs(){
        return logDao.selectLogs();
    }
}
