<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table style="width: 100%;">
	<thead>
	<tr>
	<th>idx</th><th>title</th><th>memo</th><th>created</th><th>updated</th><th></th>
	<tr>
	</thead>
	${list }
	</table>
	<a href="i1" style="padding: 10px 20px; background: #eee;">글쓰기</a>
</body>
</html>