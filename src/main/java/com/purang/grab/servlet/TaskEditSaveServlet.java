package com.purang.grab.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.BeanFactory;

import com.purang.grab.util.TaskInfoUtils;
import com.purang.grab.task.Task;

public class TaskEditSaveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Log taskLog = LogFactory.getLog("grabtask");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String taskName=req.getParameter("taskname");
		String taskgroup=req.getParameter("taskgroup");
		String configFileStr=req.getParameter("configFileStr");
		if(taskgroup==null){
			String taskConfigFilePath=TaskInfoUtils.getTaskConfigPath(taskName);
			
			File configFile=new File(taskConfigFilePath);
			if(configFile.exists()){
				configFile.delete();
			}
			configFile=new File(taskConfigFilePath);
			FileWriter fw =  new FileWriter(configFile);
			fw.write(configFileStr);
			fw.close();
			TaskInfoUtils.getTaskFactoryNew(taskName);
			
			resp.sendRedirect("taskedit.jsp?taskname="+URLEncoder.encode(taskName,"UTF-8"));
		}
		else{
			TaskInfoUtils.createNewTask(taskgroup,taskName,configFileStr);
			resp.sendRedirect("taskedit.jsp?taskname="+URLEncoder.encode(taskName,"UTF-8"));
		}
	}
	
}
