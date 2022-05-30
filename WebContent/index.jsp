<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags"%>

<%-- <%
	request.setCharacterEncoding("utf8");
%> --%>
<!DOCTYPE html>
<html>
<head>
<title>Bank</title>

<style>
table, th, td {
	border: 1px solid red;
	padding: 3px;
	margin: 5px;
}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.1/dist/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>

<script>
	//var url = "http://localhost:8080/board";
	var url = "${pageContext.request.contextPath}";
</script>

</head>
<body>
<!-- 첫 화면 -->
	<c:if test="${empty authUser}">

		<a href="join.do">[회원가입]</a>
		<a href="login.do">[로그인]</a>
		<hr />
		<a href="article/notice.do">[공지사항]</a>
		<a href="article/list.do">[자유게시판]</a>
	</c:if>
<!-- 회원로그인 -->
	<c:if test="${! empty authUser && authUser.name ne 'manager'}">
	${authUser.name}님, 환영합니다!
	<!-- <a href="javascript:void(0)" onclick="logout()">[로그아웃하기]</a> -->
		<a href="logout.do">[로그아웃]</a>
		<a href="changePwd.do">[암호변경]</a>
		<a href="deleteMember.do">[회원탈퇴]</a>
		<hr />
		<a href="article/notice.do">[공지사항]</a>
		<a href="article/list.do">[자유게시판]</a>
	</c:if>
	<hr />
	
<!-- 관리자 로그인 -->
	<c:if test="${authUser.id eq 'manager'}">
	${authUser.name}님, 환영합니다!
	<!-- <a href="javascript:void(0)" onclick="logout()">[로그아웃하기]</a> -->
		<a href="logout.do">[로그아웃]</a>
		<a href="changePwd.do">[암호변경]</a>
		<hr />
		<a href="selectMember.jsp">[회원정보조회]</a>
		<a href="article/notice.do">[공지사항]</a>
		<a href="article/list.do">[자유게시판]</a>
	</c:if>

	<hr />

	<!-- <h5>증권상품 시세정보</h5> -->


</body>
</html>