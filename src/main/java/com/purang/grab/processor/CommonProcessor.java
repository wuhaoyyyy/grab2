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
/**
 * 可以处理打开新连接。 默认fieldrule的field为gotolink
 *
 */
public class CommonProcessor extends AbstractProcessor{
	private static Log taskLog = LogFactory.getLog("grabtask");

	@SuppressWarnings("unchecked")
	@Override
	public void gotoProcess(Page page,Map<String, Object> result) {
		if(result.get("gotolink")!=null){
			List<String> linkList=(List<String>) result.get("gotolink");
			int i=0;
			for(String link:linkList){
				Request request=new Request();
				request.putExtra("level", Integer.parseInt(page.getRequest().getExtra("level").toString())+1);
				try{
					request.putExtra("result", CommonUtils.getSingleMap(result, i));
				}
				catch(Exception e){
					e.printStackTrace();
				}
				request.setUrl(link);
				page.addTargetRequest(request);
				i++;
			}
		}
	}
	
	
}
