package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;
import com.docmall.mapper.AdOrderMapper;
import com.docmall.util.Criteria;

import lombok.Setter;

@Service
public class AdOrderServiceImpl implements AdOrderService {

	@Setter(onMethod_ = @Autowired)
	private AdOrderMapper mapper;

	@Override
	public List<OrderVO> orderALLList(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.orderALLList(cri);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<UserOrderDetailInfo> OrderDetailInfo(Long odr_code) {
		// TODO Auto-generated method stub
		return mapper.OrderDetailInfo(odr_code);
	}

	@Transactional
	@Override
	public void orderInfoDelete(Long odr_code) {
		// TODO Auto-generated method stub
		mapper.orderDelete(odr_code);
		mapper.orderDetailDelete(odr_code);
	}

	@Transactional
	@Override
	public void orderProductDelete(Long odr_code, Long pdt_num) {
		// TODO Auto-generated method stub
		mapper.orderProductDelete(odr_code, pdt_num);
		mapper.orderCountChange(odr_code);
		
	}

	@Override
	public void deliveryState(Long odr_code, String odr_delivery) {
		// TODO Auto-generated method stub
		mapper.deliveryState(odr_code, odr_delivery);
		
	}
}
