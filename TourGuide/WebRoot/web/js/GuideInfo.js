
var visitDate;
var visitTime;
var visitNum;
var  phone;//讲解员的手机号
var scenicName;
var singleMax;


$('#guideinfoPage').bind('pageshow',function(event, ui){
	
	$(".guideInfoHead").width($(".guideInfoHead").height());
        $(window).bind("resize load",function(){
        	$(".guideInfoHead").width($(".guideInfoHead").height());
        });
        
      /*//加载底部导航栏
	$("#bottom_navigation").load("bottomNavigation.html").trigger("create");*/
      
	phone = GetUrlem("phone");
	visitDate=GetUrlem("visitDate");
	visitTime=GetUrlem("visitTime");
//	visitNum=GetUrlem("visitNum");
	scenicName=GetUrlem("scenicName");
	
	$("#DirectorderTicketSub").attr("phone",phone);
	
	setGuideInfo(phone);
	setGuideComment(phone);
});


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
			$.each(data, function(i,item) {
				$("#guide_info_name").html(item.name);
				$("#guide_info_sex").html(item.sex);
				$("#guide_age").html(item.age);			
				$("#guide_img").attr("src",HOST+item.image);
				$("#bgimg").attr("src",HOST+item.image);
				$("#guide_starlevel").html(item.guideLevel);
				$("#guide_Visitors").html(item.historyNum);
				$("#guide_fee").html(item.guideFee+"元");
				$("#guide_self_intro").html(item.selfIntro);
				$("#guide_phone").html(item.phone);
				$("#guide_language").html(item.language);
				$("#scenicName").html(item.scenicName);
				singleMax = item.singleMax;
				scenicName = item.scenicName;				
			});
		}
	});
}

//获取导游的历史评价记录
function setGuideComment(phone){

var Url = HOST+"/getComments.do";
	$.ajax({
		type:"post",
		url:Url,
		async:true,
		data:{"guidePhone":phone},
		datatype:"JSON",
		error:function()
		{
			alert("导游评价Request error!");
		},
		success:function(data)
		{
			$.each(data,function(i,item){
				var commentStr = "<li><a><span>"+item.nickName+"</span><span>("+item.evaluateTime+")</span><br>";
				commentStr+='<div class="starlev" data-num="'+item.star+'"></div>';
				commentStr += "<span class='commentText'>"+item.evaluateContext+"</span>";
				$("#commentList").append(commentStr);
			});
			$("#commentList").listview('refresh');
			$('.starlev').each(function(){
				$(this).raty({path:'img',readOnly: true, score: $(this).attr("data-num")});
			});
		}
	});
}

//点击立即预定,要先判断是否注册
function bookGuide()
{
	if(vistPhone == "undefined" || vistPhone == openId)
	{
		alert("您还未注册，请注册！");
		window.location.href = "register.html";
	}
	else{
		var black = sessionStorage.getItem("isBlackened");

		if(black == "false"){
			window.location.href = "confirmOrderInfo.html?"+"visitDate="
	        +visitDate+"&visitTime="+visitTime+"&scenicName="+scenicName+"&guidePhone="+phone+
	        "&singleMax="+singleMax;
		}else{
			alert("您已被系统管理员拉黑!");
			return;
		}		
	}
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