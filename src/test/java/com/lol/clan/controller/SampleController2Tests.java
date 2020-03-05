package com.lol.clan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.lol.clan.config.RootConfig;
import com.lol.clan.config.ServletConfig;
import com.lol.clan.domain.Ticket;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//WebApplicationContext를 사용하기 위함
@WebAppConfiguration  

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class,
								ServletConfig.class})
@Log4j
public class SampleController2Tests {
	
	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	
	@Test
	public void testConvert() throws Exception{
		
		Ticket ticket = new Ticket();
		ticket.setTno(123);
		ticket.setOwner("Admin");
		ticket.setGrade("AAA");
		
		String jsonStr = new Gson().toJson(ticket);
		
		
		log.info(jsonStr);
		
		
		//json형태의 내용을 /sample/ticket으로 요청
		mockMvc.perform(post("/sample2/ticket")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr))
				.andExpect(status().is(200));
	}

}
