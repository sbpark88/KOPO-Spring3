<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <a href="/horsegame/horse_list">취소</a>
  <form action="update_action" method="post">
    <input type="hidden" name="idx" value=${idx} />
    <input type="text" name="name" value=${name} />
    <input type="number" name="power" value=${power} />
    <input type="submit" value="수정" />
  </form>
</body>
</html>