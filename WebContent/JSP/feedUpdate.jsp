<%@ page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8" %>
<%@ page import="core.*" %>
<%
	request.setCharacterEncoding("UTF-8");

	FeedDAO dao = new FeedDAO();
	
	out.print(dao.Update(request.getParameter("no"), request.getParameter("feed")));
%>
