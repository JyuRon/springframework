package com.lol.clan.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

//페이징 관련 클래스
public class Criteria {
	
	private int pageNum;
	private int amount;
	
	
	//검색조건 추가
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	
	//검색 타입 분류
	
	//BoardMapper.xml에서 collection="typeArr" 부분에 해당
	//스프링에서 알아서 collection에 해당하는  get,set메소드 탐색
	public String[] getTypeArr() {
		
		return type == null? new String[] {} : type.split("");
	}
	

}
