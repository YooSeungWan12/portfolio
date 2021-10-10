package com.docmall.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

//파일 업로드시 이미지파일인지 여부를 확인하는 용도
public class MediaUtils {
	
	private static Map<String,MediaType> mediaMap;
	
	
	static {
		mediaMap = new HashMap<String,MediaType>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String type) {
		
		return mediaMap.get(type.toUpperCase());//대문자로바꿔서 
	}
	
}
