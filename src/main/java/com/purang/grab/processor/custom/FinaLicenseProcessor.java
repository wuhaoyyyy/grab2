package com.purang.grab.processor.custom;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.codecraft.webmagic.Page;
import com.purang.grab.processor.CommonProcessor;
/*
 * 中国货币网 获取下一页时判断是否大于总页数
 */
public class FinaLicenseProcessor extends CommonProcessor {

	private static Log taskLog = LogFactory.getLog("grabtask");
	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		
	}
}
