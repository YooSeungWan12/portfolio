package com.docmall.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CartListVO;
import com.docmall.domain.MemberVO;
import com.docmall.domain.OrderDetailVOList;
import com.docmall.domain.OrderVO;
import com.docmall.domain.ProductVO;
import com.docmall.domain.UserOrderDetailInfo;
import com.docmall.service.CartService;
import com.docmall.service.OrderService;
import com.docmall.service.ProductService;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/order/*")
@Controller
public class OrderController {

	@Inject//개별주입,  
	private ProductService p_service;
	@Inject
	private OrderService o_service;
	@Inject
	private CartService c_service;
	
	@RequestMapping("/orderInfo")
	public void orderInfo(Integer pdt_num_pk,Model model) throws Exception{
		
		ProductVO vo = p_service.get(pdt_num_pk);
		
		model.addAttribute("vo",vo);
	}
	
	
	
	@RequestMapping("/orderCartInfo")
	public void orderCartInfo(HttpSession session,Model model)throws Exception {
		//장바구니 테이블에서 사용자 상품정보를 가져와서 출력하는 작업.
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		
		List<CartListVO> list = c_service.cartList(mem_id);
		model.addAttribute("list", list);
		
	}
	
	
	
	//1)바로구매 : 상품1개 구매     2)장바구니->구매 : 상품이 여러개일경우(1개이상)
	@PostMapping("/orderProcess")
	public String orderProcess(OrderVO vo,OrderDetailVOList orderDetailList/*OrderDetailVO vo2*/ /* List<OrderDetailVO> orderDetailList*/,HttpSession session) throws Exception{
		
		
		
		
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id); // 주문자아이디 정보를 넣어줌.
		
		
		log.info(vo);
		log.info(orderDetailList);
		
		o_service.orderInfoAdd(vo, orderDetailList);
		
		
		return "redirect:/order/orderInfoResult";
	}
	
	

	
	
	@GetMapping("/orderInfoResult")
	public void orderInfoResult() {
		
	}
	
	
	
	//사용자 주문내역 주소
	@GetMapping("/userOrderInfo")
	public void userOrderInfo(HttpSession session,Model model) throws Exception{
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		
		List<OrderVO> list = o_service.userOrderInfo(mem_id);
		
		model.addAttribute("list", list);
		
	}
	
	//리턴되는 List<UserOrderDetailInfo> 타입의 값을 JSON으로 변환시키는 라이브러리 : jackson-databind(pom.xml)
	
	/*
	 * 
	 * userOderDetailInfo/8 요청 -> 리턴값을 xml
	 * userOderDetailInfo/8.json -> 리턴값을json
	 * 
	 * 
	 */
	@ResponseBody //꼭 이걸 써야 jsp로 읽지않고  json으로 사용가능.
	@GetMapping(value="/userOrderDetailInfo/{odr_code}",produces={MediaType.APPLICATION_XML_VALUE ,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<UserOrderDetailInfo>> userOrderDetailInfo(@PathVariable("odr_code") Long odr_code) throws Exception{
		
		ResponseEntity<List<UserOrderDetailInfo>> entity = null;
		
		entity = new ResponseEntity<List<UserOrderDetailInfo>>(o_service.userOrderDetailInfo(odr_code), HttpStatus.OK);

		
	
		
		return entity;
	}
	
	
	
}
