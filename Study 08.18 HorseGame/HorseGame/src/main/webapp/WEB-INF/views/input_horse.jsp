<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <a href="/horsegame/">홈</a>
  <a href="/horsegame/input_horse">말 정보 입력</a>
  <a href="/horsegame/horse_list">말 정보 보기</a>
  <form action="horse_insert" method="post">
    <input type="text" name="name" placeholder="말 이름 입력" />
    <input type="number" name="power" placeholder="파워 입력" />
    <input type="submit" value="입력 완료" />
  </form>
</body>
</html>