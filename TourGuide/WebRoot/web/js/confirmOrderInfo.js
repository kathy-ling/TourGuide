var Phone = GetUrlem("phone");

$(document).ready(function() {
	alert("intoConfirm");
	//获取预约信息
var visitDate = GetUrlem("visitDate");
var visitTime = GetUrlem("visitTime");
var visitNum = GetUrlem("visitNum");
var scenicName = GetUrlem("scenicName");

alert(visitDate);
alert(visitNum);
alert(visitTime);
alert(scenicName);

addDate(); //动态添加日期

//显示所有景点名称
addAllScenics();


//判断景点名称是否为空
if(scenicName == null || !scenicName)
{
	$("#scenicName0").hide();
}else{
	document.getElementById("orderChooseScenicNameDiv").style.display = "none";
	document.getElementById("scenicName0").innerText = scenicName;
}
//判断日期是否为空
if(visitDate == null || !visitDate)
{
	$("#orderChooseDateLabel").hide();
}else{
	document.getElementById("orderChooseDateLabel").innerText = visitDate;
	document.getElementById("orderChooseDateDiv").style.display = "none";
}
//判断时间是否为空
if(visitTime == null || !visitTime)
{
	$("#orderChooseTime0").hide();
}else{	
	//$("#orderChooseTime").hide();
	//$("#orderChooseTime0").val(visitTime);
	document.getElementById("orderChooseTime0").innerText = visitTime;
	document.getElementById("orderChooseTimeDiv").style.display = "none";
}

//设置预约信息
$("#orderChooseVisitNum").attr("value",visitNum);
//$("#orderChooseDate").attr("value",visitDate);

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
	var dateSelect = document.getElementById("orderChooseDate");
	//dateSelect.append("<option value='"+dayAfterTomo0+"'>"+dayAfterTomo0+"</option>");
	dateSelect.options.add(new Option(today1,today1));
	dateSelect.options.add(new Option(tomorrow1,tomorrow1));
	dateSelect.options.add(new Option(dayAfterTomo1,dayAfterTomo1));
	
}

//使信息不为空
function confirmOrder()
{
	//alert("intoConfirm");
	if(scenicName == null || !scenicName)
	{
		scenicName = $('#orderChooseScenicName option:selected').val();
	}
	if(visitDate == null || !visitDate)
	{
		visitDate = $("#orderChooseDate").val();
	}
	if(visitTime == null || !visitTime)
	{
		visitTime = $("#orderChooseTime").val();
	}
	if(visitNum == null || !visitNum)
	{
		visitNum = $("#orderChooseVisitNum").val();
	}
	var contactName = $("#orderContactName").val();
	var contactPhone = $("#orderContactPhone").val();
	alert(scenicName);
	alert(visitDate);
	alert(visitTime);
	alert(visitNum);
	alert(contactName);
	alert(contactPhone);
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
	window.location.href="orderFormPage.html?"+ "phone=" +phone+"&visitNum="+visitNum+"&visitDate="
	+visitDate+"&visitTime="+visitTime+"&scenicName="+scenicName;
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
	//var obj1 = document.getElementById('chooseScenicName1');
	// 这个只能在IE中有效
	obj.options.add(new Option(a, a)); // 这个兼容IE与firefox
	//obj1.options.add(new Option(a, a));
}