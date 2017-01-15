<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<meta name="description" content="这是一个 index 页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="<%=basePath %>/assets/css/amazeui.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/assets/css/admin.css">
<script src="<%=basePath %>/assets/js/jquery.js"></script>
<script src="<%=basePath %>/assets/js/app.js"></script>
</head>
<body>
<!--[if lte IE 9]><p class="browsehappy">升级你的浏览器吧！ <a href="http://se.360.cn/" target="_blank">升级浏览器</a>以获得更好的体验！</p><![endif]-->

</head>

<body>
<header class="am-topbar admin-header">
  <div class="am-topbar-brand"><img src="<%=basePath %>assets/i/logo.png"></div>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav admin-header-list">

   <li class="am-dropdown tognzhi" data-am-dropdown>
  
 
</li>
      
    </ul>
  </div>
</header>

<div class="am-cf admin-main"> 

<div class="nav-navicon admin-main admin-sidebar">
    
    
    <div class="sideMenu am-icon-dashboard" style="color:#aeb2b7; margin: 10px 0 0 0;"> 欢迎系统管理员</div>
    <div class="sideMenu">
      <h3 class="am-icon-flag"><em></em> <a href="#">运营人员管理</a></h3>
      <ul>
        <li><a href="operateUser.action" target="targetiframe">运营人员信息管理</a></li>
      </ul>
      <h3 class="am-icon-users"><em></em> <a href="#">讲解员管理</a></h3>
      <ul>
        <li><a href="GuideInfoYes.action" target="targetiframe">已审核信息管理</a></li>
        <li><a href="GuideInfoNo.action" target="targetiframe">未审核信息管理</a></li>
        <li><a href="guideWorkday.action" target="targetiframe">讲解员日程管理</a></li>
      </ul>
      
      <h3 class="am-icon-users"><em></em> <a href="#">游客管理</a></h3>
      <ul>
       <li><a href="visitor.action" target="targetiframe">游客信息管理</a></li>
      </ul>
      
      <h3 class="am-icon-cart-plus"><em></em> <a href="#"> 订单管理</a></h3>
      <ul>
      	<li><a href="BookOrderInfo.action" target="targetiframe">订单信息管理</a></li>
        <li>直接派单</li>
      </ul>
      
      <h3 class="am-icon-location-arrow"><em></em> <a href="#">景区管理</a></h3>
      <ul>
       <li><a href="scenicInfo.action" target="targetiframe">景区信息管理</a></li>
        
      </ul>
      <h3 class="am-icon-money"><em></em> <a href="#">收入管理</a></h3>
      <ul>
        <li><a href="ScenicFee.action" target="targetiframe">平台收入信息管理</a></li>
         <li><a href="GuideFee.action" target="targetiframe">讲解员收入查询</a></li>
        
      </ul>
      
      <!-- <h3 class="am-icon-gears"><em></em> <a href="#">系统管理</a></h3>
      <ul>
        <li>个人信息修改</li>
        
      </ul> -->
    </div>
    <!-- sideMenu End --> 
    
    <script type="text/javascript">
			jQuery(".sideMenu").slide({
				titCell:"h3", //鼠标触发对象
				targetCell:"ul", //与titCell一一对应，第n个titCell控制第n个targetCell的显示隐藏
				effect:"slideDown", //targetCell下拉效果
				delayTime:300 , //效果时间
				triggerTime:150, //鼠标延迟触发时间（默认150）
				defaultPlay:true,//默认是否执行效果（默认true）
				returnDefault:true //鼠标从.sideMen移走后返回默认状态（默认false）
				});
		</script> 

    
    
    
    
    
    
    
</div>

<div class=" admin-content">
	
		<div class="daohang">
			<ul>
				
			</ul>

       

	
</div>
	
	


<div class="admin-biaogelist" style="width: 85%;height:90%">
   
          <iframe name="targetiframe" style="width: 100%; height:90% ; " src="FirstShow.action"  ></iframe>
    
 <div class="foods">
  <ul>版权所有@2016 </ul>
</div>

</div>

</div>




</div>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/polyfill/rem.min.js"></script>
<script src="assets/js/polyfill/respond.min.js"></script>
<script src="assets/js/amazeui.legacy.js"></script>
<![endif]--> 

<!--[if (gte IE 9)|!(IE)]><!--> 
<script src="<%=basePath %>/assets/js/amazeui.min.js"></script>
<!--<![endif]-->
<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>/assets/js/jquery.js'>"+"<"+"/script>");
		</script>


</body>
</html>