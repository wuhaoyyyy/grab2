package com.purang.grab.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.purang.grab.task.Task;
import com.purang.grab.util.TaskInfoUtils;

public class TaskListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Task> taskList=TaskInfoUtils.getTaskList();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"processor","pipelineList"});
		JSONArray jsonArray=JSONArray.fromObject(taskList, jsonConfig);

		PrintWriter out = resp.getWriter();  
        out.write(jsonArray.toString());  
	}
}
