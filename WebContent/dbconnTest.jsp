<%@page import="jdbc.JdbcUtil"%>
<%@page import="java.sql.SQLException"%>
<%@page import="jdbc.connection.ConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- filter에서 처리해서 모든 페이지에 사용할 필요 없음 -->
<%-- <%
	request.setCharacterEncoding("utf8");
%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	Connection conn = null;	
	try{
		conn = ConnectionProvider.getConnection();
		out.print("커넥션 성공");
	}catch(SQLException e){
		out.print("커넥션 실패" + e);
	}finally{
		JdbcUtil.close(conn);
	}
	
	
		

%>
</body>
</html>