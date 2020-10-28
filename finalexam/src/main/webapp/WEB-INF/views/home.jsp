<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta charset="UTF-8">
  <title>Home</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.10.1-web/css/all.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/reset.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages.css" />
</head>
<body>
	<form action="signin_action" method = "post">
    <input type="text" name="id" placeholder="아이디를 입력해주세요" />
    <br />
    <input type="password" name="pw" placeholder="패스워드를 입력해주세요" />
    <br />
    <input type="submit" name="" value="로그인" />
  </form>
<a href='/finalexam/signup'> 회원가입 </a><br>
<a href='/finalexam/signout_action'> 로그아웃 </a><br>


</body>
</html>
