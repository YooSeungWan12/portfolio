package com.docmall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.domain.MemberVO;
import com.docmall.domain.ReviewVO;
import com.docmall.service.ReviewService;
import com.docmall.util.Criteria;
import com.docmall.util.PageDTO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController // ajax위주로 쓸거라 restController를 써서  @ResponseBody를 자주안써도된다.
@Log4j
@AllArgsConstructor
@RequestMapping("/review/*")
public class ReviewController {

	private ReviewService service;
	
	@PostMapping("/write")
	public ResponseEntity<String> write(ReviewVO vo,HttpSession session) {
		
		
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		vo.setMem_id(mem_id);
		
		log.info(vo);
		
		
		ResponseEntity<String> entity = null;
		
		service.write(vo);
		
		entity = new ResponseEntity<String>("success",HttpStatus.OK);
		
		return entity;
		
	}
	
	
	
	
	@PostMapping("/modify")
	public ResponseEntity<String> modify(ReviewVO vo) {
		
		//후기내용,별점수정내용 조건식 : 후기번호
		log.info(vo);
		
		
		ResponseEntity<String> entity = null;
		
		service.modify(vo);
		
		entity = new ResponseEntity<String>("success",HttpStatus.OK);
		
		return entity;
		
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> delete(int rv_num) {
		ResponseEntity<String> entity = null;
		
		service.delete(rv_num);
		
		entity = new ResponseEntity<String>("success",HttpStatus.OK);
		
		return entity;
		
	}
	
	
	
	@GetMapping(value="/{pdt_num}/{page}",produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})//bno값이 bno에  page값이 page에 들어가게생성.
	public ResponseEntity<Map<String,Object>> listPage(@PathVariable("pdt_num") int pdt_num,@PathVariable("page") Integer page){
		
		ResponseEntity<Map<String,Object>> entity = null;
			
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		
		//1)후기목록작업
		Criteria cri = new Criteria(page , 5); //pageNum을위해,   리플을 5개만 표시.
		//cri.setPageNum(page); // 받은값을 setter
		List<ReviewVO> list = service.getListWithPaging(cri, pdt_num);
		
		//2)페이징정보
		int total = service.getCountBypdtnum(pdt_num); //총페이지수
		PageDTO pageMaker = new PageDTO(total, cri);
		
		//map컬렉션에 2개의정보 추가
		map.put("list", list); //상품후기
		map.put("pageMaker", pageMaker); // 페이징정보
		
		entity = new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
		return entity;
	}
	
	
	
}
