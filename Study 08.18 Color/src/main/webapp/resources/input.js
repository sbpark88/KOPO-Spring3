$(document).on('click', '.btn', function(event){
  var code = $('#color').val();
  console.log(code);
  $.ajax({
    url:'/color/insert_action',
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
