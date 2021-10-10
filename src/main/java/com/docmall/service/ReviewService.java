package com.docmall.service;

import java.util.List;

import com.docmall.domain.ReviewVO;
import com.docmall.util.Criteria;

public interface ReviewService {

	public void write(ReviewVO vo);
	
	public List<ReviewVO> getListWithPaging(Criteria cri,int pdt_num);
	
	
	public int getCountBypdtnum(int pdt_num); 
	
	public void modify(ReviewVO vo);
	
	public void delete(int rv_num);
}
