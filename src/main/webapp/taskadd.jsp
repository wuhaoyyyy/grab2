<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8" /> 
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="static/dist/css/skins/_all-skins.min.css">
<script src="static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/plugins/jQueryUI/jquery-ui.min.js"></script>
<script type="text/javascript">

	function tasksave(){
		if(document.taskEditSave.taskgroup.value==''||document.taskEditSave.taskname.value==''||document.taskEditSave.configFileStr.value==''){
			alert('输入不完整');
			return;
		}
		document.taskEditSave.submit();
	}

</script>
</head>
<body style="overflow: hidden">
<form name="taskEditSave" action="<%= this.getServletContext().getContextPath() %>/taskeditsave" method="post">
	<div class="container-fluid">
		<div class="row" style="margin-top:2px;margin-left:2px">
			<div class="form-group" style="width:99%;"><label>所属目录:</label><input type="text" class="form-control" name="taskgroup" placeholder="请输入目录名"/></div>
			<div class="form-group" style="width:99%;"><label>任务名:</label><input type="text" class="form-control" name="taskname" placeholder="请输入任务名"/></div>
		</div>
		<div class="row" style="margin-top:2px;margin-left:2px">
			<textarea name="configFileStr" id="configFileStr" style="width:99%;height:70%"></textarea> 

		</div>
		<div class="row" style="margin-top:2px;margin-left:2px">
			<input class='btn btn-default' value='保存' onclick='javascript:tasksave()'/>
		</div>
	</div>
</form>	
</body>
</html>

