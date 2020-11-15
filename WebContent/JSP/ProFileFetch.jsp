<%@ page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8" %>
<%@ page import="core.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	String uid = request.getParameter("id");

	UserDAO dao = new UserDAO();
	String code = dao.ProFileFetch(uid);
	out.print(code);
%>
