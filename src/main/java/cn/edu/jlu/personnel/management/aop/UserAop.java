package cn.edu.jlu.personnel.management.aop;

import cn.edu.jlu.personnel.management.vo.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by nostalie on 17-5-28.
 */
@Aspect
public class UserAop {

    @Pointcut("execution( * cn.edu.jlu.personnel.management.web.controller.UserController.addUser(..))")
    public void addUser(){}

    @Pointcut("execution( * cn.edu.jlu.personnel.management.web.controller.UserController.deleteUser(..))")
    public void deleteUser(){}

    @Pointcut("execution( * cn.edu.jlu.personnel.management.web.controller.UserController.updateUser(..))")
    public void updateUser(){}

    @Before("addUser()")
    public void addUser(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        User user = null;
        for(Object arg : args){
            if(arg instanceof User){
                user = (User) arg;
                break;
            }
        }

    }
}
