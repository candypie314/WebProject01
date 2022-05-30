<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>게시글 읽기</title>
</head>
<body>
<form action="modify.do" method="post">
<input type="hidden" name="no" value="${articleData.article.number}">
<table border="1" width="100%">

<tr>
	<td>번호</td>
	<td>${articleData.article.number}</td>
</tr>
<tr>
	<td>작성자</td>
	<td>${articleData.article.writer.name}</td>
</tr>
<tr>
	<td>제목</td>
	<td><input class="art" name="title" value="<c:out value='${articleData.article.title}' />"disabled/></td>
</tr>
<tr>
	<td>내용</td>
	<td><textarea class="content" name="content" disabled><u:pre value='${articleData.content}'/></textarea></td>
</tr>
<tr>
	<td colspan="2">
		<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo}" />
		<a href="list.do?pageNo=${pageNo}">[목록]</a>
		<c:if test="${authUser.id == articleData.article.writer.id}">
		<a href="modify.do?no=${articleData.article.number}">[게시글수정]</a>
		<!-- <span id="mod">[게시글수정]</span> -->
		<a href="delete.do?no=${articleData.article.number}">[게시글삭제]</a>
		</c:if>
	</td>
</tr>
</table>
<input type="submit" value="글 수정" class="mod" style="display:none;">
</form>
<script type="text/javascript">
	document.getElementById("mod").addEventListener("click", disabledFalse);
	
	function disabledFalse(){
		document.querySelector('.title').disabled = false;
		document.querySelector('.content').disabled = false;
		document.querySelector('.mod').style.display = "block";
	}
</script>
</body>
</html>