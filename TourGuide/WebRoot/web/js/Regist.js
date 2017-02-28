$('#registerPage').bind('pagebeforecreate',function(event, ui){
	setSex();
	$("#Registsubmit").click(function(){
		Regist();
	});
	$("#visitor_img").click(function(){
		$("#btnFile").click();
	});
});
var image = "";
function selectImage(file)
{
	if(!file.files || !file.files[0])
	{
		return;
	}
	var reader = new FileReader();
	reader.onload = function(evt)
	{
		document.getElementById("visitor_img").src = evt.target.result;
		image = evt.target.result;
	}
	reader.readAsDataURL(file.files[0]);
}

function setSex(){
	var sex = $("#sexlable").attr("sex");
	if(sex==1){
		$("#Male").attr("checked",'true');
	}else if(sex==2){
		$("#Fmale").attr("checked",'true');
	}
}
function Regist()
{
	if(check()){
	var postdata=
	{
		"nickName":$("#nickname").val(),
		"sex":$("input:radio[name='guideSex']:checked").val(),
		"name":$("#name").val(),
		"phone":$("#tel").val(),
		"passwd":$("#password").val(),
		"image":$("#visitor_img").attr("src"),
		"openID":$("#openID").val()
	};	
	//console.log(JSON.stringify(data));
	var url = HOST+"/visitorRegister.do";
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:postdata,
		datatype:"JSON",
		error:function(data)
		{
			alert("注册Request error!");
			//console.log(JSON.stringify(data));
		},
		success:function(data)
		{
			window.location=HOST+"/web/index.html?phone="+postdata.phone;
			//alert("注册success!");
			//alert(data);
		}
	});
	}
}

//检验输入是否合法
function check()
{
	//获取值
	var NickName = $("#nickname").val();
	var Sex = $("input:radio[name='guideSex']:checked").val();
	var Name = $("#name").val();
	var Tel = $("#tel").val();
	var Password = $("#password").val();
	var ConfirmPassword = $("#confirm_password").val();
	var FilePath = $("#btnFile").val();
	
	if(Tel == null || Tel == "")
	{  
		alert("电话不能为空！");
		return false;
	}
	if(Tel.length != 11)
	{
		alert("请输入正确的手机号");
		return false;
	}
     if(Password == null || Password == "")
	{
		alert("密码不能为空！");
		return false;
	}
	 if(ConfirmPassword == null || ConfirmPassword == "")
	{
		alert("确认密码不能为空！");
		return false;
	}
	if(Password != ConfirmPassword)
	{
		alert("两次输入密码不一致，请重新输入！");
		return false;
	}
	if(Password.length < 3)
	{
		alert("密码长度不能少于3位，请重新输入！");
		return false;
	}
     if(!(document.getElementById("agreeClause").checked))
	{
		alert("请选择您接受相关条款服务！");
		return false;
	}
	return true;
}
	
