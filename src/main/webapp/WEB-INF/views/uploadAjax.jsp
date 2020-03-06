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
}


.uploadResult ul li img{
	
	width: 200px;
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


<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
  
 
 
 <script>
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
		 
		 var str="";
		 
		 $(uploadResultArr).each(
				 
			function(i,obj){
				
				if(!obj.image){
					str += "<li><img src='/resources/img/attach.png'>" + obj.fileName + "</li>";
				}else{
					//str += "<li>" + obj.fileName + "</li>";
					
					var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
					
					str += "<li><img src='/display?fileName="+fileCallPath+"'></li>";
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