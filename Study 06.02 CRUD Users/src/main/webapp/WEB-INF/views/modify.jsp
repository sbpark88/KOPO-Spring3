<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sign_up.css">
		<title>정보수정</title>
	</head>
	<body>
		<h1>정보수정</h1>
		<a href="/Users/">홈으로</a><br /><br />
		
		<!-- form 태그는 type="submit" 버튼을 누르면 action을 실행한다. name은 각각 파라미터 이름이 되고, 입력된 값은 각 파라미터의 값이 된다. HomeController에서 HttpServletRequest가 .getParameter를 통해 파라미터와 값에 접근한다. -->
	 	<form action="/Users/modify_action" method = "post">
	 		<input type="hidden" name="idx" value="${idx }" />	<!-- value에 DB에 있는 현재 정보를 불러와 띄워준다. -->
	 		이름 <input type="text" name="name" placeholder="이름을 입력해주세요" value="${name }" />
		    <br />
		    ID <input type="text" name="id" placeholder="아이디를 입력해주세요"  value="${id }" />
<!-- 		    <br />
		    <input type="password" name="password" placeholder="패스워드를 입력해주세요" /> -->
		    <br /><br />
	    	<input type="submit" name="" value="수정하기" />
	 	</form>
	 	
	</body>
</html>