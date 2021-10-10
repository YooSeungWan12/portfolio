<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file = "/WEB-INF/views/includes/common.jsp" %>

<script>
	let status = "${status}"; // controller에서 rttr로 인해 받은 msg값을 저장
	if(status == "fail"){ 
		alert("현재비밀번호가 틀립니다.");
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
    background-color: #4CAF50;
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

<c:if test="${status == null or status == 'fail' }">
<!-- 로그인폼 -->
<form id="pwChangeForm" method="post" action="/member/ChangePW">

  <div class="container">
    <label for="mem_pw"><b>현재비밀번호</b></label>
    <input type="password" placeholder="현재 비밀번호를 입력하세요" id="mem_pw" name="mem_pw" required>

    <label for="mem_newpw"><b>새 비밀번호</b></label>
    <input type="password" placeholder="변경 비밀번호를 입력하세요" id="mem_newpw" name="mem_newpw" required>
    <label for="mem_confirmpw"><b>새 비밀번호 확인</b></label>
    <input type="password" placeholder="확인 비밀번호를 입력하세요" id="mem_confirmpw" name="confirmmem_pw" required>



    <button type="submit">비밀번호 변경하기</button>
    </div>
</form>
</c:if>

<c:if test="${status == 'success' }">
<!-- 비밀번호 변경처리 결과 -->
<div class="container">

	<label for="uname"><b>비밀번호가 변경되었습니다.</b></label>
</div>
</c:if>

<%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>
</html>