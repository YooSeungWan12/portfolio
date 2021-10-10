package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class AdminVO {

	
	//ADMIN_ID,ADMIN_PW,LOGINTIME
	
	private String admin_id;
	private String admin_pw;
	private Date logintime;
}
