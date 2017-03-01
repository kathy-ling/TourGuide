
function guideAuthentication()
{
	var data = {"phone": document.getElementById("apply_tel").value,
				"name":document.getElementById("apply_name").value,
				"sex":$("input[name='guideSex']:checked").val(),
				"language":$("#apply_guideLanguage option:selected").val(),
				"selfIntro":document.getElementById("apply_self_info").value,
				"image":"/test",
				"age":document.getElementById("apply_age").value
				};
	/*alert(data.phone);
	alert(data.name);
	alert(data.selfIntro);
	alert(data.sex);
	alert(data.language);
	alert(data.image);
	alert(data.age);*/
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
			alert("导游申请认证拼单success!");
			if(data == -1)
			{
				alert("您申请的账号已存在！");
			}
		}
	});
}
setperinfo(vistPhone);

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
			//alert(JSON.stringify(data)!="{}");
			if(JSON.stringify(data)!="{}"){
				
			//alert("显示个人信息success!");
			document.getElementById("apply_tel").value = data.phone;
			//document.getElementById("person_info_nickname").value = data.nickName;
			document.getElementById("apply_name").value = data.name;
			//document.getElementById("person_info_sex").value = data.sex;
			//$("#visithead").attr("src",data.image);
			}
		}
	});
}
