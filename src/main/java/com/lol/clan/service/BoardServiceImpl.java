package com.lol.clan.service;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.clan.domain.BoardVO;
import com.lol.clan.domain.Criteria;
import com.lol.clan.mapper.BoardAttachMapper;
import com.lol.clan.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j
@Service
//@AllArgsConstructor  //모든 파라미터를 이용하는 생성자 생성
public class BoardServiceImpl implements BoardService {
	
	
	//spring 4.3 이상에서 자동 주입
	//단일 파라미터를 받는 생성자의 경우
	//private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Transactional
	@Override
	public void register(BoardVO board) {
		// TODO Auto-generated method stub
		
		log.info("register...."+board);
		
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size()<=0) {
		
			return;
		}
		
		board.getAttachList().forEach(attach->{
			
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
		
		
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

	
	/* 페이징 처리로 인한 주석
	@Override
	public List<BoardVO> getList() {
		// TODO Auto-generated method stub
		
		log.info("getList............");
		return mapper.getList();
	}
	*/
	
	
	@Override
	public List<BoardVO> getList(Criteria cri) {
		// TODO Auto-generated method stub
		
		log.info("get List with criteria: "+cri);
		return mapper.getListWithPaging(cri);
	}

	
	
	//게시물 개수 검색
	//cri를 전달할 피요는 없긴 하지만 목록과 전체 데이터 개수는 항상 같이 동작하는 경우가 많기 때문에 추가
	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}


}
