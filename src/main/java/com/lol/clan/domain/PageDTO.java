package com.lol.clan.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		//화면에 표시될 페이징 리스트(11~20)
		//페이지 올림처리
		this.endPage = (int) (Math.ceil(cri.getPageNum()/10.0))*10;
		this.startPage = this.endPage-9;
		
		
		
		//마지막 페이지 처리
		int realEnd = (int) (Math.ceil((total*1.0)/cri.getAmount()));
		
		if(realEnd<this.endPage) {
			
			this.endPage = realEnd;
			
		}
		
		
		//다음, 이전 페이지 버튼 표기를 위한 처리
		this.prev = this.startPage>1;
		this.next = this.endPage < realEnd;
		
		
	}
}
