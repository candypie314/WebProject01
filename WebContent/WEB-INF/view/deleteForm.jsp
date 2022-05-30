<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>가입</title>
</head>
<body>
<form action="deleteMember.do" method="post"> 
<h3>회원 탈퇴를 위해 아이디와 비밀번호를 재입력 해주세요.</h3>
<p>
	아이디:<br/><input type="text" name="id" value="${param.id}">
	<c:if test="${errors.id}">ID를 입력하세요.</c:if>
</p>
<p>
	암호:<br/><input type="password" name="password">
	<c:if test="${errors.password}">암호를 입력하세요.</c:if>
	<c:if test="${errors.duplicateId}">id나 암호가 틀렸습니다.</c:if>
</p>
<input type="submit" value="탈퇴하기" >
<script>

</script>
</form>
</body>
</html>