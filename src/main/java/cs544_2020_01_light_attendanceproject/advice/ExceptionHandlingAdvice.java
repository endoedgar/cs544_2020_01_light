package cs544_2020_01_light_attendanceproject.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ExceptionHandlingAdvice {

	@Around("execution(* cs544_2020_01_light_attendanceproject.controller.LocationController.*(..))")
	public Object handleException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object retVal = null;
		try {
			retVal = proceedingJoinPoint.proceed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
