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
<%@ include file = "/WEB-INF/views/gamemall/common.jsp" %>

<script>

	$(function(){

		//장바구니 추가작업
		$(".btnCartAdd").on("click",function(){

			let pdt_num_pk = $(this).data("pdt_num_pk");
			let cart_amount = 1;

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


    

    //바로구매 추가작업
		$(".btnDirectOrder").on("click",function(){

			let pdt_num_pk = $(this).data("pdt_num_pk");
		
    		 location.href="/order/orderInfo?pdt_num_pk=" + pdt_num_pk;


		});




	});


</script>


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
<%@ include file="/WEB-INF/views/gamemall/header.jsp" %>
<%@ include file="/WEB-INF/views/gamemall/mainCategory.jsp" %>



<div class="container">

<!-- servlet-context.xml에 주소설정. -->
<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="/images/game1.jpg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="/images/game2.jpg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="/images/3.png" class="d-block w-100" alt="...">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>



   
<!-- 하단 -->
 	<%@ include file="/WEB-INF/views/gamemall/footer.jsp" %>
</div>



    
  </body>
  

  
</html>
