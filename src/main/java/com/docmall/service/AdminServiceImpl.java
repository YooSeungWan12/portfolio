package com.docmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.AdminVO;
import com.docmall.domain.ProductVO;
import com.docmall.mapper.AdminMapper;
import com.docmall.util.Criteria;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService {

	@Setter(onMethod_ = @Autowired)
	private AdminMapper mapper;

	@Override
	public AdminVO adminLogin(AdminVO vo) {
		// TODO Auto-generated method stub
		return mapper.adminLogin(vo);
	}

	@Override
	public void loginTimeUpdate(AdminVO vo) {
		// TODO Auto-generated method stub
		mapper.loginTimeUpdate(vo);
	}


	@Override
	public void changePW(AdminVO vo, String changepw) {
		// TODO Auto-generated method stub
		mapper.changePW(vo, changepw);
	}

	


}
