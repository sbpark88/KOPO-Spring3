$(function(){
	$.ajax({
	  url:'/CRUD_jQuery/search_category',
	  data:{
	  },
	  success:function(res){
	    for(var i=0;i<res.length;i++) {
	      var category  = res[i];
	      $("#category").append(`<option value="${category}">${category}</option>`)
	    }
	  }
	});
});

$(function(){
	$("#writeBtn").click(function() {
		var category = $("#category").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var nickname = $("#nickname").val();
		var password = $("#password").val();

//		// Map을 사용하기 위해 객체 생성
//		let category = {name: "category"},
//			   title = {name: "title"},
//			 content = {name: "content"};
//		// Map 인스턴스화
//		let newpost = new Map();
//		// Map에 값 넣기
//		newpost.set(category, $("#category").val());
//		newpost.set(title, $("#title").val());
//		newpost.set(content, $("#content").val());
//		console.log(newpost.get(category),newpost.get(title),newpost.get(content));
		
		$.ajax({
			url: '/CRUD_jQuery/content_write',
			type: 'POST',
			dataType: 'json',
			data: {
				category: category,
				title: title,
				content: content,
				nickname: nickname,
				password: password
			},
			success: function(res) {
				alert('글쓰기가 완료되었습니다.\n메인 페이지로 이동합니다.');
				location.href = 'home.html';
			}
		});
	});
});

$(function(){
	$("#returnBtn").click(function(){
		location.href = "home.html";
	});
});

