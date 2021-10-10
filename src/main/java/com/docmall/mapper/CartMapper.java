package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.CartListVO;
import com.docmall.domain.CartVO;

public interface CartMapper {

	
	public void cartAdd(CartVO vo);
	
	public List<CartListVO> cartList(String mem_id);
	
	public void cartEdit(@Param("cart_code") Integer cart_code, @Param("cart_amount") int cart_amount);
	
	public void cartDel(Integer cart_code);
	
	public void cartDeleteByUserId(String mem_id);
}
