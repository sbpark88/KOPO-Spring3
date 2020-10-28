<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>목록 조회</title>
  </head>
  <body>
    <table style="width: 100%;">
      <thead>
        <tr>
          <th>idx</th>
          <th>name</th>
          <th>age</th>
          <th>memo</th>
          <th>created</th>
          <th>updated</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        ${list }
      </tbody>
    </table>
    <br />
    <a href="i1" style="padding: 10px 20px; background: #eee;">글쓰기</a>
  </body>
</html>