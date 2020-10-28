$(document).on('click', '.btn', function(event){
  var code = $('#color').val().toUpperCase();
  alert("#000000 ~ #FFFFFF 사이의 16진수로 입력해주세요.")
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
