<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Cookie[] cookies = request.getCookies();
if(cookies == null){
	return;
}
StringBuffer sb = new StringBuffer();
for(Cookie cookie : cookies){
	String cookieName = cookie.getName();
	String val = URLDecoder.decode(cookie.getValue(), "UTF-8");
	sb.append("document.cookie=" + cookieName + "=" + "escape('" + val +"') " + ";");
}
response.getOutputStream().write(sb.toString().getBytes());
out.clear();
out = pageContext.pushBody();
			
			
%>