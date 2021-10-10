<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="/">즐거운 게임샵</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
          	<c:if test="${sessionScope.loginStatus == null }">
    	<a class="nav-link" href="/member/login">로그인</a>
    </c:if>
    <c:if test="${sessionScope.loginStatus != null }">
    	<a class="nav-link" href="/member/logout">로그아웃</a>
    </c:if>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/member/mypage">계정정보 </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/order/userOrderInfo">주문정보</a>
      </li>
      <li class="nav-item">
            <a class="nav-link" href="/cart/list">장바구니</a>
      </li>
  <c:if test="${sessionScope.loginStatus == null }">
  	<a class="nav-link" href="/member/join">회원가입</a>
  </c:if>
  <c:if test="${sessionScope.loginStatus != null }">
  	<a class="nav-link" href="/member/modify">계정정보수정</a>
  </c:if>
    </ul>
 


  </div>
</nav>

</div>