<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>find Id</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/findId.css">
</head>
<body>
<form action="findId_action" method="post">
<strong>이름/반/전화번호를 넣어주세요</strong>
<br>
<br>
	<p>name</p><input type="text" name="name" placeholder="name">
	<p>class</p><input type="text" name="nclass" placeholder="nclass">
	<p>phone</p><input type="text" name="phone" placeholder="phone">
	<br>
	<br>
	<input type="submit" value="ID 찾기">
	<br>
	<br>
	<a href='/teamProject'> 메인페이지로 </a>
</form>
</body>
</html>