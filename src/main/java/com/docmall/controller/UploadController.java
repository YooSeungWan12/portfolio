package com.docmall.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.util.MediaUtils;
import com.docmall.util.UploadFileUtils;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	
	@Resource(name="uploadPath")
	private String uploadPath; //servlet-context.xml파일에서 bean구문작업이 되어있음.     C:\\upload\\real 경로를 같게된다.
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("uploadForm");
	}
	
	/*
	@PostMapping("/uploadFormAction")//uploadForm의 uploadFile을 배열에 넣음
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		for(MultipartFile multipartfile : uploadFile) {
			log.info("-------------------------------------------------");
			log.info("upload file name : " + multipartfile.getOriginalFilename());
			log.info("upload file size : " + multipartfile.getSize());
			log.info("-------------------------------------------------");
			
		}
	*/
	@PostMapping("/uploadFormAction")//uploadForm의 uploadFile을 배열에 넣음
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		String uploadFolder = "C:\\upload\\real";
		
		for(MultipartFile multipartfile : uploadFile) {
			log.info("-------------------------------------------------");
			log.info("upload file name : " + multipartfile.getOriginalFilename());
			log.info("upload file size : " + multipartfile.getSize());
			log.info("-------------------------------------------------");
			
			//File 클래스: 파일,폴더관련 기능을 제공
			File saveFile = new File(uploadFolder, multipartfile.getOriginalFilename()); // 업로드폴더에, 오리지날 파일이름의 파일객체를 생성후
			
			try {
				multipartfile.transferTo(saveFile); //파일저장.
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
	}
	
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("uploadAjax");
	}
	
	//RestController가 아니면 이렇게써야한다.
	@ResponseBody // ajax요청시 @RestController가 아니라면 반드시사용해야한다
	@PostMapping(value = "/uploadAjax", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws IOException{ //	formData.append("file",file);이부분때문에 file로써야한다.
		
		log.info("originalName : "+ file.getOriginalFilename());
		
		ResponseEntity<String> entity = null;
		
		//클라이언트에게 보낼 응답데이터가 첫번째 파라미터
		//String :  "/2021/08/24/s_7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg"
		entity = new ResponseEntity<String>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
		
		return entity;
	}
	
	
	@ResponseBody
	@GetMapping("/displayFile") // 파일이 클라이언트로보내지면 byte[]을 씀, String fileName = 원본 이미지파일명이 들어온다.
	public ResponseEntity<byte[]>  displayFile(String fileName) throws IOException{
		
		ResponseEntity<byte[]> entity = null;
		
		//입출력작업
		
		InputStream in = null;
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 가져오기.
		
		//이미지파일인지 일반파일인지 확인.( null이 아니면 이미지파일)
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		
		//서버에서 클라이언트에 보내는 데이터에 대한 부연설명.
		HttpHeaders headers = new HttpHeaders(); 
		
		
		//업로드된 파일을 참조하는 파일입력스트림 객체생성.
		in = new FileInputStream(uploadPath + fileName);
		
		//2021/08/24/s_7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg
		//2021/08/24/7405e87f-c55f-47ff-9318-f26487b2da2_1.hwp
		
		if(mType != null) {
			//이미지파일이면
			headers.setContentType(mType);
			
		}else {
			//일반파일일경우  다운로드.
			fileName = fileName.substring(fileName.indexOf("_") + 1); // 1.hwp를 뽑아옴.
			
			
			//표준으로 정의되어있지 않은 파일인경우 지정.
			//브라우저가 해석하지 못하는 의미로 지정 -> 대화상자저장 화면이 진행된다.
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); //브라우저가 이해하지못하는 성격인경우  다운로드창을 열음
			
			//headers.add(key,value)
			//웹브라우저에게 보내는 정보가 웹페이지 자체 또는 일부가 아니라 클라이언트 컴퓨터에 저장될 용도인것을 알려주는의미
			headers.add("Content-Disposition", "attachment; filename=\""+  new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+ "\"");
		}
		
		entity  = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); //headers엔 데이터 설명이들어있다.
		
		in.close();
		
		
		return entity;
	}
	
	
	
	//파일삭제
	
	@ResponseBody // deleted
	@PostMapping("/deleteFile")  //deleted 		String FileName : 썸네일이미지
	public ResponseEntity<String> deleteFile(String fileName) // data{fileName:$(this).attr("data-src")}; 의 key값과 같아야한다.
	{
		
		ResponseEntity<String> entity = null;
		
		
		//클라이언트에서 요청한 파일삭제구문
		//이미지 파일일경우, 섬네일과 이미지 둘다 삭제해야함.
		//일반파일은 1개
		
		
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 가져오기.
		
		//이미지파일인지 일반파일인지 확인.( null이 아니면 이미지파일)
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		
		// /2021/08/24/s_7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg
		// /2021/08/24/7405e87f-c55f-47ff-9318-f26487b2da2_1.hwp
		if(mType != null)		{
			
			String front = fileName.substring(0, 12); // 날짜(폴더)명 /2021/08/24/
			String end = fileName.substring(14); // s_를 땐 파일이름. 7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg
			
			//원본 이미지파일 삭제
			new File(uploadPath + (front + end).replace('/',File.separatorChar)).delete();
			
		}
		
		//일반파일,썸네일파일  삭제
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete(); // /를 \로 다시바꿈.
		
		
		entity = new ResponseEntity<String>("deleted",HttpStatus.OK); // deleted와 상태코드를 돌려줌.
		
		return entity;
		
		
	}
	
	

}
