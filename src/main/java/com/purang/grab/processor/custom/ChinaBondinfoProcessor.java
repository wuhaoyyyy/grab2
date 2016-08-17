package com.purang.grab.processor.custom;

import java.util.Map;

import us.codecraft.webmagic.Page;

import com.purang.grab.processor.AbstractProcessor;

public class ChinaBondinfoProcessor extends AbstractProcessor {

	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		System.out.println(page.getResultItems().get("responseHeader"));
	}
}
