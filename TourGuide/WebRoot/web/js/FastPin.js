
var fee;
$(document).ready(
	function()
	{
		
		addAllScenics();
	}
	
);


function getFee()
{
	$('#ul_fee').empty();
	var scenicName = $("#chooseScenicName").val();
	if (scenicName=='') {
		alert('请选择景区，进行支付');
		return;
	}
	var d = new Date();
	var str = d.getFullYear()+"-0"+(d.getMonth()+1)+"-"+d.getDate();
	var url = HOST+"/getIntroFee.do";
	var fee;
	$.ajax({
		type:"get",
		url:url,
		async:true,
		data:{scenicName:scenicName,date:str},
		success:function(data)
		{
			fee=data;
			var ul_feetext ="<li><a><h3>费用信息</h3><p>个人讲解费："+data+"元<br>";
			
			$("#ul_fee").append(ul_feetext);
			$("#ul_fee").listview("refresh");
		},
	});
}


function getFee1()
{
	$('#ul_fee').empty();
	var scenicName = $("#chooseScenicName").val();
	var num=$("#personNum").val();
	var d = new Date();
	var str = d.getFullYear()+"-0"+(d.getMonth()+1)+"-"+d.getDate();
	if (scenicName=='') {
		alert('请选择景区，进行支付');
		return;
	}
	
	if (num==undefined) {
		return;
	}
	var url = HOST+"/getIntroFee.do";
	var fee;
	$.ajax({
		type:"get",
		url:url,
		async:true,
		data:{scenicName:scenicName,date:str},
		success:function(data)
		{
			var ul_feetext ="<li><a><h3>费用信息</h3><p>个人讲解费："+data+"元<br>"+"<p>拼团人数："+num+"人<br><p>总计："+data*num+"元<br>";
			
			$("#ul_fee").append(ul_feetext);
			$("#ul_fee").listview("refresh");
		},
	});
}




function addAllScenics() {
	var url = HOST + "/getAllScenics.do";
	$.ajax({
		type : "post",
		url : url,
		async : true,
		datatype : "JSON",
		success : function(data) {
			addSelect(data);
		}
	});

}

function addSelect(a) {
	$.each(a, function(index, value) {
		addOption(value.scenicName);
	});

}

function addOption(a) {

	// 根据id查找对象，
	var obj = document.getElementById('chooseScenicName');
	// 这个只能在IE中有效
	obj.options.add(new Option(a, a));
}



function ProduceOrder()
{
	var url=HOST+"/releaseFastOrder.do";
	var scenicName = $("#chooseScenicName").val();
	var num=$("#personNum").val();
	var data={scenicName:scenicName,visitNum:num,guideFee:fee,visitorPhone:vistPhone};
	if (scenicName=='') {
		alert('请选择景区，进行支付');
		return;
	}
	if (num=='') {
		return;
	}
	$.ajax({
		type:"post",
		url:url,
		data:data,
		error:function()
		{
			alert('快捷拼团支付失败，请重新支付');
		},
		success:function(data)
		{
			if (data==1) {
				alert('支付成功，请进入订单详情进行生成二维码');
				window.location.href='orderFormList.html';
			} else{
				alert('快捷拼团支付失败，请重新支付');
			}
		}
	});
}

