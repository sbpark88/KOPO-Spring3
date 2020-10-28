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
		<input type="text" placeholder="제목" name="title" value="${title }" /><br />
		<textarea rows="5" cols="100" name="memo">${memo }</textarea><br />
		<input type="submit" value="수정 완료" />
	</form>
</body>
</html>