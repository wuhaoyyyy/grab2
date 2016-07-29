package com.purang.grab.processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.DateUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.UrlUtils;

public class ShClearingProcessor1  extends Processor {

	@Override
	public void process(Page page) {
		super.process(page);
		if(!String.valueOf(getLevel()).equals("0")) return;

		System.out.println(page.getUrl());
		JSONObject pageResult=JSONObject.parseObject(page.getRawText());

		JSONArray dataJsonArray=JSONArray.fromObject(pageResult.get("datas"));
		if(dataJsonArray.size()>0&&!dataJsonArray.get(0).equals("null")){
			for(int i=0;i<dataJsonArray.size();i++){
				HashMap<String, Object> result=new HashMap<String, Object>();
				JSONObject dataJson=JSONObject.parseObject(dataJsonArray.get(i).toString());
				String title=dataJson.get("title").toString();
				String linkurl=dataJson.get("linkurl").toString();
				String pubdate=dataJson.get("pubdate").toString();
				pubdate=DateUtils.getStringFromChStr(pubdate);
				result.put("announceTitle1",title);
				result.put("link1",linkurl);
				result.put("announceDate",pubdate);
				
				Request request = null;
				request=new Request(UrlUtils.canonicalizeUrl(linkurl, page.getRequest().getUrl()));
				request.putExtra("level", this.getLevel()+1);
				request.putExtra("lastlevelresult",result);//将本页获取的结果传递给下一级
				page.addTargetRequest(request);
			}
			//获取下一页
			if(pageRule==null) return;
			String nextPage=pageRule.getNextPage(page.getHtml());
			Request request=new Request(UrlUtils.canonicalizeUrl(nextPage,page.getUrl().toString()));
			request.putExtra("level", this.getLevel());
			page.addTargetRequest(request);
		}

	}
}
