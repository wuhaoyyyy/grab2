package com.purang.grab.processor;

import java.util.HashMap;
import java.util.List;

import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.UrlUtils;

public class ShClearingProcessor1  extends Processor {

	@Override
	public void process(Page page) {
		super.process(page);
		if(!String.valueOf(getLevel()).equals("0")) return;

		
		for(FieldRule urlRule:urlList){
			List<String> urls=CommonUtils.getSelectorLinkListResult(page.getHtml(), urlRule.getRule(), urlRule.getType());
			int i=0;
			for(String url:urls){
				Request request = null;
				request=new Request(UrlUtils.canonicalizeUrl(url, page.getRequest().getUrl()));
				request.putExtra("level", this.getLevel()+1);
				
				HashMap<String, Object> result=new HashMap<String, Object>();
				for(FieldRule fieldRule:fieldRuleList){
					String field=fieldRule.getField();
					List<String> re=CommonUtils.getSelectorListResult(page.getHtml(), fieldRule.getRule(), fieldRule.getType());
					result.put(field,re.get(i));
				}
				result.put(urlRule.getField(), request.getUrl());
				request.putExtra("lastlevelresult",result);//将本页获取的结果传递给下一级
				page.addTargetRequest(request);
				i++;
			}
		}
		
		

		//获取下一页
		if(pageRule==null) return;
		List<String> l=pageRule.getNextPageList(page.getHtml());
		for(String nextPage:l){
			Request request=new Request(UrlUtils.canonicalizeUrl(nextPage,page.getUrl().toString()));
			request.putExtra("level", this.getLevel());
			page.addTargetRequest(request);
		}

	}
}
