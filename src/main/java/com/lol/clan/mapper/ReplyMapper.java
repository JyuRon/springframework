package com.lol.clan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lol.clan.domain.Criteria;
import com.lol.clan.domain.ReplyVO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	public ReplyVO read(Long rno);
	
	public int delete(Long rno);
	
	public int update(ReplyVO reply);
	
	
	//MyBatis에서 두 개 이상의 데이터를 파라미터로 전달하기 위해서는
	//(1) 별도의 객체로 구성
	//(2) Map을 이용하는 방식
	//(3) @Param을 이용하는 방식
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);

}
