
$(function($){
	setData();
});
function setData(){
	var state = GetUrlem("state");
	var orderId = GetUrlem("orderId");
	$(".orderFormId").html(orderId);
	$(".viewState").html(state);
	var postdata = {
		"orderID":orderId,
		"orderState":state
	};
		$.ajax({
			type:"post",
			url:HOST+"/getDetailOrderInfo.do",
			async:true,
			data:postdata,
			datatype:"JSON",
			error:function()
			{
				alert("Error:获取订单详情失败");
			},
			success:function(data)
			{ 
				if(JSON.stringify(data)!="[]"){
				setNormalData(data[0]);
				setSenicData(data[0].scenicID);
				if(data[0].guidePhone!=undefined)
				{
					setGuidegData(data[0].guidePhone);
				}else{
					$("#MoneyInfo").hide();
				}
				if(data[0].totalTicket!=0){
					setTickData(data[0]);}
				}
			}
		});
	}
/*function setTickData(data){
	var url = HOST+"/geTicketsByScenicNo.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:{"scenicNo":data.scenicID},
		datatype:"JSON",
		error:function()
		{
			alert("获取门票费用Request error!");
			return false;
		},
		success:function(Tdata){
			//alert(JSON.stringify(data));
			var ticm = "";
			if(data.fullPrice!=0)
			{
			ticm +="<p>全价票"+data.fullPrice+"*"+Tdata.fullPrice+"元</p>";
			}
			if(data.halfPrice!=0)
			{
			ticm+="<p>半价票"+data.halfPrice+"*"+Tdata.halfPrice+"元</p>";
			}
			if(data.discoutPrice!=0)
			{
			ticm += "<p>折扣票"+data.discoutPrice+"*"+Tdata.discoutPrice+"元</p>";
			}
			ticm +="<p>门票合计"+data.totalTicket+"元</p>";
			$("#MoneyInfo").prepend(ticm);
		}
});
}*/
function setNormalData(data){
		$(".orderTime").html(data.produceTime);
		$(".vistTime").html(data.visitTime);
		$(".vistorNum").html(data.visitNum);
		$(".totalMoney").html(data.totalMoney);
		$("#MoneyInfo").append("<p>讲解费"+data.guideFee+"元</p><p>总计"+data.totalMoney+"元</p>");
}
	function setSenicData(scenicID){
			scenicUrl = HOST+"/getSomeScenicInfoByscenicID.do?scenicID="+scenicID;
			$.get(scenicUrl,function(data,status){
				if(status){
					$(".scenicName").html(data[0].scenicName);
					$(".scenicImg").attr("src",HOST+data[0].scenicImagePath);
				}else{
					alert("Error:获取景点信息失败！");
				}
			});
		}
	function setGuidegData(guidePhone){
		guideinfoUrl = HOST+"/getDetailGuideInfoByPhone.do?guidePhone="+guidePhone;
		$.get(guideinfoUrl,function(data,status){
		var guideif = '<img class="guideHead" src="'+HOST+data[0].image+'"><p>讲解员：'+data[0].name;
		guideif+='<br>性别：'+data[0].sex+'<br>年龄：'+data[0].age;
		var language = getLanguage(data[0].language);
		guideif+='<br>语种:'+language+'<br>联系方式：'+guidePhone+'</p>';
		$("#guideInfo").html(guideif);
		$("#infolist").listview('refresh');
		});
		
	}
