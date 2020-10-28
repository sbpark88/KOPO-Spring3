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
      <input type="text" placeholder="title" name="title" value="${title }"/>
      <textarea name="memo" style="width: 100%;">${memo }</textarea>
      <input type="submit" value="수정 완료" />
    </form>
    <br /><br />
    <a href="list" style="padding: 10px 20px; background: #eee;">리스트로</a>
  </body>
</html>