package com.purang.grab.rule;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.selector.Html;

import com.purang.grab.util.CommonUtils;

/*
 * 根据总页数 构造所有url
 */
public class UrlPagerRule extends PagerRule {

	@Override
	public String getNextPage(Html html){
		//int totalPageCount=CommonUtils.getSelectorLinkResult(html, rule, type);
		return CommonUtils.getSelectorLinkResult(html, rule, type);
	}
	@Override
	public List<String> getNextPageList(Html html){
		List<String> r=new ArrayList<>();
		for(int i=2;i<21;i++){
			String s="http://www.shclearing.com/xxpl/fxpl/mtn/index_"+i+".html";
			r.add(s);
		}
		return r;
	}
}
