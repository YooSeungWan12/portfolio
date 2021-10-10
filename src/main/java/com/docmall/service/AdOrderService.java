package com.docmall.service;

import java.util.List;

import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;
import com.docmall.util.Criteria;

public interface AdOrderService {

	public List<OrderVO> orderALLList(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<UserOrderDetailInfo> OrderDetailInfo(Long odr_code);
	
	
	//주문삭제
	public void orderInfoDelete(Long odr_code);
	
	//주문내역중 개별상품삭제
	public void orderProductDelete(Long odr_code,Long pdt_num);
	
	
	public void deliveryState(Long odr_code, String odr_delivery);
}
