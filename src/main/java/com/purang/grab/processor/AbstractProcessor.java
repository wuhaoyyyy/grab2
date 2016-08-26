package com.purang.grab.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.ExitRule;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;
/*
 * 得到html文本后的处理类，包含了共用的处理
 * 
 */
public abstract class AbstractProcessor implements Processor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	protected int level = 0;
	protected Map<String, String> defaultValue;
	protected List<FieldRule> fieldRuleList;
	protected ExitRule exitRule;
	protected FieldRule maxPagerRule;
	protected int exitPos=-1;
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public Map<String, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Map<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<FieldRule> getFieldRuleList() {
		return fieldRuleList;
	}

	public void setFieldRuleList(List<FieldRule> fieldRuleList) {
		this.fieldRuleList = fieldRuleList;
	}

	public ExitRule getExitRule() {
		return exitRule;
	}

	public void setExitRule(ExitRule exitRule) {
		this.exitRule = exitRule;
	}

	public FieldRule getMaxPagerRule() {
		return maxPagerRule;
	}

	public void setMaxPagerRule(FieldRule maxPagerRule) {
		this.maxPagerRule = maxPagerRule;
	}

	/*
	 * 处理page的result以及defaultValue，然后加入该process的defaultValue，再加入该process的field处理。 生成新的result
	 */
	@SuppressWarnings("unchecked")
	public void process(Page page){
		Map<String, Object> result=new HashMap<String, Object>();
		if(page.getStatusCode()==404) {
			taskLog.info("exit-404:"+page.getRequest().getUrl());
			return;
		}		
		if(page.getRequest().getExtra("result")!=null){
			result=(Map) page.getRequest().getExtra("result");		
		}
		if(page.getRequest().getExtra("defaultValue")!=null){
			result.putAll((Map) page.getRequest().getExtra("defaultValue"));			
		}
		if(this.defaultValue!=null){
			result.putAll(this.defaultValue);
		}	
		Map<String,String> headerMap=page.getResultItems().get("responseHeader");
		if(headerMap!=null&&headerMap.get("Content-Disposition")==null){
			if(fieldRuleList!=null){
				boolean allempty=true;
				for(FieldRule fieldRule:fieldRuleList){
					Object value=fieldRule.getRuleResult(page);
					result.put(fieldRule.getField(),value);
					if(value!=null&&(value instanceof List &&((List) value).size()>0)) {
						allempty=false;
					}
					else if(value instanceof String && !value.equals("")){
						allempty=false;
					}
					
				}
				if(allempty) {
					taskLog.info("exit-nodata:"+page.getRequest().getUrl());
					return;
				}
			}	
		}
		
		
		if(exitRule!=null) {
			this.exitPos=exitRule.getExit(page);
			if(exitPos>-1) {
				if(exitPos==0) return;
				CommonUtils.mapValueToList(result);
				for(String key:result.keySet()){
					List list=(List) result.get(key);
					list=list.subList(0, exitPos);
					result.put(key, list);
				}
				taskLog.info("exit-exitrule:"+page.getRequest().getUrl());		
			}
		}
		else{
			exitPos=-1;
		}
		
		if(exitPos>-1){
		}
		else{
			Request request=page.getRequest();
			if(request instanceof PagerRequest){
				PagerRequest pagerRequest=(PagerRequest)request;
				//request里判断最后一页
				pagerRequest=pagerRequest.getNextPager(page);
				if(pagerRequest!=null){
					//processor里判断最后一页
					if(maxPagerRule!=null){
						Object value=maxPagerRule.getRuleResult(page);
						if(value==null) {
							System.out.println(page.getRequest().getUrl());
						}
						if(pagerRequest.getStart()-pagerRequest.getTolerance()>Integer.valueOf(value.toString())){
							pagerRequest=null;
						}
					}
				}
				if(pagerRequest!=null){
					page.addTargetRequest(pagerRequest);
				}
				else{
					taskLog.info("exit-lastpage:"+page.getRequest().getUrl());
				}
			}	
		}
		page.putField("result",result);	
		gotoProcess(page,result);

	}
	
	public abstract void gotoProcess(Page page,Map<String, Object> result);

}
