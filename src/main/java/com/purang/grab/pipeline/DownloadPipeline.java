package com.purang.grab.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class DownloadPipeline extends AbstractPipeline {

	@Override
	public void gotoProcess(ResultItems resultItems, Task task) {
		if(result.get("downloadfileurl")==null) return;
	}

}
