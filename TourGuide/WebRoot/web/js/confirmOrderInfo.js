
//获取预约信息
var guidePhone = GetUrlem("guidePhone");
var visitDate = GetUrlem("visitDate");
var	visitTime = GetUrlem("visitTime");
var	visitNum = GetUrlem("visitNum");
var	scenicName = GetUrlem("scenicName");
var singleMax = GetUrlem("singleMax");

$(document).ready(function() {
	
	//动态添加日期
	addDate(); 
	//显示所有景点名称
	addAllScenics();
	//从前一个页面获取信息
	getInfofromFormer();		
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
	if(visitTime == ""){
		$("#orderChooseTime0").hide();	
		document.getElementById("orderChooseTimeDiv").style.display = "";
	}else{		
		$("#orderChooseTime0").show();
		document.getElementById("orderChooseTime0").innerText = visitTime;	
		document.getElementById("orderChooseTimeDiv").style.display = "none";
	}
	
	//设置人数
	$("#orderChooseVisitNum").attr("value",visitNum);
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

//onclick="confirmOrder()">确认,使信息不为空
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

	timeConflict();	
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
	
	if(!visitTime)
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


function restrictNum(){
	visitNum = $("#orderChooseVisitNum").val();
	if(parseInt(visitNum) > parseInt(singleMax)){
		alert("超过讲解员带团的最大人数限制");
		$("#orderChooseVisitNum").val("");
	}	
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
		window.location.href = "personalHome.html";
	}
}