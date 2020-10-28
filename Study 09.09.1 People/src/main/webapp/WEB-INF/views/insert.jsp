<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>입력 페이지</title>
  </head>
  <body>
    <form action="/people/insert_action" method="post">
      <input type="text" name="name" placeholder="이름을 입력하세요.">
      <input type="text" name="phone" placeholder="전화번호를 입력하세요.">
      <input type="text" name="address" placeholder="주소를 입력하세요.">
      <input type="submit" value="입력">
    </form>
  </body>
</html>