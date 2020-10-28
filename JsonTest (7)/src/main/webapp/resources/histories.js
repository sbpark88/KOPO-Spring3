$(function(){
  $.ajax({
    url:'/jsontes/history_action',
    success:function(data) {
      console.log(data);
      var row = $('.row');
      $('.content table tbody').html('');
      for (var i = 0; i < data.length; i++) {
        var cloned_row = row.clone(true, true);
        $('.content table tbody').append(cloned_row);
        cloned_row.find('td').eq(0).text(data[i]['idx']);
        cloned_row.find('td').eq(1).text(data[i]['code']);
        cloned_row.find('td').eq(1).css({'background': data[i]['code']});
        cloned_row.find('td').eq(2).text(data[i]['selectedDate']);
      }
    }
  });
});

$(document).on('click', '.content table td button', function(event){
	  if ($(this).text() == '수정하기') {
	    var code_text = $(this).parents('tr').find('td').eq(1).text();
	    $(this).parents('tr').find('td').eq(1).html('<input type="text" value="' + code_text + '" />');
	    $(this).text('완료');
	  } else if ($(this).text() == '완료') {
	    $.ajax({
	      url: '/jsontes/update_action',
	      data: {
	        'idx': $(this).parents('tr').find('td').eq(0).text(),
	        'code': $(this).parents('tr').find('td').eq(1).find('input').val()
	      },
	      success:function(data) {
	        $('.content table td button').text('수정하기');
	      }
	    });
	  }
	});