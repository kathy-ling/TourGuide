<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html lang="en">
	<head>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>后台管理员界面</title>

		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%=path %>/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="<%=path %>/assets/css/font-awesome.css" />

		<!-- page specific plugin styles -->

		<!-- text fonts -->
		<link rel="stylesheet" href="<%=path %>/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="<%=path %>/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="<%=path %>/assets/css/ace.onpage-help.css" />
		<link rel="stylesheet" href="<%=path %>/docs/assets/js/themes/sunburst.css" />

		<script src="<%=path %>/assets/js/ace-extra.js"></script>
	</head>

	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<div class="navbar-container" id="navbar-container">
				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa "></i>
							欢迎来到后台管理
						</small>
					</a>
				</div>
					
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
	

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<ul class="nav nav-list">
					<li class="">
						<a href="" class="dropdown-toggle">
							<i class="menu-icon fa fa-location-arrow red"></i>
							<span class="menu-text">运营人员管理</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>
						<ul class="submenu">
							<li class="">
								<a href="Guidelocation.action" target="targetiframe">
									<i class="menu-icon fa fa-caret-right"></i>
									运营人员信息管理
								</a>

								<b class="arrow"></b>
							</li>

						</ul>
					</li>

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-info-circle blue"></i>
							<span class="menu-text"> 讲解员管理</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="Guideinfo.action" target="targetiframe">
									<i class="menu-icon fa fa-caret-right"></i>
									讲解员审核
								</a>
								<b class="arrow"></b>
							</li>	
						</ul>
						<ul class="submenu">
							<li class="">
								<a href="Guideinfo.action" target="targetiframe">
									<i class="menu-icon fa fa-caret-right"></i>
									讲解员信息管理
								</a>
								<b class="arrow"></b>
							</li>	
						</ul>
					</li>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-info-circle blue"></i>
							<span class="menu-text"> 景区管理</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>
						<ul class="submenu">
							<li class="">
								<a href="Guideinfo.action" target="targetiframe">
									<i class="menu-icon fa fa-caret-right"></i>
									景区信息管理
								</a>
								<b class="arrow"></b>
							</li>	
						</ul>
						
					</li>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-venus-double red"></i>
							<span class="menu-text"> 游客管理</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="elements.html">
									<i class="menu-icon fa fa-caret-right"></i>
									游客信息管理
								</a>

								<b class="arrow"></b>
							</li>

							
						</ul>
					</li>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-reorder blue" ></i>
							<span class="menu-text"> 订单管理</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="elements.html">
									<i class="menu-icon fa fa-caret-right"></i>
									订单信息管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="buttons.html">
									<i class="menu-icon fa fa-caret-right"></i>
									直接派单
								</a>

								<b class="arrow"></b>
							</li>
							
							
						</ul>
					</li>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-cog red"></i>
							<span class="menu-text"> 收入管理</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="elements.html">
									<i class="menu-icon fa fa-caret-right"></i>
									平台收入信息管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="buttons.html">
									<i class="menu-icon fa fa-caret-right"></i>
									讲解员收入查询
								</a>

								<b class="arrow"></b>
							</li>
							
							
						</ul>
					</li>
					
				</ul><!-- /.nav-list -->


			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content" >
				<div class="page-content" >
					<iframe name="targetiframe" style="width: 100%; height:90% ; " src="FirstShow.action"  ></iframe>
				</div><!-- /.page-content -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=path %>/assets/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=path %>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="<%=path %>/assets/js/bootstrap.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="<%=path %>/assets/js/ace/elements.scroller.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.colorpicker.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.fileinput.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.typeahead.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.wysiwyg.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.spinner.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.treeview.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.wizard.js"></script>
		<script src="<%=path %>/assets/js/ace/elements.aside.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.ajax-content.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.touch-drag.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.sidebar.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.submenu-hover.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.widget-box.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.settings.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.settings-rtl.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.settings-skin.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.searchbox-autocomplete.js"></script>

		<!-- inline scripts related to this page -->

		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		

		<script type="text/javascript"> ace.vars['base'] = '..'; </script>
		<script src="<%=path %>/assets/js/ace/elements.onpage-help.js"></script>
		<script src="<%=path %>/assets/js/ace/ace.onpage-help.js"></script>
		<script src="<%=path %>/docs/assets/js/rainbow.js"></script>
		<script src="<%=path %>/docs/assets/js/language/generic.js"></script>
		<script src="<%=path %>/docs/assets/js/language/html.js"></script>
		<script src="<%=path %>/docs/assets/js/language/css.js"></script>
		<script src="<%=path %>/docs/assets/js/language/javascript.js"></script>
	</body>
</html>
