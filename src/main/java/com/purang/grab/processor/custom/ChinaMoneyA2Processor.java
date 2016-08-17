package com.purang.grab.processor.custom;

import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import com.purang.grab.processor.AbstractProcessor;

public class ChinaMoneyA2Processor extends AbstractProcessor {

	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		Selectable selectable=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links();
		if(selectable.toString()!=null&&selectable.toString().indexOf("download")>0){
			String fileDownloadUrl=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links().toString();
			String fileDownloadName=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a/font/text()").toString();
			result.put("linkurl2", fileDownloadUrl);
			result.put("title2", fileDownloadName);
		}
		
		page.putField("result", result);
			
	}
}
