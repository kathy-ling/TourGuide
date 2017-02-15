<%@ page language="java" import="java.util.*,javax.servlet.http.HttpSession,com.TourGuide.weixin.util.SNSUserInfoUtil,com.TourGuide.model.SNSUserInfo" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SNSUserInfoUtil.snsUserInfo=(SNSUserInfo) session.getAttribute("visitor");
System.out.print(SNSUserInfoUtil.snsUserInfo);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'regist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="css/Register.css">
    <link rel="stylesheet" type="text/css" href="css/jquery.mobile-1.4.5.css">
    <link rel="stylesheet" type="text/css" href="css/nativedroid2.css">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.mobile-1.4.5.min.js"></script>
    <script type="text/javascript" src="js/Regist.js"></script>
  </head>
  
  <body>
<div data-role="page" id="registerPage">
    <div data-role="header"  id="header" class="header">
    </div>
    <div data-role="main" class="ui-content">
        <form action="register_submit" method="get" accept-charset="utf-8">
            <!-- 前三个通过微信自动获取-->
            <div class="ui-field-contain">
            <label for="nickname" class="ui-hidden-accessible">昵称：</label>
            <input type="text" id="nickname" name="" class="" value="" placeholder="昵称" />
            <table><tbody>
            <tr><td>  <label for="sex" class="">性别：</label></td>
            <td><fieldset data-role="controlgroup" data-type="horizontal">
                      <label for="Fmale" >女</label>
                      <input type="radio"  id="Fmale" name="guideSex" value="女"/>
                        <label for="Male" >男</label>
                        <input type="radio"  id="Male" name="guideSex" value="男"/>
                        </fieldset>
            </td></tr></tbody></table>
            <label for="name" class="ui-hidden-accessible" class="">姓名:</label>
            <input type="text" id="name" name="" class="" placeholder="姓名"/>
            <label for="tel" class="ui-hidden-accessible" class="">电话:</label>
            <input type="tel" id="tel" name="" class="" placeholder="电话" />
            <label for="password"  class="ui-hidden-accessible" class="">密码：</label>
            <input type="password" id="password" name="" class=""  placeholder="密码"/>
            <label for="password"  class="ui-hidden-accessible" class="">确认密码：</label>
            <input type="password" id="confirm_password" name="" class="" placeholder="确认密码" />
            <label for="headimg">头像：</label>
            <div>
            	<input type="file" id="btnFile" style="display: none" onchange="selectImage(this)">
            	<img src="img/visitor.png" id="visitor_img">
            </div>  
            <label for="agreeClause" class="">我已阅读并同意<a href="#">相关服务条款</a></label>
            <input type="checkbox" id="agreeClause" value="yes" name="" class="" />
            <input type="button" id="Registsubmit" name="submit" value="注册">
            </div>
        </form>
    </div>
</div>
</body>
</html>
