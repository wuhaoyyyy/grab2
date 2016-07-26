package com.purang.grab.processor;

import java.util.HashMap;
import java.util.List;

import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.UrlUtils;

public class ShClearingProcessor2  extends Processor {

	@Override
	public void process(Page page) {
		super.process(page);
		if(!String.valueOf(getLevel()).equals("1")) return;

		//上一级的结果
		HashMap<String, Object> result=(HashMap<String, Object>) page.getRequest().getExtra("lastlevelresult");
		//获取list
        String pageTxt=page.getRawText();
        int fbeginIndex=pageTxt.indexOf("var fileNames = '")+"var fileNames = '".length();
        int fendIndex=pageTxt.indexOf("';", fbeginIndex);
        int dbeginIndex=pageTxt.indexOf("var descNames = '")+"var descNames = '".length();
        int dendIndex=pageTxt.indexOf("';", dbeginIndex);
        
        String fileNames=pageTxt.substring(fbeginIndex, fendIndex);
        String descNames=pageTxt.substring(dbeginIndex, dendIndex);
        
        result.put("fileNames", fileNames);
        result.put("descNames", descNames);

		page.putField("result", result);
	}
}
