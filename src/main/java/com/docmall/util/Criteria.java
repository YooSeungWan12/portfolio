package com.docmall.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//페이징,검색정보를 저장하는 목적

@ToString
@Setter
@Getter
public class Criteria {
	
	//페이징 기능을위한 필드(변수)
	private int pageNum; // 1	2	3	4	5  클릭한 번호가 파라미터로 전송될때 사용하는용도
	private int amount; // 매 페이지마다 출력될 게시물 갯수
	
	//검색기능을 위한 필드(변수)
	private String type; //검색종류 . "T","C","W","TC","TW","TWC"
	private String keyword; //검색어
	


	public Criteria() {
		this(1,10);//또다른 생성자 메소드, 즉 아래 Criteria(int pageNum,int amount)에 (1,10)을 넣는다.
	}



	public Criteria(int pageNum, int amount) {
		
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	//검색기능에 사용할 메소드  type필드에 getter기능을 할 매소드.
	//type필드의 정보를 String[]배열로 참조하는 기능 메소드.
	public String[] getTypeArr() {
		return type == null? new String[] {} :type.split("");
		//null이면 요소가 하나도없는 배열 생성,   null이아니면, 사용자가 검색을했다는것,
		//만약 "TWC"가 온다면, new String[] {"T","W","C"}
	}

	
	
}
