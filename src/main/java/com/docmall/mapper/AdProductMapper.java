package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.util.Criteria;

public interface AdProductMapper {
	public List<CategoryVO> mainCategory();
	
	public List<CategoryVO> subCategory(Integer cate_code_pk);
	
	public void Insert(ProductVO vo);
	
	public List<ProductVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public ProductVO edit(Integer pdt_num_pk);
	
	public void editOk(ProductVO vo);
	
	public void delete(Integer pdt_num_pk);
	
	
}
