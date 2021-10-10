package com.docmall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.docmall.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
 
  톰캣이 시작(구동)되면서 아래 message()메소드가 호출,실행
  
  com.docmall.controller 패키지의 Controller에서 진행되는 모든 jsp는 아래 model작업한 내용을 참조하게된다.
  
  
 */
@Log4j
@AllArgsConstructor
@ControllerAdvice(basePackages = {"com.docmall.controller"})
public class GlobalControllerAdvice {

	private ProductService service;
	
	
	@ModelAttribute
	public void message(Model model){
		//log.warn("톰캣시작과 함게 호출(실행됨.)");
		
		model.addAttribute("GlobalMainCategory", service.mainCategory());
		//1차 카테고리정보 model작업
	}
}
