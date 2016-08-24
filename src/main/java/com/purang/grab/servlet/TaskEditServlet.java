package com.purang.grab.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

public class TaskEditServlet extends HttpServlet {

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
		String taskName=(String) req.getParameter("taskname");
		String taskConfigFilePath=TaskInfoUtils.getTaskConfigPath(taskName);
		

		StringBuffer sb=new StringBuffer();
		File configFile=new File(taskConfigFilePath);
		InputStreamReader fReader = new InputStreamReader(new FileInputStream(configFile),"UTF-8");
		BufferedReader reader = new BufferedReader(fReader);
		String line;
		while ((line = reader.readLine()) != null)
		{
			sb.append(line).append("\r\n");     
		}
		reader.close();
        PrintWriter out = resp.getWriter();  
        out.write(sb.toString());  
		//resp.sendRedirect("taskedit.jsp");
	}
	
}
