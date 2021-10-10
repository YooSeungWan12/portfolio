package com.docmall.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.OrderVO;
import com.docmall.domain.UserOrderDetailInfo;
import com.docmall.service.AdOrderService;
import com.docmall.util.Criteria;
import com.docmall.util.PageDTO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor // 아래 클래스의 필드를 이용하여, 파라미터 생성자메서드를 자동생성해줌. 그리고 파라미터 생성자메서드는 자동으로 주입되도록 스프링에서 설계되어 있다.
@RequestMapping("/admin/order/*")
@Controller
public class AdOrderController {

	private AdOrderService service;
	
	@GetMapping("/list")
	public void orderlist(Criteria cri, Model model) throws Exception{
		
		cri.setAmount(2); //2개만 표현
		
		List<OrderVO> list = service.orderALLList(cri);
		
		int total = service.getTotalCount(cri);
		
		model.addAttribute("pageMaker", new PageDTO(total, cri)); 
		model.addAttribute("list", list); 
	}
	
	@ResponseBody
	@GetMapping(value =  "/OrderDetailInfo/{odr_code}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<UserOrderDetailInfo>> userOrderDetailInfo(@PathVariable("odr_code") Long odr_code) throws Exception{
		
		
		ResponseEntity<List<UserOrderDetailInfo>> entity = null;
		entity = new ResponseEntity<List<UserOrderDetailInfo>>(service.OrderDetailInfo(odr_code), HttpStatus.OK);
		
						
		return entity;
	}
	
	
	//주문개별삭제
	@ResponseBody //꼭 이걸 써야 jsp로 읽지않고  json으로 사용가능.
	@PostMapping(value="/orderProductDelete")
	public ResponseEntity<String> orderinfoDelete(Long odr_code,Long pdt_num) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		service.orderProductDelete(odr_code, pdt_num);
		
		entity = new ResponseEntity<String>("success",HttpStatus.OK);

		
	
		
		return entity;
	}
	
	
	
	//주문삭제
	@PostMapping("/delete")
	public String delete(Criteria cri,@RequestParam("odr_code") Long odr_code ,RedirectAttributes rttr) {
		
		//삭제기능작업
		
		service.orderInfoDelete(odr_code);
		
		
		rttr.addFlashAttribute("msg", "deleteOk");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		
		return "redirect:/admin/order/list"; // /admin/order/list?pageNum=1&amount=5&type=M&Keyword=테스트
	}
	
	
	//상태변경
	@ResponseBody
	@PostMapping("/deliverystatechange")
	public ResponseEntity<String> deliverystatechange(Long odr_code,String odr_delivery) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		service.deliveryState(odr_code, odr_delivery);
		
		entity = new ResponseEntity<String>("success",HttpStatus.OK);

		
	
		
		return entity;
	}
	
	
	//선택된 항목의 상태변경, ajax로 넘어오는 파라미터가 배열로 되어있다.
		@ResponseBody
		@PostMapping("/deliveryChangeChecked")
		public ResponseEntity<String> deliveryChangeChecked(@RequestParam("odr_codeArr[]")List<Long> odr_codeArr,@RequestParam("odrDeliveryStateArr[]")List<String> odrDeliveryStateArr) throws Exception{
			

			log.info(odr_codeArr);
			log.info(odrDeliveryStateArr);
			
			ResponseEntity<String> entity = null;
			
			for(int i = 0 ; i < odr_codeArr.size(); i++) {
			service.deliveryState(odr_codeArr.get(i), odrDeliveryStateArr.get(i));
			}
			entity = new ResponseEntity<String>("success",HttpStatus.OK);

			
		
			
			return entity;
		}
	
	
}
