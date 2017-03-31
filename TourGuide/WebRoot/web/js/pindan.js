
//从登录界面获得游客的手机号即登录名
//1-15 datebox 时间无法获取
var scenicNo = GetUrlem("scenicNo");
$(function($){
	$("#panel2").hide();
	$("#orderTicketPanel").hide();
	getconsistOrder();
	addAllScenics();
	addDate();
	$("#paySubmit").click(function(){
		var scenicName = $("#chooseScenicName").val();
		var visitdate=$("#visitTime").val();
		var visitTime=$("#chooseDatetime").val();
		var visitorPhone=$("#visitorPhone").val();
		var visitNum=$("#visitorCount").val();
		if (scenicName=="") {
			alert("请选择景区，再进行确认");
			return;
		}
		
		if (visitdate=="") {
			alert("请选择日期，再进行确认");
			return;
		}
		
		if (visitNum=="") {
			alert("请填写游览人数，再进行确认");
			return;
		}
		if (visitorPhone=="") {
			alert("请填写游览人的联系方式，再进行确认");
			return;
		}
		
		
		
		window.location.href= "consistOrder.html?" + "visitorPhone=" + visitorPhone+"&visitNum="+visitNum+"&visitDate="
		+visitdate+"&visitTime="+visitTime+"&scenicName="+scenicName;
	});
});

function addDate()
{
	var now = new Date();
	var today = new Date(now.getFullYear(), now.getMonth(), now.getDate()+1);
	var tomorrow = new Date(now.getFullYear(), now.getMonth(), now.getDate()+2);
	var dayAfterTomo = new Date(now.getFullYear(), now.getMonth(), now.getDate()+3);
	var today0 = today.toISOString();
	var today1 = today0.substring(0,10);
	var tomorrow0 = tomorrow.toISOString();
	var tomorrow1 = tomorrow0.substring(0,10);
    var SdayAfterTomo0 = dayAfterTomo.toISOString();
    var dayAfterTomo1 = SdayAfterTomo0.substring(0,10);
	
	//根据id获取select对象
	var dateSelect = document.getElementById("visitTime");
	var dateSelect1 = document.getElementById("visitTime1");
	//dateSelect.append("<option value='"+dayAfterTomo0+"'>"+dayAfterTomo0+"</option>");
	dateSelect.options.add(new Option(today1,today1));
	dateSelect.options.add(new Option(tomorrow1,tomorrow1));
	dateSelect.options.add(new Option(dayAfterTomo1,dayAfterTomo1));
	
	dateSelect1.options.add(new Option(today1,today1));
	dateSelect1.options.add(new Option(tomorrow1,tomorrow1));
	dateSelect1.options.add(new Option(dayAfterTomo1,dayAfterTomo1));
}

function getFee()
{
	var scenicName = $("#chooseScenicName").val();
	var date1=$("#visitTime").val();
	var url = HOST+"/getIntroFee.do";
	var fee;
	$.ajax({
		type:"get",
		url:url,
		async:true,
		data:{scenicName:scenicName,date:date1},
		success:function(data)
		{
			var ul_feetext ="<li><a><h3>费用信息</h3><p>个人讲解费："+data+"元<br>";
			var ticketPrice = 0;
			
			$("#ul_fee").append(ul_feetext);
			$("#ul_fee").listview("refresh");
		},
	});
	$("#fee").innerHTML=fee;
}
/*function setFeeform(){
	var url1 = HOST+"/getIntroFee.do";
	$.ajax({
		type:"post",
		url:url1,
		async:true,
		data:{'scenicID':scenicNo,'date':$("#visitTime").val()},
		datatype:"JSON",
		error:function()
		{
			alert("返回讲解费Request error!");
		},
		success:function(data)
		{
			//alert("返回讲解费Request success!");
			//alert(data);
			alert(data);
			var ul_feetext ="<li><a><h3>费用信息</h3><p>讲解费："+data+"<br>";
			var HalfPrice = sessionStorage.halfPrice;
			var FullPrice = sessionStorage.fullPrice;
			var DiscoutPrice = sessionStorage.discoutPrice;
			var Hal=0;
			var Ful=0;
			var Dis=0;
			var order = $("input[name='pindan_orderTicket']:checked").val();
			var ticketPrice = 0;
			var TotalMoney = 0;
			if(order==1){
				var TicketPrice ="";
				Hal = $("#halfPriceTicketNum").val();
				Ful = $("#fullPriceTicketNum").val();
				Dis = $("#discountTicketNum").val();		
				ticketPrice = Ful* FullPrice			
							+Hal* HalfPrice
							+Dis* DiscoutPrice;
				TotalMoney = ticketPrice + data;
				if(Ful!=0){
				TicketPrice+=Ful + "*" + FullPrice;
				if(Dis!=0||Hal!=0){
					TicketPrice+="+";
				}
			}
			if(Hal!=0){
				TicketPrice+=Hal + "*" + HalfPrice;
				if(Dis!=0){
					TicketPrice+="+";
				}
			}
			if(Dis!=0){
				TicketPrice+=Dis + "*" + DiscoutPrice;
			}
			ul_feetext+="门票："+TicketPrice+"<br>";
			}
				
			ul_feetext+="合计：<span>"+TotalMoney+"</span>";
			
			$("#ul_fee").append(ul_feetext);
			$("#ul_fee").listview("refresh");
		}
	});
}*/


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
	var obj1 = document.getElementById('chooseScenicName1');
	// 这个只能在IE中有效
	obj.options.add(new Option(a, a));
	obj1.options.add(new Option(a, a)); 
}



function chooseOrder()
{
	var date1= $("#visitTime1").val();
	var scenicName = $('#chooseScenicName1 option:selected').val();
	var visitNum = $("#chooseVisitNum").val();
	
	if (date1=="") {
		date1="null";
	}
	
	if (scenicName=="") {
		scenicName="null";
	}
	if (visitNum=="") {
		visitNum="-1";
	}
	var url = HOST + "/getConsistOrderWithSelector.do";
	$.ajax({
		type : "post",
		url : url,
		async : true,
		data:{scenicName:scenicName,visitDate:date1,visitNum:visitNum},
		datatype : "JSON",
		success : function(data) {
			
			if(data.length==0){
				$("#pindan_ul_id").empty();
				$("#pindan_ul_id").html("<div data-role='main' class='ui-content'><p>没有符合条件的拼单信息</p></div>");
			}else
			{
				$("#pindan_ul_id").empty();
				UpdateConsistOrder(data);
			}
			
			$("#pindan_ul_id").listview('refresh');
		}
	});
	
}


function UpdateConsistOrder(data)
{
	var a="";
	$.each(data, function(i,n){
		
			var htmlString="<li><a   href='ConsistOrderList.html?date="+n.visitTime+
			"&PerNum="+n.num+"&Fee="+n.guideFee+"&OrderID="+n.orderID+"'>" +
//			"<span>订单编号:"+n.orderID+"</span><br/>" +
			"<span>景区名称："+n.scenicName+"</span><br/>" +
			"<span>游览时间："+n.visitTime+"</span><br/>" +
			"<span>已有人数："+n.currentNum+"</span><br/>" +
			"<span>可拼人数："+n.num+"</span><br/>" +
			"<span>讲解费："+n.guideFee+"元/人</span><br/></a></li>";
			a = a + htmlString;
			});	
	$("#pindan_ul_id").html(a);
}

/*
function textchange()
{
	alert("進入");
	 getFee();
	
}*/






function consistOrder()
{
	var HalfPrice=0;
	var FullPrice=0;
	var DiscoutPrice=0;
	if($("input[name='pindan_orderTicket']:checked").val() == 1)
	{
		if($("#halfPriceTicketNum").val())
		{HalfPrice = $("#halfPriceTicketNum").val();}
		if($("#fullPriceTicketNum").val())
		{FullPrice = $("#fullPriceTicketNum").val();}
		if($("#discountTicketNum").val())
		{DiscoutPrice = $("#discountTicketNum").val();}
	}
	var data = 
	{
		scenicID:scenicNo,
		visitTime:$("#visitTime").val()+" "+$("#chooseDatetime").val(),
		visitNum:$("#visitorCount").val(),
		visitorPhone:vistPhone,
		purchaseTicket:$("input[name='pindan_orderTicket']:checked").val(),
		halfPrice:HalfPrice,
		discoutPrice:DiscoutPrice,
		fullPrice:FullPrice
	};
	
	var url = HOST+"/releaseConsistOrder.do";
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
		}
	});
}
//从服务器获取可拼订单

function getconsistOrder()
{
	
	var url = HOST+"/getAllAvailableConsistOrder.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		datatype:"JSON",
		error:function()
		{
			alert("可拼拼单Request error!");
		},
		success:function(data)
		{		//JSON.stringify(data)=="[]"
			if(data.length==0){
				$("#pindan_ul_id").empty();
			}
			//动态加载div布局
				$("#pindan_ul_id").empty();
				UpdateConsistOrder(data);
				$("#pindan_ul_id").listview('refresh');
			}
		});
}



function goOrder()
{
	window.location.href = "ConsistOrderList.html";
}



