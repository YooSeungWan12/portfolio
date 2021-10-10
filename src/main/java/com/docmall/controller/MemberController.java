package com.docmall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.LoginDTO;
import com.docmall.domain.MemberVO;
import com.docmall.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/member/*")
public class MemberController {
private BCryptPasswordEncoder cryptPassEnc; //주입
	
	//@Setter(onMethod_ = @Autowired) @AllArgsConstructor 가 없다면 써야함.
	private MemberService service;
	
	
	
	//회원가입
	@GetMapping("/join")
	public void join() {
		
	}
	
	//회원저장
	@PostMapping("/join")
	public String joinOk(MemberVO vo, RedirectAttributes rttr) throws Exception{
		
		//DB에 insert delete update등등은 보통 String을써서  return값으로 redirect로 이동할 주소를 준다.
		
		//비밀번호를 암호화하면 원래 일반비밀번호로 디코딩할수없다.
		vo.setMem_pw(cryptPassEnc.encode(vo.getMem_pw()));
		
		service.join(vo);
		
		return "redirect:/member/login";
	}
	
	
	//회원 수정폼
	@GetMapping(value={"/modify","/mypage"})
	//@GetMapping("/modify")
	public void modify(HttpSession session,Model model) throws Exception {
		

		
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		String mem_pw = ((MemberVO)session.getAttribute("loginStatus")).getMem_pw();
		
		LoginDTO dto = new LoginDTO();
		dto.setMem_id(mem_id);
		dto.setMem_pw(mem_pw);
		
		MemberVO vo = service.login(dto);
		
		model.addAttribute("memberVO", vo);
		
	}
	
	
	// 회원수정저장
	@PostMapping("/modify")
	public String modify(MemberVO vo, RedirectAttributes rttr) throws Exception{
		
		//아이디와 비번을 확인하는 작업
		LoginDTO dto = new LoginDTO();
		dto.setMem_id(vo.getMem_id());
		dto.setMem_pw(vo.getMem_pw());
		
		String returnUrl = "";
		
		
		MemberVO dvo = service.login(dto);
		
		if(dvo != null)
		{
			vo.setMem_pw(dvo.getMem_pw());
			//회원수정작업
			service.modify(vo);
			rttr.addFlashAttribute("msg", "modifySuccess");
			
			returnUrl = "/";
		}else {
			// 입력 비번이 틀린경우
			returnUrl = "/member/modify";
			rttr.addFlashAttribute("msg", "modifyFail");
		}
		
		return "redirect:" + returnUrl;
	}
	
	
	
	
	
	
	
	
	//회원삭제폼
	
	@GetMapping("/deleteConfirm")
	public void deleteConfirm() {
	
	}
	
	
	
	//회원삭제
	
	@PostMapping("/delete")
	public String delete(HttpSession session, @RequestParam("mem_pw") String mem_pw, RedirectAttributes rttr) throws Exception {
		
		
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		
		LoginDTO dto = new LoginDTO();
		dto.setMem_id(mem_id);
		dto.setMem_pw(mem_pw);
		
	
		
		// 아이디와 비밀번호 일치여부확인
		if(service.login(dto)!=null) {
			
			//회원삭제구문
			service.delete(dto);
			session.invalidate();//세션삭제
			rttr.addFlashAttribute("msg", "success");
			
			
		}else {
			
			//입력한 비밀번호가 다를경우
			rttr.addFlashAttribute("msg", "fail");
	
		}
		
		
		
		return "redirect:/member/deleteConfirm";
	}
	
	
	//로그인폼
	@GetMapping("/login")
	public void login() {
		
	}
	
	//로그인진행
	//HttpSession 인터페이스 : 로그인 상태를 서버측에 저장하여 , 사용자가 로그인 상태인지 확인하기위해 체크목적으로 사용
	@PostMapping("/login")
	public String login(LoginDTO dto,HttpSession session, RedirectAttributes rttr) throws Exception {
		
		
		
		MemberVO vo = service.login(dto);  //일치하면 값이나오고 일치안하면 null, 아이디만으로 정보를 가져옴
		
		String returnUrl = "";
		
		//아이디만 존재
		if(vo != null) {
			//cryptPassEnc.matches(입력한 비밀번호, 암호화된 비밀번호)    알아서 비교해줌.
			if(cryptPassEnc.matches(dto.getMem_pw(), vo.getMem_pw())) {
			
				returnUrl = "/"; //메인으로
				session.setAttribute("loginStatus", vo);//로그인 상태를 서버측 메모리에 저장
			}else {
				//입력한 비밀번호가 틀렸을때 진행되는부분
				returnUrl = "/member/login";
				rttr.addFlashAttribute("msg", "loginFail"); // login.jsp에서 msg키값을 사용가능.
			}
		}else {
			returnUrl = "/member/login";
			rttr.addFlashAttribute("msg", "loginFail"); // login.jsp에서 msg키값을 사용가능.
		}
		
		//String.format("redirect:{0}", vo != null ? "/" : "/member/login");
		
		return "redirect:" + returnUrl;
	}
	
	
	//로그아웃
	
	@GetMapping("/logout")
	public String logout(HttpSession session , RedirectAttributes rttr) {
		
		
		session.invalidate();//사용자에 의하여 생성된 세션정보 모두소멸
		
		
		return "redirect:/";
	}
	
	
	
	//아이디 중복체크
	@ResponseBody //리턴값을 클라이언트에 바로보냄.
	@PostMapping("/checkUserID")
	public ResponseEntity<String> checkUserID(@RequestParam("mem_id") String mem_id) throws Exception{
		
		ResponseEntity<String> entity = null;
		
		String msg ="yes";
		if(service.checkUserID(mem_id) != null) msg = "no";
		
		entity = new ResponseEntity<String>(msg, HttpStatus.OK); // .ajax 에 data쪽에 msg값을 보냄.
		
		
		return entity;
		
	}
	
	
	
//	//mypage
//	@GetMapping("/mypage")
//	public void mypage(HttpSession session,Model model) throws Exception{
//		
//		
//		
//	}
//	
	
	
	
	
	//아이디찾기
	
	//비밀번호찾기
	
	@GetMapping("/forgetPW")
	public void forgetPW() {
		
	}
	
	//비밀번호 변경폼
	@GetMapping("/ChangePW")
	public void changePWForm() {
		
	}

	//비밀번호변경
	@PostMapping("/ChangePW")
	public String changePW(String mem_pw,String mem_newpw,HttpSession session,RedirectAttributes rttr) {
		String mem_id =  ((MemberVO)session.getAttribute("loginStatus")).getMem_id();
		
		//아이디 비번을 확인하는작업
		LoginDTO dto = new LoginDTO();
		dto.setMem_id(mem_id);
		dto.setMem_pw(mem_pw);
		
		MemberVO vo = service.login(dto);  //일치하면 값이나오고 일치안하면 null, 아이디만으로 정보를 가져옴   아이디만 가져오는이유: 암호화된 비번은 복구가안되서.
		
		// 아이디와 비밀번호 일치여부확인
		if(vo !=null) {
									//일반비번			//암호화된 비밀번호    비교
			if(cryptPassEnc.matches(dto.getMem_pw(), vo.getMem_pw())) {
				
				//비밀번호 변경구문`
		
				service.pwChange(mem_id, cryptPassEnc.encode(mem_newpw));
				
				rttr.addFlashAttribute("status", "success"); 
				
			}else {
				
				//비번이 다를경우
				
				rttr.addFlashAttribute("status", "fail");
				
			}
			
			
			
		}
		
		return "redirect:/member/ChangePW"; // 다음주소에서 사용할 목적으론 rttr를써서, 여기서 사용하는거면 model
	}

}
