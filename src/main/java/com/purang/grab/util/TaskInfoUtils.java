package com.purang.grab.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.task.Task;

public class TaskInfoUtils {

	private static Log taskLog = LogFactory.getLog("grabtask");
	public static HashMap<String, HashMap<String,BeanFactory>> beanTaskGroupFactoryMap=new HashMap<String, HashMap<String,BeanFactory>>();
	public static HashMap<String, BeanFactory> beanFactoryMap=new HashMap<>();
	public static String ROOTPATH_PROPERTY="grab.root";
	public static String taskConfigDict=System.getProperty(ROOTPATH_PROPERTY)+"tasks\\";


	/*
	 * 任务组    根目录下xml文件不可和目录同名，同名只读取目录
	 */
	public static List<String> getTaskGroupList(){
		List<String> taskGroupList=new ArrayList<String>();
		File taskDict=new File(taskConfigDict);
		File[] allFile=taskDict.listFiles();
		for(File file:allFile){
			String fileName=file.getName();
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		    //String fileNameNt=fileName.substring(fileName.lastIndexOf("."));
			if(file.isDirectory()){
				taskGroupList.add(file.getName());
			}
			else if(fileType.equals(".xml")){
				//if(!taskGroupList.contains(fileNameNt)){
				taskGroupList.add(fileName);
				//}
			}
		}
		return taskGroupList;
	}
	

	/*
	 * 任务组下任务
	 */
	public static List<Task> getTaskList(String taskGroup){
		if(taskGroup==null||taskGroup.equals("")) return null;
		List<Task> taskList=new ArrayList<Task>();
		if(taskGroup.endsWith(".xml")){
			String taskConfigFileName=taskGroup;
			Task task=getTask("",taskConfigFileName);
			taskList.add(task);
			return taskList;
		}
		
		File taskDict=new File(taskConfigDict+taskGroup);
		File[] allTask=taskDict.listFiles();
		for(File file:allTask){
			String taskConfigFileName=file.getName();
			Task task=getTask(taskGroup,taskConfigFileName);
			taskList.add(task);
		}
		return taskList;
	}
	
	/*
	 * 获取 task
	 */
	public static Task getTask(String taskGroup,String taskConfigFileName){
		BeanFactory factory= getTaskFactory(taskGroup,taskConfigFileName);  
		Task commonTask=(Task) factory.getBean("task"); 
		Scheduler scheduler = (Scheduler) factory.getBean("scheduler"); 
		try {
			if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
				commonTask.setStatus("started");
			}
			else if(!scheduler.isStarted()||scheduler.isShutdown()){
				commonTask.setStatus("stoped");
			}
			
		} catch (SchedulerException e) {
			taskLog.error(e.toString());
			e.printStackTrace();
		}
		return commonTask;
	}
	
	/*
	 * 获取配置文件的task工厂，已经实例化的直接返回。  保存在beanFactoryMap
	 */
	public static BeanFactory getTaskFactory(String taskGroup,String taskConfigFileName){
		File taskDict=new File(taskConfigDict+taskGroup);
		File[] allTask=taskDict.listFiles();
		for(File file:allTask){
			String fileName=file.getName();
			if(!fileName.equals(taskConfigFileName)) continue;
			String taskConfigPath=getTaskConfigPath(taskGroup,taskConfigFileName);
			if(beanFactoryMap.get(taskConfigPath)==null){
				BeanFactory factory=getBeanFactory(taskConfigPath);
				beanFactoryMap.put(taskConfigPath, factory);
				return factory;
			}
			else{
				return beanFactoryMap.get(taskConfigPath);
			}
		}
		return null;
	}

	/*
	 * 获取配置文件的task工厂，启动时重新实例化
	 */
	public static BeanFactory getTaskFactoryNew(String taskGroup,String taskConfigFileName){
		File taskDict=new File(taskConfigDict);
		File[] allTask=taskDict.listFiles();
		for(File file:allTask){
			String fileName=file.getName();
			if(!fileName.equals(taskConfigFileName)) continue;
			String taskConfigPath=getTaskConfigPath(taskGroup,taskConfigFileName);
			BeanFactory factory=getBeanFactory(taskConfigPath);
			beanFactoryMap.put(taskConfigPath, factory);
			return factory;
		}
		return null;
	}

	
	/*
	 * 获取bean配置文件路径
	 */
	public static String getTaskConfigPath(String taskGroup,String taskConfigFileName){
		return taskConfigDict+taskGroup+"\\"+taskConfigFileName;
	}
	
	public static BeanFactory getBeanFactory(String xmlpath){
		Resource resource = new FileSystemResource(xmlpath);
		DefaultListableBeanFactory factory= new DefaultListableBeanFactory(); 
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory); 
		reader.loadBeanDefinitions(resource);  
		return factory;
	}
//	
//	
//	/*
//	 * 获取bean配置文件路径
//	 */
//	public static String getTaskConfigPath(String taskName){
//		return taskConfigDict+taskName+"\\"+taskName+"-beans.xml";
//	}
//	
//	
//	
//	
//	/*
//	 * 获取所有task及状态
//	 */
//	public static List<Task> getTaskList(){
//		List<Task> taskList=new ArrayList<Task>();
//		File taskDict=new File(taskConfigDict);
//		File[] allTask=taskDict.listFiles();
//		for(File file:allTask){
//			String taskName=file.getName();
//			BeanFactory factory= getTaskFactory(taskName);  
//			Task commonTask=(Task) factory.getBean("task"); 
//			taskList.add(commonTask);
//			Scheduler scheduler = (Scheduler) factory.getBean("scheduler"); 
//			try {
//				if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
//					commonTask.setStatus("started");
//				}
//				else if(!scheduler.isStarted()||scheduler.isShutdown()){
//					commonTask.setStatus("stoped");
//				}
//				
//			} catch (SchedulerException e) {
//				taskLog.error(e.toString());
//				e.printStackTrace();
//			}
//		}
//		return taskList;
//	}
}
