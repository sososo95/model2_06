<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=euc-kr"%>

<%-- <%@ page import="com.model2.mvc.service.domain.*"%>

<%
	User vo=(User)session.getAttribute("user");
%>
 --%>
<html>
<head>
<title>Model2 MVC Shop</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">

</head>

<body topmargin="0" leftmargin="0">

	<table width="100%" height="50" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td width="800" height="30"><h2>Model2 MVC Shop</h2></td>
			<td height="30">&nbsp;</td>
		</tr>
		<tr>
			<td height="20" align="right" background="/images/img_bg.gif">
				<table width="200" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="115">
			<c:if test = "${user eq null}" >
<%-- 							<%
	          	if(vo == null) { %>  --%>
	          	<a href="/user/loginView.jsp" target="rightFrame">login</a>
<%-- 							<%
	          	} 
	          %> --%>
	         </c:if> 
						</td>
						<td width="14">&nbsp;</td>
						<td width="56">
			<c:if test = "${user ne null}" >
<%-- 							<%
	          	if(vo != null) { %>  --%>
	          	<a href="/logout.do" target="_parent">logout</a> 
<%-- 	          	<% } %> --%>
	        </c:if>
						</td>
					</tr>
				</table>
			</td>
			<td height="20" background="/images/img_bg.gif">&nbsp;</td>
		</tr>
	</table>

</body>
</html>
