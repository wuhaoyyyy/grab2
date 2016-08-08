package com.purang.grab.processor;

import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;

import com.purang.grab.rule.ExitRule;
import com.purang.grab.rule.FieldRule;

public abstract class Processor {

	protected int level = 0;
	protected Map<String, String> defaultValue;
	protected List<FieldRule> fieldRuleList;
	protected List<FieldRule> gotoLinkList;
	protected ExitRule exitRule;

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

	public List<FieldRule> getGotoLinkList() {
		return gotoLinkList;
	}

	public void setGotoLinkList(List<FieldRule> gotoLinkList) {
		this.gotoLinkList = gotoLinkList;
	}
	
	public ExitRule getExitRule() {
		return exitRule;
	}

	public void setExitRule(ExitRule exitRule) {
		this.exitRule = exitRule;
	}

	public abstract void process(Page page);

}
