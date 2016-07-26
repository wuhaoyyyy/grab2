package com.purang.grab.pipeline;

import java.util.HashMap;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.ApplicationContext;

import com.purang.grab.entity.BondAnnouncement;
import com.purang.grab.service.BondAnnouncementService;
import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.DateUtils;
import com.purang.grab.util.FtpUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class BondAnnouncePipeline extends AbstractPipeline{

	@Override
	public void process(ResultItems resultItems, Task task) {
		Object level=resultItems.get("level");
		if(!level.toString().equals("1")) return;
		final ApplicationContext ac = ApplicationContextUtils.getInstance() ;
		
		HashMap<String, Object> result=resultItems.get("result");
		//日期 title1 title2唯一
		String type=resultItems.get("type").toString();
		if(type.equals("report")){
			setTotalEntityMap(result);
			BondAnnouncement bondAnnouncement=(BondAnnouncement) getEntity(result);
			bondAnnouncement.preInsert();
			bondAnnouncement.setAttachment("ftp://"+FtpUtils.ftpserver+"/"+bondAnnouncement.getFt()+".pdf");
			bondAnnouncement.setAnnounceDate(DateUtils.getString(bondAnnouncement.getAnnounceDate(),"yyyy-MM-dd","yyyyMMdd"));
			BondAnnouncementService service = (BondAnnouncementService) ac.getBean("bondAnnouncementService");
        	service.insert(bondAnnouncement);
        	String fileDownloadUrl=result.get("fileDownloadUrl").toString();
        	downloadFileToFtpGet(fileDownloadUrl,String.valueOf(bondAnnouncement.getFt()+".pdf"));
		}
		else if(type.equals("depo")){
			List<String> announceTitle2=(List<String>) result.get("announceTitle2");
			List<String> link2=(List<String>) result.get("link2");
			List<String> fileDownloadUrl=(List<String>) result.get("fileDownloadUrl");
			for(int i=0;i<announceTitle2.size();i++){
				HashMap<String, Object> single=new HashMap<String, Object>();
				single=result;
				single.put("announceTitle2", announceTitle2.get(i));
				single.put("link2", link2.get(i));
				setTotalEntityMap(single);
				BondAnnouncement bondAnnouncement=(BondAnnouncement) getEntity(single);
				bondAnnouncement.preInsert();
				bondAnnouncement.setAttachment("ftp://"+FtpUtils.ftpserver+"/"+bondAnnouncement.getFt()+".pdf");
				bondAnnouncement.setAnnounceDate(DateUtils.getString(bondAnnouncement.getAnnounceDate(),"yyyy-MM-dd","yyyyMMdd"));
				BondAnnouncementService service = (BondAnnouncementService) ac.getBean("bondAnnouncementService");
				service.insert(bondAnnouncement);
	        	downloadFileToFtpGet(fileDownloadUrl.get(i),String.valueOf(bondAnnouncement.getFt()+".pdf"));
			}
		}
		
	}
}
