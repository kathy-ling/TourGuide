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
	<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/js/bootstrap-paginator.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/echarts.js"></script>
  </head>
  
 <body>





  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">收入管理</strong> / <small>讲解员收入信息管理</small></div>
      </div>

      <hr>

      <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
          
        </div> 
        
        <div class="am-u-sm-12 am-u-md-3">
          <div class="am-input-group am-input-group-sm">
            
            <input type="text" id="searchText" class="am-form-field" placeholder="讲解员手机号">
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
                <th  style="text-align: center; width: 10%;">讲解员编号</th>
                <th  style="text-align: center; width: 10%;">讲解员名称</th>
                <th style="text-align: center; width: 10%;">一月收入</th>
                <th style="text-align: center; width: 10%;">二月收入</th>
                <th style="text-align: center; width: 10%;">三月收入</th>
                <th style="text-align: center; width: 10%;">四月收入</th>
                <th style="text-align: center; width: 10%;">五月收入</th>
                <th style="text-align: center; width: 10%;">六月收入</th>
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
<div class="modal fade" id="SearchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
			<div class="modal-dialog" >
				<div class="modal-content">
					<div class="model-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                        <span class="blue">X</span>
	                    </button>
	                    <h4 class="modal-title" id="myModalLabel" style="text-align:center;">
							讲解员收入信息
						</h4>
					</div>
					<div class="modal-body">
						<div id="Feemain" style="width: 600px;height:400px;"></div>
									
					</div>
				</div>
			</div>
</div>

  


<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<script src="<%=basePath %>/assets1/js/amazeui.min.js"></script>
<script src="<%=basePath %>/assets1/js/app.js"></script>
<script type="text/javascript">
	var id=1;
	var currentPage=1;
	var pageRows=5;
	var guideFeeInfo;
	$(document).ready(function()
  	{
  		
  		loadVisitorInfo();
  	});
  	function loadVisitorInfo()
  	{
  	
  		var url="<%=basePath%>guidefee/GetGuideFee.action";
  		$.ajax(
  		{
  			url:url,
  			type:"post",
  			datatype: "json",
  			data:{currentPage:1,pageRows:pageRows},
  			success: function(data)
  					{
  					    if(data!=null){
  					    guideFeeInfo = data.jsonStr;
  					   
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
									type: "post",
									datatype: "json",
									data:{currentPage:page,pageRows:5},
									success: function(data) {
										guideFeeInfo = data.jsonStr;
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
  		$.each(JSON.parse(jsonStr),function(index,value)
  			{
  				var t0="<tr>";
  				var t1="<td style='text-align: center; width: 10%;'>"+value.guideID+"</td>";
              	var t2="<td style='text-align: center; width: 10%;'>"+value.name+"</td>";
              	var t3="<td style='text-align: center;width: 10%;'>"+value.Jan+"</td>";
              	var t4="<td style='text-align: center; width: 10%;'>"+value.Feb+"</td>";
              	var t5="<td style='text-align: center; width: 10%;'>"+value.Mar+"</td>";
              	var t6="<td style='text-align: center; width: 10%;'>"+value.Apr+"</td>";
              	var t7="<td style='text-align: center; width: 10%;'>"+value.May+"</td>";
              	var t8="<td style='text-align: center; width: 10%;'>"+value.Jun+"</td>";
              	var t9="<td align='center'> <div class='am-btn-toolbar'>"+
              	"<div  style='text-align: center;float: none' class='am-btn-group am-btn-group-xs'>"+
              	"<button class='am-btn am-btn-default am-btn-xs am-text-secondary' type='button' onclick='LookguideFee("+index+")'>"+"<span class='am-icon-pencil-square-o'></span>查看詳情</button>"+
                "</div></div> </td>";		
                var t10="</tr>";
               $("#tby").append(t0).append(t1).append(t2).append(t3).append(t4).append(t5).append(t6).append(t7).append(t8).append(t9).append(t10);
  			});
  	}
 	
 	function search()
 	{
 
 		var url = "<%=basePath%>guidefee/GetGuideFeeByguideID.action";
 		var a = $("#searchText").val();
 		$.ajax( {
 			url:url,
 			type:"POST",
 			datatype:"json",
 			data:{guideID:a},
 			success:function(data) {
 				var d=data.jsonStr;
 				if (d == "[]") {
 					alert("没有搜索到任何信息，请重新搜索!");
	 			}
		 		else {
					SearchSuccess(d);
					
		 		};
 			}
 		});
 	
 	}
 
 	function LookguideFee(index)
 	{
 		var a=JSON.parse(guideFeeInfo);
 		var myChart = echarts.init(document.getElementById('Feemain'));
		
        // 指定图表的配置项和数据
        var option = {
            title: {
        		text: a[index].name+'  讲解员收入'
   			 		},
    		tooltip: {
        		trigger: 'axis'
    		},
    		xAxis:  {
       	 	type: 'category',
        	boundaryGap: false,
        	data: ['一月','二月','三月','四月','五月','六月','七月','八月','九月',
       		 '十月','十一月','十二月']
   			 },
    		yAxis: {
       		type: 'value',
        	axisLabel: {
            formatter: '{value} 元'
       	 	}
    		},
    		series: [
       	 	{
            name:'讲解员收入',
            type:'line',
            data:[a[index].Jan, a[index].Feb, a[index].Mar, a[index].Apr,
             a[index].May, a[index].Jun, a[index].Jul,a[index].Aug,
             a[index].Sep, a[index].Oct,a[index].Nov,a[index].Dec],
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            }
            
        },
        
    	]
		};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
 		$("#SearchModal").modal("show");
 		
 	}
 	
 	function SearchSuccess(jsonStr)
 	{
 		var a=JSON.parse(jsonStr);
 		var index=0;
 		var myChart = echarts.init(document.getElementById('Feemain'));
		
        // 指定图表的配置项和数据
        var option = {
            title: {
        		text: a[index].name+'  讲解员收入'
   			 		},
    		tooltip: {
        		trigger: 'axis'
    		},
    		xAxis:  {
       	 	type: 'category',
        	boundaryGap: false,
        	data: ['一月','二月','三月','四月','五月','六月','七月','八月','九月',
       		 '十月','十一月','十二月']
   			 },
    		yAxis: {
       		type: 'value',
        	axisLabel: {
            formatter: '{value} 元'
       	 	}
    		},
    		series: [
       	 	{
            name:'讲解员收入',
            type:'line',
            data:[a[index].Jan, a[index].Feb, a[index].Mar, a[index].Apr,
             a[index].May, a[index].Jun, a[index].Jul,a[index].Aug,
             a[index].Sep, a[index].Oct,a[index].Nov,a[index].Dec],
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            }
            
        },
        
    	]
		};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
 		$("#SearchModal").modal("show");
 	}
 	
</script>
	<script src="<%=path%>/assets/js/distpicker.data.js"></script>
	<script src="<%=path%>/assets/js/distpicker.js"></script>
	<script src="<%=path%>/assets/js/main.js"></script>
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