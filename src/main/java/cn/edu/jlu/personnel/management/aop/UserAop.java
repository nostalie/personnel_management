package cn.edu.jlu.personnel.management.aop;

import cn.edu.jlu.personnel.management.dao.LogDao;
import cn.edu.jlu.personnel.management.enums.OperationType;
import cn.edu.jlu.personnel.management.service.info.UserService;
import cn.edu.jlu.personnel.management.vo.model.Log;
import cn.edu.jlu.personnel.management.vo.model.User;
import cn.edu.jlu.personnel.management.web.interceptor.IdentityInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.apache.ibatis.session.RowBounds;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by nostalie on 17-5-28.
 */
@Aspect
@Component
public class UserAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAop.class);

    @Resource
    private UserService userService;
    @Resource
    private LogDao logDao;

    @Pointcut("execution( * cn.edu.jlu.personnel.management.web.controller.UserController.addUser(..))")
    public void addUser(){}

    @Pointcut("execution( * cn.edu.jlu.personnel.management.web.controller.UserController.deleteUser(..))")
    public void deleteUser(){}

    @Pointcut("execution( * cn.edu.jlu.personnel.management.web.controller.UserController.updateUser(..))")
    public void updateUser(){}

    @Before("addUser()")
    public void addUser(JoinPoint joinPoint){
        LOGGER.debug("进入aop");
        Object[] args = joinPoint.getArgs();
        User user = null;
        for(Object arg : args){
            if(arg instanceof User){
                user = (User) arg;
                break;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String oldContent = "";
            String newContent = mapper.writeValueAsString(user);
            Log log = new Log();
            log.setUserName(IdentityInterceptor.USER_NAME.get());
            log.setOperationType(OperationType.ADD);
            log.setOperationTime(new Date());
            log.setNewContent(newContent);
            log.setOldContent(oldContent);
            logDao.insertLog(log);
        } catch (Exception e) {
            LOGGER.error("审计日志序列化失败",e);
            throw new RuntimeException(e);
        }
    }

    @Before("deleteUser()")
    public void deleteUser(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String userName = "";
        for(Object arg : args){
            if(arg instanceof String){
                userName = (String) arg;
                break;
            }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            User query = new User();
            query.setUserName(userName);
            List<User> users = userService.queryUsers(query, new RowBounds(0, 1));
            if(users.size() == 1){
                User user = users.get(0);
                String oldContent = mapper.writeValueAsString(user);
                String newContent = "";
                Log log = new Log();
                log.setUserName(IdentityInterceptor.USER_NAME.get());
                log.setOperationType(OperationType.DELETE);
                log.setOperationTime(new Date());
                log.setNewContent(newContent);
                log.setOldContent(oldContent);
                logDao.insertLog(log);
            }
        } catch (Exception e) {
            LOGGER.error("审计日志序列化失败",e);
            throw new RuntimeException(e);
        }
    }

    @Before("updateUser()")
    public void updateUser(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        User newUser = null;
        for(Object arg : args){
            if(arg instanceof User){
                newUser = (User) arg;
                break;
            }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String newContent = mapper.writeValueAsString(newUser);
            Optional<User> userOptional = userService.queryUserById(newUser.getId());
            if(userOptional.isPresent()){
                User oldUser = userOptional.get();
                String oldContent = mapper.writeValueAsString(oldUser);
                Log log = new Log();
                log.setUserName(IdentityInterceptor.USER_NAME.get());
                log.setOperationType(OperationType.DELETE);
                log.setOperationTime(new Date());
                log.setNewContent(newContent);
                log.setOldContent(oldContent);
                logDao.insertLog(log);
            }
        } catch (Exception e) {
            LOGGER.error("审计日志序列化失败",e);
            throw new RuntimeException(e);
        }
    }
}
