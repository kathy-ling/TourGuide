var openId = GetUrlem("openId");

function weiXin()
{
	var Url = HOST+"/getInfobyOpenID.do";
    $.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{"openId":openId},
		datatype:"JSON",
		error:function()
		{
			alert("根据openId返回数据Request error!");
		},
		success:function(data)
		{
			alert("根据openId返回数据Request success!");
			alert(data.phone);
			if(!data.phone)
			{
				alert("您还未注册，请注册！");
				window.location.href = "register.html";
			}
			else{
				window.location.href = "QRcodeScan.html?"+"guidePhone="+data.guidePhone;
			}
		}
	});
}
