<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8" /> 
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="static/dist/css/skins/_all-skins.min.css">
<%  String taskgroup= "";
	if(request.getParameter("taskgroup")!=null){
		taskgroup=request.getParameter("taskgroup");
	}

%>
<script src="static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/plugins/jQueryUI/jquery-ui.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		$.ajax({  
	        type:"post",
	        url:"<%= this.getServletContext().getContextPath() %>/tasklist?taskgroup=<%= taskgroup %> ",
	        dataType:"json",  
	        data:{},  
	        success:function(data,status){  
	        	addTaskInfo(data);
	        }
		});
	});
	
	
	function addTaskInfo(alltask){
		for(var i=0;i<alltask.length;i++){
			$("#tasktable").append(getTaskHtml(alltask[i]));
		}
	}
	
	function getTaskHtml(task){
		var t="<tr>";
		t=t+"<td>"+task.taskName+"</td>";
		t=t+"<td>"+task.taskDesc+"</td>";
		t=t+"<td>"+task.autostart+"</td>";
		t=t+"<td>"+task.status+"</td>";
		
		if(task.status=='stoped'){
			t=t+"<td><input value='启动' type='button' onclick=\"taskoper(\'"+task.taskName+"\',\'start\')\"/>"
				+"<input value='停止' type='button' onclick=\"taskoper(\'"+task.taskName+"\',\'stop\')\" disabled='disabled'/></td>";
		}
		else if(task.status=='started'){
			t=t+"<td><input value='启动' type='button' onclick=\"taskoper(\'"+task.taskName+"\',\'start\')\" disabled='disabled'/>"
			+"<input value='停止' type='button' onclick=\"taskoper(\'"+task.taskName+"\',\'stop\')\" /></td>";
		}
// 		t=t+"<td><input value='启动' type='button' onclick=\"taskoper(\'"+task.taskName+"\',\'start\')\"/>"
// 			+"<input value='停止' type='button' onclick=\"taskoper(\'"+task.taskName+"\',\'shutdown\')\"/></td>";
		t=t+"</tr>";
		return t;
	}
	function taskoper(taskname,operation){
		document.taskOperate.taskname.value=taskname;
		document.taskOperate.operation.value=operation;
		document.taskOperate.submit();
	}

</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<form name="taskOperate" action="<%= this.getServletContext().getContextPath() %>/taskoperate" method="post">
<input type="hidden" name="taskname"/>
<input type="hidden" name="operation"/>
</form>
      <div class="row">
        <div class="col-xs-12">
	<div class="box">
            <div class="box-header">
              <h3 class="box-title">列表</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body table-responsive no-padding table-bordered">
              <table id="tasktable" class="table table-hover" style="width:80%">
                <tr>
                  <th>任务</th>
                  <th>描述</th>
                  <th>自动启动</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
                
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          </div>
          </div>
</body>
</html>

