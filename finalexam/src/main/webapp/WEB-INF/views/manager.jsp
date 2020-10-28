<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>관리자 페이지</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.10.1-web/css/all.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/reset.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/pages.css" />
</head>
<body>
<header class="top_nav">
  <a href="/finalexam/"><i class="fas fa-user-friends"></i> 홈으로</a>
  <a href="/finalexam/signout_action"><i class="fas fa-clipboard-list"></i> 로그아웃</a>
  </header>
<section class="content">
      <table>
        <thead>
          <tr>
            <th>id</th><th>level</th><th>name</th><th>주소</th><th>전화번호</th><th>반려동물이름</th><th>가입일</th><th>수정일</th><th></th><th></th>
          </tr>
        </thead>
        <tbody>
          <tr class="row">
            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><button>수정</button></td><td><button>삭제</button></td>
          </tr>
        </tbody>
      </table>
</section>
</body>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	var a;
	$(function(){
	  $.ajax({
	    url:'/finalexam/manager_select',
	    type:'GET',
	    success:function(data) {
	    	a = data;
	    	console.log(data);
	      var row = $('.row');
	      $('.content table tbody').html('');
	      for (var i = 0; i < data.length; i++) {
	        var cloned_row = row.clone(true, true);
	        $('.content table tbody').append(cloned_row);
	        cloned_row.find('td').eq(0).text(data[i]['id']);
	        cloned_row.find('td').eq(1).text(data[i]['level']);
	        cloned_row.find('td').eq(2).text(data[i]['name']);
	        cloned_row.find('td').eq(3).text(data[i]['address']);
	        cloned_row.find('td').eq(4).text(data[i]['phone']);
	        cloned_row.find('td').eq(5).text(data[i]['petname']);
	        cloned_row.find('td').eq(6).text(data[i]['created']);
	        cloned_row.find('td').eq(7).text(data[i]['modified']);
	      }
	    },
	    error:function(request,status,error){

	        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

	    }

	  });
	});


	$(document).on('click', '.content table td:nth-child(9) button', function(event){
	  if ($(this).text() == '수정') {
		var level = $(this).parents('tr').find('td').eq(1).text();
		var name = $(this).parents('tr').find('td').eq(2).text();
		var address = $(this).parents('tr').find('td').eq(3).text();
		var phone = $(this).parents('tr').find('td').eq(4).text();
		var petname = $(this).parents('tr').find('td').eq(5).text();
			
		$(this).parents('tr').find('td').eq(1).html('<input type="text" value="' + level + '" />');
		$(this).parents('tr').find('td').eq(2).html('<input type="text" value="' + name + '" />');
		$(this).parents('tr').find('td').eq(3).html('<input type="text" value="' + address + '" />');
		$(this).parents('tr').find('td').eq(4).html('<input type="text" value="' + phone + '" />');
		$(this).parents('tr').find('td').eq(5).html('<input type="text" value="' + petname + '" />');
		$(this).text('완료');
	  } else if ($(this).text() == '완료') {
		$.ajax({
		  url: '/finalexam/manager_update',
		  type: 'POST',  // STS 3 에서는 PUT, DELETE가 안 되는 것 같다. WARN : org.springframework.web.servlet.PageNotFound - Request method 'PUT' not supported
		  data: {
			id : $(this).parents('tr').find('td').eq(0).text(),
			level : $(this).parents('tr').find('td').eq(1).find('input').val(),
			name : $(this).parents('tr').find('td').eq(2).find('input').val(),
			address : $(this).parents('tr').find('td').eq(3).find('input').val(),
			phone : $(this).parents('tr').find('td').eq(4).find('input').val(),
			petname : $(this).parents('tr').find('td').eq(5).find('input').val()
		},
		success:function(data) {
		  $('.content table td button').text('수정');
		  $(this).parents('tr').find('td').eq(1).html(level);
		  $(this).parents('tr').find('td').eq(2).html(name);
		  $(this).parents('tr').find('td').eq(3).html(address);
		  $(this).parents('tr').find('td').eq(4).html(phone);
		  $(this).parents('tr').find('td').eq(5).html(petname);
		  if (data['result'] == 'success') {
	        alert('수정을 완료했습니다.');
	      } else {
	        alert('서버에 오류가 있습니다. 관리자에게 문의하세요.');
	      }
		}
	    });
	  }
	});
	$(document).on('click', '.content table td:nth-child(10) button', function(event){
	if ($(this).text() == '삭제') {
	  var id = $(this).parents('tr').find('td').eq(0).text();
	  $.ajax({
		url: '/finalexam/manager_delete',
		type: 'POST',
		data: {
		  id : id : $(this).parents('tr').find('td').eq(0).text()
		},
		if (data['result'] == 'success') {
	        alert('삭제를 완료했습니다.');
	      } else {
	        alert('서버에 오류가 있습니다. 관리자에게 문의하세요.');
	      }
	  });
	}
	});

</script>
</html>
