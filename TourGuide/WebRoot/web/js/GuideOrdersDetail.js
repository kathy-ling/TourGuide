
var OrderID = GetUrlem("Orderid");
//拼单 
//OrderID = 'e44dcd884e964fd39bbd2a4578d42d2a';
//预约单
//OrderID = '0490143030574ae2b31ba43dc904e570';


$(function($){
	$(".treebox .level1>a").click(function(){
		if($(this).is('.current'))
		{
			$(this).removeClass('current').find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');
			return false;
		}
		else{
			$(this).addClass('current')   //给当前元素添加"current"样式
			.find('i').addClass('down')   //小箭头向下样式
			.parent().next().slideDown('slow','easeOutQuad')  //下一个元素显示
			.parent().siblings().children('a').removeClass('current')//父元素的兄弟元素的子元素去除"current"样式
			.find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');//隐藏
			return false; //阻止默认时间
		}
	});
	
	$("#bookOrderDiv").hide();
	$("#consistOrderDiv").hide();
	$("#signInBtn").hide();
	$("#startBtn").hide();
	$("#finishBtn").hide();
	$("#locationBtn").hide();

	getGuideBookOrdersDetail();
	getGuideConsistOrderDetail();
	
	
});


//根据订单编号，讲解员查看自己的预约单的详情
function getGuideBookOrdersDetail(){	
	var Url = HOST + "/getGuideBookOrdersDetail.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderID:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("获取订单详情 Request error!");
		},
		success:function(data)
		{
//			alert("预约单"+JSON.stringify(data));
			if(JSON.stringify(data) != "{}"){
//				alert("into setBookOrderVisitorInfo");
				$("#type").html("——我的预约单");
				$("#bookOrderDiv").show();
				$("#consistOrderDiv").hide();
				
				if(data.state == "待游览"){
					$("#startBtn").show();
					$("#finishBtn").hide();
				}else{
					$("#startBtn").hide();
					$("#finishBtn").show();
				}
		
				setOrderList(data);
				setBookOrderVisitorInfo(data);
			}			
		}
	});
}

//根据订单编号，讲解员查看自己的拼单订单的详情
function getGuideConsistOrderDetail(){	
	var Url = HOST + "/getGuideConsistOrderDetail.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderID:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("获取订单详情 Request error!");
		},
		success:function(data)
		{
//			alert("拼单"+JSON.stringify(data));
			if(JSON.stringify(data) != "{}"){
//				alert("into getConsistVisitorInfo");
				$("#type").html("——我的拼团单");
				$("#bookOrderDiv").hide();
				$("#consistOrderDiv").show();
				
				//0--未讲解
				if(data.state == 0){
					$("#startBtn").show();
					$("#finishBtn").hide();
//					$("#signInBtn").show();
				}else{
					$("#startBtn").hide();
					$("#finishBtn").show();
				}
				
				setOrderList(data);
				getConsistVisitorInfo();				
			}			
		}
	});
}

//讲解员查看自己的拼团订单中的游客的信息
function getConsistVisitorInfo(){
	var Url = HOST + "/getConsistVisitorInfo.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderID:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("获取订单详情 Request error!");
		},
		success:function(data)
		{
			setConsistOrderVisitorInfoList(data);
		}
	});
}

function startVisit(){
	var Url = HOST + "/startVisit.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderId:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("签到Request error!");
		},
		success:function(data)
		{
			if(data == true){
				alert("一切就绪，开始讲解吧!");
			}else{
				alert("发生错误，请稍后再试!");
			}
		}
	});
}

function finishConsistOrder(){
	var Url = HOST + "/finishConsistOrderByGuide.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderId:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("签到Request error!");
		},
		success:function(data)
		{
			if(data == 1){
				return true;
			}else{
				return false;
			}
		}
	});
}

function finishBookOrder(){
	var Url = HOST + "/finishOrderByGuide.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderId:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("签到Request error!");
		},
		success:function(data)
		{
			if(data == 1){
				return true;
			}else{
				return false;
			}
		}
	});
}


//设置只能在参观时间当天进行签到
function checkSignInBtn(){
	
	var currentdate = getNowFormatDate();
	var visitTime = $("#visitTime").html();
	var patt = new RegExp(currentdate);

	if(!patt.test(visitTime)){
		$("#signInBtn").hide();		
	}
	
	var sign = $("#clickSign").html();
	if(sign == 1){
		$("#signInBtn").hide();									
	}else if(sign == 0){
		$("#signInBtn").show();
	}
}

function checkStartBtn(){
	var sign = $("#clickSign").html();
	var state = $("#state").html();
	var loc = $("#clickLocation").html();
	var startTime = $("#clickStart").html();
	
	if(startTime != ""){
		$("#startBtn").hide();
		$("#finishBtn").show();
	}else{
		if(sign == 0){
			$("#startBtn").hide();
		}else{
			if(loc != ""){
				$("#startBtn").show();
			}else{
				$("#startBtn").hide();
			}			
		}
		
		$("#finishBtn").hide();
	}
}

//若订单已完成讲解，则开始讲解按钮、结束讲解按钮、位置按钮都不可见
function checkFinishBtn(){
	var sign = $("#clickSign").html();
	var state = $("#state").html();
	var loc = $("#clickLocation").html();
	var startTime = $("#clickStart").html();
	var endTime = $("#clickFinish").html();

	if(startTime != ""){
		$("#startBtn").hide();
		$("#finishBtn").show();
	}else{
		if(sign == 0){
			$("#startBtn").hide();
		}else{
			if(loc != ""){
				$("#startBtn").show();
			}else{
				$("#startBtn").hide();
			}			
		}		
		$("#finishBtn").hide();
	}
	if(endTime != ""){
		$("#finishBtn").hide();
		$("#bookOrderDiv").hide();
		$("#consistOrderDiv").hide();
	}
	
	/*if(state == 1 || state == "待评价"){//已完成讲解
		$("#finishBtn").hide();
		$("#bookOrderDiv").hide();
		$("#consistOrderDiv").hide();
	}else{
		if(startTime != ""){
			$("#finishBtn").show();
		}else{
			$("#finishBtn").hide();
		}		
	}*/	
}

function checkLocationBtn(){
	var sign = $("#clickSign").html();
	var loc = $("#clickLocation").html();
	var startTime = $("#clickStart").html();
	
	if(loc != ""){
		$("#locationBtn").hide();
	}else{
		if(sign == 0){
			$("#locationBtn").hide();
		}else{
			$("#locationBtn").show();
		}
	}
}

//生成订单的二维码
function produce()
{
	var id = $("#orderID").html();
	
	$("#qrcode").empty();
	
	jQuery('#qrcode').qrcode(utf16to8(id));
	$("#popupDialog").popup('open');
}

//点击【签到】
function signIn(){	
	var Url = HOST + "/guideSignIn.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderId:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("签到Request error!");
		},
		success:function(data)
		{
			if(data == true){
				alert("签到成功");

				$("#signInBtn").hide();
				$("#startBtn").hide();
				$("#finishBtn").hide();
				$("#locationBtn").show();
			}
		}
	});
}

//点击【位置】
function setLocation(){
	
	var type = $("#type").html();
	
	window.location.href = "mapLocationGuide.html?"+"type="+type+"&OrderID="+OrderID;
}

//点击【开始讲解】
function start(){
	startVisit();
	
	$("#startBtn").hide();
	$("#finishBtn").show();
	$("#locationBtn").hide();
		
}

//点击【结束讲解】
function finish(){
	var type = $("#type").html();
	
	if(type == "——我的拼团单"){
		finishConsistOrder();
	}else if(type == "——我的预约单"){
		finishBookOrder();
	}
	
	$("#finishBtn").hide();
	$("#locationBtn").hide();
	$("#bookOrderDiv").hide();
	$("#consistOrderDiv").hide();
}

function setOrderList(data){
	$("#orderID").html(data.orderID);
	$("#scenicName").html(data.scenicName);
	$("#visitTime").html(data.visitTime);
	$("#visitNum").html(data.visitNum);	
	$("#totalFee").html(data.money);
	if(data.state == 1){
		$("#state").html("待评价");
	}else if(data.state == 0){
		$("#state").html("待游览");
	}else{
		$("#state").html(data.state);
	}
	$("#clickSign").html(data.signIn);
	$("#clickStart").html(data.startTime);
	$("#clickLocation").html(data.longitude);
	$("#clickFinish").html(data.endTime);
	
	checkSignInBtn();
	checkStartBtn();
	checkFinishBtn();
	checkLocationBtn();
}

function setBookOrderVisitorInfo(data){
	
	$("#Name").html(data.visitorName);
	$("#Phone").html(data.contact);
	var a = document.getElementById("forPhone");
	a.href = "tel:" + data.contact;
	a.setAttribute("data-ajax", false);
}

function setConsistOrderVisitorInfoList(data){

	$.each(data, function(i,n) {
		
		var UlList = document.getElementById("visitorInfo_ul");
		var LiListInfo = document.createElement("li");
		LiListInfo.className = "level1";
		UlList.appendChild(LiListInfo);
		
		var AList = document.createElement("a");
		AList.href = "#none";
		AList.setAttribute("data-ajax", false);
		
		var PList = document.createElement("p");
		
		var visitorNameSpan = document.createElement("span");
		visitorNameSpan.innerHTML = "游客姓名：" + n.visitorName + "<br/>" + "<br/>";
		
		var visitNumSpan = document.createElement("span");
		visitNumSpan.innerHTML = "游客人数：" + n.visitNum + "<br/>";
		
		PList.appendChild(visitorNameSpan);
		PList.appendChild(visitNumSpan);
		
		var i = document.createElement("i");
		i.className = "down"; 
		
		AList.appendChild(PList);
		AList.appendChild(i);
		
		var phoneUlList = document.createElement("ul");
		phoneUlList.className = "level2";				
		
		LiListInfo.appendChild(AList);
		LiListInfo.appendChild(phoneUlList);
		
		var phoneLiList = document.createElement("li");
		phoneUlList.appendChild(phoneLiList);
		
		var PList2 = document.createElement("p");
		
		var visitorPhoneSpan = document.createElement("span");
		visitorPhoneSpan.innerHTML = "游客手机号：" + n.visitorPhone;
		
		var a = document.createElement("a");
		a.href = "tel:" + n.visitorPhone;
		a.setAttribute("data-ajax", false);
		
		var phoneBtn = document.createElement("button");
		phoneBtn.className = "phone";
		phoneBtn.innerHTML = "一键拨号";
		
		PList2.appendChild(visitorPhoneSpan);
		PList2.appendChild(a);
		a.appendChild(phoneBtn);
		phoneLiList.appendChild(PList2);				
	});
	$("#visitorInfo_ul").listview('refresh');
	myrefresh();	
}


function myrefresh()
{
	$(".treebox .level1>a").click(function(){
	if($(this).is('.current'))
	{
		$(this).removeClass('current').find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');
		return false;
	}
	else{
	 		$(this).addClass('current')   //给当前元素添加"current"样式
			.find('i').addClass('down')   //小箭头向下样式
			.parent().next().slideDown('slow','easeOutQuad')  //下一个元素显示
			.parent().siblings().children('a').removeClass('current')//父元素的兄弟元素的子元素去除"current"样式
			.find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');//隐藏
			return false; //阻止默认时间
		}
	});
}


function isRegist()
{
	if(vistPhone == undefined || vistPhone == openId)
	{
		alert("您还未注册，请注册！");
		window.location.href = "register.html";
	}else{
		var black = sessionStorage.getItem("isBlackened");

		if(black == "false"){
			window.location.href = "personalHome.html";
		}else{
			alert("您已被系统管理员拉黑!");
		}
	}
}

//"yyyy-mm-dd",获取当前的时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
   
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;

    return currentdate;
}

/*$.each(data, function(i,n) {
		
		var s1 = '<li class="level1"><a href="#none">';
		var s2 = '<p><span id="visitorName">游客姓名：' + n.visitorName + '</span></br></br>';
		var s3 = '<span id="num">游客人数：' + n.visitNum + '</span></br></p>';
		var s4 = '<i class="down"></i></a><ul class="level2" data-icon="false" data-role="listview">';
		var s5 = '<li><p><span id="visitorPhone">游客手机号：' + n.visitorPhone + '</span>';
		var s6 = '<button type="button" data-inline="ture">一键拨号</button></p></li></ul></li>';
		
		$("#visitorInfo_ul").append(s1 +s2 +s3 +s4 +s5 +s6);
	});
	$("#visitorInfo_ul").listview('refresh');*/
	
//判断讲解员的订单是否签到，true--已经签到
/*function isSignIn(){
	alert("in to isSignIn");
	var Url = HOST + "/isSignIn.do";
	
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{orderId:OrderID},
		datatype:"JSON",
		error:function()
		{
			alert("签到Request error!");
		},
		success:function(data)
		{
//			alert("data="+data);
			if(data == true){//已经签到
				sign = true;
				$("#signInBtn").hide();									
				$("#startBtn").show();
				$("#finishBtn").hide();
				$("#locationBtn").show();								
			}else{//没有签到
				sign = false;
				$("#signInBtn").show();
				$("#startBtn").hide();
				$("#finishBtn").hide();
				$("#locationBtn").hide();
			}
		}
	});
}*/