var loc = location.href;
var n1 = loc.length;  //地址的总长度
var n2 = loc.indexOf("="); //取得等号的位置
var OrderID = decodeURI(loc.substr(n2+1,n1-n2));
//alert(OrderID);
	function list_consistOrder1()
	{
		var HalfPrice;
		var FullPrice;
		var DiscoutPrice;
		if($("input[name='pindan_orderTicket']:checked").val() == 0)
		{
			HalfPrice = 0;
			FullPrice = 0;
			DiscoutPrice = 0;
		}
		if($("input[name='pindan_orderTicket']:checked").val() == 1)
		{
			HalfPrice = $("#halfPriceTicketNum").val();
			FullPrice = $("#fullPriceTicketNum").val();
			DiscoutPrice = $("#discountTicketNum").val();
		}
		var data = 
		{
			orderID:OrderID,
			visitNum:$("#list_visitorCount").val(),
			visitorPhone:"15198945231",
			purchaseTicket:$("input[name='pindan_orderTicket']:checked").val(),
			halfPrice:HalfPrice,
			discoutPrice:DiscoutPrice,
			fullPrice:FullPrice
		};
		
		alert(data.orderID);
		alert(data.visitNum);
		alert(data.visitorPhone);alert(data.purchaseTicket);
		alert(data.halfPrice);
		alert(data.discoutPrice);
		alert(data.fullPrice);
		
		var url = HOST+"/consistWithconsistOrderID.do";
		$.ajax({
			type:"post",
			url:url,
			async:true,
			data:data,
			datatype:"JSON",
			error:function()
			{
				alert("可拼单列表进行拼单Request error!");
			},
			success:function(data)
			{
				alert("可拼单列表进行拼单success!");
				alert(data);
			}
		});
	}


