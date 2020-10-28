<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<title>Home</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home.css">
	</head>
	<body>
		<h1>
			User Data
		</h1>
		<a href="/Users/private">개인정보</a><br /> <!-- /Users/ 는 생략 가능하다. private만 써도 된다. -->
		<a href="/Users/signin">로그인</a>&nbsp;&nbsp;<a href="/Users/signout">로그아웃</a>&nbsp;&nbsp;<a href="/Users/signup">회원가입</a><br/><br/>
		<table>
			<thead>
				<tr>
					<th>idx</th><th>이름</th><th>아이디</th><th></th>
				</tr>
			</thead>
			<tbody>
				${userInfo }
			</tbody>
		</table>
	</body>
</html>
