package com.purang.grab.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

public class BondAnnounceProcessor1 extends Processor {

	public BondAnnounceProcessor1() {
	}
	@Override
	public void process(Page page) {
		super.process(page);
		if(!String.valueOf(getLevel()).equals("0")) return;
		//获取关联
		for(FieldRule urlRule:urlList){
			List<String> linkList=new ArrayList<String>();
			Html html=new Html(page.getRawText());
			List<Request> requestList=new ArrayList<Request>();
			List<String> jsUrlList=new ArrayList<String>();
			List<String> jsFunctionList=html.xpath(urlRule.getRule()).all();
			int i=0;
			for(String jsFunction:jsFunctionList){
				String jsUrl="";
				Request request = null;
				if(jsFunction.indexOf("viewDraft")>0){
					int codeBeginIndex=jsFunction.indexOf("'");
					int codeEndIndex=jsFunction.indexOf("'", codeBeginIndex+1);
					String code=jsFunction.substring(codeBeginIndex+1, codeEndIndex);
					jsUrl=new StringBuffer("/fe/Channel/47916?innerCode=").append(code).append("&bondInfoType=6").toString();
				}
				else if(jsFunction.indexOf("viewBond")>0){
					int codeBeginIndex=jsFunction.indexOf("'");
					int codeEndIndex=jsFunction.indexOf("'", codeBeginIndex+1);
					String code=jsFunction.substring(codeBeginIndex+1, codeEndIndex);
					int bondNameBeginIndex=jsFunction.indexOf("'", codeEndIndex+1);
					int bondNameEndIndex=jsFunction.indexOf("'",bondNameBeginIndex+1);
					String bondName=jsFunction.substring(bondNameBeginIndex+1, bondNameEndIndex);
					jsUrl=new StringBuffer("/fe/Channel/12438?bondCode=").append(code).append("&bondName=").append(bondName).toString();
				}
				//得到链接  构造request 添加已获取的值
				request=new Request(UrlUtils.canonicalizeUrl(jsUrl, "http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/beDraftByTremList.jsp?searchTypeCode=100041"));
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
		
		
		//只获取当天
		if(exitRule!=null) {
			Boolean exit=exitRule.getExit(page.getHtml());
			if(exit) return;
		}
		
		//获取下一页
		if(pageRule==null) return;
		String nextPage=pageRule.getNextPage(page.getHtml());
		if(nextPage==null||nextPage.equals("")) return;
		Request request=new Request(UrlUtils.canonicalizeUrl(nextPage,page.getUrl().toString()));
		request.putExtra("level", this.getLevel());
		page.addTargetRequest(request);
	}

}
