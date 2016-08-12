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
	protected int exitPos;
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

	/*
	 * 处理page的result以及defaultValue，然后加入该process的defaultValue，再加入该process的field处理。 生成新的result
	 */
	@SuppressWarnings("unchecked")
	@Override
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
		if(fieldRuleList!=null){
			boolean allempty=true;
			for(FieldRule fieldRule:fieldRuleList){
				Object value=fieldRule.getRuleResult(page);
				result.put(fieldRule.getField(),value);
				if(value instanceof List &&((List) value).size()>0) allempty=false;
				
			}
			if(allempty) {
				taskLog.info("exit-nodata:"+page.getRequest().getUrl());
				return;
			}
		}		
		gotoProcess(page,result);

	}
	
	public abstract void gotoProcess(Page page,Map<String, Object> result);

}
