<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<table style="width: 100%;">
<tbody>
	<c:forEach var="row" items="${list }">
	<tr>
	<td>${row.idx }</td>
	<td>${row.title }</td>
	<td><c:out value="${row.memo }"></c:out></td>
	<td><a href="u1?idx=${row.idx }">수정하기</a></td>
	</tr>		
	</c:forEach>
</tbody>
</table>

</body>
</html>
