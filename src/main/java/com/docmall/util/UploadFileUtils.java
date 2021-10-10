package com.docmall.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//업로드 기능과 관련딘 작업을 담당하는 메소드들을 담아둔 클래스(재사용성)(컨트롤러가 깔끔해야함)
//@Log4j2
public class UploadFileUtils {
	
	
	/*
	 
	 	String uploadPath:업로드경로 
	 	
	 	String originalName : 원본파일명
	 	
	 	byte[] fileData : 첨부된 파일을 참조하는 바이트배열( 모든 파일들은 바이트로 이루어져있다)
	 
	 
	 *
	 */
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
		
		//UUID ?  업로드시, 파일명이 중복되는것을 방지하기위하여 , 고유의 랜덤문자열을 반환해준다. 고유의 값을 파일명에 추가하여 사용하는 용도
		UUID uid = UUID.randomUUID(); //7405e87f-c55f-47ff-9318-f26487b2da2
		
		String savedName = uid.toString() + "_" + originalName;
		
		//업로드시, 날짜별 폴더를 생성하여 파일을 관리한다.
		//파일을 업로드할 몰더문자열을 반환받음
		String savedPath = calcpath(uploadPath); //"\2021\08\23"
		
		//파일업로드작업
		// 파일 객체 생성 = 기본 저장경로 + 날짜경로 에   UUID_파일명로 저장
		File target = new File(uploadPath + savedPath,savedName); // "C:\\upload\\real" + "\2021\08\23"
		// fileData를 파일객체에 복사
		FileCopyUtils.copy(fileData, target); // 예외관련 작업문법을 가지고있음,  복사됨
		
		
		//복사이후, 파일이 이미지파일인가 여부에따라, 썸네일 작업을 진행
		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1); // 파일명의 확장자를 가져옴  .가 있는 위치를 알아내서. 문자열자르기로 자름
		//즉 abc.jpg가 있다면, 4번째 자리이므로, +1을해서 [5]번자리부터 이후에 있는 글자 jpg를 가져옴. 
		
		String uploadedFileName = null; // 썸네일 이미지 작업에 의한 파일명
		
		
		
		//이미지파일 또는 일반파일
		if(MediaUtils.getMediaType(formatName) != null) {
			//이미지파일 -> 썸네일작업  ->  "/2021/08/24/s_7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg"
			uploadedFileName = makeThumbnail(uploadPath,savedPath,savedName);
		}else {
			//일반파일작업
			uploadedFileName = makeIcon(uploadPath,savedPath,savedName);
		}
		
		return uploadedFileName;
	}

	private static String makeIcon(String uploadPath, String savedPath, String savedName) {
		 
		String iconName = uploadPath + savedPath + File.separator + savedName;
		
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/'); // \ 를 클라이언트(자바스크립트)에서 사용하도록 /로 바꾼다
	}
	
	
	
	/*
	 
	  uploadPath : C:\\upload\\real
	  savedPath : 2021\08\24
	  savedName : uuid+파일이름 7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg
	  
	 */

	private static String makeThumbnail(String uploadPath, String savedPath, String savedName) throws IOException {
		// TODO Auto-generated method stub
		
		//이미지작업을 메모리상에서 가능하게해주는것
		//업로드된 이미지파일을 썸네일작업을 하기위하여, 메모리상에 불러들이는 작업구문
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + savedPath, savedName));
		
		//썸네일작업을 통한 사본이미지 작업을 하기위한 준바
		//파일크기를 줄이고, 해상도가 떨어지지않게하는 작업환경
		//높이고정.
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,150);
		
		//썸네일 이미지 파일명
		//" C:\\upload\\real" + "\2021\08\24" +"\" +"s_" + "7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg"
		String thumbnailName = uploadPath + savedPath + File.separator + "s_" + savedName;
		
		//확장자
		String formatName = savedName.substring(savedName.lastIndexOf(".")+1);
		
		//메모리상에 있던 썸네일 기능이 설정된 작업데이터를 파일로출력
		ImageIO.write(destImg, formatName.toUpperCase(), new File(thumbnailName));//destImg를  thumnialName에 적용된 이름으로 생성
		
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/'); // 경로구분자를 /로 바꾼다.
		//   "\2021\08\24" +"\"  ->   "/2021/08/24" +"/"  클라이언트의 브라우저에게 응답하고자
	}

	private static String calcpath(String uploadPath) {
		//날짜별 폴더관리 기능이있는  메소드,
		
		Calendar cal  = Calendar.getInstance(); // Calendar 객체 사용
		
		String yearPath = File.separator + cal.get(Calendar.YEAR);  // File.separator 파일구분자
		
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1); //01,02,03,04,05,06,..12
		// new DecimalFormat("00") 자릿수를 2자리로, format(cal.get(Calendar.MONTH)+1) +1을해야 1~12월
		
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE)); // "2021-08-23"
		
		makeDir(uploadPath,yearPath,monthPath,datePath);
		
		
		return datePath;
	}
	
	//날짜별 폴더생성기능
	//String uploadPath,String... paths     매개변수갯수제한없음,  제공안해도 에러안남  
	private static void makeDir(String uploadPath,String... paths) {
		// TODO Auto-generated method stub
		
		if(new File(paths[paths.length-1]).exists()){ // datePath에 들어있는 문자열에 해당하는 폴더가있냐 없냐
			return; // 존재하면, 그냥끝냄
		}
		
		//위 구문이 false면 폴더생성작업
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			if(! dirPath.exists()) { //존재하지않으면
				dirPath.mkdir(); // 2021년폴더가 만들어지고,들어가서 8월폴더, 들어가서 23일폴더생성
			}
		}
		
		
	}
	
	public static ResponseEntity<byte[]> getFileByte(String fileName,String uploadPath) throws IOException {
	//UploadController에 있던걸 그대로 옮겨와서 사용(재사용성),  다른 컨트롤러에서 사용	
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
	
	public static void deleteFile(String uploadPath,String fileName) {
		
		//기본이미지,썸네일 2개삭제.
		
		//기본이미지파일명
		String front = fileName.substring(0,12);
		String end=fileName.substring(14);
		String origin = front + end;
		
		//기본이미지 삭제
		new File(uploadPath + origin.replace('/',File.separatorChar)).delete();
		
		//썸네일 이미지삭제
		new File(uploadPath + fileName.replace('/',File.separatorChar)).delete();
	}
	

}
