package com.docmall.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductVO {

	//pdt_num_pk, cate_code, pdt_name, pdt_price, pdt_discount, pdt_company, pdt_detail, pdt_img, pdt_amount, pdt_buy, pdt_date_sub, pdt_date_up
	
	private Integer pdt_num_pk;
	private Integer cate_code; //2차카테고리 
	private Integer cate_code_prt; //1차카테고리
	private String pdt_name;
	private int pdt_price;
	private int pdt_discount;
	private String pdt_company;
	private String pdt_detail; //ckeditor내용
	private String pdt_img; //상품이미지명.첨부된 파일에서 이미지파일명을 참조
	private int pdt_amount;
	private String pdt_buy = ""; //상품진열여부 , 데이터가 안들어오면 공백
	private Date pdt_date_sub;
	private Date pdt_date_up;
	
	private MultipartFile file1; //메인이미지 파일부분(상품이미지)
	private String file2;
}
