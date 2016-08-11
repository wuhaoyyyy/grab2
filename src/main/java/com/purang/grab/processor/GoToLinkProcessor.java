package com.purang.grab.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
/*
 * 有链接处理类
 */
public class GoToLinkProcessor extends AbstractProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	private FieldRule gotoLink;
	
	public FieldRule getGotoLink() {
		return gotoLink;
	}

	public void setGotoLink(FieldRule gotoLink) {
		this.gotoLink = gotoLink;
	}

	@Override
	public void gotoProcess(Page page) {
		if(gotoLink!=null){		
			HashMap<String, Object> linkresult=new HashMap<String, Object>();
			linkresult.put(gotoLink.getField(),gotoLink.getRuleResult(page));
			CommonUtils.mapValueToList(linkresult);
			List<String> linkList=(List<String>) linkresult.get(gotoLink.getField());
			if(exitRule!=null) {
				int exitPos=exitRule.getExit(page);
				if(exitPos>-1) {
					linkList=linkList.subList(0, exitPos);
				}
			}
			int i=0;
			for(String link:linkList){
				Request request=new Request();
				request.putExtra("level", Integer.parseInt(page.getRequest().getExtra("level").toString())+1);
				request.putExtra("result", CommonUtils.getSingleMap(result, i));
				request.setUrl(link);
				page.addTargetRequest(request);
				i++;
			}
		}
		
		//判断是否有退出
		if(exitRule!=null) {
			int exit=exitRule.getExit(page);
			if(exit>-1) {
				for(String key:result.keySet()){
					try{
						List list=(List) result.get(key);
						list=list.subList(0, exit);
						result.put(key, list);
					}
					catch(Exception e){
						System.out.println(result);
						e.printStackTrace();
					}
				}
				taskLog.info("exit-exitrule:"+page.getRequest().getUrl());
				page.putField("result",result);			
				return;
			}
		}
		
		page.putField("result",result);
		
		Request request=page.getRequest();
		if(request instanceof PagerRequest){
			PagerRequest pagerRequest=(PagerRequest)request;
			page.addTargetRequest(pagerRequest.getNextPager());
		}		
		

	}

}
