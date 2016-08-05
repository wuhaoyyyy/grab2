package com.purang.grab.pipeline;

import com.purang.grab.save.Save;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class AbstractPipeline implements Pipeline {

	protected Save save;
	
	public Save getSave() {
		return save;
	}

	public void setSave(Save save) {
		this.save = save;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {

	}
}
