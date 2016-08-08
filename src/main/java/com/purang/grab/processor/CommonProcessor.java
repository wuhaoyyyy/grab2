package com.purang.grab.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if(page.getRequest().getExtra("result")!=null){
			result=(HashMap<String, Object>) page.getRequest().getExtra("result");		
		}
		for(FieldRule fieldRule:fieldRuleList){
			result.put(fieldRule.getField(),fieldRule.getRuleResult(page));
		}
		if(page.getRequest().getExtra("defaultValue")!=null){
			result.putAll((Map<String, String>) page.getRequest().getExtra("defaultValue"));			
		}	
		int count=CommonUtils.mapValueToList(result);
		
		//gotolink处理

		if(gotoLinkList!=null){		
			//默认gotolink和result列表一一对应
			for(FieldRule linkRule:gotoLinkList){
				HashMap<String, Object> linkresult=new HashMap<String, Object>();
				linkresult.put(linkRule.getField(),linkRule.getRuleResult(page));
				CommonUtils.mapValueToList(linkresult);
				List<String> linkList=(List<String>) linkresult.get(linkRule.getField());
				int i=0;
				for(String link:linkList){
					Request request=new Request();
					request.putExtra("level", Integer.parseInt(page.getRequest().getExtra("level").toString())+1);
					request.putExtra("result", CommonUtils.getSingleMap(result, i));
					request.setUrl(link);
					page.addTargetRequest(request);
					i++;
				}
			}
		}
		
		//没有gotolink才交给CommonPipeline处理，
		if(gotoLinkList==null||gotoLinkList.size()<1){
			page.putField("result",result);			
		}
		
		//判断是否有退出
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
		if(count<=0) {
			taskLog.info("exit-nodata:"+page.getRequest().getUrl());
			return;
		}
		Request request=page.getRequest();
		if(request instanceof PagerRequest){
			PagerRequest pagerRequest=(PagerRequest)request;
			page.addTargetRequest(pagerRequest.getNextPager());
		}		
		
	}
	
	
}
