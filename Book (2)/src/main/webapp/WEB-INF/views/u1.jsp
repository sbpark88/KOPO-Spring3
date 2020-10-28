<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="update">
		<input type="hidden" name="idx" value="${idx }" />
		<input type="text" placeholder="이름" name="name" value="${name }" />
		<input type="number" placeholder="1번째 시험 성적" name="score1" value="${score1 }" />
		<input type="number" placeholder="2번째 성적" name="score2" value="${score2 }" />
		<input type="submit" value="수정 완료" />
	</form>
</body>
</html>