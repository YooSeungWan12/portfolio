package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {
	
	//odr_code, mem_id, odr_name, odr_zipcode, odr_addr, odr_addr_d, odr_phone, odr_total_price, odr_date 
	
	private Long odr_code;
	private String mem_id;
	private String odr_name;
	private String odr_zipcode;
	private String odr_addr;
	private String odr_addr_d;
	private String odr_phone;
	private int odr_total_price;
	private Date odr_date;
	private int odr_count;
	private String odr_delivery;
}
