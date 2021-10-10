package com.docmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.domain.LoginDTO;
import com.docmall.domain.MemberVO;
import com.docmall.mapper.MemberMapper;

import lombok.Setter;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;

	@Override
	public void join(MemberVO vo) {
		// TODO Auto-generated method stub
		
		mapper.join(vo);
		
	}

	@Override
	public String checkUserID(String mem_id) {
		// TODO Auto-generated method stub
		return mapper.checkUserID(mem_id);
	}

	@Override
	public MemberVO login(LoginDTO dto) {
		// TODO Auto-generated method stub
		return mapper.login(dto);
	}

	@Override
	public void modify(MemberVO vo) {
		// TODO Auto-generated method stub
		mapper.modify(vo);
	}

	@Override
	public void delete(LoginDTO dto) {
		// TODO Auto-generated method stub
		
		mapper.delete(dto);
		
	}

	@Override
	public MemberVO mailConfirm(String mem_id, String mem_email) {
		// TODO Auto-generated method stub
		return mapper.mailConfirm(mem_id, mem_email);
	}

	@Override
	public void pwChange(String mem_id, String mem_pw) {
		// TODO Auto-generated method stub
		mapper.pwChange(mem_id, mem_pw);
	}

}
