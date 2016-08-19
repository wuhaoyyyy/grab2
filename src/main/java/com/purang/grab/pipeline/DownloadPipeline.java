package com.purang.grab.pipeline;

import java.util.Map;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class DownloadPipeline extends AbstractPipeline {

	@Override
	public void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result) {
		System.out.println(result);
		if(result.get("downloadfileurl")==null) return;
	}

}
