package com.lol.clan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lol.clan.domain.SampleDTO;
import com.lol.clan.domain.SampleDTOList;
import com.lol.clan.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	//GET, POST, PUT, DELETE
	
	@RequestMapping("")
	public void baic() {
		
		log.info("basic..............");
	}

	
	@RequestMapping(value="/basic", method = {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		
		log.info("basic get..............");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		
		log.info("basic get only get...............");
	}
	
	
	
	//DTO 테스트
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		
		//url + ?name=aa&age=20
		log.info(""+dto);
		
		return "ex01";
	}
	
	
	//파라미터 수집, 변환
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age" )int age) {
		
		//url + ?name=aa&age=20
		log.info("name: " + name);
		log.info("age: "+age);
		
		return "ex02";
	}
	
	
	//리스트
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids")ArrayList<String> ids) {
		
		//url + ?ids=111&ids=222
		log.info("ids: "+ids);
		
		return "ex02List";
	}

	
	
	//배열
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		
		//url + ?ids=111&ids=222
		log.info("array ids: "+Arrays.deepToString(ids));
		
		return "ex02Array";
	}
	
	
	//객체 리스트
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		
		
		//encodeURIComponent로 해결한 경우 : url + ?list[0].name=aaa&list[2].name=bbb
		//list%5B0%5D.name=aaa&list%5B1%5D.name=bbb&%5B2%5D.name=ccc
		log.info("list dtos: "+ list);
		
		return "ex02Bean";
	}
	
	
	//binding : 파라미터를 수집하는 다른 단어
	//InitBinder : '2018-01-01'과 같은 문자열을 java.util.Date 타입으로 변환하는 과정
	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dataFormat, false));
	}
	

	
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		
		
		//url + ?title=test&dueDate=2018-01-01
		
		
		//다른 방식의 날짜 변환
		//@DataTimeFormat
		//url + ??title=test&dueDate=2018/01/01
		log.info("todo: "+todo);
		return "ex03";
	}
	
	
	
	
	//Model
	public String home(Model model) {
		
		model.addAttribute("ServerTime",new java.util.Date());
		
		return "home";
	}
	
	
	
	//@ModelAttribute
	//view로 뿌릴 경우 객체 or 모델만이 가능하다.
	//@RequestParam 사용시 출력되지 않는다.
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		
		
		//?name=aaa&age=11&page=9
		log.info("dto : "+dto);
		log.info("page: " + page);
		
		return "/sample/ex04";
	}
	
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * Controller Return Type
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
	//void : 해당 URL의 경로를 그대로 JSP파일의 이름으로 사용
	//ServletConfig.java의 설정 때문이다.
	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05..........");
	}
	
	
	
	//String : return의 값이 jsp파일 명
	//redirect, forward방식이 사용될 수 있다.
	
	
	
	//객체 타입(JSON)
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		
		log.info("/ex06,,,,,,,,,,,,,");
		
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
	}
	
	
	
	//ResponseEntity 타입
	//HTTP Protocol Header
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		
		log.info("/ex07,,,,,,,,,,");
		
		// {"name": "홍길동"}
		String msg = "{\"name\" : \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
		
	
	/*
	 * 
	 * 
	 * 파일 업로드
	 * 
	 * 
	 */
	
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload,,,,,,,,,,,,");
	}
	
	
	
	//최종적으로 업로드 하기위해서는 byte[]처리를 해야 한다.
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		
		files.forEach(file->{
			log.info("------------------------------");
			log.info("name: "+file.getOriginalFilename());
			log.info("size: "+file.getSize() );
			
		});
	}
	
	
	
	/*
	 * 
	 * 
	 * Security
	 * 
	 * 
	 */
	@GetMapping("/all")
	public void doAll() {
		
		log.info("do all can access everybody");
	}
	
	
	@GetMapping("/member")
	public void doMember() {
		
		log.info("logined member");
	}
	
	
	@GetMapping("/admin")
	public void doAdmin() {
		
		log.info("admin only");
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doMember2() {
		
		log.info("logined annotation member");
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		log.info("admin annotation only");
	}
	
	

}
