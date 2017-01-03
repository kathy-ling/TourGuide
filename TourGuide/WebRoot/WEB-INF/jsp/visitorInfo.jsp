<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'VisitorInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	 <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<title>游客信息管理</title>
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


<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong>游客信息管理</strong> 
  </div>
</header>


  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">游客信息管理</strong> / <small>景区信息信息管理</small></div>
      </div>

      <hr>

      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button type="button" class="am-btn am-btn-default" onclick="addVisitorInfo()"><span class="am-icon-plus"></span> 增加游客信息</button>
            </div>
          </div>
        </div>
        
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            
            <input type="text" id="searchText" class="am-form-field" placeholder="手机号">
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default"  id="searchText" type="button" onclick="search()">搜索</button>
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
                <th  style="align-content: center; width: 10%;">姓名</th><th  style="align-content: center; width: 10%;">手机号</th><th style="align-content: center; width: 10%;">昵称</th><th style="align-content: center; width: 10%;">性别</th><th style="align-content: center; width: 10%;">城市</th><th style="align-content: center; width: 10%;">操作</th>
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
							增加游客信息
						</h4>
					</div>
					<div class="modal-body">
					<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr><td>姓名：</td>
						<td><input  type="text" id="add_name" name="add_name" /></td></tr>
						<tr><td>手机号：</td>
						<td><input  type="text" id="add_phone" name="add_phone" /></td></tr>
						<tr><td>昵称:</td>
						<td><input  type="text"  id="add_nickName" name="add_nickName" /></td></tr>
						<tr><td>性别:</td>
						<td>
						<input type="radio" value="男" name="add_sex" checked="checked"><label for="男">男</label>
						<input type="radio" value="女" name="add_sex"><label for="女">女</label>
						</td></tr>	
						<tr><td>城市:</td>
						<td><input  type="text"  id="add_city" name="add_city" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><button class="close" onclick="AddVisitorInfo()" >确定增加</button></td></tr>	
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
							编辑游客信息
						</h4>
					</div>
					<div class="modal-body">
					<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr><td>姓名：</td>
						<td><input  type="text" id="edit_name" name="edit_name" /></td></tr>
						<tr><td>手机号：</td>
						<td><input  type="text" id="edit_phone" name="edit_phone" /></td></tr>
						<tr><td>昵称:</td>
						<td><input  type="text"  id="edit_nickName" name="edit_nickName" /></td></tr>
						<tr><td>性别:</td>
						<td>
						<input type="radio" value="男" name="edit_sex" checked="checked"><label for="男">男</label>
						<input type="radio" value="女" name="edit_sex"><label for="女">女</label>
						</td></tr>	
						<tr><td>城市:</td>
						<td><input  type="text"  id="edit_city" name="edit_city" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><button  onclick="EditVisitorInfo()" >修改</button></td></tr>
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
						<tr><td>姓名：</td>
						<td><input  type="text" id="search_name" name="search_name" readonly="true" /></td></tr>
						<tr><td>手机号：</td>
						<td><input  type="text" id="search_phone" name="search_phone" readonly="true" /></td></tr>
						<tr><td>昵称:</td>
						<td><input  type="text"  id="search_nickName" name="search_nickName" readonly="true" /></td></tr>
						<tr><td>性别:</td>
						<td><input  type="text"  id="search_sex" name="search_sex" readonly="true" /></td></tr>	
						<tr><td>城市:</td>
						<td><input  type="text"  id="search_city" name="search_city" readonly="true" /></td></tr>
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
						<h4 class="modal-title" id="myModalLabel" style="text-align:center;" >
							删除游客信息
						</h4>
					</div>
					<div class="modal-body">
						<table style="border-collapse:separate; border-spacing:10px; margin:auto;">
						<tr><td>姓名：</td>
						<td><input  type="text" id="delete_name" name="delete_name" readonly="true" /></td></tr>
						<tr><td>手机号：</td>
						<td><input  type="text" id="delete_phone" name="delete_phone"  readonly="true"/></td></tr>
						<tr><td>昵称:</td>
						<td><input  type="text"  id="delete_nickName" name="delete_nickName" readonly="true" /></td></tr>
						<tr><td>性别:</td>
						<td><input  type="text"  id="delete_sex" name="delete_sex" readonly="true" /></td></tr>	
						<tr><td>城市:</td>
						<td><input  type="text"  id="delete_city" name="delete_city" readonly="true" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><div >
						<button class="btn btn-danger" onclick="DeleteVisitorInfo()">Delete</button>
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
	var VisitorInfo="";
	var currentPage=1;
	var pageRows=5;
	
	$(document).ready(function()
  	{
  		loadVisitorInfo();
  	});
  	function loadVisitorInfo()
  	{
  		var url="<%=basePath%>visitor/GetVisitorInfo.action";
  		$.ajax(
  		{
  			url:url,
  			type:"GET",
  			datatype: "json",
  			data:{currentPage:1,pageRows:pageRows},
  			success: function(data)
  					{
  					    if(data!=null){
  					    VisitorInfo = data.jsonStr;
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
									type: "GET",
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
  		VisitorInfo=JSON.parse(jsonStr);
  		$.each(VisitorInfo,function(index,value)
  			{
  				var t0="<tr>";
  				var t2="<td style='align-content: center; width: 10%;'>"+value.name+"</td>";
              	var t3="<td style='align-content: center; width: 10%;'>"+value.phone+"</td>";
              	var t4="<td style='align-content: center; width: 10%;'>"+value.nickName+"</td>";
              	var t5="<td style='align-content: center; width: 10%;'>"+value.sex+"</td>";
              	var t6="<td style='align-content: center; width: 10%;'>"+value.city+"</td>";
              	/*var c;
              	if(value.VisitorInfo_bool=="0"){
              		c="未禁用";
              	}else{
              		c="禁用";
              	}
              	var	t6="<td style='align-content: center; width: 10%;'>"+c+"</td>";*/
              	var t7="<td style='align-content: center; width: 10%;'> <div class='am-btn-toolbar'>"+
              	"<div class='am-btn-group am-btn-group-xs'>"+
              	"<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='editVisitor("+index+")'>"+"<span class='am-icon-pencil-square-o'></span>编辑</button>"+
                  "<button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' type='button' onclick='deleteVisitor("+index+")'>"+"<span class='am-icon-trash-o'></span>删除</button>"+
                  "</div></div> </td>";				
                var t8="</tr>";
				$("#tby").append(t0).append(t2).append(t3).append(t4).append(t5).append(t6).append(t7).append(t8);
  			});
  	}
 	function search()
 	{
 		var url = "<%=basePath%>visitor/SearchVisitorInfo.action";
 		var a = $("#searchText").val();
 		$.ajax( {
 			url:url,
 			type:"get",
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
 			$("#search_name").val(value.name);
 			$("#search_phone").val(value.phone);
 			$("#search_nickName").val(value.nickName);
 			$("#search_sex").val(value.sex);
 			$("#search_city").val(value.city);
 		 	});
 		$("#SearchModal").modal('show');
 	}
 	
 	function addVisitorInfo()
 	{
		$("#add_name").val();
		$("#add_phone").val();
		$("#add_nickName").val();
		$("#add_sex").val();
		$("#add_city").val();
 		$("#addmodal").modal('show');
 	}
 	function AddVisitorInfo() {
 		var url = "<%=basePath%>visitor/AddVisitorInfo.action";
 		var name = $("#add_name").val();
 		var phone = $("#add_phone").val();
 		var nickName = $("#add_nickName").val();
 		var sex = $("input[name=add_sex]:checked").val();
 		var city = $("#add_city").val();
 		
 		if (name != "" && city != "" && nickName != "" && phone != "") {
 			$.ajax( {
 				url:url,
 				type:"get",
 				datatype:"json",
 				data:{name:name,city:city,nickName:nickName,phone:phone,sex:sex},
 				success:function(data) {
 					if (data.confirm) {
 						$("#addmodal").modal('hide');
 						alert("添加成功！");
 						loadVisitorInfo();
 					}
 					else
 						{alert("手机号已存在，请重新添加！");}
 						loadVisitorInfo();
 				}
 			});
 		}else{
 		alert("信息不完整，请重新填写游客信息！");
 		}
 	}
 
 	function editVisitor(index)
 	{
 		$("#edit_name").val(VisitorInfo[index].name);
 		$("#edit_phone").val(VisitorInfo[index].phone);
 		$("#edit_nickName").val(VisitorInfo[index].nickName);
 		$("#edit_city").val(VisitorInfo[index].city);

 		if(VisitorInfo[index].sex=="男") $("input[name=edit_sex]:eq(0)").attr("checked",'checked'); 
 		else $("input[name=edit_sex]:eq(1)").attr("checked",'checked'); 
 		$("#editmodal").modal('show');
 		
 	}
 	function EditVisitorInfo()
 	{
 		var url = "<%=basePath%>visitor/UpdateVisitorInfo.action";
 		var name = $("#edit_name").val();
 		var nickName = $("#edit_nickName").val();
 		var city = $("#edit_city").val();
 		var phone = $("#edit_phone").val();
 		var sex = $("input[name=add_sex]:checked").val();
 		if (name != "" && nickName != "" && city != "" && phone != "") {
 			$.ajax( {
 				url:url,
 				type:"get",
 				datatype:"json",
 				data:{name:name,nickName:nickName,city:city,phone:phone,sex:sex},
 				success:function(data) {
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
 		loadVisitorInfo();
 	}
 	function deleteVisitor(index)
 	{	
 		$("#delete_name").val(VisitorInfo[index].name);
 		$("#delete_nickName").val(VisitorInfo[index].nickName);
 		$("#delete_city").val(VisitorInfo[index].city);
		$("#delete_phone").val(VisitorInfo[index].phone);
		$("#delete_sex").val(VisitorInfo[index].sex);
 		$("#deletemodal").modal('show');	
 	}
 	
 	function DeleteVisitorInfo()
 	{
 		var url = "<%=basePath%>visitor/DeleteVisitorInfo.action";
 		var phone=$("#delete_phone").val();
 		$.ajax({
 				url:url,
 				type:"get",
 				datatype:"json",
 				data:{phone:phone},
 				success:function(data) {
 					if (data.confirm) {
 						$("#deletemodal").modal('hide');
 					}
 					alert("删除成功");
 					loadVisitorInfo();
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
