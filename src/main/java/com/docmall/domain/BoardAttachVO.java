package com.docmall.domain;

import lombok.Data;

@Data
public class BoardAttachVO {

	//uuid, uploadPath, fileName, filetype, bno
	
	private String uuid;
	private String uploadPath;
	private String fileName;
	private Boolean fileType;
	
	private Long bno;
	
	
}
