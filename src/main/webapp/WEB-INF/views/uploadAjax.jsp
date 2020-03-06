<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Upload with Ajax</h1>

<div class='uploadDiv'>


<input type='file' name="uploadFile" multiple>

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
			 success: function(result){
				 alert("Uploaded");
			 }
		 });
	 });
 });
 
 </script> 
  
</body>
</html>