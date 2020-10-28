<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>데이터 입력</title>
  </head>
  <body>
    <form action="insert" method="post">
      <input type="text" placeholder="이름" name="name" />
      <input type="number" placeholder="나이" name="age" />
      <textarea name="memo" style="width: 100%;"></textarea>
      <input type="submit" value="입력 완료" />
    </form>
    <br /><br />
    <a href="list" style="padding: 10px 20px; background: #eee;">CRUD 메인</a>
  </body>
</html>