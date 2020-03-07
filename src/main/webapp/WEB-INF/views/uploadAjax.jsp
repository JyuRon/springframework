<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<style>
.uploadResult{

	width: 100%;
	background-color: gray;
	
}

.uploadResult ul{

	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
	
}


.uploadResult ul li{

	list-style: none;
	padding: 10px;
	align-content: center;
	text-align: center;
}


.uploadResult ul li img{
	
	width: 100px;
}

.uploadResult ul li span{

	color:white;
}


.bigPictureWrapper{
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top:0%;
	width:100%;
	height:100%;
	background-color: gray;
	z-index: 100;
	background:rgba(255,255,255,0.5);
	

}


.bigPicture{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img{
	width:600px;
}



</style>
</head>
<body>
<h1>Upload with Ajax</h1>

<div class='uploadDiv'>


<input type='file' name="uploadFile" multiple>

</div>

<div class="uploadResult">
	<ul>
	
	</ul>
</div>


<button id='uploadBtn'>Upload</button>

<div class="bigPictureWrapper">
	<div class="bigPicture">
	</div>
</div>


<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
  
 
 
 <script>
 
 //이미지 클릭시 크게 보기
 function showImage(fileCallPath){
	 
	 //alert(fileCallPath);
	 
	  $(".bigPictureWrapper").css("display","flex").show();
	  
	  $(".bigPicture")
	  .html("<img src='/display?fileName="+fileCallPath+"'>")
	  .animate({width:'100%', height: '100%'}, 1000);
	 
 }
 
 
 //이미지 재 클릭시 사라짐
 $(".bigPictureWrapper").on("click", function(e){
	  $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
	  setTimeout(() => {
	    $(this).hide();
	  }, 1000);
	});
 
 
 //첨부파일 삭제 구현
 $(".uploadResult").on("click","span", function(e){
	   
	  var targetFile = $(this).data("file");
	  var type = $(this).data("type");
	  console.log(targetFile);
	  
	  $.ajax({
	    url: '/deleteFile',
	    data: {fileName: targetFile, type:type},
	    dataType:'text',
	    type: 'POST',
	      success: function(result){
	         alert(result);
	       }
	  }); //$.ajax
	  
	});
 
 
 $(document).ready(function(){
	 
	 var regex = new RegExp("(.*?)\.(exe|sh|zip|alx)$");
	 var maxSize = 5242880; //5MB
	 
	 function checkExtension(fileName, fileSize){
		 
		 if(fileSize >= maxSize){
			 alert("파일 사이즈 초과");
			 return false;
		 }
		 
		 if(regex.test(fileName)){
			 alert("해당 종료의 파일은 업로드할 수 없습니다.");
			 return false;
		 }
		 
		 return true;
	 }
	 
	 //첨부파일 등록 후 초기화
	 var cloneObj = $(".uploadDiv").clone();
	 
	 
	 //첨부파일 업로드 확인결과
	 var uploadResult = $(".uploadResult ul");
	 
	 function showUploadedFile(uploadResultArr){
		 
		   var str = "";
		   
		   $(uploadResultArr).each(function(i, obj){
		     
		     if(!obj.image){
		       
		       var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
		       
		       var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
		       
		       str += "<li><div><a href='/download?fileName="+fileCallPath+"'>"+
		           "<img src='/resources/img/attach.png'>"+obj.fileName+"</a>"+
		           "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+
		           "<div></li>"
		           
		     }else{
		       
		       var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
		       
		       var originPath = obj.uploadPath+ "\\"+obj.uuid +"_"+obj.fileName;
		       
		       originPath = originPath.replace(new RegExp(/\\/g),"/");
		       
		       str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\">"+
		              "<img src='display?fileName="+fileCallPath+"'></a>"+
		              "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+
		              "</li>";
		     }
		   });
		 
		 uploadResult.append(str);
	 }

	 
	 $("#uploadBtn").on("click",function(e){
		
		 var formData = new FormData();
		 
		 var inputFile = $("input[name='uploadFile']");
		 
		 var files = inputFile[0].files;
		 
		 console.log(files);
		 
		 
		 //add filedate to formdata
		 for(var i = 0; i< files.length; i++){
			 
			 if(!checkExtension(files[i].name, files[i].size)){
				 return false;
			 }
			 
			 formData.append("uploadFile",files[i]);
		 }
		 
		 
		 $.ajax({
			
			 url: '/uploadAjaxAction',
			 processData: false,
			 contentType: false,
			 data: formData,
			 type: 'POST',
			 dataType:'json',
			 success: function(result){
				 console.log(result);
				 alert("Uploaded");
				
				 //첨부파일 등록 후 확인
				 showUploadedFile(result);
				 
				 //첨부파일 등록 후 초기화
				 $(".uploadDiv").html(cloneObj.html());
			 }
		 });
		 
		 
	 });
	 
	 
	 
	 
	 
 });
 
 </script> 
  
</body>
</html>