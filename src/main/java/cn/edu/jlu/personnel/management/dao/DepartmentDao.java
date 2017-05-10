package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.Department;
import cn.edu.jlu.personnel.management.vo.model.Position;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nostalie on 17-5-6.
 */
@Repository
public interface DepartmentDao {

    List<Department> selectDepartments();

    Department selectDepartmentById(@Param("departmentId") Integer departmentId);

    int insertDepartment(Department department);

    int deleteDepartment(@Param("departmentId") Integer departmentId);

    int updateDepartment(Department department);
}
