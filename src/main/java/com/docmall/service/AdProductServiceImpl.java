package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.mapper.AdProductMapper;
import com.docmall.util.Criteria;

import lombok.Setter;

@Service
public class AdProductServiceImpl implements AdProductService {

	@Setter(onMethod_ =@Autowired )
	private AdProductMapper mapper;

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
	public void Insert(ProductVO vo) {
		// TODO Auto-generated method stub
		mapper.Insert(vo);
	}

	@Override
	public List<ProductVO> getListWithPaging(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(cri);
	}

	@Override
	public ProductVO edit(Integer pdt_num_pk) {
		// TODO Auto-generated method stub
		return mapper.edit(pdt_num_pk);
	}

	@Override
	public void editOk(ProductVO vo) {
		// TODO Auto-generated method stub
		mapper.editOk(vo);
	}

	@Override
	public void delete(Integer pdt_num_pk) {
		// TODO Auto-generated method stub
		mapper.delete(pdt_num_pk);
	}
}
