package com.docmall.service;

import com.docmall.domain.LoginDTO;
import com.docmall.domain.MemberVO;

public interface MemberService {

	public void join(MemberVO vo);
	
	public String checkUserID(String mem_id);
	
	public MemberVO login(LoginDTO dto);
	
	public void modify(MemberVO vo);
	
	public void delete(LoginDTO dto);
	
	public MemberVO mailConfirm(String mem_id ,String mem_email);
	
	public void pwChange(String mem_id ,String mem_pw);
}
