package com.purang.grab.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.purang.grab.request.PagerRequest;
import com.purang.grab.rule.FieldRule;
import com.purang.grab.save.Save;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
/**
 * 可以处理打开新连接。 默认fieldrule的field为gotolink
 *
 */
public class CommonSaveProcessor extends AbstractProcessor{
	private static Log taskLog = LogFactory.getLog("grabtask");
	protected Save save;
	
	public Save getSave() {
		return save;
	}
	public void setSave(Save save) {
		this.save = save;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void gotoProcess(Page page,Map<String, Object> result) {
		if(save==null) return;
		save.save(result);
	}
	
	
}
