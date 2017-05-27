package com.aspectj.demo.test;

import android.util.Log;  
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Before;  
import org.aspectj.lang.annotation.Pointcut;  
import org.aspectj.lang.JoinPoint;  
   
@Aspect     
public class LogAspectj {  
    static final String TAG = "LogAspectj";  

    @Pointcut("execution(* *..Activity.on*(..))") 
public void logForActivity(){};  
   

    @Before("logForActivity()")  
    public void log(JoinPoint joinPoint){  
       Log.e(TAG, joinPoint.toShortString());  
    }  
}  