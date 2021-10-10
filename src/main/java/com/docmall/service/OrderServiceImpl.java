package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docmall.domain.OrderDetailVO;
import com.docmall.domain.OrderDetailVOList;
import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;
import com.docmall.mapper.CartMapper;
import com.docmall.mapper.OrderMapper;

import lombok.Setter;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Setter(onMethod_ = @Autowired)
	private OrderMapper mapper;
	
	@Setter(onMethod_ = @Autowired )
	private CartMapper mapper2;

	//트랜잭션 적용을 해야한다.
	@Transactional  // 이걸해야  하나라도 에러나면 다 롤백처리함.
	@Override
	public void orderInfoAdd(OrderVO vo, OrderDetailVOList orderDetailList) {
		
		Long odr_code = mapper.getOrderSeq(); // 시퀀스 번호를 한번 불러와서. 계속사용함.
		vo.setOdr_code(odr_code); // 주문번호 시퀀스대입
		
		
		
		//주문상세정보:상품이 여러개일경우 list갯수만큼 반복
		List<OrderDetailVO> list = orderDetailList.getOrderDetailList();
		int count  = list.size();//주문에 의한 상품주문건수
		
		vo.setOdr_count(count);
		//주문자정보
		mapper.orderAdd(vo);


		
		
		for(int i = 0 ; i<list.size();i++) {
			OrderDetailVO orderDetail = list.get(i);
			orderDetail.setOdr_code(odr_code);// 주문번호 시퀀스대입
			mapper.orderDetailAdd(orderDetail);
		}
		
		
		
		//사용자 장바구니 삭제

		mapper2.cartDeleteByUserId(vo.getMem_id());
		
		
		
		
	}

	@Override
	public List<OrderVO> userOrderInfo(String mem_id) {
		// TODO Auto-generated method stub
		return mapper.userOrderInfo(mem_id);
	}

	@Override
	public List<UserOrderDetailInfo> userOrderDetailInfo(Long odr_code) {
		// TODO Auto-generated method stub
		return mapper.userOrderDetailInfo(odr_code);
	}


}
