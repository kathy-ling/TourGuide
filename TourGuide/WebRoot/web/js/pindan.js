//从登录界面获得游客的手机号即登录名
//1-15 datebox 时间无法获取
var scenicNo = GetUrlem("scenicNo");
$(function($){
	$("#panel2").hide();
	$("#orderTicketPanel").hide();
	getconsistOrder();
	
	$("#panel2").find("input").bind("focus click",function(){
		$("#ul_fee").empty();
		$("#paySubmit").attr("data-state","confirm");
		$("#paySubmit").html("确定");
	});
	
	$("#paySubmit").click(function(){
		if($(this).attr("data-state")=="confirm"){
			setFeeform();
			$(this).attr("data-state","gopay");
			$(this).html("去支付");
		}else if($(this).attr("data-state")=="gopay"){
			consistOrder();
		}
	});
});
function setFeeform(){
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
}
function textchange()
{
	alert("進入");
	 getFee();
	
}

function getFee()
{
	var a=$("#date").val();
	var url = HOST+"/getIntroFee.do";
	var fee;
	$.ajax({
		type:"get",
		url:url,
		async:true,
		data:{scenicID:scenicNo,date:a},
		success:function(data)
		{
			fee=data;
		},
	});
	$("#fee").innerHTML=fee;
}

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
	alert(JSON.stringify(data));
	
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
	
	var url = HOST+"/getAvailableConsistOrder.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:{scenicID:scenicNo},
		datatype:"JSON",
		error:function()
		{
			alert("可拼拼单Request error!");
		},
		success:function(data)
		{		//JSON.stringify(data)=="[]"
			if(data.length==0){
			$("#panel1").append("<p class='errorTip'>(* ￣︿￣)该景点暂时没有可拼订单，发个订单试试吧<p>");
			}
			//动态加载div布局
			$.each(data, function(i,n){
				
				/*var OrderList = document.getElementById("panel1");							
			    var OrderListInfo = document.createElement("div");
				OrderListInfo.id = "order_list1";	
				OrderList.appendChild(OrderListInfo);
												
			   //$("#order_list1").insertBefore(document.getElementById("order_list"));
								
				var UlListInfo = document.createElement("ul");
				UlListInfo.id = "pindan_ul_id";
				$("ul").attr("data-role","listview");
				$("ul").listview();
				OrderListInfo.appendChild(UlListInfo);*/
				
				var UlList = document.getElementById("pindan_ul_id");
				var LiListInfo = document.createElement("li");
				LiListInfo.id = "pindan_li_id";
				UlList.appendChild(LiListInfo);
				
				//添加订单号
				var SpanOrderIdInfo = document.createElement("span");
				SpanOrderIdInfo.id = "consis_orderID";
				SpanOrderIdInfo.className = "orderFormId";
				SpanOrderIdInfo.innerHTML = "订单号：" + n.orderID + "<br/>";
				
				//添加浏览时间
				var SpanVisitTimeInfo = document.createElement("span");
				SpanVisitTimeInfo.id = "consis_visitTime";
				SpanVisitTimeInfo.className = "vistTime";
				SpanVisitTimeInfo.innerHTML = "浏览时间：" + n.visitTime+ "<br/>";
				
				//添加已有人数
				var SpanVisitNumInfo = document.createElement("span");
				SpanVisitNumInfo.id = "consis_visitNum";
				SpanVisitNumInfo.innerHTML = "已有人数：" + n.visitNum+ "<br/>";
				
				//添加可拼单人数
				var SpanConsisNumInfo = document.createElement("span");
				SpanConsisNumInfo.id = "consis_minus";
				var MaxNum = n.maxNum;
				var NowNum = n.visitNum;
				var Number = MaxNum - NowNum;
				SpanConsisNumInfo.innerHTML = "可拼单人数：" + Number+ "<br/>";
				
				//添加按钮
				var DivConsisBtn = document.createElement("div");
				DivConsisBtn.className = "goOrder";
				var ConsisBtn = document.createElement("button");
				ConsisBtn.id = "goOrder";
				ConsisBtn.className = "goOrderbtn ui-btn ui-btn-inline";
				ConsisBtn.innerHTML = "去拼单";
				ConsisBtn.onclick = function()
				{window.location.href = "ConsistOrderList.html?"+"OrderID="+n.orderID;};
				DivConsisBtn.appendChild(ConsisBtn);
				
				LiListInfo.appendChild(SpanOrderIdInfo)
				.appendChild(SpanVisitTimeInfo)
				.appendChild(SpanVisitNumInfo)
				.appendChild(SpanConsisNumInfo)
				.appendChild(DivConsisBtn);
			});				
				$("#pindan_ul_id").listview('refresh');
			}
		});
}

function goOrder()
{
	window.location.href = "ConsistOrderList.html";
}
