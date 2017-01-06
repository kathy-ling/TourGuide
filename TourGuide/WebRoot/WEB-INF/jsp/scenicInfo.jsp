<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ScenicInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	 <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<title>景区信息信息管理</title>
 	 <meta name="description" content="这是一个 table 页面">
  	<meta name="keywords" content="table">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<meta name="renderer" content="webkit">
 	 <meta http-equiv="Cache-Control" content="no-siteapp" />
 	 <link rel="stylesheet" href="<%=basePath %>/assets/css/ace.onpage-help.css" />
	<link rel="stylesheet" href="<%=basePath %>/docs/assets/js/themes/sunburst.css" />
  	<link rel="icon" type="image/png" href="<%=basePath %>/assets1/i/favicon.png">
  	<link rel="apple-touch-icon-precomposed" href="<%=basePath %>/assets1/i/app-icon72x72@2x.png">
  	<link rel="stylesheet" href="<%=basePath %>/assets1/css/amazeui.min.css"/>
  	<link rel="stylesheet" href="<%=basePath %>/assets1/css/admin.css">
  	<link rel="stylesheet" href="<%=path%>/assets/css/bootstrap.css" />
  	<link rel="stylesheet" href="<%=path%>/assets/css/ace.onpage-help.css" />
	<link rel="stylesheet" href="<%=path%>/docs/assets/js/themes/sunburst.css" />
	<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/js/bootstrap-paginator.min.js"></script>

  </head>
  
 <body>


<!-- <header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong>景区信息管理</strong> 
  </div>
</header> -->


  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">景区信息管理</strong> / <small>景区信息信息管理</small></div>
      </div>

      <hr>

      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button type="button" class="am-btn am-btn-default" onclick="addScenicInfo()"><span class="am-icon-plus"></span> 发布景区信息</button>
            </div>
          </div>
        </div>
        
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            <input type="text" id="searchText" class="am-form-field" placeholder="景区位置">
          	<span class="am-input-group-btn">
            <button class="am-btn am-btn-default"  id="searchText" type="button" onclick="serachOflocation()">搜索</button>
          </span>
          </div>
          
        </div>
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            <input type="text" id="searchText" class="am-form-field" placeholder="景区名称">
          	<span class="am-input-group-btn">
            <button class="am-btn am-btn-default"  id="searchText" type="button" onclick="serach()">搜索</button>
          </span>
          </div>
          
        </div>
      </div>

      <div class="am-g">
        <div class="am-u-sm-12">
          <form class="am-form">
            <table  class="am-table am-table-striped am-table-hover table-main" style="border-collapse:separate; border-spacing:5px; " >
              <thead>
              <tr>
                <th  style="text-align: center; width: 10%;">编号</th>
                <th  style="text-align: center; width: 10%;">名称</th>
                <th style="text-align: center; width: 10%;">历史参观人数</th>
                <th style="text-align: center; width: 10%;">开放时间</th>
                <th style="text-align: center; width: 10%;">景区等级</th>
                <th style="text-align: center; width: 10%;">操作</th>
              </tr>
              </thead>
              <tbody id="tby" >
              	
              </tbody>
            </table>
           <div style=" margin-bottom:10%; margin-left:40%;">
				<ul id="paginator" ></ul>
		   </div>
		  
          </form>
        </div>

      </div>
    </div>

    
  </div>
<div class="modal fade" id="addmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" >
				<div class="modal-content">
					<div class="model-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        <span class="blue">X</span>
	                    </button>
						<h4 class="modal-title" id="myModalLabel" style="text-align:center;">
							增加景区信息
						</h4>
					</div>
					<div class="modal-body">
					<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr ><td >编号：</td>
						<td><input  type="text" id="add_scenicNo" name="add_scenicNo" /></td></tr>
						<tr><td>名称：</td>
						<td><input  type="text" id="add_scenicName" name="add_scenicName" /></td></tr>
						<tr><td>景区介绍：</td>
						<td><textarea rows="3" cols="22" id="add_scenicIntro" name="add_scenicIntro"></textarea></td></tr>												
						<tr><td>历史参观人数:</td>
						<td><input  type="text"  id="add_totalVisits" name="add_totalVisits" /></td></tr>
						<tr><td>开放时间:</td>
						<td><input  type="text"  id="add_openingHours" name="add_openingHours" /></td></tr>
						<tr><td>所在省份：</td>
						<td><input  type="text" id="add_province" name="add_province" /></td></tr>
						<tr><td>所在市:</td>
						<td><input  type="text"  id="add_city" name="add_city"" /></td></tr>
						<tr><td>详细位置:</td>
						<td><input  type="text"  id="add_scenicLocation" name="add_scenicLocation"" /></td></tr>
						<tr><td>是否为热门景点:</td>
						<td>
						<input type="radio" value="HotSpot" name="add_isHotSpot" checked="checked"><label for="热门">热门</label>
						<input type="radio" value="notHotSpot" name="add_isHotSpot"><label for="非热门">非热门</label>
						</td></tr>
						<tr><td>景区等级:</td>
						<td><input  type="text"  id="add_scenicLevel" name="add_scenicLevel"" /></td></tr>
						<tr><td>负责人:</td>
						<td><input  type="text"  id="add_chargePerson" name="add_chargePerson"" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><button class="close" onclick="AddScenicInfo()" >确定增加</button></td></tr>
						
					</table>
					
									
					</div>
				</div>
			</div>
</div>
<div class="modal fade" id="editmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" >
				<div class="modal-content">
					<div class="model-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        <span class="blue">X</span>
	                    </button>
						<h4 class="modal-title" id="myModalLabel" style="text-align:center;">
							编辑景区信息
						</h4>
					</div>
					<div class="modal-body">
					<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr ><td >编号：</td>
						<td><input  type="text" id="edit_scenicNo" name="edit_scenicNo" /></td></tr>
						<tr><td>名称：</td>
						<td><input  type="text" id="edit_scenicName" name="edit_scenicName" /></td></tr>
						<tr><td>景区介绍：</td>
						<td><textarea rows="3" cols="22" id="edit_scenicIntro" name="edit_scenicIntro"></textarea></td></tr>												
						<tr><td>历史参观人数:</td>
						<td><input  type="text"  id="edit_totalVisits" name="edit_totalVisits" /></td></tr>
						<tr><td>开放时间:</td>
						<td><input  type="text"  id="edit_openingHours" name="edit_openingHours" /></td></tr>
						<tr><td>所在省份：</td>
						<td><input  type="text" id="edit_province" name="edit_province" /></td></tr>
						<tr><td>所在市:</td>
						<td><input  type="text"  id="edit_city" name="edit_city"" /></td></tr>
						<tr><td>详细位置:</td>
						<td><input  type="text"  id="edit_scenicLocation" name="edit_scenicLocation"" /></td></tr>
						<tr><td>是否为热门景点:</td>
						<td>
						<input type="radio" value="HotSpot" name="edit_isHotSpot" checked="checked"><label for="热门">热门</label>
						<input type="radio" value="notHotSpot" name="edit_isHotSpot"><label for="非热门">非热门</label>
						</td></tr>						
						<tr><td>景区等级:</td>
						<td><input  type="text"  id="edit_scenicLevel" name="edit_scenicLevel"" /></td></tr>
						<tr><td>负责人:</td>
						<td><input  type="text"  id="edit_chargePerson" name="edit_chargePerson"" /></td></tr>
				<tr><td colspan="2" style="text-align:center;"><button  onclick="editScenicInfo()" >修改</button></td></tr>
						
					</table>
					
									
					</div>
				</div>
			</div>
</div>
  
<div class="modal fade" id="SearchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
			<div class="modal-dialog" >
				<div class="modal-content">
					<div class="model-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        <span class="blue">X</span>
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel" style="text-align:center;">
							搜索结果
						</h4>
					</div>
					<div class="modal-body">
					<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr ><td >编号：</td>
						<td><input  type="text" id="search_scenicNo" name="search_scenicNo" readonly="true" /></td></tr>
						<tr><td>名称：</td>
						<td><input  type="text" id="search_scenicName" name="search_scenicName" readonly="true" /></td></tr>
						<tr><td>景区介绍：</td>
						<td><textarea rows="3" cols="22" id="search_scenicIntro" name="search_scenicIntro" readonly="true"></textarea></td></tr>												
						<tr><td>历史参观人数:</td>
						<td><input  type="text"  id="search_totalVisits" name="search_totalVisits"  readonly="true"/></td></tr>
						<tr><td>开放时间:</td>
						<td><input  type="text"  id="search_openingHours" name="search_openingHours" readonly="true" /></td></tr>
						<tr><td>所在省份：</td>
						<td><input  type="text" id="search_province" name="search_province" readonly="true"/></td></tr>
						<tr><td>所在市:</td>
						<td><input  type="text"  id="search_city" name="search_city"" readonly="true" /></td></tr>
						<tr><td>详细位置:</td>
						<td><input  type="text"  id="search_scenicLocation" name="search_scenicLocation" readonly="true" /></td></tr>
						<tr><td>是否为热门景点:</td>
						<td><input  type="text"  id="search_isHotSpot" name="search_isHotSpot" readonly="true" /></td></tr>
						<tr><td>景区等级:</td>
						<td><input  type="text"  id="search_scenicLevel" name="search_scenicLevel" readonly="true" /></td></tr>
						<tr><td>负责人:</td>
						<td><input  type="text"  id="search_chargePerson" name="search_chargePerson"" readonly="true" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><button class="close" data-dismiss="modal" aria-hidden="true" >确定</button></td></tr>
					</table>
									
					</div>
				</div>
			</div>
</div>
<div class="modal fade" id="deletemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" >
				<div class="modal-content">
					<div class="model-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        <span class="blue">X</span>
	                    </button>
						<h4 class="modal-title" id="myModalLabel" style="text-align:center;">
							删除景区信息
						</h4>
					</div>
					<div class="modal-body">
						<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr ><td >编号：</td>
						<td><input  type="text" id="delete_scenicNo" name="delete_scenicNo" readonly="true" /></td>
						</tr>
						<tr><td>名称：</td>
						<td><input  type="text" id="delete_scenicName" name="delete_scenicName" readonly="true"/></td>
						</tr>
						<tr><td>历史参观人数:</td>
						<td><input  type="text"  id="delete_totalVisits" name="delete_totalVisits" readonly="true" /></td></tr>
						<tr><td>开放时间:</td>
						<td><input  type="text"  id="delete_openingHours" name="delete_openingHours" readonly="true" /></td></tr>
						<tr><td>景区等级:</td>
						<td><input  type="text"  id="delete_scenicLevel" name="delete_scenicLevel" readonly="true" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><div >
							<button class="btn btn-danger" onclick="DeleteScenicInfo()">Delete</button>
											
						</div></td></tr>
						
					</table>
						
									
					</div>
				</div>
			</div>
		</div>



<footer>
  <hr>
  <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
</footer>


<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<script src="<%=basePath %>/assets1/js/amazeui.min.js"></script>
<script src="<%=basePath %>/assets1/js/app.js"></script>
<script type="text/javascript">
	var id=1;
	var ScenicInfo="";
	var currentPage=1;
	var pageRows=5;
	
	$(document).ready(function()
  	{
  		loadScenicInfo();
  	});
  	function loadScenicInfo()
  	{
  		var url="<%=basePath%>scenic/GetScenicInfo.action";
  		$.ajax(
  		{
  			url:url,
  			type:"POST",
  			datatype: "json",
  			data:{currentPage:1,pageRows:pageRows},
  			success: function(data)
  					{
  					    if(data!=null){
  					    ScenicInfo = data.jsonStr;
  					    initTable(data.jsonStr,data.page);	
  					       // 获取currentPage 请求页面
						var currentPage = data.page;
						// 获取totalPages 总页面
						var totalPages = data.total;
						// 获取numberofPages 显示的页面
						var numberofPages = totalPages > 10 ? 10 : totalPages;
						
						var options = {
							bootstrapMajorVersion: 3,
		                    currentPage: currentPage,       // 当前页
		                    totalPages: totalPages,      	// 总页数
		                    numberofPages: numberofPages,   // 显示的页数
		                    itemTexts: function (type, page, current) {
		                        switch (type) {
		                            case "first":
		                                return "首页";
		                                break;
		                            case "prev":
		                                return "上一页";
		                                break;
		                            case "next":
		                                return "下一页";
		                                break;
		                            case "last":
		                                return "末页";
		                                break;
		                            case "page":
		                                return page;
		                                break;
		                        }
		                    },
		                    onPageClicked: function (event, originalEvent, type, page) {
		                        $.ajax({
									url: url,
									type: "PSOT",
									datatype: "json",
									data:{currentPage:page,pageRows:5},
									success: function(data) {
  					   					initTable(data.jsonStr,page);	
						            }
						        });
						     }  					
						};					
						$("#paginator").bootstrapPaginator(options);
  					    }
  					},
  				
  					
  		});
  		
  	}
  	
  	function initTable(jsonStr,currentPage)
  	{
  		$("#tby").html("");
  		ScenicInfo=JSON.parse(jsonStr);
  		$.each(ScenicInfo,function(index,value)
  			{
  				var t0="<tr>";
  				var t2="<td style=' text-align: center;width: 10%;'>"+value.scenicNo+"</td>";
              	var t3="<td style=' text-align: center;width: 10%;'>"+value.scenicName+"</td>";
              	var t4="<td style=' text-align: center;width: 10%;'>"+value.totalVisits+"</td>";
              	var t5="<td style=' text-align: center;width: 10%;'>"+value.openingHours+"</td>";
              	var t6="<td style=' text-align: center;width: 10%;'>"+value.scenicLevel+"</td>";
              
       
              	var t7="<td align='center'  width: 10%;'> <div class='am-btn-toolbar'>"+
              	"<div style='float: none' class='am-btn-group am-btn-group-xs'>"+
              	"<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='EditScenic("+index+")'>"+"<span class='am-icon-pencil-square-o'></span>编辑</button>"+
                  "<button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' type='button' onclick='DeleteScenic("+index+")'>"+"<span class='am-icon-trash-o'></span>删除</button>"+
                  "</div></div> </td>";				
                var t8="</tr>";
				$("#tby").append(t0).append(t2).append(t3).append(t4).append(t5).append(t6).append(t7).append(t8);
  			});
  	}
 	function serach()
 	{
 		var url = "<%=basePath%>scenic/SearchScenicInfo.action";
 		var a = $("#searchText").val();
 		$.ajax( {
 			url:url,
 			type:"PSOT",
 			datatype:"json",
 			data:{sql:a},
 			success:function(data) {
 				if (data.jsonStr == "[]") {
 					alert("没有搜索到任何信息，请重新搜索!");
	 			}
		 		else {
					SearchSuccess(data.jsonStr);
		 		};
 			}
 		});
 	}
 	
 	function SearchSuccess(jsonStr) {
 			$.each(JSON.parse(jsonStr),function(index,value){
 			$("#search_scenicNo").val(value.scenicNo);
 			$("#search_scenicName").val(value.scenicName);
 			$("#search_totalVisits").val(value.totalVisits);
 			$("#search_openingHours").val(value.openingHours);
 			$("#search_scenicLevel").val(value.scenicLevel);
 			$("#search_scenicIntro").val(value.scenicIntro);
 			$("#search_province").val(value.province);
 			$("#search_city").val(value.city);
 			$("#search_scenicLocation").val(value.scenicLocation);
 			if (value.isHotSpot == 1) $("#search_isHotSpot").val("热门");
 			else $("#search_isHotSpot").val("非热门");
 			$("#search_chargePerson").val(value.chargePerson);
 		});
 		$("#SearchModal").modal('show');
 	}
 	
 	function addScenicInfo()
 	{
 		$("#add_scenicNo").val("");
 		$("#add_scenicName").val("");
 		$("#add_totalVisits").val("");
 		$("#add_openingHours").val("");
 		$("#add_scenicLevel").val("");
 		$("#add_scenicIntro").val("");
 		$("#add_province").val("");
 		$("#add_city").val("");
 		$("#add_scenicLocation").val("");
 		//$("#add_isHotSpot").val("");
 		$("#add_chargePerson").val("");
 		$("#addmodal").modal('show');
 	}
 	
 	function AddScenicInfo() {
 		var url = "<%=basePath%>scenic/AddScenicInfo.action";
  		var scenicNo = $("#add_scenicNo").val();
 		var scenicName = $("#add_scenicName").val();
 		var totalVisits = $("#add_totalVisits").val();
 		var openingHours = $("#add_openingHours").val();
 		var scenicLevel = $("#add_scenicLevel").val();
 		var scenicIntro = $("#add_scenicIntro").val();
 		var province = $("#add_province").val();
 		var city = $("#add_city").val();
 		var scenicLocation = $("#add_scenicLocation").val();
 		var isHotSpot = $("input[name=add_isHotSpot]:checked").val();
 		if (isHotSpot == "HotSpot") isHotSpot = 1;
 		else isHotSpot = 0;
 		var chargePerson = $("#add_chargePerson").val();
 		
 		if (scenicNo != "" && scenicName != "" && totalVisits != "" && openingHours != "" && scenicLevel != ""
 			 && scenicIntro != "" && province != "" && city != "" && scenicLocation != "" && chargePerson != "") {
 			$.ajax( {
 				url:url,
 				type:"PSOT",
 				datatype:"json",
 				data:{scenicNo:scenicNo,scenicName:scenicName,totalVisits:totalVisits,openingHours:openingHours,
 					scenicLevel:scenicLevel,scenicIntro:scenicIntro,province:province,city:city,scenicLocation:
 					scenicLocation,isHotSpot:isHotSpot,chargePerson:chargePerson},
 				success:function(data) {
 					if (data.confirm) {
 						$("#addmodal").modal('hide');
 						alert("添加成功！");
 						loadScenicInfo();
 					}
 					else {
 						var str = "\"" + scenicName + "\"已存在，请重新添加！";
 						alert(str);
 					}
 						loadScenicInfo();
 				}
 			});
 		}else{
 		alert("信息不能为空，请重新填写景区信息！");
 		}
 	}
 
 	function EditScenic(index)
 	{
 		$("#edit_scenicNo").val(ScenicInfo[index].scenicNo);
 		$("#edit_scenicName").val(ScenicInfo[index].scenicName);
 		$("#edit_totalVisits").val(ScenicInfo[index].totalVisits);
 		$("#edit_openingHours").val(ScenicInfo[index].openingHours);
 		$("#edit_scenicLevel").val(ScenicInfo[index].scenicLevel);
 		$("#edit_scenicLocation").val(ScenicInfo[index].scenicLocation);
 		$("#edit_province").val(ScenicInfo[index].province);
 		$("#edit_city").val(ScenicInfo[index].city);
 		$("#edit_scenicIntro").val(ScenicInfo[index].scenicIntro);
 		$("#edit_chargePerson").val(ScenicInfo[index].chargePerson);
 		if(ScenicInfo[index].isHotSpot==1) $("input[name=edit_isHotSpot]:eq(0)").attr("checked",'checked'); 
 		else  $("input[name=edit_isHotSpot]:eq(1)").attr("checked",'checked'); 
 		/*if((ScenicInfo[index].)=="0")
 		{
 			$("#no").attr("checked","checked");
 		}
 		else
 		{
 			$("#yes").attr("checked","checked");
 		}*/
 		
 		$("#editmodal").modal('show');
 		
 	}
 	function editScenicInfo()
 	{
 		var url = "<%=basePath%>scenic/UpdateScenicInfo.action";
 		var scenicNo = $("#edit_scenicNo").val();
 		var scenicName = $("#edit_scenicName").val();
 		var totalVisits = $("#edit_totalVisits").val();
 		var openingHours = $("#edit_openingHours").val();
 		var scenicLevel = $("#edit_scenicLevel").val();
 		var scenicIntro = $("#edit_scenicIntro").val();
 		var province = $("#edit_province").val();
 		var city = $("#edit_city").val();
 		var scenicLocation = $("#edit_scenicLocation").val();
 		var isHotSpot = $("input[name=edit_isHotSpot]:checked").val();
 		if (isHotSpot == "HotSpot") isHotSpot = 1;
 		else isHotSpot = 0;
 		var chargePerson = $("#edit_chargePerson").val();
 		if (scenicNo != "" && scenicName != "" && totalVisits != "" && openingHours != "" && scenicLevel != ""
 			 && scenicIntro != "" && province != "" && city != "" && scenicLocation != "" && chargePerson != "") { 			
 			 $.ajax( {
 				url:url,
 				type:"PSOT",
 				datatype:"json",
 				data:{scenicNo:scenicNo,scenicName:scenicName,totalVisits:totalVisits,openingHours:openingHours,
 					scenicLevel:scenicLevel,scenicIntro:scenicIntro,province:province,city:city,scenicLocation:
 					scenicLocation,isHotSpot:isHotSpot,chargePerson:chargePerson}, 				success:function(data) {
 					if (data.confirm) {
 						$("#editmodal").modal('hide');
 						alert("修改成功！");
 					}
 					else{alert("修改失败，请重新确认修改");
 						$("#editmodal").modal('hide');
 					}
 				}
 			});
 		}
 		loadScenicInfo();
 	}
 	function DeleteScenic(index)
 	{	
 		$("#delete_scenicNo").val(ScenicInfo[index].scenicNo);
 		$("#delete_scenicName").val(ScenicInfo[index].scenicName);
 		$("#delete_totalVisits").val(ScenicInfo[index].totalVisits);
 		$("#delete_openingHours").val(ScenicInfo[index].openingHours);
 		$("#delete_scenicLevel").val(ScenicInfo[index].scenicLevel);
 		$("#deletemodal").modal('show');	
 	}
 	
 	function DeleteScenicInfo()
 	{
 		var url = "<%=basePath%>scenic/DeleteScenicInfo.action";
 		var scenicName=$("#delete_scenicName").val();
 		$.ajax({
 				url:url,
 				type:"PSOT",
 				datatype:"json",
 				data:{scenicName:scenicName},
 				success:function(data) {
 					if (data.confirm) {
 						$("#deletemodal").modal('hide');
 					}
 					alert("删除成功");
 					loadScenicInfo();
 				}
 			});
 		
 	}
 	
</script>

	<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=path%>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	</script>
	<script src="<%=path%>/assets/js/bootstrap.js"></script>
	<!-- page specific plugin scripts -->
	<script src="<%=path%>/assets/js/dataTables/jquery.dataTables.js"></script>
	<script
		src="<%=path%>/assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
	<script src="<%=path%>/assets/js/dataTables/extensions/buttons/dataTables.buttons.js"></script>
	<script src="<%=path%>/assets/js/dataTables/extensions/buttons/buttons.flash.js"></script>
	<script src="<%=path%>/assets/js/dataTables/extensions/buttons/buttons.html5.js"></script>
	<script src="<%=path%>/assets/js/dataTables/extensions/buttons/buttons.print.js"></script>
	<script src="<%=path%>/assets/js/dataTables/extensions/buttons/buttons.colVis.js"></script>
	<script src="<%=path%>/assets/js/dataTables/extensions/select/dataTables.select.js"></script>
	<!-- ace scripts -->
	<script src="<%=path%>/assets/js/ace/elements.scroller.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.colorpicker.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.fileinput.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.typeahead.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.wysiwyg.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.spinner.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.treeview.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.wizard.js"></script>
	<script src="<%=path%>/assets/js/ace/elements.aside.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.ajax-content.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.touch-drag.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.sidebar.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.submenu-hover.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.widget-box.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.settings.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.settings-rtl.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.settings-skin.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.widget-on-reload.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.searchbox-autocomplete.js"></script>

	

	<!-- the following scripts are used in demo only for onpage help and you don't need them -->
	<script type="text/javascript">
		ace.vars['base'] = '..';
	</script>
	<script src="<%=path%>/assets/js/ace/elements.onpage-help.js"></script>
	<script src="<%=path%>/assets/js/ace/ace.onpage-help.js"></script>
	<script src="<%=path%>/docs/assets/js/rainbow.js"></script>
	<script src="<%=path%>/docs/assets/js/language/generic.js"></script>
	<script src="<%=path%>/docs/assets/js/language/html.js"></script>
	<script src="<%=path%>/docs/assets/js/language/css.js"></script>
	<script src="<%=path%>/docs/assets/js/language/javascript.js"></script>
</body>
</html>
