package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.service.info.DepartmentService;
import cn.edu.jlu.personnel.management.vo.model.Department;
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
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Resource
    private DepartmentService departmentService;

    @RequestMapping("/ordinary/department/query/all")
    public List<Department> queryAllDepartments(){
        return departmentService.queryAllDepartments();
    }
}
