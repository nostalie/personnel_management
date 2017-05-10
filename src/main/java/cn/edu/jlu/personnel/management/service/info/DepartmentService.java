package cn.edu.jlu.personnel.management.service.info;

import cn.edu.jlu.personnel.management.dao.DepartmentDao;
import cn.edu.jlu.personnel.management.vo.model.Department;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-10.
 */
@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Resource
    private DepartmentDao departmentDao;

    public List<Department> queryAllDepartments(){
        try {
            return departmentDao.selectDepartments();
        } catch (Exception e) {
            LOGGER.error("查询全部部门失败");
            throw new RuntimeException(e);
        }
    }

    public Optional<Department> queryDepartmentById(Integer departmentId){
        try {
            Preconditions.checkNotNull(departmentId);
            Department department = departmentDao.selectDepartmentById(departmentId);
            return Optional.fromNullable(department);
        } catch (Exception e) {
            LOGGER.error("查询部门失败，部门 id is: {}",departmentId);
            throw new RuntimeException(e);
        }
    }

    public int addDepartment(Department department){
        try {
            Preconditions.checkNotNull(department);
            return departmentDao.insertDepartment(department);
        } catch (Exception e) {
            LOGGER.error("增加部门失败，department is: {}",department,e);
            throw new RuntimeException(e);
        }
    }

    public int updateDepartment(Department department){
        try {
            Preconditions.checkNotNull(department);
            Preconditions.checkNotNull(department.getId());
            return departmentDao.updateDepartment(department);
        } catch (Exception e) {
            LOGGER.error("更新部门信息失败, department is: {}",department,e);
            throw new RuntimeException(e);
        }
    }

    public int deleteDepartment(Department department){
        try {
            Preconditions.checkNotNull(department);
            Preconditions.checkNotNull(department.getId());
            return departmentDao.deleteDepartment(department.getId());
        } catch (Exception e) {
            LOGGER.error("删除部门失败,department is: {}",department,e);
            throw new RuntimeException(e);
        }
    }
}
