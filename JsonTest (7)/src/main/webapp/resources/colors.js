$(function(){
  var li_elements = $('.content li');
  for (var i = 0; i < li_elements.length; i++) {
    var red = Math.floor(Math.random() * 256);
    var green = Math.floor(Math.random() * 256);
    var blue = Math.floor(Math.random() * 256);
    li_elements.eq(i).css({'background': 'rgb(' + red + ',' + green + ',' + blue + ')'});

    var color_code = '#' + decimalToHex(red) + decimalToHex(green) + decimalToHex(blue);
    li_elements.eq(i).text(color_code);
  }
});

function decimalToHex(number) {
  if (number < 16) {
    return '0' + number.toString(16).toUpperCase();
  }
  return number.toString(16).toUpperCase();
}

$(document).on('click', '.content li', function(event){
  var code = $(this).text();
  $.ajax({
    url:'/jsontes/insert_action',
    data: {'code': code},
    success:function(data) {
      if (data['result'] == 'success') {
        alert('선택된 색상을 저장했습니다.');
      } else {
        alert('서버에 오류가 있습니다. 관리자에게 문의하세요.');
      }
    }
  });
});
