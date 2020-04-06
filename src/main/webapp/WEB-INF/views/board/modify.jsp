<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>    
<%@ include file="../includes/header.jsp" %>

<!-- 첨부파일 이미지 관련 css -->
<div class="bigPictureWrapper">
	<div class="bigPicture">
	</div>
</div>

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


<!-- /.첨부부파일 이미지 관련 css -->


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Modify</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- ./row -->


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			
			<div class="panel-heading">Board Modify</div>
			<!-- /.panel-heading -->
			
			<div class="panel-body">
				
				<form role="form" action="/board/modify" method="post">
					
					<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
					
					<div class="form-group">
						<label>Bno</label>
						<input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
						
					</div>
					
					
					<div class="form-group">
						<label>Title</label>
						<input class="form-control" name='title' value='<c:out value="${board.title }"/>'>
					</div>
					
					
					
					<div class="form-group">
          				<label>Text area</label>
          				<textarea class="form-control" rows="3" name='content'><c:out value="${board.content}" /></textarea>
        			</div>
					
					
					<div class="form-group">
						<label>Writer</label>
						<input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
					</div>
					
					
					<div class="form-group">
						<label>RegDate</label>
						<input class="form-control" name='regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate }"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>UpdateDate</label>
						<input class="form-control" name='updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate }"/>' readonly="readonly">
					</div>
					
					
					
					<sec:authentication property="principal" var="pinfo"/>
					
					<sec:authorize access="isAuthenticated()">
					
						<c:if test="${pinfo.username eq board.writer }">
							<button type="submit" data-oper='modify' class="btn btn-default" onclick="location.href='/board/modify?bno=<c:out value="${board.bno }"/>'">Modify</button>
							<button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
						</c:if>
					</sec:authorize>
				
					<button type="submit" data-oper='list' class="btn btn-info" onclick="location.href='/board/list'">List</button>
					
					
					<!-- 리스트, modify 로 이동시 기존 페이지 번호 유지 -->
						<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum }"/>'>
						<input type='hidden' name='amount' value='<c:out value="${cri.amount }"/>'>
						<input type='hidden' name='keyword' value= '<c:out value="${cri.keyword }"/>'>
                        <input type='hidden' name='type' value= '<c:out value="${cri.type }"/>'>
					
				</form>
			
			</div>
			<!-- end panel-body -->
		
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->


<!-- 첨푸파일 form -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Files</div>
			
			<div class="panel panel-body">
			
				<div class="form-group uploadDiv">
					<input type="file" name='uploadFile' multiple="multiple">
				</div>
			
				<div class='uploadResult'>
					<ul>
					</ul>
				</div>
				
			</div>
		
		</div>
	</div>
</div>

<!-- /.첨푸파일 form -->



<script type="text/javascript">
<!-- 버튼 종류에 따라 action처리  -->
$(document).ready(function(){
	
	var formObj = $("form");
	
	$('button').on("click",function(e){
		
		//버튼이 눌렸을때 일단 기존 동작을 중지
		e.preventDefault();
		
		
		//버튼의 종류 분류
		var operation = $(this).data("oper");
		
		console.log(operation);
		
		if(operation ==='remove'){
			formObj.attr("action","/board/remove");
		}else if(operation ==='list'){
			
			
			//self.location="/board/list";
			//return;
			
			
			formObj.attr("action","/board/list").attr("method","get");
			
			//게시물 수정 후 수정 내용을 확인하기 위한 작업과 페이지 번호를 유지하기 위한 방안
			 var pageNumTag = $("input[name='pageNum']").clone();
	      	var amountTag = $("input[name='amount']").clone();
	      	var keywordTag = $("input[name='keyword']").clone();
	      	var typeTag = $("input[name='type']").clone();      
			
			
			//파라미터값 삭제
			formObj.empty();
			
			//복사한 파라미터 적용
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(typeTag);
			formObj.append(keywordTag);
		}else if(operation === 'modify'){
			
			//첨부파일 관련 내용 추가
			console.log("submit clicked");
			
			var str ="";
			
			$(".uploadResult ul li").each(function(i,obj){
				
				var jobj = $(obj);
				
				console.dir(jobj);
				
				str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
		        str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
		        str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
		        str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ jobj.data("type")+"'>";
				
		        
		        console.log(str);
			});
			
			formObj.append(str).submit();
		}
		
		//최종적으로 submit 수행
		formObj.submit();
	});
});
</script>



<!-- 첨부파일 관련 script -->
<script>
$(document).ready(function() {
  (function(){
    
    var bno = '<c:out value="${board.bno}"/>';
    
 	 //첨부파일 목록 출력
    $.getJSON("/board/getAttachList", {bno: bno}, function(arr){
    
      console.log(arr);
      
      var str = "";


      $(arr).each(function(i, attach){
          
          //image type
          if(attach.fileType){
            var fileCallPath =  encodeURIComponent( attach.uploadPath+ "/s_"+attach.uuid +"_"+attach.fileName);
            
            str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' "
            str +=" data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
            str += "<span> "+ attach.fileName+"</span>";
            str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' "
            str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
            str += "<img src='/display?fileName="+fileCallPath+"'>";
            str += "</div>";
            str +="</li>";
          }else{
              
            str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' "
            str += "data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
            str += "<span> "+ attach.fileName+"</span><br/>";
            str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' "
            str += " class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
            str += "<img src='/resources/img/attach.png'></a>";
            str += "</div>";
            str +="</li>";
          }
       });

      
      $(".uploadResult ul").html(str);
      
    });//end getjson
  })();//end function
  
	
  //삭제버튼 클릭시 화면에서만 사라지게 한다.
  $(".uploadResult").on("click", "button", function(e){
	    
    console.log("delete file");
      
    if(confirm("Remove this file? ")){
    
      var targetLi = $(this).closest("li");
      targetLi.remove();
    }
  });  
  
  
  
  //첨부파일 크기, 타입 검사
  var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
  var maxSize = 5242880; //5MB
  
  function checkExtension(fileName, fileSize){
    
    if(fileSize >= maxSize){
      alert("파일 사이즈 초과");
      return false;
    }
    
    if(regex.test(fileName)){
      alert("해당 종류의 파일은 업로드할 수 없습니다.");
      return false;
    }
    return true;
  }
  
	
  	//스프링 시큐리티 적용으로 인한 오류 해결
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}";
  
  //첨부파일 변경 감지
  $("input[type='file']").change(function(e){

    var formData = new FormData();
    
    var inputFile = $("input[name='uploadFile']");
    
    var files = inputFile[0].files;
    
    for(var i = 0; i < files.length; i++){

      if(!checkExtension(files[i].name, files[i].size) ){
        return false;
      }
      formData.append("uploadFile", files[i]);
      
    }
    
    $.ajax({
      url: '/uploadAjaxAction',
      processData: false, 
      contentType: false,data: 
      formData,type: 'POST',
      beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		},
      dataType:'json',
        success: function(result){
          console.log(result); 
		  showUploadResult(result); //업로드 결과 처리 함수 

      }
    }); //$.ajax
    
  });    

 
	
  //업로드 결과 반영 확인
  function showUploadResult(uploadResultArr){
	    
    if(!uploadResultArr || uploadResultArr.length == 0){ return; }
    
    var uploadUL = $(".uploadResult ul");
    
    var str ="";
    
    $(uploadResultArr).each(function(i, obj){
		
    	//image type
		if(obj.image){
			var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
			str += "<li data-path='"+obj.uploadPath+"'";
			str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
			str +=" ><div>";
			str += "<span> "+ obj.fileName+"</span>";
			str += "<button type='button' data-file=\'"+fileCallPath+"\' "
			str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			str += "<img src='/display?fileName="+fileCallPath+"'>";
			str += "</div>";
			str +="</li>";
		}else{
			var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);			      
		    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
		      
			str += "<li "
			str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
			str += "<span> "+ obj.fileName+"</span>";
			str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' " 
			str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			str += "<img src='/resources/img/attach.png'></a>";
			str += "</div>";
			str +="</li>";
		}

    });
    
    uploadUL.append(str);
  }
  
});
</script>
<!-- /.첨부파일 관련 script -->
<%@include file="../includes/footer.jsp" %>