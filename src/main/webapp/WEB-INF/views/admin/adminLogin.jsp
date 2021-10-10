<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>



<%@ include file="/WEB-INF/views/includes/common.jsp" %>






<style>
.login-block{
float:left;
width:100%;
padding : 50px 0;
}

.container{background:#D3D3D3; border-radius: 10px; width: 40%; height: 40%;}
.login-sec{padding: 50px 30px; position:relative;}
.login-sec h2{margin-bottom:30px; font-weight:800; font-size:30px; color: #0069c0;}
.login-sec h2:after{content:" "; width:100px; height:5px; background:#6ec6ff; display:block; margin-top:20px; border-radius:3px; margin-left:auto;margin-right:auto}
.btn-login{background: #0069c0; color:#fff; font-weight:600;}
</style>



<script>
let msg = "${msg}";

if(msg=="loginFail"){
	alert("정보가 틀렸습니다.\n 확인해주세요.");
}else if(msg == "logout"){
	alert("관리자가 로그아웃되었습니다.");
}
</script>






</head>
<body>
<section class="login-block">
    <div class="container">
	<div class="row ">
		<div class="col login-sec">
		    <h2 class="text-center">Admin login</h2>
  <form class="login-form" action="/admin/adminLogin" method="post">
  <div class="form-group">
    <label for="exampleInputEmail1" class="text-uppercase">Admin id</label>
    <input type="text" name="admin_id" class="form-control" placeholder="">
    
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1" class="text-uppercase">Password</label>
    <input type="password" name="admin_pw" class="form-control" placeholder="">
  </div>
  
    <button type="submit" class="btn btn-login float-right">AdminLogin</button>
  
  
</form>
  </div>
    </div>
    </div>
</section>
</body>