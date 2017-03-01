window.onload = function(){
	getData("/getMyBookedOrder.do","OrderUl");
	getData("/getFinishedBookedOrder.do","finishOrder");
}
function getData(Url,ulid){
		Url= HOST+Url;
//	var url = HOST+"/getFinishedBookedOrder.do";
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{guidePhone:"13165662195"},
		datatype:"JSON",
		error:function()
		{
			alert("全部订单Request error!");
		},
		success:function(data){
			setList(data,ulid);
		}
	});
}
function setList(data,ulid){
	$.each(data, function(i,n) {
				var UlList = document.getElementById(ulid);
				var LiList = document.createElement("li");
				UlList.appendChild(LiList);
				//Orderid
				var AList = document.createElement("a");
				AList.href="DetailBeTakenOrder.html?Orderid="+n.bookOrderID+"&fin=t";
				AList.setAttribute("target","_top");
				var PList = document.createElement("p");
				AList.appendChild(PList);
				LiList.appendChild(AList);
				//添加订单号
				var SpanListOrderId = document.createElement("span");
				SpanListOrderId.className = "orderId";
				SpanListOrderId.innerHTML = "订单号："+n.bookOrderID+"<br/>";
				
				//添加景区名称
				var SpanListName = document.createElement("span");
				SpanListName.className = "scenicName";
				SpanListName.innerHTML = "景区名称："+n.scenicName+"<br/>";
				
				//添加时间
				var SpanListTime = document.createElement("span");
				SpanListTime.className = "vistTime";
				SpanListTime.innerHTML = "参观时间："+n.visitTime+"<br/>";
				
				//添加人数
				var SpanListNum = document.createElement("span");
				SpanListNum.className = "visitNum";
				SpanListNum.innerHTML = "参观人数："+n.visitNum+"<br/>";
				
				var SpanListFee = document.createElement("span");
				SpanListFee.className = "guideFee";
				SpanListFee.innerHTML = "讲解费："+n.guideFee+"<br/>";
				
				var startButton = document.createElement("button");
				startButton.className = "start mybtn";
				startButton.innerHTML = "开始讲解";
								
				var finishButton = document.createElement("button");
				finishButton.className = "finish";
				finishButton.innerHTML = "结束讲解";
				
				PList.appendChild(SpanListOrderId)
				PList.appendChild(SpanListName)
				PList.appendChild(SpanListTime)
				PList.appendChild(SpanListNum)
				PList.appendChild(SpanListFee)
				LiList.appendChild(startButton); 	
			});
			$("#"+ulid).listview('refresh');	
			$(".start").click(function(){
				$(this).removeClass("start");
				$(this).addClass("finish");
				$(this).html("结束讲解");
			});
}
