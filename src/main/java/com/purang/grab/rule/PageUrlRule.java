package com.purang.grab.rule;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

/**
 * url上可以找出翻页规律的（非url带参数）对应的处理类，（第一页不包含在规律内如何处理）
 * 
 * @see Rule
 */
public class PageUrlRule extends PageRule {

	ThreadLocal<String> s=new ThreadLocal<>();
	
	private String urlFirst;
	private String urlTemplet;
	private String startIndex;
	@Override
	public String getNextPage(Page page){
		page.getRequest().getUrl();
		this.start=String.valueOf(Integer.parseInt(start)+Integer.parseInt(pageSize));
		return url+"&"+startField+"="+start+"&"+pageSizeField+"="+pageSize;
	}
	
}
