package com.lol.clan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.clan.domain.SampleVO;
import com.lol.clan.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample2")
@Log4j
public class SampleController2 {
	
	
	//produces : 메소드가 생산하는 MIME타입
	@GetMapping(value="/getText" ,produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		log.info("MIME TYPE : "+MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요";
	}
	
	
	@GetMapping(value="/getSample", produces= {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		
		return new SampleVO(112,"스타","로드");
	}
	
	
	
	
	//리스트 형식의 반환
	@GetMapping(value="/getList")
	public List<SampleVO> getList(){
		
		return IntStream.range(1,10).mapToObj(i->new SampleVO(i,i+" First",i+" Last")).collect(Collectors.toList());
	}
	
	
	
	//Map형식의 반환
	//키값이 포함되어 표시
	@GetMapping(value="/getMap")
	public Map<String, SampleVO> getMap(){
		
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111,"그루트","주니어"));
		
		return map;
	}
	
	
	//ResponseEntity Type
	//http://localhost:8080/sample2/check?height=140&weight=60
	@GetMapping(value = "/check", params= {"height","weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		
		SampleVO vo = new SampleVO(0,""+height,""+weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if(height <150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
			
		}else {
			
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
		
		return result;
	}
	
	
	//@PathVariable : 경로의 일부를 파라미터로 사용
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		
		return new String[] {"category: "+cat, "productid "+pid};
	}
	
	
	
	//@RequestBody : request의 내용을 이용해서 해당 파라미터의 타입으로 반환을 요구
	//json -> 객체로 파싱하는 개념(?)
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("convert..........ticket"+ticket);
		
		return ticket;
	}

}
