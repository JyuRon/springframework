package com.lol.clan.service;

import java.util.List;

import com.lol.clan.domain.BoardAttachVO;
import com.lol.clan.domain.BoardVO;
import com.lol.clan.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board);
	
	public BoardVO get(Long bno);
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long bno);
	
	//public List<BoardVO> getList();
	
	public List<BoardVO> getList(Criteria cri);
	
	
	//전체 게시물 개수 
	//cri를 전달할 피요는 없긴 하지만 목록과 전체 데이터 개수는 항상 같이 동작하는 경우가 많기 때문에 추가
	public int getTotal(Criteria cri);

	
	
	//게시물의 조회와 첨부 파일
	public List<BoardAttachVO> getAttachList(Long bno);
}
