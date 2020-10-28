<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
  <head>
  	<title>데이터 조회</title>
  </head>
  <body>
    <a href="/people/insert">데이터 입력하러 가기</a><br>
    <table style="width: 100%;">
      <thead>
        <tr>
          <th>idx</th>
          <th>이름</th>
          <th>전화번호</th>
          <th>주소</th>
        </tr>
      </thead>
      <tbody>
        ${htmlString}
      </tbody>
    </table>
  </body>
</html>
