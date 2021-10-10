package com.docmall.domain;

import java.util.List;

import lombok.Data;

@Data
public class OrderDetailVOList {

	//클라이언트에서사용할 파라미터명은 orderDetailList[0].odr_code,orderDetailList[0].pdt_num,orderDetailList[0].odr_amount,orderDetailList[0].odr_price
	//즉 info에 저렇게 name을 줘야한다.
	private List<OrderDetailVO> orderDetailList;
}
