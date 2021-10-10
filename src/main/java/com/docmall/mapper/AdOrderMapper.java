package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;
import com.docmall.util.Criteria;

public interface AdOrderMapper {

	//페이징 , 검색포함
	public List<OrderVO> orderALLList(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<UserOrderDetailInfo> OrderDetailInfo(Long odr_code);
	
	//주문삭제
	public void orderDelete(Long odr_code);
	
	public void orderDetailDelete(Long odr_code);
	
	
	
	
	
	//개별삭제
	public void orderProductDelete(@Param("odr_code")Long odr_code,@Param("pdt_num")Long pdt_num);
	
	public void orderCountChange(Long odr_code);
	
	
	
	public void deliveryState(@Param("odr_code")Long odr_code, @Param("odr_delivery") String odr_delivery);
	
}
