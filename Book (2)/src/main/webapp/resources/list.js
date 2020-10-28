	$(function(){
		$.ajax({
			url:"select_api",
			dataType:"json",
			type:"get",
			data:{
			},
			success:function(data){
				console.log(data);
				if (data && data.length) {
					for(var i = 0; i < data.length; i++) {
						var row = "<tr>";
						row = row + '<td>' + data[i].idx + '</td>';
						row = row + '<td>' + data[i].name + '</td>';
						row = row + '<td>' + data[i].phone + '</td>';
						row = row + '<td>' + data[i].created + '</td>';
						row = row + '<td>' + data[i].updated + '</td>';
						row = row + '</tr>';
						$('#list').append(row);
					}
				}
			}
		});		
	});