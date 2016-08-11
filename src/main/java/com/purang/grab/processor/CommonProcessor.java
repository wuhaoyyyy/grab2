package com.purang.grab.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

public class CommonProcessor extends AbstractProcessor{
	private static Log taskLog = LogFactory.getLog("grabtask");

	@SuppressWarnings("unchecked")
	@Override
	public void gotoProcess(Page page) {
		if(exitRule!=null) {
			int exit=exitRule.getExit(page);
			if(exit>-1) {
				for(String key:result.keySet()){
					List list=(List) result.get(key);
					list=list.subList(0, exit);
					result.put(key, list);
				}
				taskLog.info("exit-exitrule:"+page.getRequest().getUrl());
				page.putField("result",result);			
				return;
			}
		}
		page.putField("result",result);
		
		Request request=page.getRequest();
		if(request instanceof PagerRequest){
			PagerRequest pagerRequest=(PagerRequest)request;
			page.addTargetRequest(pagerRequest.getNextPager());
		}		
		
	}
	
	
}
