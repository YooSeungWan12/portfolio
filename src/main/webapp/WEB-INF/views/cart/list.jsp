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
<h3 style="text-align: center;">장바구니 목록</h3>
		
<table class="table table-bordered">
<colgroup>
	<col width="40%">
	<col width="20%">
	<col width="20%">
	<col width="20%">
</colgroup>
  <thead>
    <tr>
      <th scope="col">제품</th>
      <th scope="col">수량</th>
      <th scope="col">가격</th>
      <th scope="col">비고</th>
    </tr>
  </thead>
  <tbody> 
  <c:if test="${empty list }"> <!-- list가 비어있다면 -->
	<tr> 
		<td colspan="4">장바구니가 비어있습니다.</td>
	</tr>
  </c:if>
  
  <c:if test="${not empty list }">
  	<c:forEach items="${list }" var="vo">
    <tr class="cartrow">
      <td scope="row">
      	<input type="hidden" name="cart_code" value="${vo.cart_code }">
      	<img src="/cart/displayFile?fileName=${vo.pdt_img}" alt="">${vo.pdt_name }</td>
      <td>
        <input type="number" name="cart_amount_${vo.cart_code}" value="${vo.cart_amount}" style="width:70px;"> <input type="button" name="btnEdit" value="변경" data-cart-code="${vo.cart_code}"> 
      </td>
      <td>
      	<input type="hidden" name="pdt_price_${vo.cart_code}" value="${vo.pdt_price}">
      	${vo.pdt_price }
      </td>
      <td>
      	<input type="button" name="btnDel" value="삭제" data-cart-code="${vo.cart_code}">
      </td>
    </tr>
    </c:forEach>
</c:if>
	
  </tbody>
</table>
		
<c:if test="${not empty list }"> <!-- list가 비어있지않으면 총가격 호출 -->
<p id="cartTotalView">총 가격: <span id="cartTotal"></span></p>
</c:if>
</div>
<c:if test="${not empty list }"> 
   <div class="form-group text-center" id="cartOperationView">
       <button type="button" id="btnOrder" class="btn btn-primary">
           주문하기<i class="fa fa-check spaceLeft"></i>
       </button>
       <button type="button"  id="btnOrderDel" class="btn btn-warning">
           장바구니 비우기<i class="fa fa-times spaceLeft"></i>
       </button>
   </div>
</c:if>

<!-- 하단 -->
 	<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
</div>


<script>
	$(function(){
		
		cartTotalPrice();
    let curBtn;
    //수량변경   
    $("input[name=btnEdit]").on("click",function(){

      curBtn = $(this);
      let cart_code = $(this).data("cart-code");
      let cart_amount = $(this).parent().find("input[type=number]").val();
	
      console.log(cart_code);
      console.log(cart_amount);

      //return;

      $.ajax({
				url : '/cart/edit',
				type : 'post',
				dataType : 'text',
				data : {cart_code: cart_code,cart_amount:cart_amount},
				success: function(data){
			          if(data == "success"){
                  //$(this)가 인식이 안된다., 전역변수로 미리 참조를 해둬야한다.
			        
                  		alert("수량이 변경되었습니다.");
			            //curBtn.parent().parent().remove();
			          }
				}
			});

    });
    
    
	    //non-ajax
	    $("input[name=btnEdit2]").on("click",function(){
	    	
	    	let cart_code = $(this).data("cart-code");
	        let cart_amount = $(this).parent().prev().prev().find("input[type=number]").val();

	    	//get방식
	    	location.href="/cart/edit?cart_code=" + cart_code + "&cart_amount="+cart_amount;
	    });
	    
	    	
	    
	    //개별삭제(ajax)
	    $("input[name=btnDel]").on("click",function(){

	        curBtn = $(this);
	        let cart_code = $(this).data("cart-code");
	       

	        //return;

	        $.ajax({
	  				url : '/cart/delete',
	  				type : 'post',
	  				dataType : 'text',
	  				data : {cart_code: cart_code},
	  				success: function(data){
	  			          if(data == "success"){
	                   
	  			        	
	                    		alert("삭제되었습니다.");
	                    		curBtn.parent().parent().remove();
	                    		cartTotalPrice();
	  			            
	               
	                    		
	                    		if($("table tr.cartrow").length == 0){
	                    			$("#cartTotalView").hide();
	                    			$("#cartOperationView").hide();
	                    		}
	                    		
	                    		
	  			          }
	  				}
	  			});

	      });

	    //주문하기
        $("#btnOrder").on("click",function(){
          location.href="/order/orderCartInfo";
        });
        
        //장바구니 비우기
        $("#btnOrderDel").on("click",function(){
            location.href="/cart/delete";
          });
        
        
    
		
	});

	let totalPrice ;

	let cartTotalPrice = function(){
			//input태그면서, name에 cart_code를 가지고있는것
			
			totalPrice = 0;
			$("input[name=cart_code]").each(function(){
				

				let cart_code = $(this).val();
				let cart_price = $("input[name=pdt_price_" + cart_code + "]").val();
				let cart_amount = $("input[name=cart_amount_" + cart_code + "]").val();

				let unitTotalPrice = parseInt(cart_price) * parseInt(cart_amount);

				totalPrice += unitTotalPrice;
			});

			console.log(totalPrice);
			$("#cartTotal").text(totalPrice);

	}

</script>
    
  </body>
</html>
