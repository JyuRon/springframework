package com.lol.clan.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan(basePackages= {"com.lol.clan.controller",
								"com.lol.clan.exception"})
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)

public class ServletConfig implements WebMvcConfigurer {
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		
		InternalResourceViewResolver bean  = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		registry.viewResolver(bean);
		
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	
	
	//파일업로드 관련 설정
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException{
		
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		
		//10MB, 한번의 Request로 전달될 수 있는 최대의 크기
		resolver.setMaxUploadSize(1024*1024*10);
		
		//2MB, 하나의 파일 최대 크기
		resolver.setMaxUploadSizePerFile(1024*1024*2);
		
		//1MB, 메모리상에서 유지하는 최대의 크기
		resolver.setMaxInMemorySize(1024*1024);
		
		//temp upload, 메모리 상에서 유지하는크기보다 클 경우 임시 파일의 형태로 저장
		resolver.setUploadTempDir(new FileSystemResource("C:\\upload\\tmp"));
		
		//업로드 파일의 이름이 한글일 경우 깨짐 방지
		resolver.setDefaultEncoding("UTF-8");
		
		return resolver;
	}
	
	
	//파일업로드 설정
	@Bean
	public MultipartResolver multipartResolver() {
		
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		
		return resolver;
	}

}
