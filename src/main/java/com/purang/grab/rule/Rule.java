package com.purang.grab.rule;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

public interface Rule {
	Object getRuleResult(Page page);
}
