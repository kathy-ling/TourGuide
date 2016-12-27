<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>景区后台管理</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path %>/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=path %>/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=path %>/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=path %>/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path %>/css/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page skin-blue" style="background: gray;" >
<div class="login-box">
  <div class="login-logo">
   <b>景区后台管理页面</b>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body" style="background: burlywood;">
  
    <p class="login-box-msg">请填写你的登录信息</p>
	<div>
	<c:if test="${!empty error }">
    	<font color="red"><c:out value="${error}"></c:out></font>
    </c:if>
	</div>

    <form action="login.action" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="username" placeholder="手机号">
        <span class="glyphicon glyphicon-phone form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="password" placeholder="密码" >
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck-7">
           <label>
              <input type="checkbox" style=" opacity: 1;"> 记住密码
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    
    <!-- /.social-auth-links -->

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/js/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="<%=path %>/js/icheck.min.js"></script>

</body>
</html>