package com.purang.grab.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.UrlUtils;

public class BondAnnounceProcessor2 extends Processor {

	@Override
	public void process(Page page) {
		super.process(page);
		if(!String.valueOf(getLevel()).equals("1")) return;
		
		//上一级的结果
		HashMap<String, Object> result=(HashMap<String, Object>) page.getRequest().getExtra("lastlevelresult");
		if(page.getUrl().toString().indexOf("bondName")>0){

			List<String> listTitle=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/a[1]/text()").all();
			List<String> listTargetUrl=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/a[1]").links().all();
			List<String> listFileDownloadUrl=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/a[2]").links().all();
			listTitle.remove(0);
			listTargetUrl.remove(0);
			page.putField("type", "depo");
			result.put("announceTitle2", listTitle);
			result.put("link2", listTargetUrl);
			result.put("fileDownloadUrl", listFileDownloadUrl);
		}
		else{
			Selectable selectable=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links();
			if(selectable.toString()!=null&&selectable.toString().indexOf("download")>0){
				String fileDownloadUrl=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links().toString();
				result.put("fileDownloadUrl", fileDownloadUrl);
			}
			page.putField("type", "report");
		}
		
		
		page.putField("result", result);
		
		
	}

}
