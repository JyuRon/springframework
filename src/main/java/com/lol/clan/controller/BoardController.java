package com.lol.clan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lol.clan.domain.BoardVO;
import com.lol.clan.domain.Criteria;
import com.lol.clan.domain.PageDTO;
import com.lol.clan.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
	
	/* 
	 * 페이징 처릴 위한 주석
	 * 
	@GetMapping("/list")
	public void list(Model model) {
		
		log.info("list");
		
		model.addAttribute("list",service.getList());
	}
	*/
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list: "+cri);
		
		model.addAttribute("list",service.getList(cri));
		//model.addAttribute("pageMaker",new PageDTO(cri,123));
		
		
		//게시물 개수 검색
		//cri를 전달할 피요는 없긴 하지만 목록과 전체 데이터 개수는 항상 같이 동작하는 경우가 많기 때문에 추가
		int total = service.getTotal(cri);
		
		log.info("total: "+total);
		
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	
	
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		
		log.info("=======================");
		
		log.info("register: "+board);
		
		
		if(board.getAttachList() != null) {
			
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		service.register(board);
		
		rttr.addFlashAttribute("result",board.getBno());
		
		return "redirect:/board/list";
		
	}
	
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	
	
	
	//@ModelAttribute : controller에서 화면으로 객체는 전달이 되지만 좀더 명시적으로 이름을 지정하기 위함
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri ,Model model) {
		
		log.info("/get or modify");
		model.addAttribute("board",service.get(bno));
	}
	
	
	
	//@ModelAttribute : controller에서 화면으로 객체는 전달이 되지만 좀더 명시적으로 이름을 지정하기 위함
	@PostMapping("/modify")
	public String modify(BoardVO board, /*@ModelAttribute("cri") */Criteria cri, RedirectAttributes rttr) {
		
		log.info("modify: "+ board);
		
		if(service.modify(board)) {
			
			rttr.addFlashAttribute("result","success");
		}
		
		/*
		 * 
		 * UriComponentsBuilder로 인한 수정
		 * 
		 * 
		//v페이징 번호를 유지
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		
		*/
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	
	
	

	//@ModelAttribute : controller에서 화면으로 객체는 전달이 되지만 좀더 명시적으로 이름을 지정하기 위함
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, /*@ModelAttribute("cri") */Criteria cri, RedirectAttributes rttr) {
		
		log.info("remove..." + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		
		/*
		 * 
		 * UriComponentsBuilder로 인한 수정
		 * 
		 * 
		
		//페이징 번호를 유지
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		
		*/
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	
	
	

}
