
//获取预约信息
var guidePhone = GetUrlem("guidePhone");
var visitDate = GetUrlem("visitDate");
var	visitTime = GetUrlem("visitTime");
//var	visitNum = GetUrlem("visitNum");
var	scenicName = GetUrlem("scenicName");
var singleMax = GetUrlem("singleMax");
var	visitNum;

$(document).ready(function() {
	
	//动态添加日期
	addDate(); 
	//显示所有景点名称
	addAllScenics();
	//从前一个页面获取信息
	getInfofromFormer();	
	
	if(guidePhone != "null"){
		setGuideInfo(guidePhone);		
	}
	
});


//从前一个页面获取景点名称、日期、时间、人数
function getInfofromFormer(){
	//从前一个页面获取到了相应的值后，隐藏选择器，显示lable并赋值
	if(scenicName == ""){
		$("#scenicName0").hide();
		document.getElementById("orderChooseScenicNameDiv").style.display = "";
	}else{				
		$("#scenicName0").show();
		document.getElementById("scenicName0").innerText = scenicName;
		document.getElementById("orderChooseScenicNameDiv").style.display = "none";
	}	
	if(visitDate == ""){
		$("#orderChooseDateLabel").hide();
		document.getElementById("orderChooseDateDiv").style.display = "";
	}else{
		document.getElementById("orderChooseDateDiv").style.display = "none";
		$("#orderChooseDateLabel").show();
		document.getElementById("orderChooseDateLabel").innerText = visitDate;			
	}
	if(visitTime == "" || visitTime == "请选择时间"){
		$("#orderChooseTime0").hide();	
		document.getElementById("orderChooseTimeDiv").style.display = "";
	}else{		
		$("#orderChooseTime0").show();
		document.getElementById("orderChooseTime0").innerText = visitTime;	
		document.getElementById("orderChooseTimeDiv").style.display = "none";
	}
	
	//设置人数
//	$("#orderChooseVisitNum").attr("value",visitNum);
}

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
	var dateSelect = document.getElementById("orderChooseDate");
	dateSelect.options.add(new Option(today1,today1));
	dateSelect.options.add(new Option(tomorrow1,tomorrow1));
	dateSelect.options.add(new Option(dayAfterTomo1,dayAfterTomo1));	
}

//onclick="confirmOrder()">【确认】,使信息不为空
function confirmOrder()
{
	confirmOrderBefore();
	
	var contactName = $("#orderContactName").val();
	var contactPhone = $("#orderContactPhone").val();

	if(!scenicName)
	{
		alert("请选择景区！");
		return false;
	}
	if(!visitDate)
	{
		alert("请选择日期！");
		return false;
	}
	if(!visitTime)
	{
		alert("请选择时间！");
		return false;
	}
	if(!visitNum)
	{
		alert("请填写参观人数！");
		return false; 
	}
	if(!contactName)
	{
		alert("请填写联系人姓名！");
		return false;
	}
	if(!contactPhone)
	{
		alert("请填写联系人手机号！");
		return false;
	}
	
	var time = visitDate + " " + visitTime;
	var timeNow = getNowFormatDate();
	if(!timeCompare(time, timeNow)){
			return false;	
	}

	timeConflict();	
}

//当时间选定后触发的事件
function setTime(){
	visitTime = $('#orderChooseTime option:selected').val();
//	alert("setTime visitTime" + visitTime);
}

//如果从前一页面没有取到值，则从选择器中选择
function confirmOrderBefore()
{
	if(!scenicName)
	{
		scenicName = $('#orderChooseScenicName option:selected').val();
	}
	
	if(!visitDate)
	{
		visitDate = $("#orderChooseDate").val();
	}
	
	if(!visitTime && visitTime != "请选择时间")
	{
		visitTime = $('#orderChooseTime option:selected').val();
	}

	if(!visitNum)
	{
		visitNum = $("#orderChooseVisitNum").val();
	}
}

//判断讲解员的时间与预约时间是否冲突，True 冲突,false  不冲突
function timeConflict(){
	var url = HOST + "/isTimeConflict.do";
	var time = visitDate + " " + visitTime;
	var contactPhone = $("#orderContactPhone").val();

	$.ajax({
		type : "post",
		url : url,
		async : true,
		data:{"guidePhone":guidePhone,"visitTime": time},
		datatype : "JSON",
		error:function()
		{
			alert("timeConflict Request error!");
		},
		success : function(data) {
			if(data != false){
				alert("改讲解员时间发生冲突，请重新选择");
				window.location.href = "orderGuide.html";
			}else{
				window.location.href="orderFormPage.html?"+ "contactPhone=" +contactPhone+"&visitNum="+visitNum+"&visitDate="
				+visitDate+"&visitTime="+visitTime+"&scenicName="+scenicName+"&guidePhone="+guidePhone;
			}			
		}
	});
}

//获取并设置导游信息
function setGuideInfo(phone){
	var Url = HOST+"/getDetailGuideInfoByPhone.do";
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{"guidePhone":phone},
		datatype:"JSON",
		error:function()
		{
			alert("导游详细信息Request error!");
		},
		success:function(data)
		{
//			alert(JSON.stringify(data));
			$.each(data, function(i,item) {				
				singleMax = item.singleMax;				
			});
		}
	});
}

function restrictNum(){
	
	var num = $("#orderChooseVisitNum").val();

	if (num == "") {
		alert("请填写游览人数");
		$("#orderChooseVisitNum").val("");
		return false;		
	}else if(!( /^\+?[1-9][0-9]*$/).test(num)){
		alert("请输入正确的人数");
		$("#orderChooseVisitNum").val("");
		return false;
	}
	
	if(parseInt(num) > parseInt(singleMax)){
		alert("超过讲解员带团的最大人数限制");
		$("#orderChooseVisitNum").val("");
		return false;
	}	
}

//输入手机号后，验证手机号的有效性
function checkPhone(){
	//表示以1开头，第二位可能是3/4/5/7/8等的任意一个，在加上后面的\d表示数字[0-9]的9位，总共加起来11位结束。
	var reg = /^1[3|4|5|7|8][0-9]{9}$/; 
	var phone = $("#orderContactPhone").val()
	
	if(!reg.test(phone)){
		alert('请输入有效的手机号码！');
		$("#orderContactPhone").val("");
    	return false;
	}
}

//比较两个时间的大小
function timeCompare(endTime, beginTime){
	
	var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	if(a < 0){
		alert("您选择的时间在当前时间之前，请重新选择!");
		return false;
	}else{
		return true;	
	}
}

//"yyyy-mm-dd hh:mm",获取当前的时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var minute = date.getMinutes();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + minute;

    return currentdate;
}

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
	var obj = document.getElementById('orderChooseScenicName');
	// 这个只能在IE中有效
	obj.options.add(new Option(a, a)); // 这个兼容IE与firefox
}

function isRegist()
{
	if(vistPhone == "undefined" || vistPhone == openId)
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