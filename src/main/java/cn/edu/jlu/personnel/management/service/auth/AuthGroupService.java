package cn.edu.jlu.personnel.management.service.auth;

import cn.edu.jlu.personnel.management.dao.AuthGroupDao;
import cn.edu.jlu.personnel.management.dao.UserAuthDao;
import cn.edu.jlu.personnel.management.datasource.DataSourceContext;
import cn.edu.jlu.personnel.management.support.Read;
import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import cn.edu.jlu.personnel.management.vo.model.UserAuth;
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
public class AuthGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthGroup.class);
    @Resource
    private AuthGroupDao authGroupDao;
    @Resource
    private UserAuthDao userAuthDao;

    public int addGroup(AuthGroup authGroup){
        try {
            Preconditions.checkNotNull(authGroup);
            return authGroupDao.insertAuthGroup(authGroup);
        } catch (Exception e) {
            LOGGER.error("创建权限组失败，authGroup is: {}",authGroup);
            throw new RuntimeException(e);
        }
    }

    public int deleteGroup(AuthGroup authGroup){
        try {
            Preconditions.checkNotNull(authGroup);
            Preconditions.checkNotNull(authGroup.getGroupName());
            Preconditions.checkNotNull(authGroup.getId());
            DataSourceContext.salve();
            List<UserAuth> userAuth = userAuthDao.selectUserAuthByAuthGroupId(authGroup.getId());
            DataSourceContext.master();
            if(userAuth.size() != 0){
                throw new Exception("该权限组仍存在用户，无法删除");
            }
            return authGroupDao.deleteAuthGroup(authGroup.getGroupName());
        } catch (Exception e) {
            LOGGER.error("删除权限组失败，authGroup is: {}",authGroup,e);
            throw new RuntimeException(e);
        }
    }

    public int updateGroup(AuthGroup authGroup){
        try {
            Preconditions.checkNotNull(authGroup);
            Preconditions.checkNotNull(authGroup.getId());
            return authGroupDao.updateAuthGroup(authGroup);
        } catch (Exception e) {
            LOGGER.error("更新权限组信息失败，authGroup is: {}",authGroup);
            throw new RuntimeException(e);
        }
    }

    @Read
    public List<AuthGroup> queryAllGroup(){
        try {
            return authGroupDao.selectAuthGroups();
        } catch (Exception e) {
            LOGGER.error("查询权限组失败");
            throw new RuntimeException(e);
        }
    }
}