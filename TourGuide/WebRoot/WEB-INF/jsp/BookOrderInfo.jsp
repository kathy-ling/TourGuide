<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<link rel="stylesheet" href="<%=path%>/docs/assets/js/themes/sunburst.css" />
  	<link rel="icon" type="image/png" href="<%=path %>/assets1/i/favicon.png">
  	<link rel="apple-touch-icon-precomposed" href="<%=path %>/assets1/i/app-icon72x72@2x.png">
  	<link rel="stylesheet" href="<%=path %>/assets1/css/amazeui.min.css"/>
  	<link rel="stylesheet" href="<%=path %>/assets1/css/admin.css">
  	<link rel="stylesheet" href="<%=path%>/assets/css/bootstrap.css" />
	<script type="text/javascript" src="<%=path %>/assets/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/js/bootstrap-paginator.min.js"></script>

  </head>
  
 <body>




  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">订单管理</strong> / <small>订单信息管理</small></div>
      </div>

      <hr>

      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
              
            </div>
          </div>
        </div>
        
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            
            <input type="text"  id="querytxt" class="am-form-field" placeholder="证号">
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="button" onclick="search()">搜索</button>
          </span>
          </div>
        </div>
      </div>

      <div class="am-g">
        <div class="am-u-sm-12">
          <form class="am-form">
            <table class="am-table am-table-striped am-table-hover table-main" style="border-collapse:separate; border-spacing:5px;">
              <thead>
              <tr>
              	<th style="text-align: center; width: 10%;">订单编号</th>
               <th style="text-align: center; width: 5%;">讲解员姓名</th>
               <th style="text-align: center; width: 5%;">游客姓名</th>
               <th style="text-align: center; width: 10%;">景区名称</th>
               <th style="text-align: center; width: 10%;">开始时间</th>
                <th style="text-align: center; width: 5%;">订单状态</th>
                <th style="text-align: center; width: 5%;">游客人数</th>
              	<th style="text-align: center; width: 20%;">操作</th>
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


<footer>
  <hr>
  <p class="am-padding-left">© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
</footer>


<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<script src="<%=path %>/assets1/js/amazeui.min.js"></script>
<script src="<%=path %>/assets1/js/app.js"></script>
<script type="text/javascript">
	var id=1;
	var OrderInfo="";
	var currentPage=1;
	var pageRows=5;
	var GlobalIndex;
	$(document).ready(function()
  	{
  			loadOrderInfo();
  	});
  	
  	function loadOrderInfo()
  	{
  		var url="<%=basePath%>bookorder/getBookorderInfo.action";
  		
  		$.ajax(
  		{
  			url:url,
  			type:"POST",
  			datatype: "json",
  			data:{currentPage:1,pageRows:pageRows},
  			success: function(data)
  					{
  					    if(data!=null){
  					    OrderInfo = data.jsonStr;
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
									type: "POST",
									datatype: "json",
									data:{currentPage:page,pageRows:5},
									success: function(data) {
										OrderInfo = data.jsonStr;
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
  		alert(jsonStr);
  		$("#tby").html("");
  		$.each(JSON.parse(jsonStr),function(index,value)
  			{
  				var t0="<tr>";
              	var t1="<td style='text-align: center; width: 8%;'>"+value.bookOrderID+"</td>";
              	var t2="<td style='text-align: center; width: 8%;'>"+value.guideName+"</td>";
              	var t3="<td style='text-align: center; width: 8%;'>"+value.name+"</td>";
              	var t4="<td style='text-align: center; width: 8%;'>"+value.scenicName+"</td>";
              	var t5="<td style='text-align: center; width: 15%;'>"+value.time+"</td>";
              	var t6="<td style='text-align: center; width: 8%;'>"+value.orderState+"</td>";
              	var t7="<td style='text-align: center; width: 8%;'>"+value.visitNum+"</td>";
              	var t8="<td style='text-align: center; '> <div class='am-btn-toolbar'>"+
	              	"<div style='text-align: center;float: none' class='am-btn-group am-btn-group-xs'>"+
	              	"<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='LookGuideInfo("+index+")'>"+"<span class='am-icon-pencil-square-o'></span> 查看</button>";		
                var t9="</div></div> </td></tr>";
               
                $("#tby").append(t0).append(t1).append(t2).append(t3).append(t4).append(t5).append(t6).append(t7).append(t8).append(t9);
  			});
  	}
 	
</script>

	<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=path%>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	</script>
	<script src="<%=path %>/assets/js/bootstrap.js"></script>

</body>
</html>
