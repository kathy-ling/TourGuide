<%@ page language="java"
	import="java.util.*,java.text.SimpleDateFormat,java.util.Date,java.util.Calendar"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date1 = calendar.getTime();
	String day1 = sdf.format(date1);
	calendar.add(Calendar.DAY_OF_YEAR, 1);
	Date date2 = calendar.getTime();
	String day2 = sdf.format(date2);
	calendar.add(Calendar.DAY_OF_YEAR, 1);
	Date date3 = calendar.getTime();
	String day3 = sdf.format(date3);
	calendar.add(Calendar.DAY_OF_YEAR, 1);
	Date date4 = calendar.getTime();
	String day4 = sdf.format(date4);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'guide.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>讲解员信息管理</title>
<meta name="description" content="这是一个 table 页面">
<meta name="keywords" content="table">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" href="<%=path%>/assets/css/ace.onpage-help.css" />
<link rel="stylesheet"
	href="<%=path%>/docs/assets/js/themes/sunburst.css" />
<link rel="icon" type="image/png" href="<%=path%>/assets1/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="<%=path%>/assets1/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="<%=path%>/assets1/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=path%>/assets1/css/admin.css">
<link rel="stylesheet" href="<%=path%>/assets/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath%>assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=basePath%>assets/css/admin.css">
<script type="text/javascript" src="<%=path%>/assets/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/assets/js/bootstrap-paginator.min.js"></script>

</head>

<body>
	<div class="admin-content">
		<div class="admin-content-body">
			<div class="am-cf am-padding am-padding-bottom-0"></div>
			<hr>
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-xs"></div>
					</div>
				</div>
			</div>

			<div class="am-tabs am-margin" data-am-tabs>
				<ul class="am-tabs-nav am-nav am-nav-tabs">
					<li class="am-active"><a href="#tab1">景区拼团价格人数设置</a></li>
					<li><a href="#tab2">景区信息设置</a></li>
					<li><a href="#tab3">景区门票信息</a></li>
					<li><a href="#tab8">景区活动信息</a></li>
				</ul>
				<div class="am-tabs-bd">
					<div class="am-tab-panel am-fade am-in am-active" id="tab1">
						<form class="am-form">
							<div class="am-tabs am-margin" data-am-tabs>
								<ul class="am-tabs-nav am-nav am-nav-tabs">
									<li class="am-active"><a href="#tab4"><%=day1%></a></li>
									<li><a href="#tab5"><%=day2%></a></li>
									<li><a href="#tab6"><%=day3%></a></li>
									<li><a href="#tab7"><%=day4%></a></li>
								</ul>
								<div class="am-tabs-bd">
									<div class="am-tab-panel am-fade am-in am-active" id="tab4">
										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团价格</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day1_fee" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>

										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团人数</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day1_num" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>
										<div class="am-g am-margin-top" align="center">
											<button type="button"
												class="am-btn am-btn-success am-radius "
												onclick="updatePass()">保存</button>
										</div>
									</div>

									<div class="am-tab-panel am-fade" id="tab5">
										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团价格</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day2_fee" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>

										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团人数</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day2_num" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>
										<div class="am-g am-margin-top" align="center">
											<button type="button"
												class="am-btn am-btn-success am-radius "
												onclick="updatePass()">保存</button>
										</div>
									</div>

									<div class="am-tab-panel am-fade" id="tab6">
										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团价格</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day3_fee" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>

										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团人数</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day3_num" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>
										<div class="am-g am-margin-top" align="center">
											<button type="button"
												class="am-btn am-btn-success am-radius "
												onclick="updatePass()">保存</button>
										</div>
									</div>

									<div class="am-tab-panel am-fade" id="tab7">
										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团价格</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day4_fee" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>

										<div class="am-g am-margin-top">
											<div class="am-u-sm-4 am-u-md-2 am-text-right">拼团人数</div>
											<div class="am-u-sm-8 am-u-md-4">
												<input id="day4_num" style="text-align: center;" type="text"
													value="" class="am-input-sm">
											</div>
											<div class="am-hide-sm-only am-u-md-6"></div>
										</div>
										<div class="am-g am-margin-top" align="center">
											<button type="button"
												class="am-btn am-btn-success am-radius "
												onclick="updatePass()">保存</button>
										</div>
									</div>



								</div>
							</div>
						</form>
					</div>
					<div class="am-tab-panel am-fade" id="tab2">
						<div class="am-g">
							<div class="am-u-sm-12">
								<form class="am-form">
									<table
										style="border-collapse:separate; border-spacing:10px; margin:auto;">
										<tr>
											<td colspan="1">景区图片：</td>
											<td colspan="3" align="center"><img
												style="width: 150px;height: 150px" id="search_headimg"
												name="search_headimg" src="" /></td>
										</tr>
										<tr>
											<td>编号：</td>
											<td><input type="text" id="search_scenicNo"
												name="search_scenicNo" readonly="true" /></td>
											<td>名称：</td>
											<td><input type="text" id="search_scenicName"
												name="search_scenicName" readonly="true" /></td>
										</tr>
										<tr>
											<td>景区介绍：</td>
											<td><textarea rows="3" cols="22" id="search_scenicIntro"
													name="search_scenicIntro" readonly="true"></textarea></td>
											<td>历史参观人数:</td>
											<td><input type="text" id="search_totalVisits"
												name="search_totalVisits" readonly="true" /></td>
										</tr>
										<tr>
											<td>开放时间:</td>
											<td><input type="text" id="search_openingHours"
												name="search_openingHours" readonly="true" /></td>
											<td>详细地址 ：</td>
											<td><input type="text" id="search_province"
												name="search_province" readonly="true" /></td>
										</tr>
										<tr>
											<td>是否为热门景点:</td>
											<td><input type="text" id="search_isHotSpot"
												name="search_isHotSpot" readonly="true" /></td>
											<td>景区等级:</td>
											<td><input type="text" id="search_scenicLevel"
												name="search_scenicLevel" readonly="true" /></td>
										</tr>
										<tr>
											<td>负责人:</td>
											<td><input type="text" id="search_chargePerson"
												name="search_chargePerson" readonly="true" /></td>
											<td>账号:</td>
											<td><input type="text" id="search_account"
												name="search_account" readonly="true" /></td>
										</tr>
									</table>
								</form>
							</div>
							<div class="am-g am-margin-top" align="center">
								<button type="button" class="am-btn am-btn-success am-radius "
									onclick="updatePass()">编辑</button>
							</div>
						</div>
					</div>
					<div class="am-tab-panel am-fade" id="tab3">
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">半价门票</div>
							<div class="am-u-sm-8 am-u-md-4">
								<input id="fee_half" style="text-align: center;" type="text"
									value="" class="am-input-sm">
							</div>
							<div class="am-hide-sm-only am-u-md-6"></div>
						</div>

						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">折扣价门票</div>
							<div class="am-u-sm-8 am-u-md-4">
								<input id="fee_discount" style="text-align: center;" type="text"
									value="" class="am-input-sm">
							</div>
							<div class="am-hide-sm-only am-u-md-6"></div>
						</div>
						<div class="am-g am-margin-top">
							<div class="am-u-sm-4 am-u-md-2 am-text-right">全价门票</div>
							<div class="am-u-sm-8 am-u-md-4">
								<input id="fee_all" style="text-align: center;" type="text"
									value="" class="am-input-sm">
							</div>
							<div class="am-hide-sm-only am-u-md-6"></div>
						</div>
						<div class="am-g am-margin-top" align="center">
							<button type="button" class="am-btn am-btn-success am-radius "
								onclick="updatePass()">保存</button>
						</div>
					</div>

					<div class="am-tab-panel am-fade" id="tab8">
					<div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            
            
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default"  id="searchText" type="button" onclick="search()">+发布景区活动</button>
          </span>
          </div>
        </div>
						<form class="am-form">
							<table
								class="am-table am-table-striped am-table-hover table-main"
								style="border-collapse:separate; border-spacing:5px; ">
								<thead>
									<tr>
										<th style="text-align: center; width: 10%;">景区编号</th>
										<th style="text-align: center; width: 10%;">景区名称</th>
										<th style="text-align: center; width: 10%;">活动标题</th>
										<th style="text-align: center; width: 10%;">活动发布时间</th>
										<th style="text-align: center; width: 10%;">活动开始时间</th>
										<th style="text-align: center; width: 10%;">活动结束时间</th>
										<th style="text-align: center; width: 10%;">操作</th>
									</tr>
								</thead>
								<tbody id="tby">

								</tbody>
							</table>
							<div style=" margin-bottom:10%; margin-left:40%;">
								<ul id="paginator"></ul>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!--[if (gte IE 9)|!(IE)]><!-->
	<!--<![endif]-->
	<script src="<%=path%>/assets1/js/amazeui.min.js"></script>
	<script src="<%=path%>/assets1/js/app.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			loadScenicConSist();
			loadScenicinfo();
			loadScenicTicket();
		});
		
		
		function loadScenicTicket()
		{
			var url = "<%=basePath%>scenicTicket/getScenicTicketByscenicID.action";
 			$.ajax( {
 			url:url,
 			type:"post",
 			datatype:"json",
 			data:{scenicID:"a"},
 			success:function(data) {
 					
		 			var b=JSON.parse(data);
					$("#fee_half").val(b[0].halfPrice);
					$("#fee_discount").val(b[0].discoutPrice);
		 			$("#fee_all").val(b[0].fullPrice);
 			}
 		});
		
		}
		
		function loadScenicinfo()
		{
			var url = "<%=basePath%>scenic/SearchScenicInfo.action";
 		$.ajax( {
 			url:url,
 			type:"POST",
 			datatype:"json",
 			data:{sql:"a"},
 			success:function(data) {
 					var d=data.jsonStr;
					SearchSuccess(d);
					
		 		
 			}
 		});
		}
		function loadScenicConSist()
		{
			var url = "<%=basePath%>scenicTeam/getscenicTeamByscenicNo.action";
 			$.ajax( {
 			url:url,
 			type:"post",
 			datatype:"json",
 			data:{scenicNo:"a"},
 			success:function(data) {
 			alert(data);
		 			var b=JSON.parse(data);
		 			$("#day1_fee").val(b[0].day1_fee);
		 			$("#day1_num").val(b[0].day1_maxNum);
		 			$("#day2_fee").val(b[0].day2_fee);
		 			$("#day2_num").val(b[0].day2_maxNum);
		 			$("#day3_fee").val(b[0].day3_fee);
		 			$("#day3_num").val(b[0].day3_maxNum);
		 			$("#day4_fee").val(b[0].day4_fee);
		 			$("#day4_num").val(b[0].day4_maxNum);
					
 			}
 		});
		}
		
		function SearchSuccess(jsonStr) {
 			var value=JSON.parse(jsonStr);
 			$("#search_scenicNo").val(value.scenicNo);
 			$("#search_scenicName").val(value.scenicName);
 			$("#search_totalVisits").val(value.totalVisits);
 			$("#search_openingHours").val(value.openingHours);
 			$("#search_scenicLevel").val(value.scenicLevel);
 			$("#search_scenicIntro").val(value.scenicIntro);
 			var province=value.province+value.city+value.scenicLocation;
 			$("#search_province").val(province);
 			if (value.isHotSpot == 1) $("#search_isHotSpot").val("热门");
 			else $("#search_isHotSpot").val("非热门");
 			var f="<%=path%>"+value.scenicImagePath;
  			document.getElementById("search_headimg").src=f;
 			$("#search_chargePerson").val(value.chargePerson);
 			$("#search_account").val(value.account);
 		
 		
 	}
	</script>
	<script src="<%=path%>/assets/js/bootstrap.js"></script>

</body>
</html>
