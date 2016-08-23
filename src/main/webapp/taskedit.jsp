<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8" /> 
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="static/dist/css/skins/_all-skins.min.css">
<%  String taskname= "";
	if(request.getParameter("taskname")!=null){
		taskname=request.getParameter("taskname");
	}

%>
<script src="static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/plugins/jQueryUI/jquery-ui.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$.ajax({  
	        type:"post",
	        url:"<%= this.getServletContext().getContextPath() %>/taskedit?taskname=<%= taskname %> ",
	        dataType:"text",  
	        data:{},  
	        success:function(data,status){  
	    		$("#configFileStr").val(data);
	        }
		});
	});
	function tasksave(taskname){
		document.taskEditSave.submit();
	}

</script>
</head>
<body style="overflow: hidden">
<form name="taskEditSave" action="<%= this.getServletContext().getContextPath() %>/taskeditsave" method="post">
<input type="hidden" name="taskname" value="<%= taskname %>"/>
	<div class="container-fluid">
		<div class="row" style="margin-top:2px;margin-left:2px">
			<label><%= taskname.equals("")?"新增":taskname %>(taskName不可修改)</label> 
		</div>
		<div class="row">
			<div align="center">
				<textarea name="configFileStr" id="configFileStr" style="width:99%;height:90%" align="center"></textarea> 
			</div>
		</div>
		<div class="row" style="margin-top:2px;margin-left:2px">
			<input class='btn btn-default' value='保存' onclick='javascript:tasksave("<%= taskname %>")'/>
		</div>
	</div>

</form>	


	
</body>
</html>

