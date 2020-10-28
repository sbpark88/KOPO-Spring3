<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>데이터 수정</title>
  </head>
  <body>
    <form action="update" method="post">
      <input type="hidden" name="idx" value="${idx }" />
      <input type="text" placeholder="이름" name="name" value="${name }"/>
      <input type="number" placeholder="나이" name="age" value="${age }"/>
      <textarea name="memo" style="width: 100%;">${memo }</textarea>
      <input type="submit" value="수정 완료" />
    </form>
    <br /><br />
    <a href="list" style="padding: 10px 20px; background: #eee;">CRUD 메인</a>
  </body>
</html>