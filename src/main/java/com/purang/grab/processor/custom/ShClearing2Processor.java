package com.purang.grab.processor.custom;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.processor.AbstractProcessor;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.DateUtils;

import us.codecraft.webmagic.Page;

public class ShClearing2Processor extends AbstractProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void gotoProcess(Page page,Map<String, Object> result) {

		//获取list
        String pageTxt=page.getRawText();
        int fbeginIndex=pageTxt.indexOf("var fileNames = '")+"var fileNames = '".length();
        int fendIndex=pageTxt.indexOf("';", fbeginIndex);
        int dbeginIndex=pageTxt.indexOf("var descNames = '")+"var descNames = '".length();
        int dendIndex=pageTxt.indexOf("';", dbeginIndex);
        
        String fileNames=pageTxt.substring(fbeginIndex, fendIndex);
	    if(!fileNames.equals("")){
	        String descNames=pageTxt.substring(dbeginIndex, dendIndex);
	        String[] fileArray=fileNames.split(";;");
			String[] descArray=descNames.split(";;");
			int i=0;
			List<String> downloadfileurlList=new ArrayList<String>();
			List<String> filedescList=new ArrayList<String>();
			for(String file:fileArray){
				String downloadurl="http://www.shclearing.com/wcm/shch/pages/client/download/download.jsp?";
				String postFileName=fileArray[i].substring(fileArray[i].indexOf("/")+1);
				downloadurl=downloadurl+"FileName="+postFileName+"&";
				try {
					downloadurl=downloadurl+"DownName="+URLEncoder.encode(descArray[i],"UTF-8")+"&";
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				filedescList.add(descArray[i]);
				downloadfileurlList.add(downloadurl);
				i++;
			}
	        result.put("linkurl2", downloadfileurlList);
	        result.put("title2", filedescList);
        }
		page.putField("result", result);
	}
}
