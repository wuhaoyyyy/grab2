import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.util.ApplicationContextUtils;


public class Test6 {

	public static void main(String[] args) {
//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债务融资工具注册信息\\债务融资工具注册信息-全量.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		
//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债务融资工具注册信息\\债务融资工具注册信息-每日.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		
//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-上海清算所\\上海清算所-发行披露-中期票据.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		

//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-上海清算所\\债券公告-上海清算所(不分20页)-每日.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		
//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-上海清算所\\债券公告-上海清算所(不分20页)-全部.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		
//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-中国债券信息网\\债券公告-中国债券信息网-国债-全部.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//		
//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-中国货币网\\债券公告-中国货币网-债券发行-全部.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}

//		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
//		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-中国货币网-每日\\债券公告-中国货币网-每日-流通上市、付息兑付、评级公告.xml");
//		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
//		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
//		try {
//			scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		
		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\src\\main\\webapp\\tasks\\债券公告-中国货币网-每日\\债券公告-中国货币网-每日-债券发行.xml");
		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
