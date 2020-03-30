package com.lol.clan.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lol.clan.config.RootConfig;
import com.lol.clan.domain.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class})
@Log4j
public class MemberMapperTests {
	
	@Setter(onMethod_ = {@Autowired})
	private MemberMapper mapper;
	
	@Test
	public void testRead() {
		
		MemberVO vo = mapper.read("admin90");
		
		log.info(vo);
		
		vo.getAuthList().forEach(authVO->log.info(authVO));
		
		
	}
}
