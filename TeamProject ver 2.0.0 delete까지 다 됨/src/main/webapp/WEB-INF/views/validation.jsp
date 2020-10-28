<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>verify</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/insert.css">
</head>
<body>
	<form action="/teamProject/${destination }" method="post">
	<strong>비밀번호 확인</strong>
	<br>
	<br>
	비밀번호: <input type="password" name="pw" placeholder="비밀번호를 입력하세요.">
	<input type="submit" value="확인">
	<br>
	<br>
</form>
</body>
</html>