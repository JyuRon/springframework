package com.lol.clan.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;


import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	
	

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		
		//404페이지 호출 설정
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		
		//첨부파일 설정
		MultipartConfigElement multipartConfig = new MultipartConfigElement("C:\\upload\\temp",20971520,41943040,20971520);
		registration.setMultipartConfig(multipartConfig);

	}
	
	
	
	//한글 꺠짐 해결
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		
		return new Filter[] {characterEncodingFilter};

	}
	
	
	
  
}
