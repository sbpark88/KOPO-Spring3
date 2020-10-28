<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert teacher data</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/insert.css">
</head>
<body>
<form action="insert_action" method="post">
<strong>선생님 정보 등록</strong>
	<p>name</p><input type="text" name="name" placeholder="name">
	<p>id</p><input type="text" name="id" placeholder="id">
	<p>pw</p><input type="text" name="pw" placeholder="pw">
	<p>class</p><input type="text" name="nclass" placeholder="nclass">
	<p>address</p><input type="text" name="address" placeholder="address">
	<p>phone</p><input type="text" name="phone" placeholder="phone">
	<br>
	<br>
	<input type="submit" value="선생님 등록">
	<br>
	<br>
	<a href='/teamProject'> 메인페이지로 </a>
</form>
</body>
</html>