package cn.edu.jlu.personnel.management.service.auth;

import cn.edu.jlu.personnel.management.dao.AuthGroupDao;
import cn.edu.jlu.personnel.management.dao.UserAuthDao;
import cn.edu.jlu.personnel.management.service.info.UserService;
import cn.edu.jlu.personnel.management.support.Read;
import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.vo.model.UserAuth;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-10.
 */
@Service
public class UserAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthService.class);
    @Resource
    private UserAuthDao userAuthDao;
    @Resource
    private AuthGroupDao authGroupDao;
    @Resource
    private UserService userService;

    public int addAuthForUser(UserAuth userAuth){
        try {
            Preconditions.checkNotNull(userAuth);
            Preconditions.checkNotNull(userAuth.getAuthId());
            Preconditions.checkNotNull(userAuth.getUserId());
            UserAuth result = userAuthDao.selectUserAuth(userAuth.getUserId(),userAuth.getAuthId());
            if(result != null){
                throw new Exception("该用户已经在该权限组中");
            }
            return userAuthDao.insertUserAuth(userAuth);
        } catch (Exception e) {
            LOGGER.error("为用户添加权限组失败，userAuth is: {}",userAuth,e);
            throw new RuntimeException(e);
        }
    }

    public int deleteAuthForUser(UserAuth userAuth){
        try {
            Preconditions.checkNotNull(userAuth);
            Preconditions.checkNotNull(userAuth.getUserId());
            Preconditions.checkNotNull(userAuth.getAuthId());
            return userAuthDao.deleteUserAuth(userAuth.getUserId(),userAuth.getAuthId());
        } catch (Exception e) {
            LOGGER.error("删除用户组失败，userAuth is: {}",userAuth,e);
            throw new RuntimeException(e);
        }
    }

    @Read
    public List<UserAuth> queryAuthForUser(Integer userId){
        try {
            Preconditions.checkNotNull(userId);
            List<UserAuth> result = userAuthDao.selectUserAuthByUserId(userId);
            Optional<User> userOptional = userService.queryUserById(userId);
            if(!userOptional.isPresent()){
                throw new Exception("用户不存在");
            }
            final User user = userOptional.get();
            return Lists.transform(result, new Function<UserAuth, UserAuth>() {
                public UserAuth apply(UserAuth userAuth) {
                    userAuth.setUser(user);
                    AuthGroup authGroup = authGroupDao.selectAuthGroupGById(userAuth.getAuthId());
                    userAuth.setAuthGroup(authGroup);
                    return userAuth;
                }
            });
        } catch (Exception e) {
            LOGGER.error("查询用户权限失败，userId is: {}",userId,e);
            throw new RuntimeException(e);
        }
    }

    @Read
    public List<UserAuth> queryUserInAuth(Integer authGroupId){
        try {
            Preconditions.checkNotNull(authGroupId);
            List<UserAuth> result = userAuthDao.selectUserAuthByAuthGroupId(authGroupId);
            final AuthGroup authGroup = authGroupDao.selectAuthGroupGById(authGroupId);
            return Lists.transform(result, new Function<UserAuth, UserAuth>() {
                public UserAuth apply(UserAuth userAuth) {
                    userAuth.setAuthGroup(authGroup);
                    Optional<User> userOptional = userService.queryUserById(userAuth.getUserId());
                    if(userOptional.isPresent()){
                        User user = userOptional.get();
                        userAuth.setUser(user);
                    }
                    return userAuth;
                }
            });
        } catch (Exception e) {
            LOGGER.error("查询权限组下的用户失败,group id is: {}",authGroupId,e);
            throw new RuntimeException(e);
        }
    }
}
