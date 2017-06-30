package com.aspectj.test.demo;
import org.aspectj.lang.*;
import org.aspectj.lang.reflect.*;
import com.teleca.*;
public aspect TestAspectJ{

	public pointcut test(): call(* *.*Listener(..));
before():test(){
         System.out.println("before calling: " + thisJoinPoint);
         System.out.println("      at: " + thisJoinPoint.getSourceLocation());
}
after():test(){
         Signature sig = thisJoinPoint.getSignature();
         if(sig instanceof ConstructorSignature){
            System.out.println("after calling: " + thisJoinPoint);
            System.out.println("      at: " + thisJoinPoint.getSourceLocation());
         }

}
}
