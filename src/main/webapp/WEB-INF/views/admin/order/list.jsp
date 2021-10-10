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
  
  <%@include file="/WEB-INF/views/admin/includes/config.jsp" %>
 
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

  <!-- Main Header -->
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
      
	<h3>주문목록</h3>
	
	<div id="example2_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
		
		<!-- 검색기능 화면출력 -->
		<div class="float-left">
			<div class="row">
				<div class="col-lg-10">
					<form id="searchForm" action="/admin/order/list" method="get">
						<select name="type">
							<option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
							<option value="M" <c:out value="${pageMaker.cri.type eq 'M' ? 'selected' : '' }" />>주문자ID</option>
							<option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }" />>주문번호</option>
							<option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }" />>수령인</option>
						</select>
						
						<input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword }" />">
						<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
						<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
						<button type="submit" class="btn btn-default">검색</button>
					</form>
				</div>

				<div class="col-lg-2">
					<button type="button" name="btnDeliveryChange" id="btnDeliveryChange">선택주문상태변경</button>

				</div>




			</div>
		</div>
		
		<div class="row"><div class="col-sm-6"></div>
			<div class="col-sm-6"></div></div>
				<div class="row">
				<div class="col-sm-12">
				<table id="example2" class="table table-bordered table-hover dataTable" role="grid" aria-describedby="example2_info">
			      <colgroup>
			      	<col width="10%">
			      	<col width="15%">
			      	<col width="35%">
			      	<col width="10%">
			      	<col width="10%">
			      	<col width="10%">
			      	<col width="10%">
			      	
			      </colgroup>
			      
			      <thead>
			      	<tr role="row">
			      		<th>
			      			<input type="checkbox" id="checkAll" name="ord_check">
			      		</th>
			      		<th>
			      		주문일자
			      		</th>
			      		<th>
			      		주문번호
			      		</th>
			      		<th>
			      		주문인
			      		</th>
			      		<th>
			      		수령인
			      		</th>
			      		<th>
			      		배송상태
			      		</th>
			      		<th>
			      		비고
			      		</th>
			      	</tr>
			      </thead>
			      <tbody>
			      <c:forEach items="${list }" var="vo">
				       <tr role="row" class="odd" style="background-color: burlywood;">
				        <td class="sorting_1">
				        	<input type="checkbox" class="check"  name="odr_check" value="<c:out value="${vo.odr_code }" />">
				        </td>
				        <td>[<fmt:formatDate value="${vo.odr_date }" pattern="yyyy-MM-dd HH:mm:ss"/>]</td>
				        <td><span class="orderDetail" style="cursor:pointer;" data-odr_code="<c:out value="${vo.odr_code }" />">내역보기 : 주문번호 :[<c:out value="${vo.odr_code }" />]
				        	/상품건수 : (${vo.odr_count }) 건 <span style="color:red;"> [주문내역보기]</span></span>
				        </td>
				        <td><c:out value="${vo.mem_id }" /></td>
				        <td><c:out value="${vo.odr_name }" /></td>
				        <td>
							<select name="odr_delivery" id="odr_delivery_${vo.odr_code}">
								<option value="입금확인중" <c:out value="${vo.odr_delivery == '입금확인중' ? 'selected' : '' }" />>입금확인중</option>
								<option value="결제완료" <c:out value="${vo.odr_delivery == '결제완료' ? 'selected' : '' }" />>결제완료</option>
								<option value="상품준비중" <c:out value="${vo.odr_delivery == '상품준비중' ? 'selected' : '' }" />>상품준비중</option>
								<option value="배송준비중" <c:out value="${vo.odr_delivery == '배송준비중' ? 'selected' : '' }" />>배송준비중</option>
								<option value="배송중" <c:out value="${vo.odr_delivery == '배송중' ? 'selected' : '' }" />>배송중</option>
								<option value="배송완료" <c:out value="${vo.odr_delivery == '배송완료' ? 'selected' : '' }" />>배송완료</option>
								<option value="구매확정" <c:out value="${vo.odr_delivery == '구매확정' ? 'selected' : '' }" />>구매확정</option>
							</select>
							<button type="button" class="btnDeliveryState" data-odr_code="${vo.odr_code }">변경</button></td>
						</td>
				        <td><button type="button" class="btnOrderDelete" data-odr_code="${vo.odr_code }">주문삭제</button></td>
				      </tr>
				  </c:forEach>
			      </tbody>
			      
			  </table>
			</div>
			</div>
		<div class="row">
		<div class="col-sm-5">
		<div class="dataTables_info" id="example2_info" role="status" aria-live="polite">Showing 1 to 10 of 57 entries</div>
		</div>
		<div class="col-sm-7">
		<div class="dataTables_paginate paging_simple_numbers" id="example2_paginate">
			<ul class="pagination">
			<c:if test="${pageMaker.prev }">
				<li class="paginate_button previous" id="example2_previous">
					<a data-pagenum="${pageMaker.startPage - 1 }" class="page-link btnPagePrev" href="${pageMaker.startPage-1}" aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a>
				</li>
			</c:if>
			
			<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
				<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active" : ""} "><a data-pagenum="${num }" href="${num}" aria-controls="example2" data-dt-idx="1" tabindex="0">${num }</a></li>
			</c:forEach>
			
			<c:if test="${pageMaker.next }">
				<li class="paginate_button next" id="example2_next"><a class="page-link btnPageNext" href="${pageMaker.endPage+1}" aria-controls="example2" data-dt-idx="7" tabindex="0">Next</a></li>
			</c:if>
			</ul>
		</div>
		
		<!--  페이지 번호(prev,next포함)클릭시 아래 form이 작동되게 처리한다. -->
		<form id="actionForm" action="" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
			<input type="hidden" name="type" value="${pageMaker.cri.type}">
			<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
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
<%@include file="/WEB-INF/views/admin/includes/common.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script id="orderDetailTemplate" type="text/x-handlebars-template">
	{{#each .}}
  <tr class="addOrderDetail">
		<td><input type="checkbox" class="checkitem" ></td>
    <td scope="row">
      <a href="${pdt_num_pk }" class="detail">
        <img src="/product/displayFile?fileName={{pdt_img}}" alt="이미지" height="200px">
      </a>

    </td>
    <td> 상품명 : {{pdt_name}}</td>
    <td>가격 : {{odr_price}}</td>
    <td>수량 : {{odr_amount}}</td>
		<td></td>
    <td><button type="button" class="btnOrderProductDelete" data-odr_code="{{odr_code}}" data-pdt_num="{{pdt_num}}">주문상품삭제</button></td>

  </tr>
  {{/each}}
</script>
<script>

  $(function(){


      // 주문번호 선택한 행을 가리키는 tr태그참조.
    $("table td span.orderDetail").on("click", function () {
      let cur_tr = "";
      let odr_code = $(this).data("odr_code");

      console.log(odr_code);
      //return;

      cur_tr = $(this).parent().parent();

      $.ajax({
        url: '/admin/order/OrderDetailInfo/' + odr_code + ".json",
        //data: {odr_code: odr_code},
        type: 'get',
        success: function(data){
          
        	//console.log(data[0].pdt_num);
        	fn_userOrderDetail(data, cur_tr, $("#orderDetailTemplate"));
        }
      });
    });

		//주문내역 개별 상품삭제작업.
		//동적인 태그를 선택자로 할 경우 루트({{#each}}에서부터 선택자 명을 진행해야한다.
		$("table tbody").on("click","tr.addOrderDetail button.btnOrderProductDelete",function(){

			let cur_tr = "";
			cur_tr = $(this).parent().parent();
			let odr_code = $(this).data("odr_code");
			let pdt_num = $(this).data("pdt_num");
			console.log(odr_code);
			$.ajax({
				url: '/admin/order/orderProductDelete',
				type: 'post',
				dataType : 'text',
				data : {odr_code : odr_code , pdt_num : pdt_num},  // 자바스크립트 오브젝트 문법
				success : function(data){
						if(data == "success"){
							alert("주문 개별상품이 삭제가 되었습니다.");
							cur_tr.remove();
						}
				}

		});
  });


  

  let fn_userOrderDetail = function(userOrderDetails, target, templateObj) {
  let template = Handlebars.compile(templateObj.html());
  let html = template(userOrderDetails);
  
  console.log(html);
  
  target.siblings(".addOrderDetail").remove();

  target.after(html);

	}
});

	
</script>

<script>
  // $(document).ready(function(){});
	$(function(){

		let actionForm = $("#actionForm"); // 페이징, 검색정보를 가지고 있는 폼
		
		// 상품수정폼 페이지
		$(".btnEdit").on("click", function(){
			
			let pdt_num = $(this).data("pdt-num");
			
			console.log("edit: " + pdt_num);
			
			//브라우저-검사기능으로 폼정보태그를 확인하자.			
			actionForm.append("<input type='hidden' name='pdt_num' value='" + pdt_num  +"'>" );
			actionForm.attr("action", "/admin/product/edit"); // /board/list -> /board/get 주소변경
			
			actionForm.submit();
		});

		//주문삭제
		$(".btnOrderDelete").on("click", function(){
			
			let odr_code = $(this).data("odr_code");
			//<button type="button" class="btnOrderDelete" data-odr_code="주문번호">주문삭제</button>

			if(confirm("주문코드 " + odr_code + "를 삭제하겠습니까? " )){
				
				
				console.log("delete: " + odr_code);
				
				//브라우저-검사기능으로 폼정보태그를 확인하자.			
				actionForm.append("<input type='hidden' name='odr_code' value='" + odr_code  +"'>" );
				actionForm.attr("method", "post");
				actionForm.attr("action", "/admin/order/delete");
				actionForm.submit();
			}
		});



		//배송상태 변경
		$(".btnDeliveryState").on("click", function(){
			
			let odr_code = $(this).data("odr_code");
			let odr_delivery = $(this).prev().val();            //버튼 앞에 있던 select name="odr_delivery"

			if(confirm("주문코드 " + odr_code + "의 배송상태를" + odr_delivery +"로 변경하시겠습니까?" )){
				
				$.ajax({
					url: '/admin/order/deliverystatechange',
					type: 'post',
					dataType : 'text',
					data : {
						odr_code : odr_code,  // 배열
						odr_delivery : odr_delivery   // 배열
					},
					success : function(data){
						if(data == "success"){
						alert("배송상태가 변경이 되었습니다.");
//						actionForm.attr("action", "/admin/order/list"); // /board/list -> /board/get 주소변경
//						actionForm.submit();
						}
					}

					
				});
	
			}
		});


		//선택된 주문배송상태 변경
		$("#btnDeliveryChange").on("click",function(){

			if($("input[name=odr_check]:checked").length == 0){
				alert("배송상태 변경항목을 선택하세요.");
				return;
			}
			
			let result = confirm("배송상태를 변경 하겠습니까?");

			if(result){

				let odr_codeArr = []; //변경을 할 체크된 주문번호를 배열로 저장
				let odrDeliveryStateArr = []; // 체크된 주문번호의 배송상태를 배열로 저장

				//선택자 수만큼 반복
				$("input[name=odr_check]:checked").each(function(){
					let odr_code = $(this).val();
					odr_codeArr.push(odr_code);

					let odr_delivery = $("#odr_delivery_"+ odr_code).val();
					odrDeliveryStateArr.push(odr_delivery);
				});
				
				$.ajax({
					url: '/admin/order/deliveryChangeChecked',
					type: 'post',
					dataType : 'text',
					data : {
						odr_codeArr : odr_codeArr,  // 배열
						odrDeliveryStateArr : odrDeliveryStateArr   // 배열
					},
					success : function(data){
						if(data == "success"){
							alert("선택한 주문건이 배송상태가 변경됨.");
						}
					}

					
				});

				console.log(odr_codeArr);
				console.log(odrDeliveryStateArr);

			}


		});







		let isCheck = true;
		// attr(), prop() : 속성을 제어하는 기능
		//전체선택 checkbox.  
		$("#checkAll").on("click", function(){
			$(".check").prop("checked", this.checked);

			isCheck = this.checked;
		});

		// 개별선택 checkbox
		$(".check").on("click", function(){
			$("#checkAll").prop("checked", false);

			
			$(".check").each(function(){
				if(!$(this).checked){
					isCheck = false
				}
			});

			if(isCheck) $("#checkAll").prop("checked", isCheck);
		});

		// 선택삭제 버튼클릭시
		$("#btnChkDel").on("click", function(){
			if($(".check:checked").length == 0){
				alert("삭제할 상품을 선택해주세요.");
				return;
			};

			var result = confirm("선택한 상품을 삭제하시겠습니까?");
			if(result){

				// 선택한 상품코드, 이미지 정보를 배열로 저장
				let pdtnumArr = [];
				let imgArr = [];

				// 삭제하고 하는 선택된 체크박스의 개수.(상품)
				$(".check:checked").each(function(){
					let pdt_num = $(this).val();
					let pdt_img = $(this).next().val();

					pdtnumArr.push(pdt_num);
					imgArr.push(pdt_img);

					
				});

				console.log("선택상품: " + pdtnumArr);
				console.log("선택상품이미지: " + imgArr);

				$.ajax({
					url: '/admin/product/deleteChecked',
					type: 'post',
					dataType : 'text',
					data : {
						pdtnumArr : pdtnumArr,  // 배열
						imgArr : imgArr   // 배열
					},
					success : function(data){
						alert("선택된 상품이 삭제가 되었습니다.");

						// 폼전송
						
						actionForm.attr("action", "/admin/product/list"); // /board/list -> /board/get 주소변경
						actionForm.submit();
					}

					
				});


		};
	 });
		
		
		
	// 이전, 다음, 페이지번호 클릭시
	//페이지번호 클릭 작업  prev 1 2 3 4 5 next
	 $(".paginate_button  a").on("click", function(e){

		e.preventDefault(); // <a>태그의 링크기능을 비활성화

		actionForm.find("input[name='pageNum']").val($(this).attr("href"));  // 사용자가 선택한 페이지번호 변경
		actionForm.submit();	

		console.log("click");
	 });

	});

</script>

</body>
</html>