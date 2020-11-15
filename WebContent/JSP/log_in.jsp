<%@ page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8" %>
<%@ page import="core.UserDAO" %>
<%
	request.setCharacterEncoding("UTF-8");

	String uid = request.getParameter("id");
	String upw = request.getParameter("ps");
	
	String sid = (String) session.getAttribute("uid");
	if(sid != null){
		out.print("EX");
		return;
	}
	
	UserDAO dao = new UserDAO();
	String code = dao.login(uid, upw);
	
	if(code != "EX" && code != "PS"){
		session.setAttribute("usrobj", code);
	}
	out.print(code);
	
%>