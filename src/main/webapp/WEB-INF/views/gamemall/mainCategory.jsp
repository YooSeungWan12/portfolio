<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- 1차 카테고리 -->
<div style="text-align:center;">
	<ul class="nav" id="mainCategory">
       <c:forEach items="${GlobalMainCategory }" var="category">
	       <li class="nav-item">
	     	  <a class="nav-link main" href="${category.cate_code_pk }">${category.cate_name }</a>
	       </li>
	   </c:forEach>
    </ul>
 <!-- 2차 카테고리 -->
    <ul class="nav" id="subCategory" style="height:50px;"></ul>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script><!-- 핸들바, 바인딩 -->

<script>
  $(function () {
			  let actionForm =  $("#actionForm");
	  
		

			//categorymenu 안에 a를 누를시  ( a태그를 가르키고있는상태), 1차카테고리 선택  ,  on 이벤트설정 / off 이벤트해제 / one 단 한번의 이벤트만 설정
			$("#mainCategory li a.main").on("mouseover click",function(e){
				
				e.preventDefault(); // 클릭시 이동하는걸 막음
				
				let mainCategory = $(this).attr("href");//a태그의 href값을  mainCategory에 넣는다.
				let url = "/product/subCategory/" + mainCategory;  // 서버측에 요청할 url mapping주소


				//2차 카테고리의 정보가 data로들어감.
				$.getJSON(url,function(data){  // data는 지금 배열속성

					//2차 카테고리 data를 가지고 작업

					//console.log(data[0]);
					subCategoryView(data,$("#subCategory"),$("#subCateTemplate")); // 받을데이터,위치,템플릿

				});
				
			});
				
				//2차 카테고리 선택,  동적으로 추가된 이벤트 연결작업
				$("#subCategory").on("click","li a.sub",function(e){
					
					e.preventDefault(); // 클릭시 이동하는걸 막음
					

					let subCategory = $(this).attr("href");
					console.log(subCategory);
					
					//2차에 해당하는 상품리스트 정보
					location.href="/product/list?subCategory=" + subCategory;
					
					//actionForm.submit();

					});
					
				
			

  })
</script>
<script id="subCateTemplate" type="text/x-handlebars-template">

	{{#each .}}
		<li  class="nav-item" value="{{cate_code_pk}}">
			<a class="nav-link sub" href="{{cate_code_pk}}">{{cate_name}}</a>
		</li>
	{{/each}}

</script>
<script>
  // 받을데이터,위치,템플릿
let subCategoryView = function(subCategory,target,templateObject){



let template = Handlebars.compile(templateObject.html());  //템플릿에 결합
let options = template(subCategory);
$("#subCategory").empty(); // 기존옵션 제거
target.append(options); // 위치에 값을 저장
}

</script>