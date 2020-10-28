<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/kim.css">
<title>삭제하기</title>
</head>
<body>
<h3>학교 탈퇴하기</h3>
  <form action="delete_action" method = "post">
  	<h3>이 름</h3>
    <input type="text" name="name" value = "${name }" readonly = "readonly"/>
     <br />
    <h3>아 이 디</h3>
    <input type="text" name="id"  value = "${id }" readonly = "readonly"/>
    <br />
    <h3>담당 학급</h3>
    <input type="text" name="nclass" value = "${nclass }" readonly = "readonly"/>
    <br />
    <h3>주 소</h3>
    <input type="text" name="address" value = "${address }" readonly = "readonly"/>
    <br />
    <h3>번 호</h3>
    <input type="text" name="phone" value = "${phone }" readonly = "readonly"/>
     <br />
    <input type="submit" name="" value="탈퇴하기" />
  </form>
 <a href="/teamProject/">선생님 명단</a>