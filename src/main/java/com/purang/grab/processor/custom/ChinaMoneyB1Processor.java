package com.purang.grab.processor.custom;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;

import com.purang.grab.processor.AbstractProcessor;
import com.purang.grab.request.PagerRequest;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.StringUtils;

public class ChinaMoneyB1Processor extends AbstractProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {

		Html html=new Html(page.getRawText());
		List<String> jsFunctionList=(List<String>) result.get("linkurl1");
		int i=0;

		if(exitRule!=null) {
			int exitPos=exitRule.getExit(page);
			if(exitPos>-1) {
				jsFunctionList=jsFunctionList.subList(0, exitPos);
			}
		}
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
			request=new Request(UrlUtils.canonicalizeUrl(jsUrl, page.getRequest().getUrl()));
			request.putExtra("level", this.getLevel()+1);
			result.put("linkurl1", request.getUrl());
			request.putExtra("result", CommonUtils.getSingleMap(result, i));
			page.addTargetRequest(request);
			i++;
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
