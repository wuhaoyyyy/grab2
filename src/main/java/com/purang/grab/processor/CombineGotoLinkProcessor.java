package com.purang.grab.processor;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

/**
 * 列表里每一项点击进入一个页面，拼接进入页面的url
 * 
 */
public class CombineGotoLinkProcessor extends AbstractProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	String prefix;
	Map<String, String> combineUrlValue;
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Map<String, String> getCombineUrlValue() {
		return combineUrlValue;
	}

	public void setCombineUrlValue(Map<String, String> combineUrlValue) {
		this.combineUrlValue = combineUrlValue;
	}

	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		int i=CommonUtils.mapValueToList(result);
		String url=prefix+"?";
		for(int j=0;j<i;j++){
			String gotoLink=url;
			Map singleMap=CommonUtils.getSingleMap(result, j);
			
			for(String key:combineUrlValue.keySet()){
				String value=combineUrlValue.get(key);
				if(value.startsWith("[")&&value.endsWith("]")){
					value=(String) singleMap.get(value.substring(1, value.length()-1));
				}
				gotoLink=gotoLink+key+"="+value+"&";
			}
			Request request=new Request();
			request.putExtra("level", Integer.parseInt(page.getRequest().getExtra("level").toString())+1);
			request.putExtra("result", singleMap);
			request.setUrl(gotoLink);
			page.addTargetRequest(request);
		}		
	}
}
