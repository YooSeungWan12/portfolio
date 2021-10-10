package com.docmall.mapper;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.LoginDTO;
import com.docmall.domain.MemberVO;

public interface MemberMapper {

	public void join(MemberVO vo);
	
	public String checkUserID(String mem_id);
	
	public MemberVO login(LoginDTO dto);
	
	public void modify(MemberVO vo);
	
	public void delete(LoginDTO dto);
	
	public MemberVO mailConfirm(@Param("mem_id") String mem_id , @Param("mem_email") String mem_email);
	
	public void pwChange(@Param("mem_id") String mem_id , @Param("mem_pw") String mem_pw);
}
