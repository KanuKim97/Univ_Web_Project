<%@ page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8" %>
<%@ page import="core.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	
	String uid = request.getParameter("id");
	String univ = request.getParameter("Univ");
	String ugrade = request.getParameter("Grade");
	String uname = request.getParameter("name");
	
	UserDAO dao = new UserDAO();
	String code = dao.ProFileUpdate(uid, univ, ugrade, uname);
	out.print(code);
%>
