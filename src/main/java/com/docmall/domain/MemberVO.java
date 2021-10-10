package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {

	//mem_id, mem_name, mem_pw, mem_email, mem_zipcode, mem_addr, mem_addr_d, mem_phone, mem_regdate, mem_modifydate
	
	private String mem_id;
	private String mem_name;
	private String mem_pw;
	private String mem_email;
	private String mem_zipcode;
	private String mem_addr;
	private String mem_addr_d;
	private String mem_phone;
	private Date mem_regdate;
	private Date mem_modifydate;
}
