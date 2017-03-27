
function list_consistOrder()
{
	var OrderID=GetUrlem("OrderID");
	var perNum=GetUrlem("PerNum");
	var phone=$("#list_Phone").val();
	var num=$("#list_visitorCount").val();

	if(parseInt(num) >= parseInt(perNum))
	{
		alert("填写人数超过了可拼单人数");
		return;
	}
	var data = 
	{
		orderID:OrderID,
		visitNum:num,
		visitorPhone:vistPhone,
		contact:phone
	};

	var url = HOST+"/consistWithconsistOrderID.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:data,
		datatype:"JSON",
		error:function()
		{
			alert("发起拼单Request error!");
		},
		success:function(data)
		{
			alert("发起拼单success!");
			window.location.href = "orderFormList.html";
		}
	});
}


function OrderShow()
{
	
	var guideFee=GetUrlem("Fee");
	var perNum=GetUrlem("PerNum");
	var phone=$("#list_Phone").val();
	var num=$("#list_visitorCount").val();
	
	if(parseInt(num) > parseInt(perNum))
	{
		alert("填写人数超过了可拼单人数");
		return;
	}
	$("#list_pindan_guide_phone").html(phone);
	$("#list_pindan_guide_num").html(num);
	$("#list_pindan_guide_money").html(guideFee+"元/每人");
	$("#list_pindan_total_money").html(guideFee*num+"元");
}
