
$(function($){
	
    $('#star1').raty({ path:'img', score: 0,width:200});
    $('#star2').raty({ path:'img', score: 0,width:200});
    $('#star3').raty({ path:'img', score: 0,width:200});
    
    $("#evalueContext").keydown(function(){       
        var maxCount = 200;
        var curLength = $("#evalueContext").val().length;
        if(curLength > maxCount){
            var num = $("#evalueContext").val().substr(0,4);
            $("#evalueContext").val(num);
            alert("超过字数限制啦!" );
        }
        else{
            $("#textCount").text(maxCount-$("#evalueContext").val().length + "字")
        }
    });	             
});

//点击【提交】，发布评论
function commentSub(){
	
	var star1 =  $('#star1').raty('score');
    var star2 =  $('#star2').raty('score');
    var star3 =  $('#star3').raty('score');
    var text = $("#evalueContext").val();
    var orderId = GetUrlem("orderId");
    var isanonymous = 0;
    if($("input:checkbox[name='anonymous']").get(0).checked)
    {
        isanonymous = 1;
    }
    if(star1==undefined && star2==undefined && star3==undefined){
    	alert("请为其中的至少一项打分!");
    	return false;
    }
    if(text == ""){
    	alert("评论内容不能为空");
    	return false;
    }
    var postData={
        "orderID":orderId,//动态获取
        "evaluateContext":text,
        "isAnonymous":isanonymous,
        "star1":star1,
        "star2":star2,
        "star3":star3
    };
    alert(star1);
    commentByVisitor(postData);
}

//
function commentByVisitor(postData){
	var postUrl = HOST+"/commentByVisitor.do";
    
    $.ajax({
    	type:"post",
    	url:postUrl,
    	async:true,
    	data:postData,
		datatype:"JSON",
		error:function()
		{
		alert("Error:评论失败！");
		},
		success:function(data)
		{ 
			alert("评论成功！");
		}
    });
}
