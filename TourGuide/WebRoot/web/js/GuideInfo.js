$('#guideinfoPage').bind('pageshow',function(event, ui){
	
	$(".guideInfoHead").width($(".guideInfoHead").height());
        $(window).bind("resize load",function(){
        	$(".guideInfoHead").width($(".guideInfoHead").height());
        });
	var phone = GetUrlem("phone");
	$("#DirectorderTicketSub").attr("phone",phone);
	$("#bookGuide").click(function(){
		//alert("123");
		$.mobile.changePage("./orderGuide.html#orderTicketPop", "pop", false, false);
	});
//	$("#bookGuide").click(function(){
//		bookGuide(phone);
//	});
	setGuideInfo(phone);
	setGuideComment(phone);
});

//$("#bookGuide").click(function(){
//	 $.mobile.changePage('#orderTicketPop', {
//          transition: "slide",
//          role: "dialog"
//      });
//});

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
			//alert("导游详细信息success!");
			$.each(data, function(i,item) {
				$("#guide_info_name").html(item.name);
				$("#guide_info_sex").html(item.sex);
				$("#guide_age").html(item.age);			
//				$("#guide_img").attr("src","img/1.jpg");
				$("#guide_img").attr("src",HOST+item.image);
				$("#bgimg").attr("src",HOST+item.image);
				$("#guide_starlevel").html(item.guideLevel);
				$("#guide_Visitors").html(item.historyNum);
				$("#guide_fee").html(item.guideFee+"元");
				$("#guide_self_intro").html(item.selfIntro);
				$("#guide_phone").html(item.phone);
				if(item.language == "0")
				{
					$("#guide_language").html("中文");
				}
				if(item.language == "1")
				{
					$("#guide_language").html("英文");
				}
				if(item.language == "2")
				{
					$("#guide_language").html("中文 英文");
				}
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
			//alert(JSON.stringify(data));
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

//点击立即预定
function bookGuide(guidephone)
{
	$("#DirectorderTicketSub").attr("phone",guidephone);
}
