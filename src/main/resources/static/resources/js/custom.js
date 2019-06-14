$(document).ready(function(){
	
	$("#namecheck").focusout(function(){
		var name = $(this).val();
		$.ajax({
			url:"/checkusernameandemail",
			type:"GET",
			data:{
				type:'1',
				name:name
			},
			success:function(value){
				if(value=="true"){
					$("#report").text("Tên user name đã có người sử dụng , chọn tên khác!")
					$("#report").css("color","red");
					$("#btn-singup").prop('disabled', true);
					
				}else
				{
					$("#report").text("Bạn có thể sử dụng user name này")
					$("#report").css("color","black");
					$("#btn-singup").prop('disabled', false);
				}
			}
		})
	})
	
	$("#emailcheck").focusout(function(){
		var email = $(this).val();
		$.ajax({
			url:"/checkusernameandemail",
			type:"GET",
			data:{
				type:'0',
				name:email
			},
			success:function(value){
				if(value=="true"){
					$("#report").text("Email có người sử dụng , chọn email khác!")
					$("#report").css("color","red");
					$("#btn-singup").prop('disabled', true);
					
				}else
				{
					$("#report").text("Bạn có thể sử dụng email này")
					$("#report").css("color","black");
					$("#btn-singup").prop('disabled', false);
				}
			}
		})
	})
	
	//adđ to carrt
	$(".add-single-cart").click(function(e){
		e.preventDefault();
		var soluong = $(this).closest("form").find("input[name=soluong]").val();
		var pid = $(this).closest("form").find("input[name=pid]").val();
		$.ajax({
			url:"/cart",
			type:"POST",
			data:{
				pid:pid,
				soluong:soluong
			},
			success:function(value){
				$.notify("Đã thêm "+soluong+" sản phẩm vào giỏ hàng","success");
			}
		})
	})
	
	$("#update-cart").click(function(){
		var data ='';
		$(".cart-table > tbody > tr").each(function(){
			idsp =  $(this).find("td").find("#idp").text();
			soluong = $(this).find("td").find("input[name=quantity]").val();
			iditem = $(this).find("td").find("#idp").attr("data-iditem");
			
			data+=idsp+'/'+soluong+"/"+iditem+",";
		})
		console.log(data);
		$.ajax({
			url:"/cart/update",
			type:"POST",
			data:{
				data:data
			},
			success:function(value){
				location.href = "/cart"
			}
			,error:function(e){
				alert('Error: '+e);
			}
		})
	})
	
	$(".check_out").click(function(e){
		e.preventDefault();
		var tongtien = $("#total").text();
		$.confirm({
		    title: 'Thông Báo!',
		    content: 'Bạn Có Chắc Muốn Thanh Toán <br/>Tổng Tiền: <span style="color:red;">'+tongtien+' </span>',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        CheckOut: {
		            text: 'Check Out',
		            btnClass: 'btn-red',
		            action: function(){
		            	location.href = "/checkout";
		            }
		        },
		        Back: function () {
		        }
		    }
		});
	})
	
	$(".add-towishlist").click(function(e){
		e.preventDefault();
		var pid = $(this).attr("data-pid");
		$.ajax({
			url:"/wishlist/"+pid,
			type:"GET",
			data:{
				
			},
			success:function(value){
				$.notify("Đã thêm sản phẩm vào wishlist","success");
			}
		})
		
	})
	
	$(".delete-customer").click(function(e){
		e.preventDefault();
		self = $(this);
		var idCus = $(this).attr("data-idcus");
		$.ajax({
			url:"/delete/"+idCus,
			type:"GET",
			data:{
			},
			success:function(value){
				if(value=="true"){
					$.notify("Xóa thành công cusomer","success");
					self.closest("tr").remove();
				}else{
					$.notify("Xóa thành công cusomer");
				}
			}
		})
	})
	
	$("#btn-xacnhan").click(function(e){
		e.preventDefault();
		var idOrder = $(this).attr("data-id");
		var tenkh = $(".tenkh").text();
		$.confirm({
		    title: 'Thông Báo!',
		    content: 'Xác Nhận Order Của <span style="color:red">'+tenkh+'</span>',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        Ok: {
		            text: 'Xác Nhận',
		            btnClass: 'btn-red',
		            action: function(){
		            	$.ajax({
		        			url:"/xacnhan/"+idOrder,
		        			type:"POST",
		        			data:{
		        			},
		        			success:function(value){
		        				if(value=="true"){
		        					$.alert({
		        					    title: 'Thông Báo!',
		        					    content: 'Xác nhận thành công',
		        					    type: 'green',
		        					    buttons: {
		        					    	Ok:{
		        					    		text: 'OK',
		        					    		action: function(){
		        					    			location.href="/orders";
		        					    		}
		        					    	}
		        					    }
		        					});
		        				}else{
		        					$.notify("Xác nhận thất bại");
		        				}
		        			}
		        		})
		            	
		            }
		        },
		        Back: function () {
		        }
		    }
		});
	})
	
	
	$("#btn-huybo").click(function(e){
		e.preventDefault();
		var idOrder = $(this).attr("data-id");
		var tenkh = $(".tenkh").text();
		$.confirm({
		    title: 'Thông Báo!',
		    content: 'Xác Nhận Order Của <span style="color:red">'+tenkh+'</span>',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        Ok: {
		            text: 'Xác Nhận',
		            btnClass: 'btn-red',
		            action: function(){
		            	$.ajax({
		        			url:"/huybo/"+idOrder,
		        			type:"POST",
		        			data:{
		        			},
		        			success:function(value){
		        				if(value=="true"){
		        					$.alert({
		        					    title: 'Thông Báo!',
		        					    content: 'Hủy Bỏ Thành Công Order',
		        					    type: 'green',
		        					    buttons: {
		        					    	Ok:{
		        					    		text: 'OK',
		        					    		action: function(){
		        					    			location.href="/orders";
		        					    		}
		        					    	}
		        					    }
		        					});
		        				}else{
		        					$.notify("Hủy Bỏ Thất Bại");
		        				}
		        			}
		        		})
		            	
		            }
		        },
		        Back: function () {
		        }
		    }
		});
	})

})