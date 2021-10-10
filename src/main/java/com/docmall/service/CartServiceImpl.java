package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CartListVO;
import com.docmall.domain.CartVO;
import com.docmall.mapper.CartMapper;

import lombok.Setter;

@Service
public class CartServiceImpl implements CartService {

	@Setter(onMethod_ =@Autowired )
	private CartMapper mapper;

	@Override
	public void cartAdd(CartVO vo) {
		// TODO Auto-generated method stub
		mapper.cartAdd(vo);
	}

	@Override
	public List<CartListVO> cartList(String mem_id) {
		// TODO Auto-generated method stub
		return mapper.cartList(mem_id);
	}

	@Override
	public void cartEdit(Integer cart_code, int cart_amount) {
		// TODO Auto-generated method stub
		mapper.cartEdit(cart_code, cart_amount);
	}

	@Override
	public void cartDel(Integer cart_code) {
		// TODO Auto-generated method stub
		mapper.cartDel(cart_code);
	}

	@Override
	public void cartDeleteByUserId(String mem_id) {
		// TODO Auto-generated method stub
		mapper.cartDeleteByUserId(mem_id);
	}
}
