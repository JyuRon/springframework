<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<%@ include file="../includes/header.jsp" %>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- ./row -->


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			
			<div class="panel-heading">Board Read</div>
			<!-- /.panel-heading -->
			
			<div class="panel-body">
				
					
					
					<div class="form-group">
						<label>Bno</label>
						<input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
						
					</div>
					
					
					<div class="form-group">
						<label>Title</label>
						<input class="form-control" name='title' value='<c:out value="${board.title }"/>' readonly="readonly">
					</div>
					
					
					
					<div class="form-group">
          				<label>Text area</label>
          				<textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}" /></textarea>
        </div>
					
					
					<div class="form-group">
						<label>Writer</label>
						<input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
					</div>
					
					
					<button data-oper='modify' class="btn btn-default">Modify</button>
					<button data-oper='list' class="btn btn-info">List</button>
					
					
					<!-- 수정, 리스트 이동을 위한 form -->
					<form id='openForm' action="/board/modify" method="get">
						<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno }"/>'>
						
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





<script type="text/javascript">



$(document).ready(function(){
	
	
	//버튼의 종류 파악 후 action
	var openForm = $("#openForm");
	
	$("button[data-oper='modify']").on("click",function(e){
	
		openForm.attr("action","/board/modify").submit();
	});
	
	
	$("button[data-oper='list']").on("click",function(e){
		
		//리스트 이동시 bno정보는 필요 없기 때문
		openForm.find("#bno").remove();
		openForm.attr("action","/board/list");
		openForm.submit();
	});
	
});

</script>

<%@include file="../includes/footer.jsp" %>