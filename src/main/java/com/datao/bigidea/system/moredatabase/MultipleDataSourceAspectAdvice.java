package com.datao.bigidea.system.moredatabase;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Aspect
@Order(0)
public class MultipleDataSourceAspectAdvice {

    @Around("execution(* com.datao.bigidea.mapper.*.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();

        String markName = null;
        for(Annotation A : method.getAnnotations()){
            DataSourceCom an = (DataSourceCom) A;
            markName = an.value();
        }


        if (StringUtils.isEmpty(markName) || markName.equals("read")) {
            System.out.println("进入了one数据库 ------------------------------------");
            MultipleDataSource.setDataSourceKey("dataVSource");
        } else if (markName.equals("write")){
            System.out.println("进入了two数据库 ------------------------------------");
            MultipleDataSource.setDataSourceKey("dataTSource");
        }
        return jp.proceed();
    }

}
