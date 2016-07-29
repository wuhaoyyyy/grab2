package com.purang.grab.rule;

import java.util.ArrayList;
import java.util.List;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.selector.Html;

public class PagerRule extends AbstractRule {
	public String getNextPage(Html html){
		return CommonUtils.getSelectorLinkResult(html, rule, type);
	}
	public List<String> getNextPageList(Html html){
		return CommonUtils.getSelectorLinkListResult(html, rule, type);
	}
}
