package com.purang.grab.rule;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.selector.Html;

public abstract class AbstractRule implements Rule {
	
	protected String title=null;//无用
	protected String name=null;//无用
	protected String ruleType=null;//css xpath
	protected String ruleExpression=null;//表达式
	protected String ruleResultType=null;//结果 1--string string 2--string list 3--link string 4--link list
	protected String field=null;//导出的field
	protected String fieldtype=null;//无用
	
	
	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getRuleType() {
		return ruleType;
	}



	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}



	public String getRuleExpression() {
		return ruleExpression;
	}



	public void setRuleExpression(String ruleExpression) {
		this.ruleExpression = ruleExpression;
	}



	public String getRuleResultType() {
		return ruleResultType;
	}



	public void setRuleResultType(String ruleResultType) {
		this.ruleResultType = ruleResultType;
	}



	public String getField() {
		return field;
	}



	public void setField(String field) {
		this.field = field;
	}



	public String getFieldtype() {
		return fieldtype;
	}



	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}



	@Override
	public Object getRuleResult(Html html) {
		switch(ruleResultType){
			case "1":
				return CommonUtils.getSelectorResult(html, this.ruleExpression, this.ruleType);
			case "2":
				return CommonUtils.getSelectorListResult(html, this.ruleExpression, this.ruleType);
			case "3":
				return CommonUtils.getSelectorLinkResult(html, this.ruleExpression, this.ruleType);
			case "4":
				return CommonUtils.getSelectorLinkListResult(html, this.ruleExpression, this.ruleType);
		}
		return null;
	}

	
}
