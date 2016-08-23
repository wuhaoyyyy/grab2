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
import com.purang.grab.processor.CommonProcessor;
import com.purang.grab.request.PagerRequest;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.StringUtils;
/*
 * 中国货币网 获取下一页时判断是否大于总页数
 */
public class ChinaMoneyA1Processor extends CommonProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		super.gotoProcess(page, result);
	}
}
