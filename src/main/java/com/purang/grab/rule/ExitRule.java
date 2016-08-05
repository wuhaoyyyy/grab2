package com.purang.grab.rule;

import us.codecraft.webmagic.selector.Html;

public class ExitRule extends AbstractRule {

	public Object getRuleResult(Html html) {
		return super.getRuleResult(html);
	}
	
    /*
     * 返回list开始移除index，-1不退出
     */
	public int getExit(Html html){
		Object obj=this.getRuleResult(html);
		if(obj==null||obj.toString().equals("")) return 0;
		else return -1;
	}
	

}
