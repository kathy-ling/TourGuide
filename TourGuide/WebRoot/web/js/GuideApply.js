
$(document).ready(function() {
				
	$(window).bind('resize load', function() {
		$(".perheader").height($(window).width() * 0.5);
		$(".squareImg").width($(".squareImg").height());
		
		//加载底部导航栏
	$("#bottom_navigation").load("bottomNavigation.html").trigger("create");
	});
	
	var Phone = vistPhone;
	setperinfo(Phone);
	
	$("#visithead").click(function() {
		$("#btn_file").click();		
	});
});
			

function setperinfo(Phone){
	var url = HOST+"/getVisitorInfoWithPhone.do";
	
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:{phone:Phone},
		datatype:"JSON",
		error:function()
		{
			alert("显示个人信息Request error!");
		},
		success:function(data)
		{
			if(JSON.stringify(data)!="{}"){
				$("#apply_tel").val(data.phone);
				$("#apply_name").val(data.name);
			}
		}
	});
}

var image0 = "";
function selectImage(file)
{
	if(!file.files || !file.files[0]) {
		return;
	}
	var reader = new FileReader();
	reader.onload = function(evt) {
		document.getElementById("visithead").src = evt.target.result;
		image0 = evt.target.result;		
	}
	reader.readAsDataURL(file.files[0]);	
}

function upLoadImg()
{
	var URL = HOST+"/upLoadImg.do";
	
	$.ajaxFileUpload({
			url : URL,
			fileElementId:'btn_file',
			dataType : "json",
			success: function(data){
				if(data == true)					
				{
					guideAuthentication();
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

function guideAuthentication()
{
	var phone = document.getElementById("apply_tel").value;
	var name = document.getElementById("apply_name").value;
	var sex = $("input[name='guideSex']:checked").val();
	var language = $("#apply_guideLanguage option:selected").val();
	var selfIntro = document.getElementById("apply_self_info").value;
	var age = document.getElementById("apply_age").value;
	var workAge = $("#apply_workAge").val();
	
	var data = {"phone":phone,
				"name":name,
				"sex":sex,
				"language":language,
				"selfIntro":selfIntro,
				"age":age,
				"workAge":workAge
			};
	
	var url = HOST+"/getGuideAuthentication.do";
	
	$.ajax({
		type:"post",
		url:url,
		async:true,
		data:data,
		datatype:"JSON",
		error:function()
		{
			alert("导游申请认证Request error!");
		},
		success:function(data)
		{			
			if(data == -1)
			{
				alert("您申请的账号已存在！");
			}
			if(data == 1){
				alert("导游申请成功，系统审核后将会以短信的方式通知您，请耐心等待!");
				alert("请您继续填写挂靠景区信息！");
				window.location.href = "applyForAffiliation.html";
			}
			if(data == 0){
				alert("导游申请认证Request error!");
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

