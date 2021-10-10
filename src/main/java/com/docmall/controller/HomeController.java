package com.docmall.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.docmall.domain.ProductVO;
import com.docmall.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * Handles requests for the application home page.
 */
@Log4j
@AllArgsConstructor
@Controller
public class HomeController {
	
	private ProductService service;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		
		/*
		 main.jsp에서 보여줄 1차 카테고리에 해당하는 상품 4개씩 각각출력하는 작업
		 * */
		
		//Top카테고리에 해당하는 상품 4개출력작업
		
		List<ProductVO> hardList = service.mainProductList(1);
		List<ProductVO> softList = service.mainProductList(2);
		List<ProductVO> peripheralList = service.mainProductList(3);
		List<ProductVO> bestList = service.mainProductList(4);
		
		List<ProductVO> lastList = service.getLast();

		
		model.addAttribute("hardList", hardList );
		model.addAttribute("softList", softList );
		model.addAttribute("peripheralList", peripheralList );
		model.addAttribute("bestList",bestList  );
		model.addAttribute("lastList", lastList);
		
		return "main";
	}
	
	

}
