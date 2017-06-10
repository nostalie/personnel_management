package cn.edu.jlu.personnel.management.service.info;

import cn.edu.jlu.personnel.management.dao.UserDao;
import cn.edu.jlu.personnel.management.enums.Auth;
import cn.edu.jlu.personnel.management.support.Read;
import cn.edu.jlu.personnel.management.util.EncryptUtil;
import cn.edu.jlu.personnel.management.vo.model.Department;
import cn.edu.jlu.personnel.management.vo.model.Position;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-9.
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserDao userDao;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private PositionService positionService;

    /**
     * 由用户名查询用户信息
     *
     * @param userName
     * @return
     */
    @Read
    public Optional<User> queryUserInfo(String userName) {
        try {
            Preconditions.checkNotNull(userName);
            User result = userDao.selectUserByUserName(userName);
            if (result != null) {
                result.setPassword(EncryptUtil.decode(result.getPassword()));
                fillUser(result);
            }
            return Optional.fromNullable(result);
        } catch (Exception e) {
            LOGGER.error("查询用户信息失败，userName is: {}", userName, e);
            throw new RuntimeException(e);
        }
    }

    @Read
    public Optional<User> queryUserById(Integer userId) {
        try {
            Preconditions.checkNotNull(userId);
            User result = userDao.selectUserByUserId(userId);
            if (result != null) {
                result.setPassword(EncryptUtil.decode(result.getPassword()));
                fillUser(result);
            }
            return Optional.fromNullable(result);
        } catch (Exception e) {
            LOGGER.error("查询用户信息失败，userId is: {}", userId, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 按条件查询结果集
     *
     * @param user
     * @return
     */
    @Read
    public List<User> queryUsers(User user, RowBounds rowBounds) {
        try {
            Preconditions.checkNotNull(user);
            Integer auth = IdentityInterceptor.AUTH.get();
            if (Auth.isContain(auth, Auth.BU_LEADER) && !Auth.isContain(auth, Auth.SUPER_LEADER)) {
                String requestUserName = IdentityInterceptor.USER_NAME.get();
                User requestUser = queryUserInfo(requestUserName).isPresent() ? queryUserInfo(requestUserName).get() : null;
                if (requestUser != null) {
                    user.setDepartmentId(requestUser.getDepartmentId());
                }
            }
            if (user.getPassword() != null) {
                user.setPassword(EncryptUtil.encode(user.getPassword()));
            }
            return Lists.transform(userDao.selectUsers(user, rowBounds), new Function<User, User>() {
                public User apply(User user) {
                    user.setPassword(EncryptUtil.decode(user.getPassword()));
                    fillUser(user);
                    return user;
                }
            });
        } catch (Exception e) {
            LOGGER.error("安条件查询用户失败，查询条件为: {}", user);
            throw new RuntimeException(e);
        }
    }

    private void fillUser(User user) {
        Preconditions.checkNotNull(user);
        Optional<Department> departmentOptional = departmentService.queryDepartmentById(user.getDepartmentId());
        Optional<Position> positionOptional = positionService.queryPositionById(user.getPositionId());
        if (departmentOptional.isPresent()) {
            user.setDepartment(departmentOptional.get());
        }
        if (positionOptional.isPresent()) {
            user.setPosition(positionOptional.get());
        }
    }

    public int deleteUser(String userName) {
        try {
            Preconditions.checkNotNull(userName);
            return userDao.deleteUserByUserName(userName);
        } catch (Exception e) {
            LOGGER.error("删除用户失败，userName is: {}", userName);
            throw new RuntimeException(e);
        }
    }

    public int addUser(User user) {
        try {
            Preconditions.checkNotNull(user);
            if (user.getPassword() != null) {
                user.setPassword(EncryptUtil.encode(user.getPassword()));
            }
            return userDao.insertUser(user);
        } catch (Exception e) {
            LOGGER.error("添加用户失败，user is: {}", user);
            throw new RuntimeException(e);
        }
    }

    public int updateUser(User user) {
        try {
            Preconditions.checkNotNull(user);
            if (user.getPassword() != null) {
                user.setPassword(EncryptUtil.encode(user.getPassword()));
            }
            return userDao.updateUser(user);
        } catch (Exception e) {
            LOGGER.error("更新用户失败,user is: {}", user);
            throw new RuntimeException(e);
        }
    }
}
