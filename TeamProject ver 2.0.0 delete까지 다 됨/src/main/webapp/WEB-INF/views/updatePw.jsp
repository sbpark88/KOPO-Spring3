<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>선생 비밀번호 변경</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/insert.css">
</head>
<body>
	<form action="/teamProject/update_pw_action" method="post">
	<strong>선생님 정보</strong>
	<br>
	<br>
	${name } 선생님의 비밀번호를 변경합니다.
	<br><br>
	새 비밀번호를 입력하세요: <input type="password" name="password1">
	<br>
	새 비밀번호를 1번 더 입력하세요: <input type="password" name="password2">
	<br>
	<input type="submit" value="비밀번호 변경">
	<br>
	<br>
<a href='/teamProject/success'> 뒤로 돌아가기 </a><br>
</form>
</body>

</html>
