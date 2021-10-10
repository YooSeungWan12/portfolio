package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.ReviewVO;
import com.docmall.util.Criteria;

public interface ReviewMapper {

	public void write(ReviewVO vo);
	
	public List<ReviewVO> getListWithPaging(@Param("cri") Criteria cri,@Param("pdt_num") int pdt_num);
	
	
	public int getCountBypdtnum(int pdt_num); 
	
	public void modify(ReviewVO vo);
	
	public void delete(int rv_num);
}
