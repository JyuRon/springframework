package com.lol.clan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lol.clan.domain.BoardVO;
import com.lol.clan.domain.Criteria;

public interface BoardMapper {
	
	//@Select("select * from tbl_board where bno>0")
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(Long bno);
	
	//삭제시 1이상 숫자 반환
	public int delete(Long bno);
	
	
	//업데이트시 업데이트된 수 반환
	public int update(BoardVO board);

}
