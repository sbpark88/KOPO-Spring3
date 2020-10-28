<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Home</title>
</head>
<body>
  <a href="/horsegame/">홈</a>
  <a href="/horsegame/input_horse">말 정보 입력</a>
  <a href="/horsegame/horse_list">말 정보 보기</a>
  <table>
    <thead>
      <tr>
        <th>idx</th>
        <th>name</th>
        <th>power</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      ${list_string }
    </tbody>
  </table>
</body>
</html>