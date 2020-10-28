<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Message</title>
  </head>
  <body>
    <P>${message}</P>
    <a href="home">홈으로</a><br />
    <a href="list">CRUD 메인</a><br />
  </body>
</html>