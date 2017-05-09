package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.Department;
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
public class DepartmentDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoTest.class);

    @Resource
    private DepartmentDao departmentDao;

    @Test
    public void testInsert(){
        Department department = new Department();
        department.setName("采购");
        department.setTel("2322222222");
        department.setUserId(2);
        int count = departmentDao.insertDepartment(department);
        LOGGER.debug("count is :{}",count);
    }

    @Test
    public void testSelect(){
        List<Department> departments = departmentDao.selectDepartments();
        LOGGER.debug("departments is: {}",departments);
    }

    @Test
    public void testDelete(){
        int count = departmentDao.deleteDepartment("采购");
        LOGGER.debug("count is: {}",count);
    }

    @Test
    public void testUpdate(){
        Department department = departmentDao.selectDepartments().get(0);
        department.setTel("333333333333");
        int count = departmentDao.updateDepartment(department);
        LOGGER.debug("count is: {}",count);
    }
}