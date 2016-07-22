package com.purang.grab.rule;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.selector.Html;

public abstract class AbstractRule implements Rule {
	
	protected String title=null;
	protected String name=null;
	protected String type=null;
	protected String rule=null;
	protected String field=null;
	protected String fieldtype=null;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
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
	public String getRuleResult(Html html) {
//		switch(fieldtype){
//			case "result":
//				return CommonUtils.getSelectorResult(html, this.name, this.type);
//			case "resultlist":
//				return CommonUtils.getSelectorListResult(html, this.name, this.type);
//			case "link":
//				return CommonUtils.getSelectorLinkResult(html, this.name, this.type);
//			case "linklist":
//				return CommonUtils.getSelectorLinkListResult(html, this.name, this.type);
//		}
		return CommonUtils.getSelectorResult(html, this.rule, this.type);
	}

	
}
