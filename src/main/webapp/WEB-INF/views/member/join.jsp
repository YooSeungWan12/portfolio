<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file = "/WEB-INF/views/includes/common.jsp" %>

<script>

	$(document).ready(function(){

        $("#btnSubmit").on("click",function(){
            let joinForm = $("#joinForm");

            joinForm.submit();
        });


        //아이디 중복체크
        $("#btnUserIDCheck").on("click",function(){
            let mem_id = $("#mem_id");

            if(mem_id.val() == "" || mem_id.val() == null) // 입력안했다면
            {
                alert("아이디를 입력하세요.");
                mem_id.focus();
                return;
            }
       

        $.ajax({
            url:'/member/checkUserID',
            type:'post',
            dataType:'text',
            data:{mem_id : mem_id.val()},  //id중복체크 부분에 mem_id.val()의 값을 mem_id로 보낸다.
            success:function(data){
                console.log(data);

                if(data =="yes"){
                    $("#result").html("사용이 가능");
                }else{
                    $("#result").html("사용이 불가능");
                }
            }
            
        });

        });




    });



</script>

</head>
<body>
<%@ include file="/WEB-INF/views/includes/header.jsp" %>


<!-- 회원가입폼 -->

       <article class="container">
            <div class="page-header">
                <div class="col-md-6 col-md-offset-3">
                <h3>회원가입</h3>
                </div>
            </div>
            <div class="col-sm-6 col-md-offset-3">
                <form role="form" id="joinForm" action="/member/join" method="post">
                	<div class="form-group">
                        <label for="mem_name">이름</label>
                        <input type="text" class="form-control" id="mem_name" name="mem_name" placeholder="이름을 입력해 주세요">
                    </div>
                	<div class="form-group">
                        <label for="mem_id">아이디</label>
                        <input type="text" class="form-control" id="mem_id" name="mem_id" placeholder="아이디를 입력해 주세요">
                        <button id="btnUserIDCheck" type="button" class="btn btn-default">중복확인</button>
                        <span id="result" style="color:red;"></span>
                    </div>
                    <div class="form-group">
                        <label for="mem_pw">비밀번호</label>
                        <input type="password" class="form-control" id="mem_pw" name="mem_pw" placeholder="비밀번호를 입력해주세요">
                    </div>
                    <div class="form-group">
                        <label for="mem_repw">비밀번호 확인</label>
                        <input type="password" class="form-control" id="mem_repw"  name="mem_repw" placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요">
                    </div>
                    <div class="form-group">
                        <label for="mem_email">이메일 주소</label>
                        <input type="email" class="form-control" id="mem_email" name="mem_email" placeholder="이메일 주소를 입력해주세요">
                    </div>
                    <div class="form-group">
                        <label for="sample2_postcode">우편 번호</label>
                        <input type="email" class="form-control" id="sample2_postcode" name="mem_zipcode" placeholder="우편번호를 입력해주세요">
                        <input type="button" class="form-control" onclick="sample2_execDaumPostcode()" value="우편번호 찾기">
                    </div>
                    <div class="form-group">
                        <label for="sample2_address">기본 주소</label>
                        <input type="email" class="form-control" id="sample2_address" name="mem_addr" placeholder="기본 주소를 입력해주세요">
                    </div>
                    <div class="form-group">
                        <label for="InputEmail">나머지 주소</label>
                        <input type="email" class="form-control" id="sample2_detailAddress" name="mem_addr_d" placeholder="나머지 주소를 입력해주세요">
                        <input type="hidden" id="sample2_extraAddress" placeholder="참고항목">
                    </div>
                    <div class="form-group">
                        <label for="mem_phone">휴대폰 번호</label>
                        <input type="tel" class="form-control" id="mem_phone" name="mem_phone" placeholder="휴대폰번호를 입력해 주세요">
                    </div>

                 

                    <div class="form-group text-center">
                        <button type="button" id="btnSubmit" class="btn btn-primary">
                            회원가입<i class="fa fa-check spaceLeft"></i>
                        </button>
                        <button type="reset" class="btn btn-warning">
                            가입취소<i class="fa fa-times spaceLeft"></i>
                        </button>
                    </div>
                </form>
            </div>

        </article>







<%@ include file="/WEB-INF/views/includes/footer.jsp" %>


<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none'; //div태그를 가르킴
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample2_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample2_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample2_postcode').value = data.zonecode;
                document.getElementById("sample2_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample2_detailAddress").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();  //나타낸 위치를 가르킴
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>

</body>
</html>