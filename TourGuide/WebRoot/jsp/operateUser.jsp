<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'operateUser.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	 <meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<title>运营人员信息管理</title>
 	 <meta name="description" content="这是一个 table 页面">
  	<meta name="keywords" content="table">
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<meta name="renderer" content="webkit">
 	 <meta http-equiv="Cache-Control" content="no-siteapp" />
 	 <link rel="stylesheet" href="<%=path%>/assets/css/ace.onpage-help.css" />
	<link rel="stylesheet" href="<%=path%>/docs/assets/js/themes/sunburst.css" />
  	<link rel="icon" type="image/png" href="<%=path %>/assets1/i/favicon.png">
  	<link rel="apple-touch-icon-precomposed" href="<%=path %>/assets1/i/app-icon72x72@2x.png">
  	<link rel="stylesheet" href="<%=path %>/assets1/css/amazeui.min.css"/>
  	<link rel="stylesheet" href="<%=path %>/assets1/css/admin.css">
  	<link href="css/bootstrap.css" rel="stylesheet">
  	<link rel="stylesheet" href="<%=path%>/assets/css/bootstrap.css" />
  	<link rel="stylesheet" href="<%=path%>/assets/css/ace.onpage-help.css" />
	<link rel="stylesheet" href="<%=path%>/docs/assets/js/themes/sunburst.css" />
	<script type="text/javascript" src="<%=path %>/assets/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/js/bootstrap-paginator.min.js"></script>

  </head>
  
 <body>


<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong>运营人员信息管理</strong> 
  </div>
</header>


  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">运营人员管理</strong> / <small>运营人员信息管理</small></div>
      </div>

      <hr>

      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              <button type="button" class="am-btn am-btn-default" onclick="addOperateUser()"><span class="am-icon-plus"></span> 新增运营人员</button>
            </div>
          </div>
        </div>
        
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            
            <input type="text" class="am-form-field" placeholder="账号">
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="button" onclick="serach()">搜索</button>
          </span>
          </div>
        </div>
      </div>

      <div class="am-g">
        <div class="am-u-sm-12">
          <form class="am-form">
            <table class="am-table am-table-striped am-table-hover table-main">
              <thead>
              <tr>
                <th  style="width: 5%;"><input type="checkbox"  /></th><th  style="align-content: center; width: 15%;">姓名</th><th  style="align-content: center; width: 15%;">账号</th><th style="align-content: center; width: 15%;">角色</th><th style="align-content: center; width: 15%;">手机号</th><th style="align-content: center; width: 15%;">操作</th>
              </tr>
              </thead>
              <tbody id="tby">
              
              </tbody>
            </table>
           <div>
				<ul id="paginator"></ul>
		   </div>
          </form>
        </div>

      </div>
    </div>

    
  </div>
  <div class="modal fade" id="addmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:30%; ">
				<div class="modal-content">
					<div class="model-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        <span class="blue">X</span>
	                    </button>
						<h4 class="modal-title" id="myModalLabel" style="text-align:center;">
							增加运营人员
						</h4>
					</div>
					<div class="modal-body">
					<table style="border-collapse:separate; border-spacing:10px;">
						<tr><td><label class="col-lg-4 control-label">姓名：</label></td>
						<td><input  type="text" id="delete_name" name="delete_name" /></td>
						</tr>
						<tr><td><label class="col-lg-4 control-label">账号：</label></td>
						<td><input  type="text" id="delete_account" name="delete_account" /></td>
						</tr>
						<tr><td><label class="col-lg-4 control-label">角色:</label></td>
						<td><input  type="text"  id="delete_role" name="delete_role" /></td></tr>
						<tr><td><label class="col-lg-4 control-label">手机号:</label></td>
						<td><input  type="text"  id="delete_phone" name="delete_phone" /></td></tr>
						<tr><td colspan="2" style="text-align:center;"><button class="close"  >确定增加</button></td></tr>
						
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
<script src="<%=path %>/assets1/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=path %>/assets1/js/amazeui.min.js"></script>
<script src="<%=path %>/assets1/js/app.js"></script>
<script type="text/javascript">
	var id=1;
	var OperateUseInfo="";
	var currentPage=1;
	var pageRows=5;
	
	$(document).ready(function()
  	{
  			loadGuideInfo();
  	});
  	function loadGuideInfo()
  	{
  		var url="<%=basePath%>/GetOperateUser.action";
  		$.ajax(
  		{
  			url:url,
  			type:"get",
  			data:{currentPage:1,pageRows:pageRows},
  			success: function(data)
  					{
  					    if(data!=null){
  					    OperateUseInfo = data.jsonStr;
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
		                                return "|<<";
		                                break;
		                            case "prev":
		                                return "<";
		                                break;
		                            case "next":
		                                return ">";
		                                break;
		                            case "last":
		                                return ">>|";
		                                break;
		                            case "page":
		                                return page;
		                                break;
		                        }
		                    },
		                    onPageClicked: function (event, originalEvent, type, page) {
		                    	
		                    	
		                        $.ajax({
									url: url,
									datatype: "json",
									type: "GET",
									data:{currentPage:page,pageRows:5},
									success: function(data) {
										OperateUseInfo = data.jsonStr;
  					   					initTable(data.jsonStr,page);	
						            }
						        });
						     }  					
						};					
						$("#paginator").bootstrapPaginator(options);
  					    }
  					}
  		});
  		
  	}
  	
  	function initTable(jsonStr,currentPage)
  	{
  		
  	}
 	
 	function serach()
 	{
 		alert("搜索已运行");
 	}
 	
 	function addOperateUser()
 	{
 		$("#addmodal").modal('show');
 	}
</script>
<script src="<%=path%>/assets/js/jquery.js"></script>
	<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=path%>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	</script>
	<script src="<%=path %>/assets/js/bootstrap.js"></script>

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
