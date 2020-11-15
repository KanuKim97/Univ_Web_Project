<%@ page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="core.*" %>
<%@ page import="util.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
	sfu.setSizeMax(16 * 1024 * 1024);	// upto 16MB file
	sfu.setHeaderEncoding("UTF-8"); 
	
	List items = sfu.parseRequest(request);
	Iterator iter = items.iterator();
	
	String id = null, feed = null;
	JSONArray images = new JSONArray();
	while(iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		String name = item.getFieldName();
	
		if (item.isFormField()) {
			String value = item.getString("UTF-8").trim();
			if (name.equals("id")) {
				id = value;
			}
			else if (name.equals("feed")) {
				feed = value;
			}
		}
		else {
			if (name.equals("images")) {
				String fname = item.getName();
				images.add(fname);
				
				String base = application.getRealPath(File.separator) + "/users";
				Util.saveImage(base, id, fname, item.get());
			}
		}
	}
	
	FeedDAO dao = new FeedDAO();
	out.print(dao.insert(id, feed, images));
%>