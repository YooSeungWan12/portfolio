package com.docmall.domain;

import lombok.Data;

@Data
public class CartVO {

	//cart_code, pdt_num_pk, mem_id, cart_amount
	
	private Long cart_code;
	private Integer pdt_num_pk;
	private String mem_id;
	private int cart_amount;
}
