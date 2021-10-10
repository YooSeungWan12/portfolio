<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Starter</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<%@ include file="/WEB-INF/views/admin/includes/config.jsp" %>

<script>
	let msg = "${msg}";
	if(msg == "insertOk"){
		alert("상품등록이 되었습니다.");
	}else	if(msg == "editOk"){
		alert("상품수정이 되었습니다.")
	}else	if(msg == "deleteOk"){
		alert("삭제되었습니다.");
	}
</script>

</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

 <%@include file="/WEB-INF/views/admin/includes/top.jsp" %>
  <!-- Left side column. contains the logo and sidebar -->
  <%@include file="/WEB-INF/views/admin/includes/nav.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Page Header
        <small>Optional description</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

      <!--------------------------
        | 기능에따른 실제 내용 |
        -------------------------->
        <h3>상품목록</h3>
        
        <div id="example2_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
					<!-- 검색기능 화면출력,  검색종류 type 검색어 keyword라고 Criteria에 이미 지정해둠.-->
					<!-- type,keyword , pageNum,amount 다 pageMaker.cri에 저장되어있다.
					이유는 model.addAttribute("pageMaker",new PageDTO(total,cri));에서 생성했기때문. -->
			<div class="float-left">
				<div class="row">
					<div class="col-lg-12">
						<form id="searchForm" action="/admin/product/list" method="get">
							<select name="type"> <!-- <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : '' }"></c:out> 를 사용하여, 전에 사용한값을 selected -->
								<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }"></c:out>> --</option>
								<option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }"></c:out>>상품명</option>
								<option value="D" <c:out value="${pageMaker.cri.type eq 'D' ? 'selected' : '' }"></c:out>>내용</option>
								<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }"></c:out>>제조사</option>
								<option value="ND" <c:out value="${pageMaker.cri.type eq 'ND' ? 'selected' : '' }"></c:out>>상품 or 내용</option>
								<option value="NC" <c:out value="${pageMaker.cri.type eq 'NC' ? 'selected' : '' }"></c:out>>상품 or 제조사</option>
								<option value="NDC" <c:out value="${pageMaker.cri.type eq 'NDC' ? 'selected' : '' }"></c:out>>상품 or 내용 or 제조사</option>
							</select>
							<input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword }"/>">
							<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
							<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
							<button type="submit" class="btn btn-default">검색</button>
						</form>

					</div>	
				</div>
			</div>
	        <div class="row"><div class="col-sm-6"><button type="button" id="btnChkDel">선택삭제</button></div>
		        <div class="col-sm-6"></div></div>
			        <div class="row">
				        <div class="col-sm-12">
				        <table id="example2" class="table table-bordered table-hover dataTable" role="grid" aria-describedby="example2_info">
				         <thead>
					         <tr role="row">
						         <th class="sorting_asc" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending">
									<input type="checkbox" id="checkAll">
								</th>
						         <th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending">
									상품코드
								</th>
						         <th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending">
									상품명
								</th>
						         <th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending">
									등록일
								</th>
						         <th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending">
									가격
								</th>
								<th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending">
									재고
								</th>
								<th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending">
									진열
								</th>
								<th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending">
									수정
								</th>
								<th class="sorting" tabindex="0" aria-controls="example2" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending">
									삭제
								</th>
					         </tr>
				         </thead>
				         <tbody>
				         <c:forEach items="${list}" var="vo">
					          <tr role="row" class="odd">
					            <td class="sorting_1">
					            	<input type="checkbox" class="check" value="<c:out value="${vo.pdt_num_pk }"/>">
					            	<input type="hidden" class="pdt_img" value="<c:out value="${vo.pdt_img }"/>">
					            </td>
					            <td><c:out value="${vo.pdt_num_pk }"/></td>
					            <td><img src="/admin/product/displayFile?fileName=<c:out value="${vo.pdt_img }"/>" alt=""><c:out value="${vo.pdt_name }"/></td>
					            <td><fmt:formatDate value="${vo.pdt_date_up }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					            <td><input type="text" value='<c:out value="${vo.pdt_price }"/>'></td>
					            <td><input type="text" value='<c:out value="${vo.pdt_amount }"/>'></td>
					            <td><input type="checkbox" <c:out value="${vo.pdt_buy == 'Y' ? 'checked': '' }"/>></td> <!-- c:out value는 값을 그 자리에 넣는것, 'Y'가 들어가면 그자리에 checked가 출력된다. -->
					            <td><input type="button" value="수정" class="btnEdit" data-pdt-num="<c:out value="${vo.pdt_num_pk }"/>"></td>
					            <td><input type="button" value="삭제" class="btnDelete" data-pdt-num="<c:out value="${vo.pdt_num_pk }"/>"></td>
					          </tr>
				          </c:forEach>
				          
<!--				          <tr role="row" class="even">
				            <td class="sorting_1">Gecko</td>
				            <td>Firefox 1.5</td>
				            <td>Win 98+ / OSX.2+</td>
				            <td>1.8</td>
				            <td>A</td>
				          </tr>
-->
				          </tbody>
				         
				        </table>
						</div>
					</div>
			<div class="row">
			<div class="col-sm-5">
			<div class="dataTables_info" id="example2_info" role="status" aria-live="polite"></div>
			</div>
			<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers" id="example2_paginate">
			<ul class="pagination">
			<c:if test="${pageMaker.prev }"><!-- test는 참일경우 실행한다는뜻. -->
				<li class="paginate_button previous" id="example2_previous">
					<a data-pagenum="${pageMaker.startPage-1 }" class="page-link btnPagePrev" href="${pageMaker.startPage - 1}" aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a>
				</li>
			</c:if>
			
			<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
				<li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active' : ''}">
					<a data-pagenum="${num}"  class="page-link btnPageNum" href="${num}" aria-controls="example2" data-dt-idx="1" tabindex="0">${num }</a>
				</li>
			</c:forEach>
			
			<c:if test="${pageMaker.next }">
				<li class="paginate_button next" id="example2_next">
					<a class="page-link btnPageNext" href="${pageMaker.endPage + 1}" aria-controls="example2" data-dt-idx="7" tabindex="0">Next</a>
				</li>
			</c:if>
			</ul>
			</div>
			
			
				<form id="actionForm" action="" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					<input type="hidden" name="type" value="${pageMaker.cri.type }">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
				</form>
			
		</div>
	</div>
</div>
        

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
   <%@include file="/WEB-INF/views/admin/includes/bottom.jsp" %>



</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<%@ include file="/WEB-INF/views/admin/includes/common.jsp" %>

<script>
		//$(document).ready이벤트 약식버전
		$(function(){ 

			let actionForm =  $("#actionForm");

			//상품수정폼 페이지 작업
			$(".btnEdit").on("click",function(){


				let pdt_num = $(this).data("pdt-num");

				console.log("edit : " + pdt_num);

				//RequestParam으로 받는 pdt_num를이름으로 쓴다
				//브라우저 검사로 폼정보 태그확인.
				actionForm.append("<input type='hidden' name='pdt_num' value='"+ pdt_num + "'>");
				actionForm.attr("action","/admin/product/edit"); 
				actionForm.submit();

			});

			//상품삭제
			$(".btnDelete").on("click",function(){

				let pdt_num = $(this).data("pdt-num");
				
				if(confirm("상품코드 " + pdt_num +"를 삭제하시겠습니까?")){

				

				console.log("delete : " + pdt_num);

				//RequestParam으로 받는 pdt_num를이름으로 쓴다
				//브라우저 검사로 폼정보 태그확인.
				actionForm.append("<input type='hidden' name='pdt_num' value='"+ pdt_num + "'>");
				actionForm.attr("action","/admin/product/delete");
				actionForm.attr("method","post"); 
				actionForm.submit();
				}
			});


			//attr(),prop() : 속성을바꾸는 기능.
			//다른건 attr로하면 좋지만, 체크박스는 prop를쓰자.

			//전체선택 체크박스 
			$("#checkAll").on("click",function(){

				//.는 클래스 , 속성변경은 attr도 있지만. 여기선 쓰지말자.
				$(".check").prop("checked",this.checked); //전체체크를 눌렀다면, check'클래스'들을 다 체크.,  체크해제를 하면 전부다 해제
			});

			//개별선택 체크박스

			$(".check").on("click",function(){
				$("#checkAll").prop("checked",false); // 만약 전체체크가 아니라 일반체크를 누른다면 전체체크를 해제
			});

			//선택삭제 버틀 클릭시
			$("#btnChkDel").on("click",function(){
				if($(".check:checked").length == 0){
					//check된게 없을경우
					alert("삭제할 상품을 선택해주세요.");
					return;
				}
				var result = confirm("선택한 상품을 삭제하시겠습니까?");
				if(result){
				
					//선택한 상품코드,이미지 정보를 배열로 저장
					let pdtnumArr = [];
					let imgArr = [];

					//each는 익명함수 반복.
					//삭제하고자 하는 선택된check들의 수(상품)만큼 익명함수가 반복,
					$(".check:checked").each(function(){
						
						let pdt_num = $(this).val(); //체크된값을
						let pdt_img = $(this).next().val(); // 체크된값의 다음값을 가져옴

						pdtnumArr.push(pdt_num); //배열에 추가하는건 push
						imgArr.push(pdt_img);

						
					});
					console.log("선택상품 : " +pdtnumArr);
					console.log("선택상품 이미지 : " +imgArr);

					$.ajax({
						url: '/admin/product/deleteChecked',
						type: 'post',
						dataType : 'text', //스프링에서 넘어오는 데이터타입
						data : {
							pdtnumArr : pdtnumArr, //배열
							imgArr : imgArr //배열
						},  //스프링에 보내는 데이터  key : value  스프링에는 key값으로 사용
						success : function(data){
							alert("선택된 상품이 삭제가 되었습니다.");
							actionForm.attr("action","/admin/product/list");
						
							actionForm.submit();
						}

						//폼전송
					});

				}
			});

			//이전,다음,페이지번호클릭시

					//페이지번호 클릭작업     paginate_button 안에 있는 a태그를 클릭시,
		$(".paginate_button a").on("click",function(e){

			e.preventDefault();//a태그 링크기능비활성화

			actionForm.find("input[name='pageNum']").val($(this).attr("href")); //액션폼에서  pageNum부분을 찾아서,값을 this의 href속성값 으로바꾼다.
			//사용자가 선택한 페이지 번호 변경  $(this)는 내가 누른곳의 href성격 값을바꿈. 
			//find는  자식들중 찾는것,  즉 actionForm의 하위요소 에 값을 넣어라.
			

			actionForm.submit();
			//console.log("click");
		});

		});

</script>


</body>
</html>