package net.aspectj.demo.test;
import org.aspectj.lang.*;
import org.aspectj.lang.reflect.*;

public aspect TestAspect{

//pointcut testAll():within(*);
    public pointcut test(): call(* *.*Listener(..));
	pointcut testAll():cflow(call(* *.*Listener(..)));
before():testAll(){
         System.out.println("before calling: " + thisJoinPoint);
         System.out.println("      at: " + thisJoinPoint.getSourceLocation());
}
after():testAll(){
         Signature sig = thisJoinPoint.getSignature();
         if(sig instanceof ConstructorSignature){
            System.out.println("after calling: " + thisJoinPoint);
            System.out.println("      at: " + thisJoinPoint.getSourceLocation());
         }

}
 

}

