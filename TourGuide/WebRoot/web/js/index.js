
var province;

var openId;

$(function($) {
	
	//加载底部导航栏
	$(".bottom_navigation").load("bottomNavigation.html").trigger("create");
	

	$(document).bind("mobileinit", function() {
		$.mobile.page.prototype.options.addBackBtn = true;
	});
	function bindSearch() {
		$(".search").focus(function(event) {
			$.mobile.changePage("#searchPanelPage", "slideright");
		});
	}
	bindSearch();
	$(".searchTagPanel").click(function(event) {
		var searchtext = event.target.innerText;
		$("#mySearchInput").val(searchtext);
		$("#searchSubmit").trigger("click");
	});
	//点击搜索按钮
	$("#searchSubmit").click(function(event) {
		sessionStorage.searchText = $("#mySearchInput").val();
		$.mobile.changePage("#searchResultPage", "slideright");
	});
	//更多景点页面创建时触发 执行一次
	$('#moreSpotPage').bind('pagecreate', function(event, ui) {
		bindSearch();
		var url = HOST + "/getAllScenics.do";
		$.ajax({
			type : "post",
			url : url,
			async : true,
			datatype : "JSON",
			error : function() {
				alert("全部景点Request error!");
			},
			success : function(data) {
				var UlList = document.getElementById("more_ul");
				freshList(data, UlList);
			}
		});
	});
	//搜索结果页面创建时触发 执行一次
	$('#searchResultPage').bind('pagecreate', function(event, ui) {
		bindSearch();
	});
	//搜索面板显示时触发 每次都执行
	$('#searchPanelPage').bind('pageshow', function(event, ui) {
		setTimeout(function() {
			$("#mySearchInput").trigger("click").focus();
		}, 10);

	});

	$('#searchResultPage').bind(
			'pagebeforeshow',
			function(event, ui) {
				if (sessionStorage.searchText) {
					$(".search").val(sessionStorage.searchText);
					var url = HOST + "/getScenicByName.do";
					$.ajax({
						type : "post",
						url : url,
						async : true,
						data : {
							"scenicName" : sessionStorage.searchText
						},
						datatype : "JSON",
						error : function() {
							alert("搜索结果Request error!");
						},
						success : function(data) {
							//alert("搜索结果Request success!");
							$.each(data, function(i, item) {
								//alert(item.scenicNo);
								$("#imgA").attr(
										"href",
										"scenicSpot.html?scenicNo="
												+ item.scenicNo);
								$("#search_img").attr("src",
										HOST + item.scenicImagePath);
								$("#search_scenic_intro")
										.html(item.scenicIntro);
								$("#search_starlevel").html(item.scenicLevel);
								$("#search_address").html(
										item.province + item.city
												+ item.scenicLocation);
							});

						}
					});
					//获取推荐景点
					var url = HOST + "/getScenicRelatesByName.do";
					$.ajax({
						type : "post",
						url : url,
						async : true,
						data : {
							"scenicName" : sessionStorage.searchText
						},
						datatype : "JSON",
						error : function() {
							alert("相关推荐景点Request error!");
						},
						success : function(data) {
							var UlList = document.getElementById("search_ul");
							freshList(data, UlList);
						}
					});

				}
			});

});

/////////////////////////////////////  HTML5定位  ////////////////////////////////////////////
//function getLocation() {
//
//	if (navigator.geolocation) {
//
//		navigator.geolocation.getCurrentPosition(showPosition, showError);
//	} else {
//		alert("浏览器不支持地理定位。");
//	}
//}
//
//function showError(error) {
//	switch (error.code) {
//	case error.PERMISSION_DENIED:
//		alert("定位失败,用户拒绝请求地理定位");
//		break;
//	case error.POSITION_UNAVAILABLE:
//		alert("定位失败,位置信息是不可用");
//		break;
//	case error.TIMEOUT:
//		alert("定位失败,请求获取用户位置超时");
//		break;
//	case error.UNKNOWN_ERROR:
//		alert("定位失败,定位系统失效");
//		break;
//	}
//}
//
//function showPosition(position) {
//	var x = position.coords.latitude; //纬度 
//	var y = position.coords.longitude; //经度 
//	alert('纬度:' + x + ',经度:' + y);
//
//	//配置Baidu Geocoding API 
//	var url = "http://api.map.baidu.com/geocoder/v2/?ak=C93b5178d7a8ebdb830b9b557abce78b"
//			+ "&callback=renderReverse"
//			+ "&location="
//			+ x
//			+ ","
//			+ y
//			+ "&output=json" + "&pois=0";
//
//	$.ajax({
//		type : "GET",
//		async: false,
//		dataType : "jsonp",
//		url : url,
//		success : function(json) {
//			if (json == null || typeof (json) == "undefined") {
//				return;
//			}
//			if (json.status != "0") {
//				return;
//			}
//			setAddress(json.result.addressComponent);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//			alert("[x:" + x + ",y:" + y + "]地址位置获取失败,请手动选择地址");
//		}
//	});
//}
//
///** 
// * 设置地址 
// */
//function setAddress(json) {
//	var position = document.getElementById("location");
//	//省 
//	province = json.province;
//	//市 
//	var city = json.city;
//	//区 
//	var district = json.district;
//	province = province.replace('市', '');
//	position.value = province + "," + city + "," + district;
//	position.style.color = 'black';
//	
////	alert(province);
//	//从服务端获取推荐景点信息(获取当前省份的热门景点)
//	getScenicByProvince();
//}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////腾讯定位////////////////////////////////////////////////
var geolocation = new qq.maps.Geolocation("OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77", "myapp");
var positionNum = 0;
var options = {timeout: 8000};

function showPosition(position) {
//    alert(position.province);
	province = position.province;
		
	getScenicByProvince();
};

function showErr() {
    alert("定位失败");
};
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

window.onload = function() {

	geolocation.getLocation(showPosition, showErr, options);
	
//	getLocation(); 
	
	//从服务端获取首页活动信息
	getPromotion();	
}


function getScenicByProvince()
{
//	alert(province);
	var url1 = HOST + "/getScenicByLocation.do";
	$.ajax({
		type : "post",
		url : url1,
		async: false,
		data : {
			province : province
		},
		datatype : "JSON",
		error : function() {
			alert("推荐景点Request error!");
		},
		success : function(data) {
			var UlList = document.getElementById("index_scenic_ul_id");
			freshList(data, UlList);
//			alert("推荐景点success!");
		}
	});

}


function getPromotion()
{
	var url = HOST + "/getPromotions.do";
	$.ajax({
		url : url,
		datatype : "JSON",
		async: false,
		type : "GET",
		error : function(data) {
			alert("活动信息request error!");
			console.log(JSON.stringify(data));
		},
		success : function(data) {
			//				alert("活动信息success!");
			$.each(data, function(i, item) {
				var UlList = document.getElementById("index_ul_id");
				var LiList = document.createElement("li");
				UlList.appendChild(LiList);

				var AList = document.createElement("a");

				AList.setAttribute("href", item.promotionLinks);

				LiList.appendChild(AList);

				var ImgList = document.createElement("img");
				ImgList.setAttribute("src", item.promotionImage);
				ImgList.setAttribute("alt", item.promotionTitle);
				AList.appendChild(ImgList);
			});
			$(".slider").yxMobileSlider({
				width : 640,
				height : 320,
				during : 3000
			});//轮播图片初始化
		}
	});
}


//填充景区列表
function freshList(data, UlList) {
	$.each(data, function(i, item) {
		var LiList = document.createElement("li");
		UlList.appendChild(LiList);

		var DivList = document.createElement("div");
		DivList.className = "imglist-box";
		LiList.appendChild(DivList);

		var AList = document.createElement("a");
//		AList.href = "scenicSpot.html?" + "scenicNo=" + item.scenicName;
		AList.href = "scenicSpot.html?" + "scenicNo=" + item.scenicNo;

		DivList.appendChild(AList);

		var ImgList = document.createElement("img");
		ImgList.setAttribute("src", HOST + item.scenicImagePath);
		var Plist = document.createElement("p");
		Plist.className = "imgbar";
		Plist.innerHTML = item.scenicName;
		AList.appendChild(ImgList);
		AList.appendChild(Plist);
	});
	$(".imglist-box").height($(document).width() * 0.25);
}

/*function LoginOrPersonal()
{
	var AllCookies = document.cookie;
//	alert(AllCookies);
	if (AllCookies != "")

	{

		window.location.href = "personalHome.html";

	}

	else

	{
		window.location.href = "TourLogin.html";
	}

}*/




