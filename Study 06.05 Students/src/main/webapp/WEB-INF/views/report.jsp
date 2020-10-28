<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home.css">
	<title>성적표</title>
</head>
<body>
	<a href="/Students/">홈으로</a><br /><br />
	<a href="/Students/manageScore">성적 관리</a><br /><br />
	
	<table>
		<thead>
			<tr>
				<th>idx</th><th>이름</th><th>국어</th><th>영어</th><th>수학</th><th></th><th></th>
			</tr>
		</thead>
		<tbody>
			${testReport }
		</tbody>
	</table>
	
	

</body>
</html>