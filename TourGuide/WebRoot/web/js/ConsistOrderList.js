
vistPhone = GetUrlem("vistPhone");

OrderID=GetUrlem("OrderID");
perNum=GetUrlem("PerNum");
guideFee=GetUrlem("Fee");


$(document).on("pagecreate",function(){
//	getNum();
	
	
});
				
$("#orderTicketRadio").bind('click', function(event) {
	
	if($("#orderTicketRadio").attr("data-cacheval")=="false")
    {
        $("#orderTicketPanel").show('200', function() {
        });
    }else{
        $("#orderTicketPanel").hide('200', function() {
        });
    }
});


function getNum(){
	var num = GetUrlem("PerNum");//可拼人数
	var visitNum = GetUrlem("visitNum");//用户输入的参观人数
	
	if(!visitNum || visitNum != ""){
		$("#visitorNum").val(visitNum);
	}
}

//点击去支付，先判断用户是否注册
function list_consistOrder(){
	
	if(vistPhone == "undefined" || vistPhone == openId)
	{
		alert("您还未注册，请注册！");
		window.location.href = "register.html";
	}
	else{
		consistOrder();
	}
}


function consistOrder()
{
	
	var phone=$("#contact").val();
	var num=$("#visitorNum").val();

	if(parseInt(num) > parseInt(perNum))
	{
		alert("填写人数超过了可拼单人数");
		return;
	}
	if(phone == ""){
		alert("请填写联系方式");
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
			alert("拼单Request error!");
		},
		success:function(data)
		{
			alert("拼单success!");
			window.location.href = "orderFormList.html";
		}
	});
}


function OrderShow()
{	
	
	var phone=$("#contact").val();
	var num=$("#visitorNum").val();
	alert(num);
	alert(perNum);
	
	if(parseInt(num) > parseInt(perNum))
	{
		alert("填写人数超过了可拼单人数");
		window.location.href = "pindan.html";
	}
	$("#list_pindan_guide_phone").html(phone);
	$("#list_pindan_guide_num").html(num);
	$("#list_pindan_guide_money").html(guideFee+"元/每人");
	$("#list_pindan_total_money").html(guideFee*num+"元");
}


function isRegist()
{
	if(vistPhone == "undefined" || vistPhone == openId)
	{
		alert("您还未注册，请注册！");
		window.location.href = "register.html";
	}else{
		window.location.href = "personalHome.html";
	}
}