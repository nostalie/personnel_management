package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nostalie on 17-5-5.
 */
@Repository("userDao")
public interface UserDao {

    List<User> selectUsers(User user);

    int deleteUserByUserName(@Param("userName") String userName);

    int insertUser(User user);
}
