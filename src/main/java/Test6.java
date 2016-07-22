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
		ApplicationContext ac = ApplicationContextUtils.getInstance() ;
		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\tasks\\task\\task-beans.xml");
		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
		Scheduler scheduler = (Scheduler) taskBeanFactory.getBean("scheduler"); 
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

}