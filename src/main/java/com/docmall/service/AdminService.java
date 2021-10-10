package com.docmall.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.AdminVO;
import com.docmall.domain.ProductVO;
import com.docmall.util.Criteria;

public interface AdminService {

	public AdminVO adminLogin(AdminVO vo);
	public void loginTimeUpdate(AdminVO vo);
	public void changePW(AdminVO vo,String changepw);

	
}
