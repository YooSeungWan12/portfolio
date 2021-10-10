<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
    //ajax가 동작하기전에 호출되는 메소드
    /*
    $(document).ajaxSend(function(event,request,settings){
        console.log("ajaxSend");
        request.setRequestHeader("AJAX","true"); // ajax요청이란걸 판단하기위함
    });
*/

    //ajax에러처리  서버에서 500상태코드가 클라이언트에게 보내지면 
    $(document).ajaxError(function(event,request,settings,thrownError){

        if(request.status == 500 || request.status == 0){
            console.log("ajaxError");
            alert("ajax error : " + request.status);
            location.href="/member/login";
        }else{
        	console.log(request.status);
            alert("다음 위치에서 에러가 발생했습니다. 관리자에게 문의요망 \n" + settings.url);
        }
    });

</script>

<footer class="pt-4 my-md-5 pt-md-5 border-top">
	<h3>즐거운 게임샵  010-6858-9489</h3> <br>
	주소: 구로구 디지털로 319	 | 이메일 : cy745@naver.com
</footer>