package com.purang.grab.rule;

import us.codecraft.webmagic.selector.Html;

public class ExitRule extends AbstractRule {

	public String getRuleResult(Html html) {
		return super.getRuleResult(html);
	}
	
	//默认存在不为空即不退出
	public Boolean getExit(Html html){
		if(this.getRuleResult(html).equals("")) return true;
		else return false;
	}
	

}
