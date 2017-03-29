var Phone = GetUrlem("phone");

$(document).ready(function() {
	//获取预约信息
var visitDate = GetUrlem("visitDate");
var visitTime = GetUrlem("visitTime");
var visitNum = GetUrlem("visitNum");
var scenicName = GetUrlem("scenicName");
alert(visitDate);
alert(visitNum);
alert(visitTime);
alert(scenicName);

//显示所有景点名称
addAllScenics();

//设置预约信息
$("#orderChooseVisitNum").attr("value",visitNum);
$("#orderChooseDate").attr("value",visitDate);
//$("#orderChooseTime option[value = '9:00']").attr("selected","selected");
//$("#orderChooseTime").find("option[text='10:00']").attr("selected",true);
$("#orderChooseTime").val(visitTime);
//$("#orderChooseScenicName").val(scenicName);
$("#orderChooseTime").find("option[text=scenicName]").attr("selected",true);
});


//使信息不为空
function confirmOrder()
{
	//alert("intoConfirm");
	var scenicName = $('#orderChooseScenicName option:selected').val();
	var visitDate = $("#orderChooseDate").val();
	var visitTime = $("#orderChooseTime").val();
	var visitNum = $("#orderChooseVisitNum").val();
	var contactName = $("#orderContactName").val();
	var contactPhone = $("#orderContactPhone").val();
	/*alert(scenicName);
	alert(visitDate);
	alert(visitTime);
	alert(visitNum);
	alert(contactName);
	alert(contactPhone);*/
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