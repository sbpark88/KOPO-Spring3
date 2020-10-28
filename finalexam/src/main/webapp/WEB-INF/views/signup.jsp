<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>회원가입</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.10.1-web/css/all.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/reset.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages.css" />
</head>
<body>
<form action="signup_action" method="post">
<strong>회원가입</strong><br><br>
	ID <input type="text" name="id" placeholder="id"><br>
	PW <input type="password" name="pw" placeholder="pw"><br>
	이름 <input type="text" name="name" placeholder="name"><br>
	주소 <input type="text" name="address" placeholder="address"><br>
	핸드폰 <input type="text" name="phone" placeholder="phone"><br>
	반려동물 이름 <input type="text" name="petname" placeholder="petname"><br>
	<br>
	<input type="submit" value="회원가입">
	<br>
	<br>
</form>

<a href='/finalexam/'> 홈으로 </a><br>
<a href='/finalexam/signout_action'> 로그아웃 </a><br>

</body>
</html>