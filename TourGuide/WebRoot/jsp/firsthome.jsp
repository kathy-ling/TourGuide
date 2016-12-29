<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'firsthome.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
 <body>
		<div style="margin-top: 1%;">本系统的管理员初始密码是123456。</div>
		
		<div style="margin-top: 1%;">运营人员管理：管理员可以对运营人员进行增加、编辑、查看。并且可以对运营人员的信息编辑或者删除禁用运营人员。</div>
		
		<div style="margin-top: 1%;">讲解员管理:管理员对讲解员进行审核、浏览、删除禁用等功能。</div>
		<div style="margin-top: 1%;">景区管理:管理员可以自己发布景区信息供游客进行游览，对景区搜索时可以直接搜索也可以进行景区名称、位置搜索查看景区。</div>
		<div style="margin-top: 1%;">游客管理：管理员可以查看注册过的游客信息并且可以对其进行锁定与删除。</div>
		<div style="margin-top: 1%;">订单管理：订单分为直接预约订单、拼接订单，查看订单可以进行搜索查看订单详情，每个订单都提供打印与导出功能。</div>
		<div style="margin-top: 1%;">收入管理：管理员与运营人员可以对平台总收入、平台净利润、每个讲解员收入进行查询
</div>
	</body>
</html>
