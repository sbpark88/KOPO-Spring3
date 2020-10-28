<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호를 수정하세요</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/kim.css">
</head>
<body>
<form action="modifiedPw" method="post">
<input type="hidden" value="${id }" name="id">
<br />
새 비밀번호: <input type="password"  name="pw" id='pwd1' placeholder="새 비밀번호">

<input type="submit" value="PWD 수정완료">
</form>

</body>
</html>