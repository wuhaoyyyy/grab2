package com.purang.grab.processor;

import java.util.HashMap;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.Page;

public class DownloadProcessor extends Processor{

	@Override
	public void process(Page page) {
		HashMap<String, Object> result=(HashMap<String, Object>) page.getRequest().getExtra("result");
		CommonUtils.mapValueToList(result);
	}

}
