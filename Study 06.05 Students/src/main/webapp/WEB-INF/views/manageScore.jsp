<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sign_up.css">
	<title>성적 입력</title>
</head>
<body>
	<a href="/Students/">홈으로</a><br /><br />
	성적표 생성
	<form action="/Students/select_action" method = "post">
		번호 <input type="text" name="idx" placeholder="번호를 입력해주세요." />
		<input type="submit" name="" value="성적표 생성" />
	</form>
	
	<br /><br /><br />
	성적 입력
	<form action="/Students/insert_action" method = "post">
		이름 <input type="text" name="name" placeholder="이름을 입력해주세요." /><br />
		국어 <input type="text" name="kor" placeholder="국어 점수를 입력해주세요." /><br />
		영어 <input type="text" name="eng" placeholder="영어 점수를 입력해주세요." /><br />
		수학 <input type="text" name="math" placeholder="수학 점수를 입력해주세요." /><br />
		<input type="submit" name="" value="성적 입력" />
	</form>

</body>
</html>