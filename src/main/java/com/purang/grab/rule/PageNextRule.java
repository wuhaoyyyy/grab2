package com.purang.grab.rule;

import us.codecraft.webmagic.selector.Html;

public class PageNextRule extends PageRule {
	
	@Override
	public String getNextPage(Html html) {
		return getRuleResult(html).toString();
	}

}
