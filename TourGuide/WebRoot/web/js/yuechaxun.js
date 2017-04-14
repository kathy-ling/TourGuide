
function tixian(){
	window.location.href="tixian.html";
}

$(function($){
	$(".treebox .level1>a").click(function(){
		if($(this).is('.current'))
		{
			$(this).removeClass('current').find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');
			return false;
		}
		else{
			$(this).addClass('current')   //给当前元素添加"current"样式
			.find('i').addClass('down')   //小箭头向下样式
			.parent().next().slideDown('slow','easeOutQuad')  //下一个元素显示
			.parent().siblings().children('a').removeClass('current')//父元素的兄弟元素的子元素去除"current"样式
			.find('i').removeClass('down').parent().next().slideUp('slow','easeOutQuad');//隐藏
			return false; //阻止默认时间
		}
	});
	
	guideInfo();
	getSalaryAmount();
//	getSalaryRecord();
});

function guideInfo(){
	var Url = HOST + "/getDetailGuideInfoByPhone.do";

	$.ajax({
		type: "get",
		url: Url,
		async: true,
		data: {
			guidePhone:'13165662195'
		}, //vistPhone
		datatype: "JSON",
		error: function() {
			alert("request error");
		},
		success: function(data) {
			$.each(data, function(i, n) {
				$('#guideName').html(n.name);
			});
		}
	});
}

function detailInfo(){
	window.location.href="guideInfo.html?phone=" + '13165662195';
}

function getSalaryAmount(){
	var Url = HOST + "/getSalaryAmount.do";

	$.ajax({
		type: "get",
		url: Url,
		async: true,
		data: {
			guidePhone:'13165662195'
		}, //vistPhone
		datatype: "JSON",
		error: function() {
			alert("request error");
		},
		success: function(data) {
			if(JSON.stringify(data)!="{}"){
				$('#totalOrders').html(data.totalOrders);
				$('#totalMoney').html(data.totalMoney);
			}
		}
	});
}

function getSalaryRecord(){
	var Url = HOST + "/getSalaryRecord.do";

	$.ajax({
		type: "get",
		url: Url,
		async: true,
		data: {
			guidePhone:'13165662195'
		}, //vistPhone
		datatype: "JSON",
		error: function() {
			alert("request error");
		},
		success: function(data) {
			alert(data.length);
			$.each(data, function(i, n) {
				alert(n.totalMoney);
//<li class="level1"><a href="#none"><p>日期时间：<span>2017-3-23 10:09:27</span></br></br>团总金额：<span>15元</span></br></p><i class="down"></i></a><ul class="level2" data-icon="false" data-role="listview"><li><span>订单编号:005</span></br><span>景区名称：秦始皇兵马俑</span></br><span>带团人数：15</span></br></ul></li>
				$("#order_ul").empty();
//				var str = '<li class="level1"><a href="#none"><p>日期时间：<span>' + n.time + '</span></br></br>团总金额：<span>' + n.totalMoney + '</span></br></p><i class="down"></i></a>';
				var time = '<li><p>日期时间: <span>' + n.time + '</span><br/>';
				var money = '团总金额: <span>' + n.totalMoney + '</span><br/></p></li> ';
				$("#order_ul").append(time+money);
/*					<li class="level1">
						<a href="#none">
							<p>
								日期时间：<span>2017-3-23 10:09:27</span></br></br>
								团总金额：<span>15元</span></br></p>
							<i class="down"></i>
						</a>
						<ul class="level2" data-icon="false" data-role="listview">
							<li>
								<span>订单编号:005</span></br>
								<span>景区名称：秦始皇兵马俑</span></br>
								<span>带团人数：15</span></br>							
						</ul>
					</li>*/
				/*var UlList = document.getElementById("order_ul");
				var LiListInfo = document.createElement("li");
				LiListInfo.className = "level1";
				UlList.appendChild(LiListInfo);
				
				var AList = document.createElement("a");
				AList.href = "#none";
				AList.setAttribute("data-ajax", false);
				LiListInfo.appendChild(AList);
				
				var PList = document.createElement("p");
				PList.innerHTML = "日期时间：";
				
				var timeSpan = document.createElement("span");
				timeSpan.innerHTML = n.time + "<br/>" + "<br/>";
				
				var totalMoneySpan = document.createElement("span");
				totalMoneySpan.innerHTML = n.totalMoney + "<br/>";
				
				var i = document.createElement("i");
				i.className = "down";*/
				
				
			});
			$("#order_ul").listview("refresh");
		}
	});
}
