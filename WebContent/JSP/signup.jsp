<%@ page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8" %>
<%@ page import="core.UserDAO" %>
<%
	request.setCharacterEncoding("UTF-8");
	String uid = request.getParameter("id");
	String upw = request.getParameter("ps");
	String uname = request.getParameter("Nick_Name");
	
	UserDAO dao = new UserDAO();
	String code = dao.signup(uid, upw, uname);
	out.print(code);
	
%>