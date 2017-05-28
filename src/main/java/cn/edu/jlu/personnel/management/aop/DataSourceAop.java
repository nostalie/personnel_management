package cn.edu.jlu.personnel.management.aop;

import cn.edu.jlu.personnel.management.datasource.DataSourceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by nostalie on 17-5-28.
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(cn.edu.jlu.personnel.management.support.Read)")
    public void switchMethod(){}

    @Around("switchMethod()")
    public Object switchSource(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            DataSourceContext.salve();
            result = joinPoint.proceed();
        } finally {
            DataSourceContext.master();
        }
        return result;
    }
}
