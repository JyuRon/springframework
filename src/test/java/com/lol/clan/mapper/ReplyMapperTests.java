package com.lol.clan.mapper;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lol.clan.config.RootConfig;
import com.lol.clan.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class})
@Log4j
public class ReplyMapperTests {
	
	@Setter(onMethod_ = {@Autowired})
	private ReplyMapper mapper;
	
	private Long[] bnoArr = {3538950L,3538948L,3538947L,3538946L,3538945L};
	
	/*
	@Test
	public void testMapper() {
		
		log.info(mapper);
	}
	
	
	
	@Test
	public void testCreate() {
		
		IntStream.rangeClosed(1,10).forEach(i->{
			
			ReplyVO vo = new ReplyVO();
			
			//게시물의 번호
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글 테스트 "+i);
			vo.setReplyer("replyer"+i);
			
			mapper.insert(vo);
		});
	}
	
	
	@Test
	public void testRead() {
		
		Long targetRno = 5L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}
	
	
	@Test
	public void testDelete() {
		
		Long targetRno = 1L;
		
		mapper.delete(targetRno);
	}
	
	*/
	
	
	@Test
	public void testUpdate() {
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		vo.setReply("Update Reply ");
		
		int count = mapper.update(vo);
		
		log.info("UPDATE COUNT: "+count);
	}

}
