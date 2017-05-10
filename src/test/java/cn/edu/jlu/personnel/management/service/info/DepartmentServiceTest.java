package cn.edu.jlu.personnel.management.service.info;

import cn.edu.jlu.personnel.management.vo.model.Department;
import org.junit.Assert;
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
 * Created by nostalie on 17-5-10.
 */@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/mvc.xml" })
public class DepartmentServiceTest {

     private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceTest.class);

     @Resource
    private DepartmentService departmentService;

     @Test
     public void testAdd(){
         Department department = new Department();
         department.setTel("2222");
         department.setName("采购");
         department.setUserId(3);
         int count = departmentService.addDepartment(department);
         Assert.assertEquals(count,1);
     }

     @Test
     public void testQuery(){
         Department department = departmentService.queryDepartmentById(1).get();
         LOGGER.debug("department is: {}",department);
     }

     @Test
    public void testQuery2(){
         List<Department> departments = departmentService.queryAllDepartments();
         LOGGER.debug("departments is: {}",departments);
     }

     @Test
     public void testUpdate(){
        Department department = departmentService.queryDepartmentById(1).get();
        department.setName("fenxiao");
        department.setTel("21111111");
        int count = departmentService.updateDepartment(department);
        Assert.assertEquals(count,1);
     }

     @Test
    public void testDelete(){
         int count = departmentService.deleteDepartment(departmentService.queryDepartmentById(1).get());
         Assert.assertEquals(count,1);
     }
}