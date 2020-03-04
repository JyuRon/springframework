package com.lol.clan.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.lol.clan.domain.BoardVO;
import com.lol.clan.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
@AllArgsConstructor  //모든 파라미터를 이용하는 생성자 생성
public class BoardServiceImpl implements BoardService {
	
	
	//spring 4.3 이상에서 자동 주입
	//단일 파라미터를 받는 생성자의 경우
	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		// TODO Auto-generated method stub
		
		log.info("register...."+board);
		
		mapper.insertSelectKey(board);
		
	}

	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		
		log.info("get.........."+bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		// TODO Auto-generated method stub
		
		log.info("modify...." + board);
		return mapper.update(board)==1;
	}

	@Override
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		
		log.info("remove...." + bno);
		return mapper.delete(bno)==1;
	}

	@Override
	public List<BoardVO> getList() {
		// TODO Auto-generated method stub
		
		log.info("getList............");
		return mapper.getList();
	}

}
