package com.purang.grab.processor;

import java.util.HashMap;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.FieldRule;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

public class CommonProcessor extends Processor{

	@Override
	public void process(Page page) {
		if(page.getStatusCode()==404) return;
		HashMap<String, Object> result=new HashMap<String, Object>();
		for(FieldRule fieldRule:fieldRuleList){
			result.put(fieldRule.getField(),fieldRule.getRuleResult(page.getHtml()));
		}
		page.putField("result",result);
		
		
		//判断是否有退出
		if(exitRule!=null) {
			Boolean exit=exitRule.getExit(page.getHtml());
			if(exit) return;
		}

		Request request=page.getRequest();
		if(request instanceof PagerRequest){
			PagerRequest pagerRequest=(PagerRequest)request;
			page.addTargetRequest(pagerRequest.getNextPager());
		}
	}
	
	
}
