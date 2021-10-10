package com.docmall.mapper;

import java.util.List;

import com.docmall.domain.OrderDetailVO;
import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;

public interface OrderMapper {
	
	//주문하기(주문정보추가 , 주문상세정보추가) 
	
	
	//주문정보추가(주문자 관련정보)
	public void orderAdd(OrderVO vo);
	
	
	//주문상세정보추가(주문상품관련정보)
	public void orderDetailAdd(OrderDetailVO vo);
	
	public Long getOrderSeq();
	
	public List<OrderVO> userOrderInfo(String mem_id);
	
	public List<UserOrderDetailInfo> userOrderDetailInfo(Long odr_code);
}
