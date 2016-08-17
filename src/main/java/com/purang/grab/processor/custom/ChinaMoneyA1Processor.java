package com.purang.grab.processor.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

import com.purang.grab.processor.AbstractProcessor;
import com.purang.grab.processor.GoToLinkProcessor;
import com.purang.grab.request.PagerRequest;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.StringUtils;
/*
 * 中国货币网 获取下一页时判断是否大于总页数
 */
public class ChinaMoneyA1Processor extends GoToLinkProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		if(gotoLink!=null){		
			HashMap<String, Object> linkresult=new HashMap<String, Object>();
			linkresult.put(gotoLink.getField(),gotoLink.getRuleResult(page));
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
				try{
					request.putExtra("result", CommonUtils.getSingleMap(result, i));
				}
				catch(Exception e){
					e.printStackTrace();
					System.out.println("----"+linkList);
					System.out.println("----"+result);
				}
				request.setUrl(link);
				page.addTargetRequest(request);
				i++;
			}
		}
		
		
		//判断是否有退出
		if(exitRule!=null) {
			int exit=exitRule.getExit(page);
			if(exit>-1) {
				CommonUtils.mapValueToList(result);
				for(String key:result.keySet()){
					List list=(List) result.get(key);
					list=list.subList(0, exit);
					result.put(key, list);
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
			String text=page.getHtml().xpath("/html/body/table/tbody/tr[18]/td/table/tbody/tr/td/text()").toString();
			if(!StringUtils.isBlankCustom(text)){
				String pageLastNum=text.substring(text.indexOf("/")+1, text.indexOf("页"));//获取总页码，大于则结束
				if(pagerRequest.getStart()-pagerRequest.getTolerance()>Integer.valueOf(pageLastNum)) {
					taskLog.info("exit-lastpage:"+page.getRequest().getUrl());
					return;
				}
			}
		
			page.addTargetRequest(pagerRequest.getNextPager());
		}		
	}
}
