
window.onload = function() {

	getUserInfo();

	$("#visitor_img").click(function() {
		$("#btn_file").click();		
	});
}

//从服务端获取用户的部分信息，并显示在页面
function getUserInfo() {
	var url = HOST + "/getInfobyOpenID.do";

	$.ajax({
		type: "post",
		url: url,
		async: true,
		data: {
			openId: openId
		},
		datatype: "JSON",
		error: function() {
			alert("获取个人信息Request error!");
		},
		success: function(data) {

			if(JSON.stringify(data) != "{}") {
				$("#nickname").val(data.nickName);
				$("#visitor_img").attr("src", data.image);
			}
		}
	});
}

var image = "";

function selectImage(file) {
	if(!file.files || !file.files[0]) {
		return;
	}
	var reader = new FileReader();
	reader.onload = function(evt) {
		document.getElementById("visitor_img").src = evt.target.result;
		image = evt.target.result;
	}
	reader.readAsDataURL(file.files[0]);	
}


//上传头像，并进行注册
function changePerHeadImg()
{
	alert(openId);
	var URL = HOST+"/putImg.do";
	
	$.ajaxFileUpload({
			url : URL,
			fileElementId:'btn_file',
			dataType : "json",
			success: function(data){
				if(data == true)					
				{
					Regist();
				}else
				{
					alert("图片上传失败");
				}				
			 },
			error: function(data)
			{
				
		  	alert("图片上传异常");
			}
	});	
}


function Regist() {
	
	if(check()) {
		var postdata = {
			"nickName": $("#nickname").val(),
			"sex": $("input:radio[name='guideSex']:checked").val(),
			"name": $("#name").val(),
			"phone": $("#tel").val(),
			"passwd": $("#password").val(),
			"openID": openId
		};

		var url = HOST + "/visitorRegister.do";
		$.ajax({
			type: "post",
			url: url,
			async: true,
			data: postdata,
			datatype: "JSON",
			error: function(data) {
				alert("注册Request error!");
			},
			success: function(data) {
				window.location = HOST + "/web/index.html?phone=" + postdata.phone;				
			}
		});
	}
}

//检验输入是否合法
function check() {
	//获取值
	var NickName = $("#nickname").val();
	var Sex = $("input:radio[name='guideSex']:checked").val();
	var Name = $("#name").val();
	var Tel = $("#tel").val();
	var Password = $("#password").val();
	var ConfirmPassword = $("#confirm_password").val();
	var FilePath = $("#btnFile").val();

	if(Tel == null || Tel == "") {
		alert("电话不能为空！");
		return false;
	}
	if(Tel.length != 11) {
		alert("请输入正确的手机号");
		return false;
	}
	if(Password == null || Password == "") {
		alert("密码不能为空！");
		return false;
	}
	if(ConfirmPassword == null || ConfirmPassword == "") {
		alert("确认密码不能为空！");
		return false;
	}
	if(Password != ConfirmPassword) {
		alert("两次输入密码不一致，请重新输入！");
		return false;
	}
	if(Password.length < 3) {
		alert("密码长度不能少于3位，请重新输入！");
		return false;
	}
	if(!(document.getElementById("agreeClause").checked)) {
		alert("请选择您接受相关条款服务！");
		return false;
	}
	return true;
}