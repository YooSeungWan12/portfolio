package com.docmall.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.service.AdProductService;
import com.docmall.util.Criteria;
import com.docmall.util.PageDTO;
import com.docmall.util.UploadFileUtils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

//관리자 : 상품관리기능

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/admin/product/*")
public class AdProductController {
	
	private AdProductService service;
	
	
	
	//업로드되는 웹프로젝트 영역의 외부에 파일을 저장할 경로 정보 주입
	//servlect-context.xml에서 가져옴
	
	@Resource(name = "uploadPath")
	private String uploadPath;  // 
	
	//상품 등록폼
	@GetMapping("/insert")
	public void productInsert(Model model) {
			
		model.addAttribute("mainCategory", service.mainCategory());
	}
	
	
	
	@PostMapping("/insert")
	public String prodctInsert(ProductVO vo, RedirectAttributes rttr) throws Exception {
		
		
		vo.setPdt_img(UploadFileUtils.uploadFile(uploadPath,vo.getFile1().getOriginalFilename(), vo.getFile1().getBytes()));
		//com.docmall.util에서 만든것 사용
		
		//<input type="checkbox"> ->선택시 "on"값을받음
		//선택을안할경우 널로 인하여 에러발생됨, 널처리해야함.
		if(vo.getPdt_buy().equals("on")) {
			vo.setPdt_buy("Y");
		}else {
			vo.setPdt_buy("N");
		}
		
		
		//log.info(vo.toString());
		//상품작업저장
		service.Insert(vo);
		rttr.addFlashAttribute("msg", "insertOk");
		
		return "redirect:/admin/product/list"; //아래 list실행
	}
	
	//상품 조회,상품수정폼 페이지.
	@GetMapping(value= {"get","edit"})	//modelattribute는 받고 바로jsp실행할때만 준다.	//jsp 페이지에서 Criteria의 값을 cri로 사용가능.  모델과같은 의미
	public void productEdit(@ModelAttribute("cri") Criteria cri,@RequestParam("pdt_num") Integer pdt_num_pk,Model model) throws Exception {
		//pdt_num의 값을 pdt_num_pk로 사용.


		ProductVO vo = service.edit(pdt_num_pk); ////1차 카테고리정보,2차카테고리정보가 들어있다.
		//1차카테고리 정보
		model.addAttribute("mainCategory", service.mainCategory());
		
		Integer categoryCode = vo.getCate_code_prt();
		//1차 카테고리를 참조하는 2차카테고리 목록
		List<CategoryVO> subCategory = service.subCategory(categoryCode);
		model.addAttribute("subCategory", subCategory);
		
		
		
		model.addAttribute("productVO", vo);
	
		//현재상품의 2차 카테고리를

		//ProductVO vo = service.edit(pdt_num_pk);
		//model.addAttribute("vo", service.edit(pdt_num_pk));
		//model.addAttribute(service.edit(pdt_num_pk));  // "productVO" -> jsp에서 참조하는 이름
		//model.addAttribute("productVO", service.edit(pdt_num_pk));가 된다.

	}
	
	
	
	@PostMapping("/edit")  //insert update delete는 model이 필요없다(바로넘어가서)
	public String prodctEdit(Criteria cri,/*@RequestParam("pdt_num_pk") Integer pdt_num_pk ,*/ ProductVO vo, RedirectAttributes rttr) throws Exception {
		
		/*
		 
		 rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());를 위해 cri를 사용,
		 
		 
		 * */

		
		
		//<input type="checkbox"> ->선택시 "on"값을받음
		//선택을안할경우 널로 인하여 에러발생됨, 널처리해야함.
		if(vo.getPdt_buy().equals("on")) {
			vo.setPdt_buy("Y");
		}else {
			vo.setPdt_buy("N");
		}
		
		
		
		//상품정보 수정작업
		
		//상품 이미지가 변경된 경우에 진행되는 작업
		if(vo.getFile1().getSize() >0) { //파일이 존재하면
			
//			//이미지 삭제
//			new File(uploadPath + vo.getFile2()).delete();
//			String front = vo.getFile2().substring(0, 12); // 날짜(폴더)명 /2021/08/24/
//			String end = vo.getFile2().substring(14); // s_를 땐 파일이름. 7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg
//			
//			//썸네일 이미지파일 삭제
//			new File(uploadPath + (front + end).replace('/',File.separatorChar)).delete();
			
			UploadFileUtils.deleteFile(uploadPath,vo.getFile2());
			
			//변경 이미지 업로드 작업
			vo.setPdt_img(UploadFileUtils.uploadFile(uploadPath,vo.getFile1().getOriginalFilename(), vo.getFile1().getBytes()));
			//기존이미지 파일삭제작업
		}
		
		log.info(vo.toString());
		service.editOk(vo);
		//상품이미지 수정여부에 따른 작업
		
	
		rttr.addFlashAttribute("msg", "editOk");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		
		return "redirect:/admin/product/list"; //아래 list실행
	}
	
	
	
	//개별삭제
	@PostMapping("delete")
	public String delete(Criteria cri,@RequestParam("pdt_num") Integer pdt_num_pk ,RedirectAttributes rttr) {
		
		//삭제기능작업
		
		service.delete(pdt_num_pk);
		
		
		rttr.addFlashAttribute("msg", "deleteOk");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		
		return "redirect:/admin/product/list";
	}
	
	
	
	
	//선택삭제 (ajax기능)
		@ResponseBody
		@PostMapping("/deleteChecked")	//배열이라 []sjgrl.
		public ResponseEntity<String> deleteChecked(@RequestParam("pdtnumArr[]") List<Integer> pdtnumArr,@RequestParam("imgArr[]") List<String> imgArr,RedirectAttributes rttr){
			
			ResponseEntity<String> entity = null;
			
			
			for(int i=0; i < pdtnumArr.size();i++) {
				//상품삭제 진행
				service.delete(pdtnumArr.get(i));
				
				UploadFileUtils.deleteFile(uploadPath,imgArr.get(i));
				
				//이미지삭제 진행
				
				
				
				
				rttr.addFlashAttribute("msg", "deleteOk");
				
			}
			
			return entity;
			
		}
		

	
	
	//페이징,검색기능 포함
	@GetMapping("/list")
	public void productList(Criteria cri,Model model) throws Exception {
		

		
		cri.setAmount(4); // amount값만 4로바꿈
		
		
		log.info("list : "+cri); //페이징정보,검색정보 같이 출력
		
		
		//cri : PageNum=1 amount= 10
		List<ProductVO> list = service.getListWithPaging(cri);
		
		
		//테이블의 전체 데이터 개수불러오는 작업(total)
		int total = service.getTotalCount(cri);
		
		log.info("total : " + total);
		
		model.addAttribute("pageMaker",new PageDTO(total,cri)); //list.jsp에서 "pageMaker"란 이름으로 테이블의 모든데이터를 참조.
		
		
		model.addAttribute("list",list); //model에 넣으면  list란 이름으로 list.jsp에서 사용가능,
										//list란 이름으로 모든 테이블의 데이터 참조
	}
	
	
	@ResponseBody // ajax받기 //상품등록
	@GetMapping(value= "/subCategory/{mainCategoryCode}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})//값을 JSON으로 받겠다.
	public ResponseEntity<List<CategoryVO>> subCategory(@PathVariable("mainCategoryCode") Integer cate_code_pk){
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		
		//cate_code_pk값이 존재하지않아 예외 발생시 처리하는 구문
		try {
			entity = new ResponseEntity<List<CategoryVO>>(service.subCategory(cate_code_pk),HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<List<CategoryVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	//CKEditor 업로드 기능
	@PostMapping("/imgUpload") // form이 post타입
	public void ckeditor_upload(HttpServletRequest req, HttpServletResponse res, MultipartFile upload) {
		//왜 upload라는 이름인가: 파일선택부분이 upload라는 name이라서 그대로쓴다.
		
		OutputStream out = null;
		PrintWriter printWriter = null;
		
		
		//서버에서 클라이언트에게 보내는 정보에 대한 설정
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 보낸 파일명
			byte[] bytes = upload.getBytes(); // 파일을 바이트배열로읽어드림.
			
			//1)프로젝트에서 관리하는 업로드 폴더 작업  
			String uploadPath = req.getServletContext().getRealPath("/");//프로젝트에 해당하는 톰캣의 물리적경로(실제운영되는폴더)
			
			//톰캣이 업로드 임시폴더를 삭제하는 경향이있어서, 삭제되면 생성하도록 코드작업
			if(!new File(uploadPath,"resources/upload/").exists()) { // resources/upload폴더가 존재하지않으면
				new File(uploadPath,"resources/upload/").mkdir();//생성해라
			}
			
			
			uploadPath = uploadPath + "resources\\upload\\" + fileName;
			
			log.info(uploadPath);//실제 업로드경로
			
			out = new FileOutputStream(new File(uploadPath));
			out.write(bytes);  // 출력스트림 작업. 파일업로드완료
			
			
			//2)ckeditor 4에서 제공하는 형식구문  : 서버에서 ckeditor에게 보내는 업로드된 파일정보
			//서버에서 response객체의 printWriter객체를 참조하여,정보를 보낼때 사용 
			printWriter = res.getWriter();
			String fileUrl = "/upload/" + fileName;  // servlet-context.xml파일에서 
			//<resources mapping="/upload/**" location="/resources/upload/" /> 지정
			
			
			//
			//{"filename":"업로드파일명","uploaded":1,"url":"fileUrl"} -> JSON구문
			printWriter.println("{\"filename\":\"" +  fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}");
			printWriter.flush();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(out != null) {
				try {out.close();}catch(Exception e) {e.printStackTrace();}
			}
			if(printWriter != null) {
				try {printWriter.close();}catch(Exception e) {e.printStackTrace();}
			}
		}
		
		
	}
	
	@ResponseBody//이미지 해당하는부분만 받아오는거라 responseEntity  ( 페이지넘기는게아니라 값을 가져오니깐)
	@GetMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		
		
		ResponseEntity<byte[]> entity = null;

		entity  = UploadFileUtils.getFileByte(fileName, uploadPath);

		
		return entity;
		
	}
	
	
	
	
}
