package com.purang.grab.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.annotation.ThreadSafe;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.task.Task;

@NotThreadSafe
public class TaskInfoUtils {

	private static Log taskLog = LogFactory.getLog("grabtask");
	public static HashMap<String, HashMap<String,BeanFactory>> beanTaskGroupFactoryMap=new HashMap<String, HashMap<String,BeanFactory>>();
	
	public static String ROOTPATH_PROPERTY="grab.root";
	public static String TASK_CONFIG_DICT=System.getProperty(ROOTPATH_PROPERTY)+"tasks\\";

	public static HashMap<String, BeanFactory> beanFactoryMap=new LinkedHashMap<>();//任务名 beanFactory 用来重新读取该任务的配置文件
	public static Map<String,Task> taskPathMap=new LinkedHashMap<>();//配置文件路径  任务 
	public static List<String> taskGroupList=new ArrayList<String>();
	public static Map<String,List<Task>> taskGroupMap=new HashMap<>();
	
	/*
	 * 初始化所有任务
	 */
	public static void initAllTask(){
		File taskDict=new File(TASK_CONFIG_DICT);
		File[] allFile=taskDict.listFiles();
		for(File file:allFile){
			String fileName=file.getName();
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
			if(file.isDirectory()){
				for(File subFile:file.listFiles()){
					String subFileName=subFile.getName();
					fileType = subFileName.substring(subFileName.lastIndexOf(".")+1,subFileName.length());
					if(fileType.equals("xml")){
						String taskConfigFilePath=TASK_CONFIG_DICT+fileName+"\\"+subFileName;
						BeanFactory beanFactory=CommonUtils.getBeanFactory(taskConfigFilePath);
						Task task=(Task)beanFactory.getBean("task");
						Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
//						if(task.getAutostart()){
//							try {
//								scheduler.start();
//							} catch (SchedulerException e) {
//								e.printStackTrace();
//							}
//						}
						
						setTaskStatus(beanFactory);
						taskPathMap.put(taskConfigFilePath, task);
						beanFactoryMap.put(task.getTaskName(),beanFactory);
					}
				}
			}
			else if(fileType.equals(".xml")){
				String taskConfigFilePath=TASK_CONFIG_DICT+fileName;
				BeanFactory beanFactory=CommonUtils.getBeanFactory(taskConfigFilePath);
				Task task=(Task)beanFactory.getBean("task");
				Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
				if(task.getAutostart()){
					try {
						scheduler.start();
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
				setTaskStatus(beanFactory);
				taskPathMap.put(taskConfigFilePath, task);
				beanFactoryMap.put(task.getTaskName(),beanFactory);
			}
		}
	}
	
	public static List<String> getTaskGroupList(){
		List<String> taskGroupList=new ArrayList<String>();
		File taskDict=new File(TASK_CONFIG_DICT);
		File[] allFile=taskDict.listFiles();
		for(File file:allFile){
			String fileName=file.getName();
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
			if(file.isDirectory()){
				taskGroupList.add(file.getName());
			}
			else if(fileType.equals(".xml")){
				taskGroupList.add(fileName);
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
		String taskGroupPath=TASK_CONFIG_DICT+taskGroup;

		for(String key: taskPathMap.keySet()){
			if(key.indexOf(taskGroupPath)>-1){
				Task task=taskPathMap.get(key);
				BeanFactory beanFactory=getTaskFactory(task.getTaskName());
				setTaskStatus(beanFactory);
				taskList.add(taskPathMap.get(key));
			}
		}
		return taskList;
	}
	
	public static Task getTask(String taskName){
		for(String key: taskPathMap.keySet()){
			Task task=taskPathMap.get(key);
			if(task.getTaskName().equals(taskName)){
				return task;
			}
		}
		return null;
	}
	
	public static BeanFactory getTaskFactory(String taskName){
		return beanFactoryMap.get(taskName);
	}
	
	/*
	 * 重新读取该任务的配置文件
	 */
	public static BeanFactory getTaskFactoryNew(String taskName){
		BeanFactory beanFactory=null;
		String taskConfigFilePath=null;
		for(String key: taskPathMap.keySet()){
			Task task=taskPathMap.get(key);
			if(task.getTaskName().equals(taskName)){
				taskConfigFilePath=key;
				beanFactory=CommonUtils.getBeanFactory(taskConfigFilePath);
				task=(Task) beanFactory.getBean("task");
				setTaskStatus(beanFactory);
				taskPathMap.put(taskConfigFilePath, task);
				beanFactoryMap.put(taskName, beanFactory);
				return beanFactory;
			}
		}
		return beanFactory;
	}

	/*
	 * 获取任务配置文件
	 */
	public static String getTaskConfigPath(String taskName){
		String taskConfigFilePath=null;
		for(String key: taskPathMap.keySet()){
			Task task=taskPathMap.get(key);
			if(task.getTaskName().equals(taskName)){
				return key;
			}
		}
		return taskConfigFilePath;
	}
	

	/*
	 * 新建任务
	 */
	public static boolean createNewTask(String taskGroup,String taskName,String configFileStr){
		if(getTaskFactory(taskName)!=null) return false;
		String taskConfigPath=TASK_CONFIG_DICT+taskGroup+"\\"+taskName+".xml";
		File configFile=new File(taskConfigPath);
		boolean dircreate=false;
		boolean filecreate=false;
		if (!configFile.getParentFile().exists()) {  
            if (configFile.getParentFile().mkdirs()) {  
            	dircreate=true;
            }  
        }  
        if(!configFile.exists()) {  
            try {  
            	configFile.createNewFile();  
            	filecreate=true;
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
		try {
			configFile.createNewFile();
			FileWriter fw =  new FileWriter(configFile);
			fw.write(configFileStr);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			BeanFactory beanFactory=CommonUtils.getBeanFactory(taskConfigPath);
			Task task=(Task) beanFactory.getBean("task");
			setTaskStatus(beanFactory);
			taskPathMap.put(taskConfigPath, task);
			beanFactoryMap.put(taskName, beanFactory);
		}
		catch(Exception e){
			if(filecreate){
				configFile.delete();
			}
			if(dircreate){
				configFile.getParentFile().delete();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/*
	 * 设置任务状态
	 */
	public static void setTaskStatus(BeanFactory beanFactory){
		Task task=(Task) beanFactory.getBean("task");
		Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
		try {
			if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
				task.setStatus("started");
			}
			else if(!scheduler.isStarted()||scheduler.isShutdown()){
				task.setStatus("stoped");
			}
			
		} catch (SchedulerException e) {
			taskLog.error(e.toString());
		}
	}
	/*
	 * 获取任务状态
	 */
	public static void getTaskStatus(BeanFactory beanFactory){
		Task task=(Task) beanFactory.getBean("task");
		Scheduler scheduler = (Scheduler) beanFactory.getBean("scheduler"); 
		try {
			if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
				task.setStatus("started");
			}
			else if(!scheduler.isStarted()||scheduler.isShutdown()){
				task.setStatus("stoped");
			}
			
		} catch (SchedulerException e) {
			taskLog.error(e.toString());
		}
	}
	
	
	
	
	
	/*
	 * 获取 task   前台列表显示的是配置文件的taskname
	 */
//	public static Task getTask(String taskGroup,String taskConfigFileName){
//		BeanFactory factory= getTaskFactory(taskGroup,taskConfigFileName);  
//		Task commonTask=(Task) factory.getBean("task"); 
//		Scheduler scheduler = (Scheduler) factory.getBean("scheduler"); 
//		try {
//			if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
//				commonTask.setStatus("started");
//			}
//			else if(!scheduler.isStarted()||scheduler.isShutdown()){
//				commonTask.setStatus("stoped");
//			}
//			
//		} catch (SchedulerException e) {
//			taskLog.error(e.toString());
//			e.printStackTrace();
//		}
//		return commonTask;
//	}
	
	
//	public void s(){
//		File taskDict=new File(taskConfigDict);
//		File[] allFile=taskDict.listFiles();
//		for(File file:allFile){
//			String fileName=file.getName();
//			String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
//			if(file.isDirectory()){
//				taskGroupList.add(file.getName());
//			}
//			else if(fileType.equals(".xml")){
//				taskGroupList.add(fileName);
//			}
//		}
//		
//		if(taskGroupList==null||taskGroupList.size()<0) return;
//		for(String taskGroupName:taskGroupList){
//			List<Task> taskList=new ArrayList<Task>();
//			if(taskGroupName.endsWith(".xml")){
//				Task task=getTask(taskGroupName,taskConfigFileName);
//				taskList.add(task);
//			}
//			else{
//				File taskDict=new File(taskConfigDict+taskGroup);
//				File[] allTask=taskDict.listFiles();
//				for(File file:allTask){
//					String taskConfigFileName=file.getName();
//					Task task=getTask(taskGroup,taskConfigFileName);
//					taskList.add(task);
//				}
//			}
//		}
//	}
//	
//	
//
//	/*
//	 * 获取配置文件的task工厂，已经实例化的直接返回。  保存在beanFactoryMap
//	 */
//	public static BeanFactory getTaskFactory(String taskGroup,String taskConfigFileName){
//		File taskDict=new File(taskConfigDict+taskGroup);
//		File[] allTask=taskDict.listFiles();
//		for(File file:allTask){
//			String fileName=file.getName();
//			if(!fileName.equals(taskConfigFileName)) continue;
//			String taskConfigPath=getTaskConfigPath(taskGroup,taskConfigFileName);
//			if(beanFactoryMap.get(taskConfigPath)==null){
//				BeanFactory factory=getBeanFactory(taskConfigPath);
//				beanFactoryMap.put(taskConfigPath, factory);
//				return factory;
//			}
//			else{
//				return beanFactoryMap.get(taskConfigPath);
//			}
//		}
//		return null;
//	}
//	
//	
//	
//	
//	
//	
//	
//
//	/*
//	 * 任务组    根目录下xml文件不可和目录同名，同名只读取目录
//	 */
//	public static List<String> getTaskGroupList(){
//		List<String> taskGroupList=new ArrayList<String>();
//		File taskDict=new File(taskConfigDict);
//		File[] allFile=taskDict.listFiles();
//		for(File file:allFile){
//			String fileName=file.getName();
//			String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
//		    //String fileNameNt=fileName.substring(fileName.lastIndexOf("."));
//			if(file.isDirectory()){
//				taskGroupList.add(file.getName());
//			}
//			else if(fileType.equals(".xml")){
//				//if(!taskGroupList.contains(fileNameNt)){
//				taskGroupList.add(fileName);
//				//}
//			}
//		}
//		return taskGroupList;
//	}
//	
//
//	/*
//	 * 任务组下任务
//	 */
//	public static List<Task> getTaskList(String taskGroup){
//		if(taskGroup==null||taskGroup.equals("")) return null;
//		List<Task> taskList=new ArrayList<Task>();
//		if(taskGroup.endsWith(".xml")){
//			String taskConfigFileName=taskGroup;
//			Task task=getTask("",taskConfigFileName);
//			taskList.add(task);
//			return taskList;
//		}
//		
//		File taskDict=new File(taskConfigDict+taskGroup);
//		File[] allTask=taskDict.listFiles();
//		for(File file:allTask){
//			String taskConfigFileName=file.getName();
//			Task task=getTask(taskGroup,taskConfigFileName);
//			taskList.add(task);
//		}
//		return taskList;
//	}
//	
//	/*
//	 * 获取 task   前台列表显示的是配置文件的taskname，读取xml的是文件名    文件名要和taskname一致，map的key是xml文件全路径
//	 */
//	public static Task getTask(String taskGroup,String taskConfigFileName){
//		BeanFactory factory= getTaskFactory(taskGroup,taskConfigFileName);  
//		Task commonTask=(Task) factory.getBean("task"); 
//		Scheduler scheduler = (Scheduler) factory.getBean("scheduler"); 
//		try {
//			if(scheduler.isStarted()&&!scheduler.isInStandbyMode()){
//				commonTask.setStatus("started");
//			}
//			else if(!scheduler.isStarted()||scheduler.isShutdown()){
//				commonTask.setStatus("stoped");
//			}
//			
//		} catch (SchedulerException e) {
//			taskLog.error(e.toString());
//			e.printStackTrace();
//		}
//		return commonTask;
//	}
//	
//
//	/*
//	 * 获取配置文件的task工厂，启动时重新实例化
//	 */
//	public static BeanFactory getTaskFactoryNew(String taskGroup,String taskConfigFileName){
//		File taskDict=new File(taskConfigDict);
//		File[] allTask=taskDict.listFiles();
//		for(File file:allTask){
//			String fileName=file.getName();
//			if(!fileName.equals(taskConfigFileName)) continue;
//			String taskConfigPath=getTaskConfigPath(taskGroup,taskConfigFileName);
//			BeanFactory factory=getBeanFactory(taskConfigPath);
//			beanFactoryMap.put(taskConfigPath, factory);
//			return factory;
//		}
//		return null;
//	}

	
	/*
	 * 获取bean配置文件路径
	 */
//	public static String getTaskConfigPath(String taskGroup,String taskConfigFileName){
//		return taskConfigDict+taskGroup+"\\"+taskConfigFileName;
//	}
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
