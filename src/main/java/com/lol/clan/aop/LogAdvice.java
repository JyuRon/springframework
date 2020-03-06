package com.lol.clan.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
//빈으로 등록하기 위한 설정
@Component
public class LogAdvice {
	
	
	
	@Before("execution(* com.lol.clan.service.SampleService*.*(..))")
	public void logBefore() {
		
		log.info("==========================");
	}
	
	
	@Before("execution(* com.lol.clan.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		
		log.info("str1: "+str1);
		log.info("str2: "+str2);
	}
	
	
	@AfterThrowing(pointcut = "execution(* com.lol.clan.service.SampleService*.*(..))", throwing="exception" )
	public void logException(Exception exception) {
		
		log.info("Exception.....!!!!");
		log.info("exception: "+exception);
	}
	
	
	
	
	//좀 더 구체적인 처리 방법
	//@Before를 실행하고 완료까지 걸린 시간 출력
	@Around("execution(* com.lol.clan.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		
		long start = System.currentTimeMillis();
		
		log.info("Target: "+pjp.getTarget());
		log.info("Param: " + Arrays.deepToString(pjp.getArgs()));
		
		
		//invoke method
		Object result = null;
		
		try {
			result = pjp.proceed(); //실행시키고
			
		}catch(Throwable e) {
			
			e.printStackTrace();
		}
		
		
		long end = System.currentTimeMillis();
		
		log.info("Time: " + (end-start));
		
		return result;
	}
	

}
