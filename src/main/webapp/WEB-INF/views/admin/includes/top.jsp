<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <header class="main-header">


    <!-- Logo -->
    <a href="/admin/adminMenu" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>A</b>LT</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Yoo </b> Seung Wan</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
          <li class="dropdown messages-menu">
            <a href="#">최근접속시간 : <fmt:formatDate value="${sessionScope.adminStatus.logintime }" pattern="yyyy-MM-dd HH:mm:ss"/></a><!-- session에서 adminStatus에 저장된 logintime을사용 -->
          </li>
          
           <li class="dropdown messages-menu">
            <a href="/admin/logout">로그아웃</a>
          </li>

        </ul>
      </div>
    </nav>
  </header>