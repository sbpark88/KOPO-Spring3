<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html>
  <head>
	<title>Home</title>
  </head>
  <body>
    <h1>
	  Hello world!  
    </h1>
    <%
    int a = 10;
    int b = 5;
    int c = a + b;
    
    
    // jsp 안에 java코드 사용(이제는 하지 말 것)
    // jstl : jsp에서 태그 <> 형태로 제공하는 기능 (jsp 기반)(한국은 아직 여기...)
    // thymeleaf (<html> 기반) (spring boot 기본 방식 가장 최신이고 표준이다.)
    
    %>
    <P> axb+c=<%=a*b+c %>. </P> <!-- 대문자로 써도 되긴 하지만 html 표준은 태그를 소문자로 사용한다. <p> 이런식으로 사용 -->
    
    <!-- test라는 변수가 HomeController에게 Memo라는 객체를 넘겨 받아 그 객체의 title, memo 안에 원하는 value를 넣는다. -->
    <c:set target="${test }" property="title" value="야호호호"></c:set>
    <c:set target="${test }" property="memo" value="코로나야끝나라아아아"></c:set>
    <p> title :  <c:out value="${test.title }"></c:out> </p>
    <p> memo :  <c:out value="${test.memo }"></c:out> </p>
    
     <table style="width: 100%;">
      <tbody>
        <c:forEach var="each" items="${list }">
          <tr>
            <td>${each.getIdx() }</td>                        <!-- getter를 쓸 수도 있고, -->
            <td>${each.title }</td>                           <!-- c:out 태그를 생략하고 사용할 수도 있다. (편리!) -->
            <td><c:out value="${each.memo }"></c:out></td>    <!-- Full로 쓰면 c:out 태그를 넣어야 한다. -->
            <td><a href="u1?idx=${each.idx }">수정하기</a>
          </tr>
        </c:forEach>
      </tbody>
    </table>
        
  </body>
</html>
