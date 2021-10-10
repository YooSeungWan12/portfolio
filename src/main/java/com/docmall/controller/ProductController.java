package com.docmall.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.service.AdProductService;
import com.docmall.service.ProductService;
import com.docmall.util.Criteria;
import com.docmall.util.PageDTO;
import com.docmall.util.UploadFileUtils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@RequestMapping("/product/*")
@Controller
public class ProductController {

	@Resource(name = "uploadPath")
	private String uploadPath;  // 
	
	private ProductService service;
	private AdProductService a_service;
	
	
	//1차 카테고리를 선택할시
	//2차 카테고리를 가져오는 구문
	@ResponseBody
	@GetMapping(value= "/subCategory/{mainCategory}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})//값을 JSON으로 받겠다. 1차 카테고리 정보,  주소 mainCategory부분 값을 category로 받겠다.
	public ResponseEntity<List<CategoryVO>> subCategory(@PathVariable("mainCategory") Integer category){

		ResponseEntity<List<CategoryVO>> entity = null;
		
		
		//cate_code_pk값이 존재하지않아 예외 발생시 처리하는 구문
		try {
			entity = new ResponseEntity<List<CategoryVO>>(service.subCategory(category),HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<List<CategoryVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//2차 카테고리에 해당하는 상품리스트 출력
	@GetMapping(value="/list")
	public String list(@ModelAttribute("subCategory")Integer subCategory,Criteria cri,Model model) throws Exception{
		
		cri.setAmount(8);
		
		model.addAttribute("list", service.getListWithPaging(cri, subCategory));
		
		int total = service.getTotalCount(subCategory);
		
		model.addAttribute("pageMaker",new PageDTO(total,cri)); 
		
		
		return "/product/list";
		
	}
	
	//페이징,검색기능 포함
	@GetMapping("/alllist")
	public void productList(Criteria cri,Model model) throws Exception {
		

		
		cri.setAmount(8);//한 페이지에 최대 출력수
		
		
		log.info("list : "+cri); //페이징정보,검색정보 같이 출력
		
		
		//cri : PageNum=1 amount= 10
		//List<ProductVO> list = a_service.getListWithPaging(cri);
		List<ProductVO> list = service.searchList(cri);
		
		
		//테이블의 전체 데이터 개수불러오는 작업(total)
		//int total = a_service.getTotalCount(cri);
		int total = service.searchCount(cri);
		
		log.info("total : " + total);
		
		model.addAttribute("pageMaker",new PageDTO(total,cri)); //list.jsp에서 "pageMaker"란 이름으로 테이블의 모든데이터를 참조.
		
		
		model.addAttribute("list",list); //model에 넣으면  list란 이름으로 list.jsp에서 사용가능,
										//list란 이름으로 모든 테이블의 데이터 참조
	}
	
	
	
	
	//상품상세설명
		@GetMapping("get")	//modelattribute는 받고 바로jsp실행할때만 준다.	//jsp 페이지에서 Criteria의 값을 cri로 사용가능.  모델과같은 의미
		public void productDetailView(@ModelAttribute("cri") Criteria cri,@RequestParam("pdt_num") Integer pdt_num_pk,Model model) throws Exception {
			

			ProductVO vo = service.get(pdt_num_pk);
			model.addAttribute("vo", vo);

		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//이미지 파일
	@ResponseBody//이미지 해당하는부분만 받아오는거라 responseEntity  ( 페이지넘기는게아니라 값을 가져오니깐)
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		
		ResponseEntity<byte[]> entity = null;

		entity  = UploadFileUtils.getFileByte(fileName, uploadPath);

		
		return entity;
		
	}
	
	
	//페이징기능넣어보기.
	
	
	
}
	
	
	
	

