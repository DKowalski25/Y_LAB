package dev.personal.financial.tracker.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.logging.Logger;

@Aspect
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* dev.personal.financial.tracker.controller..*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        long startTime = System.currentTimeMillis();
        logger.info(() -> String.format("--> %s.%s() started", className, methodName));

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;

            logger.info(() -> String.format("<-- %s.%s() completed in %d ms", className, methodName, duration));
            return result;
        } catch (Exception e) {
            logger.severe(() -> String.format(
                    "! %s.%s() failed after %d ms: %s",
                    className, methodName,
                    System.currentTimeMillis() - startTime,
                    e.getMessage()
            ));
            throw e;
        }
    }
}
