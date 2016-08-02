package com.purang.grab.rule;

import us.codecraft.webmagic.selector.Html;

public interface Rule {
	Object getRuleResult(Html html);
}
