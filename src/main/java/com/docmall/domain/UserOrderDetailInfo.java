package com.docmall.domain;

import lombok.Data;

@Data
public class UserOrderDetailInfo {

	//od.odr_code, od.pdt_num, od.odr_amount, od.odr_price,p.pdt_name, p.pdt_img
	private Long odr_code;
	private Long pdt_num;
	private int odr_price;
	private int odr_amount;
	private String pdt_name;
	private String pdt_img;
}
