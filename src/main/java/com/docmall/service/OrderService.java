package com.docmall.service;

import java.util.List;

import com.docmall.domain.OrderDetailVOList;
import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;

public interface OrderService {
	
	public void orderInfoAdd(OrderVO vo,OrderDetailVOList orderDetailList);
	
	public List<OrderVO> userOrderInfo(String mem_id);
	
	public List<UserOrderDetailInfo> userOrderDetailInfo(Long odr_code);

}
