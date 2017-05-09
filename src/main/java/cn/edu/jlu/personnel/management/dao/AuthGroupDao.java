package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import cn.edu.jlu.personnel.management.vo.model.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nostalie on 17-5-6.
 */
@Repository
public interface AuthGroupDao {

    List<AuthGroup> selectAuthGroups();

    int insertAuthGroup(AuthGroup authGroup);

    int deleteAuthGroup(@Param("groupName") String groupName);

    int updateAuthGroup(AuthGroup authGroup);
}
