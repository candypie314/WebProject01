<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>문의 사항</title>
</head>
<body>
<form action="write.do" method="post">
	
<h3>게시글 작성</h3>
<p>
	제목:<br/><textarea name="title" rows="3" cols="50" >${param.content}</textarea>
	<c:if test="${errors.title}">제목을 입력하세요.</c:if>
</p>
<!-- 질문 카테고리 입력 -->
<p>
	내용:<br/>
	<textarea name="content" rows="10" cols="50" >${param.content}</textarea>
</p>

<input type="submit" value="등록">
</form>
</body>
</html>