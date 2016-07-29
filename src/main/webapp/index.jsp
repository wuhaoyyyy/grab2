<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="static/dist/css/skins/_all-skins.min.css">

<script src="static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/plugins/jQueryUI/jquery-ui.min.js"></script>
<!-- <script src="https://www.google-analytics.com/analytics.js"></script> -->

<!-- 	<link href='http://fonts.googleapis.com/css?family=Dosis:400,500,600' rel='stylesheet' type='text/css'> -->
<!-- 	<link href='http://fonts.googleapis.com/css?family=Abel' rel='stylesheet' type='text/css'> -->
	<link rel="stylesheet" href="http://www.solutiisoft.com/bootstrap-sidebar/public/css/font-awesome.min.css">
	<link rel="stylesheet" href="http://www.solutiisoft.com/bootstrap-sidebar/public/css/design.css">
	
<!--   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css"> -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	
	

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
			$("#h").before(getTaskGroupHtml(taskgroup));
		}
	}
	
	function getTaskGroupHtml(taskgroupname){
		var t='<li class="treeview"><a href="tasklist.jsp?taskgroup='+taskgroupname+'" target="mainFrame"> <i'
				+'class="glyphicon glyphicon-th-list"></i> <span>'+taskgroupname+'</span> <i'
				+'class="fa fa-angle-left pull-right"></i>'
				+'</a></li>';
		
		return t;
	}

</script>
</head>
<body>
<div class="body slide">
	<aside class="sidebar show perfectScrollbar">
		<div id="solso-sidebar">
	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
		<div class="panel panel-default">
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="heading1">
					<h4 class="panel-title">
						<a href="index.php" class="single-item">
							<i class="fa fa-dashboard"></i> Dashboard 
						</a>
					</h4>
				</div>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading2">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse2" aria-expanded="true" aria-controls="collapse2">
						<i class="fa fa-bar-chart-o"></i> Charts
						<i class="pull-right fa fa-caret-down"></i>
					</a>
				</h4>
			</div>
			
			<div id="collapse2" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading2">
				<div>
					<a href="bar-chart.php" class="list-group-item">bar Chart</a>
					<a href="pie-chart.php" class="list-group-item">pie Chart</a>
					<a href="google-chart.php" class="list-group-item">google Chart</a>
				</div>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading3">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse3" aria-expanded="true" aria-controls="collapse3">
						<i class="fa fa-table"></i> Tables
						<i class="pull-right fa fa-caret-down"></i>
					</a>
				</h4>
			</div>
			
			<div id="collapse3" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading3">
				<div>
					<a href="simple-tables.php" class="list-group-item">Simple Tables</a>
					<a href="data-tables.php" class="list-group-item">Data Tables</a>
					<a href="beauty-tables.php" class="list-group-item">Beauty Tables</a>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading4">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse4" aria-expanded="true" aria-controls="collapse4">
						<i class="fa fa-edit"></i> Forms
						<i class="pull-right fa fa-caret-down"></i>
					</a>
				</h4>
			</div>
			
			<div id="collapse4" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading4">
				<div>
					<a href="form-1.php" class="list-group-item">Form 1</a>
					<a href="form-2.php" class="list-group-item">Form 2</a>
					<a href="form-3.php" class="list-group-item">Form 3</a>
					<a href="form-4.php" class="list-group-item">Form 4</a>
					<a href="form-5.php" class="list-group-item">Form 5</a>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading5">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse5" aria-expanded="true" aria-controls="collapse5">
						<i class="fa fa-desktop"></i> UI Elements
						<i class="pull-right fa fa-caret-down"></i>
					</a>
				</h4>
			</div>
			
			<div id="collapse5" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading5">
				<div>
					<a href="element-1.php" class="list-group-item">Element 1</a>
					<a href="element-2.php" class="list-group-item">Element 2</a>
					<a href="element-3.php" class="list-group-item">Element 3</a>
					<a href="element-4.php" class="list-group-item">Element 4</a>
					<a href="element-5.php" class="list-group-item">Element 5</a>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading6">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse6" aria-expanded="true" aria-controls="collapse6">
						<i class="fa fa-file"></i> Pages
						<i class="pull-right fa fa-caret-down"></i>
					</a>
				</h4>
			</div>
			
			<div id="collapse6" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading6">
				<div>
					<a href="page-1.php" class="list-group-item">Page 1</a>
					<a href="page-2.php" class="list-group-item">Page 2</a>
					<a href="page-3.php" class="list-group-item">Page 3</a>
					<a href="page-4.php" class="list-group-item">Page 4</a>
					<a href="page-5.php" class="list-group-item">Page 5</a>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading7">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse7" aria-expanded="true" aria-controls="collapse7">
						<i class="fa fa-picture-o"></i> Gallery
						<i class="pull-right fa fa-caret-down"></i>
					</a>
				</h4>
			</div>
			
			<div id="collapse7" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading7">
				<div>
					<a href="gallery-1.php" class="list-group-item">Gallery 1</a>
					<a href="gallery-2.php" class="list-group-item">Gallery 2</a>
					<a href="gallery-3.php" class="list-group-item">Gallery 3</a>
					<a href="gallery-4.php" class="list-group-item">Gallery 4</a>
					<a href="gallery-5.php" class="list-group-item">Gallery 5</a>
				</div>
			</div>
		</div>		
	</div>
</div>	</aside>

</div>
		<header class="main-header">
			<a href="index.html" class="logo"> <span class="logo-mini"><b>A</b>LT</span>
				<span class="logo-lg"><b>G</b>rab</span>
			</a>
			<nav class="navbar navbar-static-top">
			</nav>
			
		</header>

		
		
		<div class="content-wrapper">
			<iframe id="mainFrame" name="mainFrame" src="tasklist.jsp" style="width:100%;height:100%;border:0px;">
			
	
			
			</iframe>
		</div>
</body>
</html>

