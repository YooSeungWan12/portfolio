<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.fileDrop{
		width:100%;
		height:200px;
		border: 1px dotted blue;
	}
</style>


<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script>
$(document).ready(function(){

	//dragenter dragover  특정 영역에 이미지를 놨을때, 여는 이벤트
	$(".fileDrop").on("dragenter dragover",function(event){
		event.preventDefault();//를 없앤다.
	});

	$(".fileDrop").on("drop",function(event){
		event.preventDefault();

		//.fileDrop 선택자에 해당하는 태그영역에 드래그되었던 파일정보를 참조하는 구문 
		let files= event.originalEvent.dataTransfer.files;

		let file = files[0];
		//console.log(file);

		//<form>태그에 해당하는의미
		let formData = new FormData();
		//<input type="file">태그를 통한 첨부파일선택
		formData.append("file",file);

		//ajax를 통한 파일전송
		/*
		댓글기능에서 사용안한것들(기본값이 true)
		processData: true(기본값)
		contentType: true(기본값)
		*/

		$.ajax({  //uploadFormAction폼에 관련해서 쓴것.
			url:'/uploadAjax', //주소
			data: formData, //클라이언트에서 서버로 보내는 데이터(파일)
			dataType: 'text', //서버로부터 받은 응답데이터 포맷
			processData :false, //클라이언트에서 데이터를 서버로 보낼때, 내부적으로 쿼리스트링형태로 만들어진다. 하지만 파일전송시엔 사용하면안되서 false를 준다(기본값true)
			contentType: false, //enctype="multipart/form-data" 이 인코딩으로 데이터를 보내겠다는 소리.  true면 enctype="application/x-www-form-urlencoded"
			type: 'POST',
			success:function(data){
				//일반파일,이미지파일에 따라서 작업방향이 분기가된다.
				//console.log(data); // "/2021/08/24/s_7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg"
				//1)이미지 파일은 s_가 붙고(썸네일파일)
				//2)그외 파일은 s_가 없다.

				let str ="";

				if(checkImageType(data)){
					//이미지파일이면
					str = "<div><a href='displayFile?fileName=" + getImageLink(data) + "''>";
					str += "<img src='displayFile?fileName=" + data + "'/></a>";
					str += "<small style='cursor:pointer;' data-src=" + data + ">X</small></div>";

				}else{
					//일반 파일인경우
					str = "<div><a href='displayFile?fileName=" + data + "'>";
					str += getOriginalName(data) + "</a>";
					str += "<small style='cursor:pointer;' data-src=" + data + ">X</small></div>";
				}

				console.log(str);

				$(".uploadedList").append(str);


			}
		});
	});

	// X를 클릭해서 파일 삭제작업
	//uploadedList 안에 small태그누를시.
	$(".uploadedList").on("click","small",function(event){

		let that = $(this);

		$.ajax({
			url:"deleteFile",
			type:"post",
			data:{fileName: $(this).attr("data-src")},
			dataType:"text",
			success:function(result){
				if(result == 'deleted'){
					that.parent("div").remove();
				}
			}

		});

	});





});

function checkImageType(fileName){

	let pattern = /jpg|gif|png|jpeg/i;  //i : 대소문자 무시  그외 옵션 m  ,  g
	//jpg거나 gif거나 png이거나 jpeg이 존재하고있으면,
	//true 리턴
	return fileName.match(pattern);

}


//그림파일 사용함수:원본이미지파일명.
function getImageLink(fileName){
	if(!checkImageType(fileName)){
		return; //이미지가 아니면 걍 종료
	}
	//"/2021/08/24/s_7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg"
	let front = fileName.substr(0,12); // "/2021/08/23/"  0번부터 12-1번까지
	let end = fileName.substr(14); // s_이후 전부,

	return front + end;  // "/2021/08/24/7405e87f-c55f-47ff-9318-f26487b2da2_1.jpg"  즉 원본이미지파일명이됨.

}

//일반파일 사용함수
function getOriginalName(fileName){
	if(checkImageType(fileName)){
		return; //이미지가 아니면 걍 종료
	}

	let idx = fileName.indexOf("_") + 1; // ~~~_ 이후 전부.
	
	return fileName.substr(idx); //파일명을 읽어옴

}


</script>


</head>
<body>
	<h3>Ajax File Upload</h3>
	<div class="fileDrop"></div>
	
	
	<div class="uploadedList"></div>
	
</body>
</html>