<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sign_up.css">
    <title>로그인</title>
  </head>
  <body>
  	<h3>로그인</h3>
  	<a href="/Users/">홈으로</a><br /><br />
  	
    <form action="/Users/signin_action" method="post">
      ID <input type="text" name="id" placeholder="아이디를 입력해주세요" />
      <br />
      PW <input type="text" name="password" placeholder="패스워드를 입력해주세요" />
      <br />
      <input type="submit" value="로그인" />
    </form>
  </body>
</html>