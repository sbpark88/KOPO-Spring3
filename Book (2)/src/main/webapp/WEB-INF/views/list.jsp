<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/list.js"></script>
</head>
<body>

	<table style="width: 100%;">
	<thead>
	<tr>
	<th>idx</th><th>name</th><th>phone</th><th>생성일</th><th>수정일</th><th></th>
	<tr>
	</thead>
	<tbody id="list">
	
	</tbody>
	</table>
	<a href="i1" style="padding: 10px 20px; background: #eee;">글쓰기</a>
</body>
</html>