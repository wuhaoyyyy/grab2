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

public class CommonProcessor extends AbstractProcessor{
	private static Log taskLog = LogFactory.getLog("grabtask");

	@SuppressWarnings("unchecked")
	@Override
	public void gotoProcess(Page page,Map<String, Object> result) {
		return;
	}
	
	
}
