
var Phone = GetUrlem("phone");
var ScenicNo = GetUrlem("scenicNo");
var scenicName = GetUrlem("sname");


window.onload = function() {
	
	addDate();
	
	getInfofromFormer(scenicName);
	
	$("#submitOrderForm").click(function() {
		checkOrderForm();// 检查表单正确后，调用提交方法
	});
}

//从前一个页面获取景点名称
function getInfofromFormer(scenicName){
	//从前一个页面获取到了相应的值后，隐藏选择器，显示lable并赋值
	if(scenicName == "" || scenicName == "null" || scenicName == null){
		$("#ScenicName").hide();
		document.getElementById("chooseScenicNameDiv").style.display = "";
		$("#ScenicName1").hide();
		document.getElementById("chooseScenicNameDiv1").style.display = "";
		addAllScenics();
		addPopularGuides();
	}else{	
		$("#ScenicName").show();
		document.getElementById("ScenicName").innerText = scenicName;
		document.getElementById("chooseScenicNameDiv").style.display = "none";
		$("#ScenicName1").show();
		document.getElementById("ScenicName1").innerText = scenicName;
		document.getElementById("chooseScenicNameDiv1").style.display = "none";
		getAvailableGuides1();
	}	
}

//从服务器获取所有的景区名称，并填充到下拉选择菜单
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
	obj.options.add(new Option(a, a)); // 这个兼容IE与firefox
	obj1.options.add(new Option(a, a));
}


//从服务端获取系统中最受欢迎的讲解员
function addPopularGuides() {
	var url = HOST + "/getPopularGuides.do";
	$.ajax({
		type : "post",
		url : url,
		async : true,
		datatype : "JSON",
		error : function() {
			alert("获取讲解员Request error!");
		},
		success : function(data) {
			addlist(data);
		}
	});
}


//设置日期格式，填充至下拉选择菜单
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
	var dateSelect = document.getElementById("chooseDate");
	var dateSelect1 = document.getElementById("orderDate");
	dateSelect.options.add(new Option(today1,today1));
	dateSelect.options.add(new Option(tomorrow1,tomorrow1));
	dateSelect.options.add(new Option(dayAfterTomo1,dayAfterTomo1));
	
	dateSelect1.options.add(new Option(today1,today1));
	dateSelect1.options.add(new Option(tomorrow1,tomorrow1));
	dateSelect1.options.add(new Option(dayAfterTomo1,dayAfterTomo1));
}

//只输入景区时，进行的筛选
function getAvailableGuides1()
{	
	var scenicName = $('#chooseScenicName option:selected').val();

	if(scenicName == "" || scenicName == "请选择景区"){
		addPopularGuides();
		return;
	}
	
	var Url = HOST + "/getAvailableGuides.do";
	$.ajax({
		type : "post",
		url : Url,
		async : true,
		data : {scenicName:scenicName,visitTime:"null",visitNum:"0"},
		datatype : "JSON",
		error : function() {
			alert("筛选失败，请重新进行选择");
		},
		success : function(data) {
			if (jQuery.isEmptyObject(data)) {
				alert("没有符合条件的讲解员");
			}
			addlist(data);
		}
	});	
}

//当用户输入日期时，
function getAvailableGuides2()
{	
	var scenicName = $('#chooseScenicName option:selected').val();
	var date1= $("#chooseDate").val();
	var time1=$("#orderDatetime123").val();
	var visitTime = "null";
	
	if(scenicName == "" || scenicName == "请选择景区"){
		
		alert("请选择景区！");
		$("#chooseDate option").eq(0).attr("selected",true);
		return;
	}

	if(date1 != "" && time1 != ""){
		visitTime =  date1 + " " + time1
	}
	
	var Url = HOST + "/getAvailableGuides.do";
	$.ajax({
		type : "post",
		url : Url,
		async : true,
		data : {scenicName:scenicName,visitTime: visitTime,visitNum:"0"},
		datatype : "JSON",
		error : function() {
			alert("筛选失败，请重新进行选择");
		},
		success : function(data) {
			if (jQuery.isEmptyObject(data)) {
				alert("没有符合条件的讲解员");
			}
			addlist(data);
		}
	});	
}

//当用户输入时间时，
function getAvailableGuides3()
{	
	var scenicName = $('#chooseScenicName option:selected').val();
	var date1= $("#chooseDate").val();
	var time1=$("#orderDatetime123").val();
	var visitTime = "null";
	
	if(scenicName == "" || scenicName == "请选择景区"){
		
		alert("请选择景区和日期！");
		$("#chooseDate option").eq(0).attr("selected",true);
		return;
	}
	
	if(date1 == "" || date1 == "请选择日期"){
		
		alert("请选择景区和日期！");
		$("#orderDatetime123 option").eq(0).attr("selected",true);
		$("#orderDatetime123 option[value='请选择时间']").attr("selected", true);
		return;
	}

	if(date1 != "" && time1 != ""){
		visitTime =  date1 + " " + time1
	}
	
	var Url = HOST + "/getAvailableGuides.do";
	$.ajax({
		type : "post",
		url : Url,
		async : true,
		data : {scenicName:scenicName,visitTime: visitTime,visitNum:"0"},
		datatype : "JSON",
		error : function() {
			alert("筛选失败，请重新进行选择");
		},
		success : function(data) {
			if (jQuery.isEmptyObject(data)) {
				alert("没有符合条件的讲解员");
			}
			addlist(data);
		}
	});	
}

//当人数发生改变onblur="getAvailableGuides()"
function getAvailableGuides()
{
	var date1= $("#chooseDate").val();
	var time1=$("#orderDatetime123").val();
	var scenicName = $('#chooseScenicName option:selected').val();
	var visitTime =  date1 + " " + time1;
	var visitNum = $("#chooseVisitNum").val();
	$("#chooseVisitNum").empty();
	
	if (scenicName=="") {
		alert("请选择景区，再进行筛选");
		return;
	}	
	if (date1=="") {
		alert("请选择游览日期，再进行筛选");
		return;
	}
	if (time1=="") {
		alert("请选择游览时间，再进行筛选");
		return;
	}	
	if (visitNum=="") {
		alert("请输入游览人数");
		return;
	}
	
	var Url = HOST + "/getAvailableGuides.do";
	$.ajax({
		type : "post",
		url : Url,
		async : true,
		data : {scenicName:scenicName,visitTime:visitTime,visitNum:visitNum},
		datatype : "JSON",
		error : function() {
			alert("筛选失败，请重新进行选择");
		},
		success : function(data) {
			if (jQuery.isEmptyObject(data)) {
				alert("没有符合条件的讲解员");
			}
			addlist(data);
		}
	});	
}


// 确定筛选 onclick="selectAvailableGuides()",根据详细筛选条件显示导游
function selectAvailableGuides() {
	var sex; // 转换性别
	var issex = $("input:radio[name='sex']:checked").val();
	var date1= $("#chooseDate").val();
	var time1=$("#orderDatetime123").val();
	var scenicName = $('#chooseScenicName option:selected').val();
	var visitTime =  date1 + " " + time1;
	var visitNum = $("#chooseVisitNum").val();
	var starlevel = $("#starleve").val();
	var age; // 转换年龄
	var isage = $("input:radio[name='age']:checked").val();
	var language; // 转换语种
	var islanguage = $("input:radio[name='language']:checked").val();
		
	if(starlevel == 0)
	{
		starlevel = "null";
	}
	if (issex == undefined){
		sex = "null";
	} else {
		sex = issex;
	}
	if (isage == undefined) {
		age = "null";
	} else {
		age = isage;
	}
	if (islanguage == undefined) {
		language = "null";
	} else {
		language = islanguage;
	}
	
	if (scenicName=="") {
		alert("请选择景区，再进行筛选");
		return;
	}	
	if (date1=="") {
		alert("请选择游览日期，再进行筛选");
		return;
	}
	if (time1=="") {
		alert("请选择游览时间，再进行筛选");
		return;
	}	
	if (visitNum=="") {
		alert("请选择人数");
		return;
	}
	
	var data = {
		"scenicName" : scenicName,
		"visitTime" : visitTime,
		"visitNum" : visitNum,
		"sex" : sex,
		"age" : age,
		"level" : starlevel,
		"language" : language
	};

	// 从数据库返回符合详细筛选条件的导游
	var url = HOST + "/getAvailableGuidesWithSelector.do"
	$.ajax({
		type : "post",
		url : url,
		async : true,
		data : data,
		datatype : "JSON",
		error : function() {
			alert("筛选Request error!");
		},
		success : function(data) {
			 alert("筛选success!");
			if (jQuery.isEmptyObject(data)) {
				alert("没有符合条件的讲解员");
			}
			addlist(data);
		}

	});
}

// 更新管理员列表
function addlist(data) {
	var visitDate =  $("#chooseDate").val();
	var visitTime=$("#orderDatetime123").val();
	var visitNum = $("#chooseVisitNum").val();
	var scenicName = $('#chooseScenicName option:selected').val();
	$("#order_guide_ul").empty();
	
	$.each(data, function(i, n) {
		var UlList = document.getElementById("order_guide_ul");
		var LiListInfo = document.createElement("li");
		LiListInfo.className = "liStyle";
		UlList.appendChild(LiListInfo);

		var AList = document.createElement("a");
		AList.className = "aStyle";
		AList.href = "guideInfo.html?" + "phone=" + n.phone+"&visitNum="+visitNum+"&visitDate="
		+visitDate+"&visitTime="+visitTime+"&scenicName="+scenicName;

		AList.setAttribute("data-ajax", false);

		LiListInfo.appendChild(AList);

		var ImgList = document.createElement("img");
		ImgList.src = HOST + n.image;
		AList.appendChild(ImgList);
		var PList = document.createElement("p");
		PList.className = "pStyle";

		AList.appendChild(PList);

		// 添加姓名
		var SpanListName = document.createElement("span");
		SpanListName.className = "name";
		SpanListName.innerHTML = "姓名：" + n.name + "<br/>";

		// 添加性别
		var SpanListSex = document.createElement("span");
		SpanListSex.className = "sex";
		SpanListSex.innerHTML = "性别：" + n.sex + "<br/>";

		// 添加年龄
		var SpanListAge = document.createElement("span");
		SpanListAge.className = "age";
		SpanListAge.innerHTML = "年龄：" + n.age + "<br/>";

		// 添加等级
		var SpanListLevel = document.createElement("span");
		SpanListLevel.className = "starLevel";
		SpanListLevel.innerHTML = "等级：" + n.guideLevel + "<br/>";

		// 添加语言
		var SpanListLanguage = document.createElement("span");
		SpanListLanguage.className = "language";
		SpanListLanguage.innerHTML = "讲解语言：" + n.language + "<br/>";
		
		PList.appendChild(SpanListName);
		PList.appendChild(SpanListSex);
		PList.appendChild(SpanListAge);
		PList.appendChild(SpanListLevel);
		PList.appendChild(SpanListLanguage);
		
		// 添加立即预约链接
		var A1List = document.createElement("a");
		A1List.innerHTML = "立即预约";
		A1List.href = "confirmOrderInfo.html?"+ "phone=" + n.phone+"&visitNum="+visitNum+"&visitDate="
		+visitDate+"&visitTime="+visitTime+"&scenicName="+scenicName;
		A1List.setAttribute("Phone", n.phone);
		A1List.setAttribute("data-ajax", false);
		A1List.setAttribute("data-position-to", "window");
		LiListInfo.appendChild(A1List);
	});
	$("#order_guide_ul").listview('refresh');
	myrefresh();
}

function myrefresh() {

	$(".DirectOrderBtn").bind("click", function() {
		var guidephone = $(this).attr("phone");
		$("#DirectorderTicketSub").attr("phone", guidephone);
		$.mobile.changePage("#orderTicketPop", "pop", false, false);
	});
}

//发布订单：验证用户的输入信息，并进行订单的发布
function checkOrderForm() {
	var HalfPrice = 0;
	var DiscoutPrice = 0;
	var FullPrice = 0;
	var PurchaseTicket = 2;
//	var PurchaseTicket = $("input[name='orderTicket']:checked").val();
//	if (PurchaseTicket != null) {
//		if (PurchaseTicket)// 购票
//		{
//			FullPrice = $("#fullPriceTicketNum").val();
//			HalfPrice = $("#halfPriceTicketNum").val();
//			DiscoutPrice = $("#discountTicketNum").val();
//		}
//	} else {
//		alert("请选择是否代购门票！");
//		return false;
//	}
	var scenicName = $('#chooseScenicName1 option:selected').val();

	var data = {
		scenicName : scenicName,
		otherCommand : $("#otherRequest").val(),
		visitNum : $("#visitorCount").val(),
		priceRange : $("#orderM").val(),
		guideSex : $("input[name='guideSex']:checked").val(),
		//vistPhone全局变量，游客的手机号
		visitorPhone : vistPhone, 
		visitorName : $("#visitorName").val(),
		language : $("#guideLanguage option:selected").val(),
		purchaseTicket : PurchaseTicket,
		halfPrice : HalfPrice,
		discoutPrice : DiscoutPrice,
		fullPrice : FullPrice,
		visitTime : $("#orderDate").val() + " " + $("#orderDatetime").val(),
		//contact,游客在发布订单界面填写的联系电话
		contact : $("#visitorPhone").val()
	};

	if (!$("#orderDate").val()) {
		alert("请选择日期!");
		return false;
	}
	if (!$("#orderM").val()) {
		alert("请输入预期价格！");
		return false;
	}
	if (!$("#orderDatetime").val()) {
		alert("请选择时间!");
		return false;
	}
	if (!data.visitNum || data.visitNum < 1) {
		alert("请输入参观人数！");
		return false;
	}
	if (!data.contact || data.contact.length != 11) {
		alert("请输入正确的电话号码！");
		return false;
	}
	if (!data.visitorName) {
		alert("请输入您的姓名！");
		return false;
	}
	releaseOrder(data);
}


// 发布订单
function releaseOrder(formdata) {
	var Url = HOST + "/releaseBookOrder.do";
	$.ajax({
		type : "post",
		url : Url,
		async : true,
		data : formdata,
		datatype : "JSON",
		error : function() {
			alert("发布订单Request error!");
		},
		success : function(data) {
			if (data == true) {
				alert("发布订单成功！");
				window.location.href="orderFormList.html";
			} else {
				alert("发布订单失败");
			}
		}
	});
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

function isRegistOrder()
{
	if(vistPhone == "undefined" || vistPhone == openId)
	{
		alert("您还未注册，请注册！");
		window.location.href = "register.html";
	}
}
//$('#OrderguidePage').bind('pagecreate', function(event, ui) {
//	var ScenicNo = GetUrlem("scenicNo");
//	sname = GetUrlem("sname");
//	addAllScenics();
//	addPopularGuides();
//
//	//$("#bottom_navigation").load("bottomNavigation.html").trigger("create");
//	
//	if (ScenicNo != null) {
//		sessionStorage.scenicNo = ScenicNo;
//	}
//	if (sname != null) {
//		$("#chooseScenicName").val(sname);
//	}
//	//若等级为0，则传空值
//	
//	$("#selectAvailableGuides").click(function() {
//		selectAvailableGuides();
//	});
//	// 查看推荐
//	$("#getAvailableGuides").click(function() {
//		getAvailableGuides();
//	});
//	// 提交订单
//	$("#submitOrderForm").click(function() {
//		checkOrderForm();// 检查表单正确后，调用提交方法
//	});
//});


/*// 查看推荐显示的导游
function getAvailableGuides() {
	var scenicName = $("#chooseScenicName").val();
	var visitTime = $("#chooseDate").val();
	var visitNum = $("#chooseVisitNum").val();

	sessionStorage.directVisitTime = visitTime;
	sessionStorage.directVisitNum = visitNum;

	var url = HOST + "/getNameSimilarScenics.do";
	$.ajax({
		type : "post",
		url : url,
		async : true,
		data : {
			"scenicName" : scenicName
		},
		datatype : "JSON",
		error : function() {
			alert("搜索景区名称Request error!");
		},
		success : function(data) {
			// alert("搜索景区名称Request success!");
			if (data == null) {
				alert("您输入的名称有误，请重新输入！");
			}
			$.each(data, function(i, item) {
				var scenicNo = item.scenicNo;
				sessionStorage.scenicNo = scenicNo;
//				alert(item.scenicName);
				sessionStorage.directScenicName = item.scenicName;
				// $.cookie("scenicFullName",item.scenicName);
				var dataGuide = {
					"scenicName" : item.scenicName,
					"visitTime" : visitTime1,
					"visitNum" : visitNum
				};
				var url = HOST + "/getAvailableGuides.do";
				$.ajax({
					type : "post",
					url : url,
					async : true,
					data : dataGuide,
					datatype : "JSON",
					error : function() {
						alert("推荐讲解员Request error!");
					},
					success : function(data) {
						addlist(data);
					}
				});
			});
		}
	});
}*/