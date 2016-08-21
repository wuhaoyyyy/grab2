<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/font-awesome-4.6.3/css/font-awesome.min.css">
<link rel="stylesheet" href="static/bootstrap-sidebar/css/design.css">

<script src="static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/plugins/jQueryUI/jquery-ui.min.js"></script>
<style type="text/css">
</style>

<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({  
	        type:"post",
	        url:"<%= this.getServletContext().getContextPath() %>/taskgrouplist",
	        dataType:"json",  
	        data:{},  
	        success:function(data,status){  
		 		addTaskGroupInfo(data);
	        }
		});
	});
	
	function addTaskGroupInfo(data){
		for(var i=0;i<data.length;i++){
			var taskgroup=data[i];
			$("#taskgroupmenu").append(getTaskGroupHtml(taskgroup));
		}
	}
	
	function getTaskGroupHtml(taskgroupname){
		var t='<a href="tasklist.jsp?taskgroup='+taskgroupname+'" target="mainFrame" class="list-group-item">'+taskgroupname+'</a>';
		return t;
	}

</script>
</head>
<body>

	<header>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
			
					<div class="brand">
						<a class="navbar-brand" href="index.php"><span>Grab</span></a>
					</div>		
				</div>

			</div>
		</nav>
	</header>


<div class="body slide">

	<aside class="sidebar show perfectScrollbar">
		<div id="solso-sidebar">
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading2">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapse" aria-expanded="true" aria-controls="collapse">
							<i class="fa fa-bar-chart-o"></i> 任务组
							<i class="pull-right fa fa-caret-down"></i>
						</a>
					</h4>
				</div>
				
				<div id="collapse" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading2">
					<div id='taskgroupmenu' >
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading2">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="true" aria-controls="collapse2">
							<i class="fa fa-edit"></i> 任务管理
							<i class="pull-right fa fa-caret-down"></i>
						</a>
					</h4>
				</div>
				
				<div id="collapse2" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading2">
					<div id='taskgroupmenu' >
						<a href="" class="list-group-item">bar Chart</a>
					</div>
				</div>
			</div>
		</div>
	</div>	</aside>
	<div class="container-fluid">
		<iframe id="mainFrame" name="mainFrame" src="tasklist.jsp" style="width:100%;height:100%;border:0px;">
		
		
		</iframe>
	</div>
</div>
		
</body>
</html>

