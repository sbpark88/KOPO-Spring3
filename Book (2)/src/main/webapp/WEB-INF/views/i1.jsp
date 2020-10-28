<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(document).on('click', 'input[type="button"]', function(event){
		$.ajax({
			url:"insert_api",
			dataType:"json",
			type:"get",
			data:{
				'name':$('input[name="name"]').val(),
				'phone':$('input[name="phone"]').val()
			},
			success:function(data){
				console.log(data);
				alert('입력이 완료되었습니다.');
				history.go(-1);
			}
		});		
	});

</script>
</head>
<body>
<!--
	<form action="insert">
		<input type="text" placeholder="이름" name="name" />
		<input type="phone" placeholder="전화번호" name="phone" />
		<input type="submit" value="입력 완료" />
	</form>
-->
<input type="text" placeholder="이름" name="name" />
<input type="phone" placeholder="전화번호" name="phone" />
<input type="button" value="입력 완료" />

</body>
</html>