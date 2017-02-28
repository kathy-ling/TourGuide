$('#OrderguidePage').bind('pagecreate',function(event, ui){
	var ScenicNo = GetUrlem("scenicNo");
	var sname = GetUrlem("sname");
	if(ScenicNo!=null){
		sessionStorage.scenicNo = ScenicNo;
	}
	if(sname!=null){
		$("#chooseScenicName").val(sname);
	}
	$("#selectAvailableGuides").click(function(){
		selectAvailableGuides();
	});
	//查看推荐
	$("#getAvailableGuides").click(function(){
		getAvailableGuides();
	});
	//提交订单
	$("#submitOrderForm").click(function(){
		checkOrderForm();//检查表单正确后，调用提交方法
	});
});
function myrefresh(){
	
	$(".DirectOrderBtn").bind("click",function(){
		var guidephone = $(this).attr("phone");
		$("#DirectorderTicketSub").attr("phone",guidephone);
		$.mobile.changePage("#orderTicketPop", "pop", false, false);
	});
}
	
function checkOrderForm(){
	var HalfPrice = 0;
	var DiscoutPrice = 0;
	var FullPrice = 0;
	var PurchaseTicket = $("input[name='orderTicket']:checked").val();
	if(PurchaseTicket!=null)
	{
		if(PurchaseTicket)//购票
		{
		    FullPrice = $("#fullPriceTicketNum").val();
			HalfPrice = $("#halfPriceTicketNum").val();
			DiscoutPrice = $("#discountTicketNum").val();
		}
	}else{
		alert("请选择是否代购门票！");
		return false;
	}
	var data =
	{
		scenicID:sessionStorage.scenicNo,
		otherCommand:$("#otherRequest").val(),
		visitNum:$("#visitorCount").val(),
		priceRange:$("#orderM").val(),
		guideSex:$("input[name='guideSex']:checked").val(),
		visitorPhone:$("#visitorPhone").val(),
		visitorName:$("#visitorName").val(),
		language:$("#guideLanguage option:selected").val(),
		purchaseTicket:PurchaseTicket,
		halfPrice:HalfPrice,
		discoutPrice:DiscoutPrice,
		fullPrice:FullPrice,
	    visitTime:$("#orderDate").val()+" "+$("#orderDatetime").val()
	};
	if(!$("#orderDate").val())
	{
		alert("请选择日期!");
		return false;
	}
	if(!$("#orderM").val())
	{
		alert("请输入预期价格！");
		return false;
	}
	if(!$("#orderDatetime").val())
	{
		alert("请选择时间!");
		return false;
	}
	if(!data.visitNum||data.visitNum<1)
	{
		alert("请输入参观人数！");
		return false;
	}
	if(!data.visitorPhone||data.visitorPhone.length!=11)
	{
		alert("请输入正确的电话号码！");
		return false;
	}
	if(!data.visitorName)
	{
		alert("请输入您的姓名！");
		return false;
	}
	//alert(JSON.stringify(data));
	releaseOrder(data);
}
//发布订单
function releaseOrder(formdata)
{
	var Url = HOST+"/releaseBookOrder.do";
	$.ajax({
	type:"post",
	url:Url,
	async:true,
	data:formdata,
	datatype:"JSON",
	error:function()
	{
		alert("发布订单Request error!");
	},
	success:function(data)
	{
		if(data==true){
		alert("发布订单成功！");
	}else{
		alert("发布订单失败");
	}
	}
});
}
//从服务器获取讲解员
window.onload = function()
{
	var url = HOST+"/getPopularGuides.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		datatype:"JSON",
		error:function()
		{
			alert("获取讲解员Request error!");
		},
		success:function(data)
		{
			/*alert("获取讲解员success!");	*/
			addlist(data);
		}
			
	});
}



//查看推荐显示的导游
function getAvailableGuides()
{
	var scenicName = $("#chooseScenicName").val();
	var visitTime = $("#chooseDate").val();
	var visitNum = $("#chooseVisitNum").val();
	
	sessionStorage.directVisitTime = visitTime;
	sessionStorage.directVisitNum =  visitNum;
	//alert(scenicName);alert(visitTime);alert(visitNum);
	//返回数据库中的名字
	var url = HOST+"/getNameSimilarScenics.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:{"scenicName":scenicName},
		datatype:"JSON",
		error:function()
		{
			alert("搜索景区名称Request error!");
		},
		success:function(data)
		{
			//alert("搜索景区名称Request success!");
			if(data == null)
			{
				alert("您输入的名称有误，请重新输入！");
			}
			$.each(data, function(i,item) {
				var scenicNo = item.scenicNo;
				sessionStorage.scenicNo = scenicNo;
				alert(item.scenicName);
				sessionStorage.directScenicName = item.scenicName;
				//$.cookie("scenicFullName",item.scenicName);
				var dataGuide = {"scenicName":item.scenicName,
								 "visitTime":visitTime,
								 "visitNum":visitNum};
				var url = HOST+"/getAvailableGuides.do";
				$.ajax({
					type:"post",
					url:url,
					async:true,
					data:dataGuide,
					datatype:"JSON",
					error:function()
					{
						alert("推荐讲解员Request error!");
					},
					success:function(data)
					{					
						addlist(data);
					}
				});
			});
		}
	});
	
//	var scenicFullName = $.cookie("scenicFullName");
//	alert(scenicFullName);
}

//根据详细筛选条件显示导游
function selectAvailableGuides()
{	
	var sex;     //转换性别
	var issex = $("input:radio[name='sex']:checked").val();
	if(issex == undefined)
	{
		sex = "null";
	}
	else
	{
		sex = issex;
	}
	
	var age;    //转换年龄
	var isage = $("input:radio[name='age']:checked").val();
	if(isage == undefined)
	{
		age = "null";
	}
	else
	{
		age = isage;
	}
	
	var language;    //转换语种
	var islanguage = $("input:radio[name='language']:checked").val();
	if(islanguage == undefined)
	{
		language = "null";
	}
	else
	{
		language = islanguage;
	}
	
	var scenicName = $("#chooseScenicName").val();
	var visitTime = $("#chooseDate").val();
	var visitNum = $("#chooseVisitNum").val();	
	var starlevel = $("#starleve").val();
	
	//根据用户输入返回数据库中存在的名字
	var url = HOST+"/getNameSimilarScenics.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:{"scenicName":scenicName},
		datatype:"JSON",
		error:function()
		{
			alert("搜索景区名称Request error!");
		},
		success:function(data)
		{
			
			//alert("搜索景区名称Request success!");
			if(data == null)
			{
				alert("您输入的名称有误，请重新输入！");
			}
			$.each(data, function(i,item) {
				alert(item.scenicName);
				
				//定义要发送到服务器的参数
			var data = {"scenicName":item.scenicName,
						"visitTime":visitTime,
						"visitNum":visitNum,
						"sex":sex,"age":age,
						"level":starlevel,
						"language":language};
			//从数据库返回符合详细筛选条件的导游
		    var url = HOST+"/getAvailableGuidesWithSelector.do"
	        $.ajax({
			type:"post",
			url:url,
			async:true,
			data:data,
			datatype:"JSON",
			error:function()
			{
				alert("筛选Request error!");
			},
			success:function(data)
			{
				//alert("筛选success!");
				if(jQuery.isEmptyObject(data))
				{
					alert("没有符合条件的讲解员");
				}
				addlist(data);
			}
			
		});	
	});
  }
});		
}


//更新管理员列表
function addlist(data)
{
$("#order_guide_ul").empty();
	$.each(data,function(i,n)
			{
				//动态显示最受欢迎的讲解员
				var UlList = document.getElementById("order_guide_ul");
				var LiListInfo = document.createElement("li");
				UlList.appendChild(LiListInfo);
				
				var AList = document.createElement("a");		
				AList.href = "guideInfo.html?"+"phone="+n.phone;
				//AList.setAttribute("href","guideInfo.html");
				//AList.target = "_top";
				//AList.setAttribute("rel","external");
				LiListInfo.appendChild(AList);	
				
				
				var ImgList = document.createElement("img");
				ImgList.src =HOST+n.image;
				AList.appendChild(ImgList);
				var PList = document.createElement("p");
				
				AList.appendChild(PList);
				
				//添加姓名
				var SpanListName= document.createElement("span");
				SpanListName.className = "name";
				//SpanListName.id = "order_guide_name";
				SpanListName.innerHTML = "姓名："+n.name+"<br/>";
		
				//添加性别
				var SpanListSex= document.createElement("span");
				SpanListSex.className = "sex";
				SpanListSex.innerHTML = "性别："+n.sex+"<br/>";
				
				//添加年龄
				var SpanListAge= document.createElement("span");
				SpanListAge.className = "age";
				SpanListAge.innerHTML = "年龄："+n.age+"<br/>";
				
				//添加等级
				var SpanListLevel= document.createElement("span");
				SpanListLevel.className = "starLevel";
				SpanListLevel.innerHTML = "等级："+n.guideLevel+"<br/>";
				
				PList.appendChild(SpanListName)
				PList.appendChild(SpanListSex)
				PList.appendChild(SpanListAge)
				PList.appendChild(SpanListLevel);
				//添加立即预约链接
				var A1List = document.createElement("a");
				//A1List.href = "?phone="+n.phone+"#orderTicketPop";
				//A1List.setAttribute("data-transition","pop");
				A1List.setAttribute("Phone",n.phone);
				A1List.setAttribute("class","DirectOrderBtn");
				A1List.setAttribute("data-position-to","window");
				LiListInfo.appendChild(A1List);  
			});
			$("#order_guide_ul").listview('refresh');
			myrefresh();
}



