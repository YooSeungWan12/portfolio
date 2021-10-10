package com.docmall.domain;

public class EmailDTO {

	private String senderName; // 발신자이름
	private String senderMail; //발신자 메일주소
	private String receiveMail; //수신자 메일주소
	private String subject; //제목
	private String message; //본문내용
	
	
	
	public EmailDTO(String senderName, String senderMail, String receiveMail, String subject, String message) {
		super();
		this.senderName = senderName;
		this.senderMail = senderMail;
		this.receiveMail = receiveMail;
		this.subject = subject;
		this.message = message;
	}
	
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderMail() {
		return senderMail;
	}
	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}
	public String getReceiveMail() {
		return receiveMail;
	}
	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "EmailDTO [senderName=" + senderName + ", senderMail=" + senderMail + ", receiveMail=" + receiveMail
				+ ", subject=" + subject + ", message=" + message + "]";
	}

	
	
	
}
