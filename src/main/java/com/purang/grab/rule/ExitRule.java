package com.purang.grab.rule;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

public class ExitRule extends FieldRule {

	public Object getRuleResult(Page page) {
		return super.getRuleResult(page);
	}
	
    /*
     * 返回list开始移除index，-1不退出
     */
	public int getExit(Page page){
		Object obj=this.getRuleResult(page);
		if(obj==null||obj.toString().equals("")) return 0;
		else return -1;
	}
	

}
