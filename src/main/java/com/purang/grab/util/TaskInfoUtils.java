package com.purang.grab.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.task.Task;

public class TaskInfoUtils {
	
	public static String ROOTPATH_PROPERTY="grab.root";
	
	public static List<Task> getTaskList(){
		List<Task> taskList=new ArrayList<Task>();
		String taskDictStr=System.getProperty(ROOTPATH_PROPERTY)+"tasks\\";
		File taskDict=new File(taskDictStr);
		File[] allTask=taskDict.listFiles();
		for(File file:allTask){
			String taskName=file.getName();
			String taskConfigPath=taskDictStr+taskName+"\\"+taskName+"-beans.xml";
			Resource resource = new FileSystemResource(taskConfigPath);
			DefaultListableBeanFactory factory= new DefaultListableBeanFactory(); 
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory); 
			reader.loadBeanDefinitions(resource);  
			Task commonTask=(Task) factory.getBean("task"); 
			taskList.add(commonTask);
		}
		return taskList;
	}
	
	public static BeanFactory getTaskFactory(String taskName){
		List<Task> taskList=new ArrayList<Task>();
		String taskDictStr=System.getProperty(ROOTPATH_PROPERTY)+"tasks\\";
		File taskDict=new File(taskDictStr);
		File[] allTask=taskDict.listFiles();
		for(File file:allTask){
			String fileName=file.getName();
			if(!fileName.equals(taskName)) continue;
			String taskConfigPath=taskDictStr+taskName+"\\"+taskName+"-beans.xml";
			Resource resource = new FileSystemResource(taskConfigPath);
			DefaultListableBeanFactory factory= new DefaultListableBeanFactory(); 
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory); 
			reader.loadBeanDefinitions(resource);  
			return factory;
		}
		return null;
	}

}
