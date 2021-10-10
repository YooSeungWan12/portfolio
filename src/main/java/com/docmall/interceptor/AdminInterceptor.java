package com.docmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.log4j.Log4j;

@Log4j
public class AdminInterceptor  extends HandlerInterceptorAdapter{

	//특정 주소 요청시 컨트롤러의 메소드 동작이전에 먼저 호출되는 메소드
		//인증된 사용자만 접근하는 주소를설정하여 인증여부를 체크하는 목적으로 사용
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			
			//롬북에서 제공하는 log객체가 작동안함
			System.out.println("테스트");
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("adminStatus") == null) {
				
				
				if(isAjaxRequest(request)) {
					log.info("AjaxRequest");
					System.out.println("AjaxRequest");
					response.sendError(500); //오류를 의미하는 http상태코드를 설정해야 클라이언트의 ajax에서 오류로 인식하여  $(document).ajaxError()가 동작된다.
					return false;
				}else {

					//log.info("인증된  상태가 아님");
					System.out.println("인증된  상태가 아님");
					//saveDest(request); // 사용자와 관리자를 구분하여 사용해야한다.
					
					response.sendRedirect("/admin/main");
					return false;
				}
//				
//				log.info("인증된  상태가 아님");
//				System.out.println("인증된  상태가 아님");
//				saveDest(request);
//				
//				response.sendRedirect("/member/login");
//				return false;
			}else {
				log.info("인증된 상태");
				//request.getSession().removeAttribute("dest");
				return true;
			}
		}

		private boolean isAjaxRequest(HttpServletRequest request) {
			// TODO Auto-generated method stub
			
			String header = request.getHeader("AJAX");
			
			/*
			 * beforeSend: function(xmlHttpRequest) {
			          xmlHttpRequest.setRequestHeader("AJAX","true");
			        },
			        값을 가져옴
			 */
			
			
			if("true".equals(header)) {
				return true;
			}else {
				return false;
			}
		}
		
		

		private void saveDest(HttpServletRequest request) {
			// TODO Auto-generated method stub
			
			//비 로그인 상태에서 get방식으로 요청한 주소를 세션으로 저장하여 로그인후 리다이렉트 하기위한 용도
			
			//ex) /board/list?idx=1    
			
			
			String uri = request.getRequestURI(); // 사용자가 요청한 주소를 가져올수있음.  /board/list
			String query = request.getQueryString(); //idx=1
			
			if(query==null) {
				query="";
			}else {
				query="?" + query;
			}
			
			
			if(request.getMethod().equals("GET")) {
				log.info("dest :" + (uri + query));
				request.getSession().setAttribute("dest", uri + query);
			}
			
		}

}
