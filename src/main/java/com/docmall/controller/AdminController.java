package com.docmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.AdminVO;
import com.docmall.service.AdminService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/admin/*")  // admin폴더 만들기
public class AdminController {
	
	private AdminService service;
	
	//관리자 로그인폼
	@GetMapping("/main")
	public String adminLoginForm() {
		
		return "/admin/adminLogin";
	}

	//관리자 로그인
	@PostMapping("/adminLogin")
	public String adminLoginCheck(AdminVO vo , HttpSession session,RedirectAttributes rttr) throws Exception {
		
		
		
		AdminVO adLoginVO = service.adminLogin(vo);
		String returnUrl="";
		
		
		if(adLoginVO != null) //정상적인데이터면
		{
			returnUrl="/admin/adminMenu";
			session.setAttribute("adminStatus", adLoginVO);
			
			service.loginTimeUpdate(vo);
			
		}else {
			returnUrl="/admin/main";
			rttr.addFlashAttribute("msg", "loginFail");
		
		}
		
		
		return "redirect:"+returnUrl;  // 이건 get방식이다;
	}
	
	
	//관리자 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session,RedirectAttributes rttr) {
		
		session.invalidate();
		
		rttr.addFlashAttribute("msg", "logout");
		
		
		return "redirect:/admin/main";
		
	}
	
	
	//관리자 비번변경 폼
	@GetMapping("/adminPwChange")
	public void  adminPwChange(HttpSession session) throws Exception{
		
		
		
	}
	
	//관리자 비밀번호변경
	@PostMapping("/adminPwChange")
	public String adminPwChange(HttpSession session,@RequestParam("currentpw") String currentpw,@RequestParam("changepw") String changepw,RedirectAttributes rttr) throws Exception{
	
		
		//관리자 비번변경작업
		
		String admin_id = ((AdminVO)session.getAttribute("adminStatus")).getAdmin_id();
		AdminVO vo = new AdminVO();
		vo.setAdmin_id(admin_id);
		vo.setAdmin_pw(currentpw); // 관리자가 입력한 확인용 비밀번호
		AdminVO adminVO = service.adminLogin(vo);
		
		String returnUrl = "";
		
		if(adminVO != null) // 정상적인 비밀번호면{
		{
			
			returnUrl="/admin/adminPwChange";
			service.changePW(adminVO, changepw);
			rttr.addFlashAttribute("msg", "changeSuccess");
		}else {//일치하지않으면
			returnUrl="/admin/adminPwChange";
			rttr.addFlashAttribute("msg", "changeFail");
		}
	
		

		
		return "redirect:"+returnUrl;
		
	}
	
	
	
	//관리자 메뉴페이지
	@GetMapping("/adminMenu")
	public void adminMenu() throws Exception{
		
		
	}
	
	@GetMapping("/admin/starter")
	public void starter() {
		
	}
	
	
}
