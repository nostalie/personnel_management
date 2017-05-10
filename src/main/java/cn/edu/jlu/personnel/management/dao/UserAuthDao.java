package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.UserAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nostalie on 17-5-6.
 */
@Repository
public interface UserAuthDao {

    int insertUserAuth(UserAuth userAuth);

    UserAuth selectUserAuth(@Param("userId") int userId,@Param("authId") int authId);

    List<UserAuth> selectUserAuthByUserId(@Param("userId") int userId);

    List<UserAuth> selectUserAuthByAuthGroupId(@Param("authId") int authId);

    int deleteUserAuth(@Param("userId") int userId,@Param("authId") int authId);
}
