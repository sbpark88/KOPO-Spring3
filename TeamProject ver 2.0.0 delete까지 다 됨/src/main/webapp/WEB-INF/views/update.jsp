<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>선생 정보 변경</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/insert.css">
</head>
<body>
	<form action="/teamProject/update_action" method="post">
	<strong>선생님 정보</strong>
	<br>
	<br>
	이름: <input type="text" value="${name }" name="name" placeholder="name">
	<br>
	반: <input type="text" value="${nclass }" name="nclass" placeholder="nclass">
	<br>
	<br>
	주소: <input type="text" value="${address }" name="address" placeholder="address">
	<br>
	<br>
	전화번호: <input type="text" value="${phone }" name="phone" placeholder="phone">
	<br>
	<br>
	<input type="submit" value="정보 수정">
	<br>
	<br>
<a href='/teamProject'> 메인페이지로 </a><br>
<a href='/teamProject/delete_view'> 선생탈퇴 </a>
</form>
</body>

</html>
