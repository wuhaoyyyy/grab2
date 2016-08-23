package com.purang.grab.processor.custom;

import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import com.purang.grab.processor.AbstractProcessor;
import com.purang.grab.util.StringUtils;

public class ChinaMoneyB2Processor extends AbstractProcessor {

	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		
		if(page.getUrl().toString().indexOf("bondName")>0){
			List<String> listTitle=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td[1]/a[1]/@title").all();
			List<String> listTargetUrl=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td[1]/a[2]").links().all();
			result.put("title2", listTitle);
			result.put("linkurl2", listTargetUrl);
			if(listTargetUrl==null||listTargetUrl.size()==0){
				List<String> listLinkUrl=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td[1]/a[1]").links().all();
				result.put("linkurl2", listLinkUrl);
			}

			//没有download的要处理
			if(listTitle.size()!=listTargetUrl.size()){
				for(int i=0;i<listTitle.size();i++){
					String r=page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td[1]/a[2]").get();
					if(!StringUtils.isNotBlank(r)){
						listTargetUrl.add(i, page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td[1]/a[1]").links().get());
					}
				}
				result.put("linkurl2", listTargetUrl);
			}
			
			
		}
		else{
			Selectable selectable=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links();
			if(selectable.toString()!=null&&selectable.toString().indexOf("download")>0){
				String fileDownloadUrl=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links().toString();
				String fileDownloadName=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a/font/text()").toString();
				result.put("linkurl2", fileDownloadUrl);
				result.put("title2", fileDownloadName);
			}
		}
		page.putField("result", result);
			
	}
}
