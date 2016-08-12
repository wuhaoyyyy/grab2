package com.purang.grab.rule;

import java.util.List;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;

public class FieldRule extends AbstractRule{

	protected String cutPrefix=null;
	protected String cutPostfix=null;
	
	public String getCutPrefix() {
		return cutPrefix;
	}

	public void setCutPrefix(String cutPrefix) {
		this.cutPrefix = cutPrefix;
	}

	public String getCutPostfix() {
		return cutPostfix;
	}

	public void setCutPostfix(String cutPostfix) {
		this.cutPostfix = cutPostfix;
	}

	@Override
	public Object getRuleResult(Page page) {
		switch(ruleResultType){
			case "1":
				return CommonUtils.getSelectorResult(page, this.ruleExpression, this.ruleType,this.cutPrefix,this.cutPostfix);
			case "2":
				return CommonUtils.getSelectorListResult(page, this.ruleExpression, this.ruleType,this.cutPrefix,this.cutPostfix);
			case "3":
				return CommonUtils.getSelectorLinkResult(page, this.ruleExpression, this.ruleType);
			case "4":
				return CommonUtils.getSelectorLinkListResult(page, this.ruleExpression, this.ruleType);
		}
		return null;
	}
}
