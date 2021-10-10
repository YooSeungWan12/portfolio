package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.mapper.ProductMapper;
import com.docmall.util.Criteria;

import lombok.Setter;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Setter(onMethod_ = @Autowired)
	private ProductMapper mapper;

	@Override
	public List<CategoryVO> mainCategory() {
		// TODO Auto-generated method stub
		return mapper.mainCategory();
	}

	@Override
	public List<CategoryVO> subCategory(Integer cate_code_pk) {
		// TODO Auto-generated method stub
		return mapper.subCategory(cate_code_pk);
	}

	@Override
	public List<ProductVO> getListWithPaging(Criteria cri, Integer cate_code) {
		// TODO Auto-generated method stub
		return mapper.getListWithPaging(cri, cate_code);
	}

	@Override
	public int getTotalCount(Integer cate_code) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(cate_code);
	}

	//직접구매시 보여주는 상품1개의 내역
	@Override
	public ProductVO get(Integer pdt_num_pk) {
		// TODO Auto-generated method stub
		return mapper.get(pdt_num_pk);
	}

	@Override
	public List<ProductVO> mainProductList(Integer cate_code_prt) {
		// TODO Auto-generated method stub
		return mapper.mainProductList(cate_code_prt);
	}

	@Override
	public List<ProductVO> getLast() {
		// TODO Auto-generated method stub
		return mapper.getLast();
	}

	@Override
	public List<ProductVO> searchList(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.searchList(cri);
	}

	@Override
	public int searchCount(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.searchCount(cri);
	}








}
