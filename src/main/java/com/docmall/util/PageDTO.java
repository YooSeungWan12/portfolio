package com.docmall.util;

import lombok.Getter;
import lombok.ToString;

//페이징 기능 목적 - 페이징 로직
//jsp페이지에서 [참고1]의 형태로 출력하고자 할때 필요한 정보를 구성하는 기능

@Getter
@ToString
public class PageDTO {
	
	/*
	 한페이지에 5개씩 출력
	 전체 데이터 개수 60
	 총페이지수 = 60/5 = 12페이지.
	 61페이지면 61/5  = 13페이지...  엔드페이지는 데이터수에 따라 달라진다.
	 
	 [참고1]
	 한블락에 페이지번호를 5개씩 사용.
	 		1	2	3	4	5	[next]           - 한 block
	 [prev]	6	7	8	9	10	[next]			 - 2block
	 [prev]	11	12	13	14	15	[next] 			-3block  총페이지수가 61이면 endpage는 13.
	 
	 
	 
	 */
	
	
	private int startPage; // block에서 첫번째 페이지 정보
	private int endPage; //block에서 마지막 페이지정보
	private boolean prev,next; // 이전,다음 표시 여부를 가지고 있는 값

	private int total; // 게시판 테이블의 전체 데이터 개수
	
	private Criteria cri; // 페이징,검색정보가 저장

	//생성자 메소드
	public PageDTO(int total, Criteria cri) {
		//super();  생략가능
		
		//로직을 만들어서, 위의 4개필드에 필요한 정보를 저장한다.
		
		this.total = total;
		this.cri = cri;
		
		//1block당 페이지번호를 5개 출력할경우
		//endPage 5.0  5
		//startPage 4
		
		//1block당 페이지번호를 10개 출력할경우
		//endPage 10.0  10
		//startPage 9
		this.endPage = (int)(Math.ceil(cri.getPageNum() / 5.0))*5;    // 5.0은 페이지의 사이즈. Math.ceil은 소수부분이 하나라도있으면 올림.
		//즉 1~5까지는 무조건 endpage가 5   6~10이면  10.
		//총 데이터 갯수에따라서 위의 공식이 적용이되지않는다.(예외)
		//전체 데이터 갯수에 따라서, 실제 마지막 페이지를 구하자.
		
		this.startPage = this.endPage - 4;
		
		//total = 61.0~65.0 / 5 에 Math.ceil()을 적용하면 무조건 13
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		//int realEnd = (int) (Math.ceil((61 * 1.0) / 5));
		
		// 1block에선 false, 2block에서도 false,  3block에선 13<15라  endPage가 13으로 바뀐다.
		//아래구문에서 조건식 true인 경우에는 3block에서 endPage의 값이 정확한 값이 아니기때문에,실제 총 데이터의 총 갯수를 구하여 endPage를 구하자.
		if(realEnd <= this.endPage) {
			this.endPage = realEnd; 
		}
		
		
		this.prev = this.startPage > 1; //prev는 첫 블록에만없다. 즉 startPage가 1이상이면, 첫 블록이 아니란것이므로  1보다크면 화면에 표시.
		
		this.next = this.endPage < realEnd; 
		
		
	}
	
	

}
