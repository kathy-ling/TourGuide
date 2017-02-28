$(function($){
	setapotlist();
	setCurrentSpot();
});


 
    //显示加载器  
    

//填充当前挂靠景点
function setCurrentSpot(){
	$.ajax({
	type:"get",
	url:HOST+'/getCurrentAffiliation.do?guidePhone='+vistPhone,
	async:true,
	error:function(){alert("false");},
	success:function(data){
		var scenid = data[0].scenicBelong;
		//var applyDate = data[0].applyDate;
			$.ajax({
				type:"get",
				url:HOST+'/getSomeScenicInfoByscenicID.do?scenicID='+scenid,
				async:true,
				error:function(){alert("false");},
				success:function(info){
					var appstr="<p>您还未挂靠景点</p>"
					if(info.length==0){
						
					}else{
						appstr = '<p style="font-style:strong;">当前挂靠景区<p><li><a href="#" scenicNo="'+scenid+'"><img src="'+HOST+info[0].scenicImagePath+'"><p>'+
                info[0].scenicName+'<br/>';
					//appstr+='申请时间：'+applyDate+'</p></a>';
					appstr+='<div class="sidebtn"><a href="#" scenicNo="'+scenid+'" onclick="cancleW($(this))" class="ui-btn ui-mini ui-btn-inline  ui-btn-raised clr-warning ">取消挂靠</a><div>';
					$("#apply_ul").find("div.sidebtn").children("a").addClass("ui-disabled");
					}
					$("#currentW_ul").html(appstr);
					$("#currentW_ul").listview("refresh");
				}
			});
	}
	});
}

//填充景点列表
function setapotlist(){
$.ajax({
	type:"get",
	url:HOST+"/getAllScenicByLocation.do?province=陕西",
	async:true,
	error:function(){alert("false");},
	success:function(data){
		$("#apply_ul").empty();
		$.each(data,function(i,n){
			var appstr='<li><a><img src="'+HOST+n.scenicImagePath+'"><p>'+
                '<span class="spotname" scenicNo="'+n.scenicNo+'">'+n.scenicName+'</span><br/></p>'
//                  '景点等级：<span class="starLevel">AAAAA</span><br/>'+
//                  '景点地址：<sapn calss="location">陕西省西安市临潼区临蓝路</sapn>'+
               +'<div class="sidebtn"><a  href="#" scenicNo="'+n.scenicNo+'"onclick="applyit($(this))" class="ui-btn ui-mini ui-btn-raised clr-primary ui-btn-inline" '
                 +'>申请挂靠</a></div>';
			$("#apply_ul").append(appstr);
		});
		$("#apply_ul").listview("refresh");
	}
});
}
//申请挂靠景点
function applyit(This){
	var URL=HOST+'/applyForAffiliation.do?guidePhone='+vistPhone+'&scenicID='+This.attr("scenicNo");
	$.ajax({
		type:"get",
		url:URL,
		async:true,
		error:function(data){console.log(JSON.stringify(data));},
		success:function(data){
			if(data==true){
				setCurrentSpot();
				alert("成功!");
			}else{
				alert("申请失败!");
			}
		}
	});
}
//取消挂靠
function cancleW(This){
	//alert(This.attr("scenicNo"));
	var scenicId = This.attr("scenicNo");
	var URL = HOST+'/cancleAffiliation.do?guidePhone='+vistPhone+'&scenicID='+scenicId;
	$.ajax({
		type:"get",
		url:URL,
		async:true,
		error:function(data){console.log(JSON.stringify(data));},
		success:function(data){
			setCurrentSpot();
			$("#apply_ul").find("div.sidebtn").children("a").removeClass("ui-disabled");
			alert(data);
		}
		});
}
