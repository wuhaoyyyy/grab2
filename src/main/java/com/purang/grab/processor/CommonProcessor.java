package com.purang.grab.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

public class CommonProcessor extends Processor{
	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void process(Page page) {
		if(page.getStatusCode()==404) {
			taskLog.info("exit-404:"+page.getRequest().getUrl());
			return;
		}

		HashMap<String, Object> result=new HashMap<String, Object>();
		//json处理
		if(isJson){
			page.getJson().jsonPath("").all();
			
		}
		else{
			for(FieldRule fieldRule:fieldRuleList){
				result.put(fieldRule.getField(),fieldRule.getRuleResult(page.getHtml()));
			}
			CommonUtils.mapValueToList(result);
			page.putField("result",result);
		}
		//判断是否有退出
		if(exitRule!=null) {
			int exit=exitRule.getExit(page.getHtml());
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
		
		Request request=page.getRequest();
		if(request instanceof PagerRequest){
			PagerRequest pagerRequest=(PagerRequest)request;
			page.addTargetRequest(pagerRequest.getNextPager());
		}
		
		
		
		
		
		
		
	}
	
	
}
