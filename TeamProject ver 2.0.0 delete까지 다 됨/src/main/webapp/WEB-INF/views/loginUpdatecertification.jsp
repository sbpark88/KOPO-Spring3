<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/kim.css">
  <title>정보확인</title>
</head>

<body>
   <h3> 정보일치확인 </h3>
  <form action="/teamProject/update_certify_action" method = "post">
    <input type="text" name="id" placeholder="아이디를 입력해주세요" />
    <br />
    <input type="password" name="pw" placeholder="패스워드를 입력해주세요" />
    <br />
    <input type="submit" name="" value="로그인" />
  </form>

</body>

</html>