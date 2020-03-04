<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<%@ include file="../includes/header.jsp" %>


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
					
					
					
					
					<button type="submit" data-oper='modify' class="btn btn-default" onclick="location.href='/board/modify?bno=<c:out value="${board.bno }"/>'">Modify</button>
					<button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
					<button type="submit" data-oper='list' class="btn btn-info" onclick="location.href='/board/list'">List</button>
					
					
				</form>
			
			</div>
			<!-- end panel-body -->
		
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->




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
			//리스트 이동시 파라미터는 필요 없기 때문...
			formObj.empty();
		}
		
		//최종적으로 submit 수행
		formObj.submit();
	});
});
</script>

<%@include file="../includes/footer.jsp" %>