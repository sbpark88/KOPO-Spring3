<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/findId.css">
</head>
<body>
<form action="findPw_action" method="post">
<strong>정보를 입력해주세요</strong>
<br>
<br>
	<p>name</p><input type="text" name="name" placeholder="name">
	<p>id</p><input type="text" name="id" placeholder="id">
	<p>H.P</p><input type="text" name="phone" placeholder="phone">
	<br>
	<br>
	<input type="submit" value="PW 찾기">
	<br>
	<br>
	<a href='/teamProject'> 메인페이지로 </a>
</form>
</body>
</html>