<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.80.0">
    <title>Pricing example · Bootstrap v4.6</title>

   <!--  <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/pricing/"> -->

    

    <!-- Bootstrap core CSS,jquery -->
<%@ include file = "/WEB-INF/views/includes/common.jsp" %>
<script>
let msg = "${msg}";
if(msg == "modifySuccess"){
	alert("회원수정이 되었습니다.");
}
</script>




<meta name="theme-color" content="#563d7c">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    

  </head>
  <body>

    
    <!-- 상단메뉴 메인 -->
<%@ include file="/WEB-INF/views/includes/header.jsp" %>
<%@ include file="/WEB-INF/views/includes/mainCategory.jsp" %>




<div class="container">
	<div class="row">
	 
	 
	 	<div class="col-md-12">
	      <div class="row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
	      	        <div class="col-auto d-none d-lg-block">
	          <img src="/product/displayFile?fileName=${vo.pdt_img }" class="bd-placeholder-img" width="200" height="250" alt="">
	
	        </div>
	        <div class="col p-4 d-flex flex-column position-static">
	          <strong class="d-inline-block mb-2 text-primary">${vo.pdt_name}</strong>
	          	<b>가격:${vo.pdt_price }</b><br>
	          	<b>주문수량:<input type="number" id="cartAmount"></b><br>
	         	<b>제조사:${vo.pdt_company }</b><br>
	         	<div>
	         	<button type="button" class="btn btn-primary">buy</button>
	         	<button type="button" id="btnCartAdd" class="btn btn-primary">cart</button>
	         	</div>
	        </div>

	      </div>
    	</div>

	</div>
	
	<div class="row">
		<div class="col-md-12">
			${vo.pdt_detail }
		</div>
	</div>
	
		<div class="row">
		<div class="col-md-12">
			상품후기 표시.
		</div>
	</div>

<!-- 하단 -->
 	<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
</div>

<form id="actionForm" action="" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					<input type="hidden" name="type" value="${pageMaker.cri.type }">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
</form>


<script>

	$(function(){

		//장바구니 추가작업
		$("#btnCartAdd").on("click",function(){

			let pdt_num_pk = ${vo.pdt_num_pk};
			let cart_amount = $("#cartAmount").val();

			$.ajax({
				url : '/cart/cartAdd',
				type : 'post',
				dataType : 'text',
				data : {pdt_num_pk : pdt_num_pk, cart_amount : cart_amount},
				success: function(data){
					let result = confirm("장바구니에 추가되었습니다.\n 지금 확인하시겠습니까?")
					if(result){
						location.href="/cart/list";
					}
				}
			});

		});



	});


</script>



    
  </body>
</html>
