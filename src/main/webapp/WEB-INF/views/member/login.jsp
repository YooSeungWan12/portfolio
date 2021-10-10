<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file = "/WEB-INF/views/includes/common.jsp" %>

<script>
	let msg = "${msg}"; // controller에서 rttr로 인해 받은 msg값을 저장
	if(msg == "loginFail"){ 
		alert("로그인 실패");
	}

</script>

<style>
form {
    border: 3px solid #f1f1f1;
}

/* Full-width inputs */
input[type=text], input[type=password] {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

/* Set a style for all buttons */
button {
    background-color: black;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 100%;
}

/* Add a hover effect for buttons */
button:hover {
    opacity: 0.8;
}

/* Extra style for the cancel button (red) */
.cancelbtn {
    width: auto;
    padding: 10px 18px;
    background-color: #f44336;
}

/* Center the avatar image inside this container */
.imgcontainer {
    text-align: center;
    margin: 24px 0 12px 0;
}

/* Avatar image */
img.avatar {
    width: 40%;
    border-radius: 50%;
}

/* Add padding to containers */
.container {
    padding: 16px;
}

/* The "Forgot password" text */
span.psw {
    float: right;
    padding-top: 16px;
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
    span.psw {
        display: block;
        float: none;
    }
    .cancelbtn {
        width: 100%;
    }
}
</style>

</head>
<body>
<%@ include file="/WEB-INF/views/includes/header.jsp" %>


<!-- 로그인폼 -->
<form id="loginForm" method="post" action="">

  <div class="container">
    <label for="mem_id"><b>UserID</b></label>
    <input type="text" placeholder="아이디를 입력하세요" id="mem_id" name="mem_id" required>

    <label for="mem_pw"><b>Password</b></label>
    <input type="password" placeholder="비밀번호를 입력하세요" id="mem_pw" name="mem_pw" required>

    <button type="submit">Login</button>
  </div>
  
    <div class="container" style="background-color:#f1f1f1">
    <button type="button" class="cancelbtn">Cancel</button>
    <span class="psw">Forgot <a href="/member/forgetPW">password?</a></span>
  </div>

</form>




<%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>
</html>