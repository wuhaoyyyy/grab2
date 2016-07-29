package com.purang.grab.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;

import com.purang.grab.rule.ExitRule;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.rule.PagerRule;

public abstract class Processor {

	protected int level = 0;
	protected Map<String, String> defaultValue;
	protected List<FieldRule> fieldRuleList;
	protected List<FieldRule> urlList;
	protected ExitRule exitRule;
	protected PagerRule pageRule;

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

	public List<FieldRule> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<FieldRule> urlList) {
		this.urlList = urlList;
	}

	public PagerRule getPageRule() {
		return pageRule;
	}

	public void setPageRule(PagerRule pageRule) {
		this.pageRule = pageRule;
	}
	
	public ExitRule getExitRule() {
		return exitRule;
	}

	public void setExitRule(ExitRule exitRule) {
		this.exitRule = exitRule;
	}

	public void process(Page page){
		Object level=page.getRequest().getExtra("level");
		page.putField("level",level);//交给pipeline处理
		this.setLevel((Integer)level);
	}

}
