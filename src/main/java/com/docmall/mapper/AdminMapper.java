package com.docmall.mapper;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.AdminVO;

public interface AdminMapper {

	public AdminVO adminLogin(AdminVO vo);
	
	public void loginTimeUpdate(AdminVO vo);
	
	//mapper인터페이스에서  메소드 파라미터가 2개이상일 경우 @Param어노테이션을 사용
	public void changePW(@Param("vo") AdminVO vo,@Param("changepw") String changepw);
	
	
}
