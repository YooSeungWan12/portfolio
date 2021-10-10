package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.ReviewVO;
import com.docmall.mapper.ReviewMapper;
import com.docmall.util.Criteria;

import lombok.Setter;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Setter(onMethod_ = @Autowired)
	private ReviewMapper mapper;

	@Override
	public void write(ReviewVO vo) {
		// TODO Auto-generated method stub
		mapper.write(vo);
	}

	@Override
	public List<ReviewVO> getListWithPaging(Criteria cri, int pdt_num) {
		// TODO Auto-generated method stub
		return mapper.getListWithPaging(cri, pdt_num);
	}

	@Override
	public int getCountBypdtnum(int pdt_num) {
		// TODO Auto-generated method stub
		return mapper.getCountBypdtnum(pdt_num);
	}

	@Override
	public void modify(ReviewVO vo) {
		// TODO Auto-generated method stub
		mapper.modify(vo);
	}

	@Override
	public void delete(int rv_num) {
		// TODO Auto-generated method stub
		mapper.delete(rv_num);
	}
}
