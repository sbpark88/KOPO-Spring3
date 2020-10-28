$(function(){
  $.ajax({
    url:'/jsontes/info',
    data: {},
    success:function(data) {
      console.log(data);
      $('#name').text(data['name']);
      $('#version').text(data['version']);
      $('#version_code').text(data['version_code']);
    }
  });
});
