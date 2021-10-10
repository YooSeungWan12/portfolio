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
		$(".btnDirectOrder").on("click",function(){

			let pdt_num_pk = $(this).data("pdt_num_pk");
		
    		 location.href="/order/orderInfo?pdt_num_pk=" + pdt_num_pk;


		});


	});


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
	
	  <c:if test="${empty list }"> <!-- list가 비어있다면 -->
	<tr> 
		<td colspan="4">상품목록이 비어있습니다. 금방 입고하겠습니다.</td>
	</tr>
	  </c:if>
	
	
	
	<c:if test="${not empty list }">
	  <c:forEach items="${list }" var="vo">
		<div class="col-md-3">
          <div class="card mb-4 shadow-sm">
  			<a href="${vo.pdt_num_pk }" class="detail">
  				<img src="/product/displayFile?fileName=${vo.pdt_img }" alt="이미지"  width="200px" height="200px">
  			</a>
            <div class="card-body">
              <p class="card-text">${vo.pdt_name }</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary btnDirectOrder" data-pdt_num_pk="${vo.pdt_num_pk }">buy</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary btnCartAdd" data-pdt_num_pk="${vo.pdt_num_pk }">cart</button>
                </div>
                <!-- <small class="text-muted">테스트</small> -->
              </div>
            </div>
          </div>
        </div>
		</c:forEach>
	</c:if>
		
	</div>


<div class="row">
			<div class="col-sm-5">
			<div class="dataTables_info" id="example2_info" role="status" aria-live="polite">&nbsp;</div>
			</div>
			<div class="col-sm-7">
			<div>
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
	</div>
</div>


<form id="actionForm" action="" method="get">
	<!-- 2차카테고리 코드를 포함시켜야한다. -->
	<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	<input type="hidden" name="type" value="${pageMaker.cri.type }">
	<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
</form>

<!-- 하단 -->
 	<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
</div>


<script>
	$(function(){
		
		let actionForm =  $("#actionForm");
		
		$("a.detail").on("click",function(e){
			e.preventDefault();//링크기능 중지
			
			let pdt_num = $(this).attr("href"); //선택한 값의 href값

			
			
			actionForm.append("<input type='hidden' name='pdt_num' value='"+ pdt_num + "'>");
			actionForm.attr("action","/product/get");
			actionForm.submit();
		});
		
		$(".paginate_button a").on("click",function(e){

			e.preventDefault();//a태그 링크기능비활성화

			actionForm.append("<input type='hidden' name='subCategory' value='"+ ${subCategory} + "'>");
			actionForm.find("input[name='pageNum']").val($(this).attr("href")); //액션폼에서  pageNum부분을 찾아서,값을 this의 href속성값 으로바꾼다.
			//사용자가 선택한 페이지 번호 변경  $(this)는 내가 누른곳의 href성격 값을바꿈. 
			//find는  자식들중 찾는것,  즉 actionForm의 하위요소 에 값을 넣어라.
			actionForm.attr("action","/product/alllist");
			actionForm.submit();
			//console.log("click");
		});
		
	
		
	});

</script>
    
  </body>
</html>
