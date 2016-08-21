package com.purang.grab.pipeline;

import java.util.HashMap;
import java.util.Map;

import com.purang.grab.save.Save;
import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class AbstractPipeline implements Pipeline {

	protected int level;
	Map<String,String> defaultValue=new HashMap<String, String>();
	protected Save save;
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Map<String, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Map<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Save getSave() {
		return save;
	}

	public void setSave(Save save) {
		this.save = save;
	}

	public void process(ResultItems resultItems, Task task) {
		Map<String, Object> result=new HashMap<String, Object>();
		if(!resultItems.get("level").toString().equals(String.valueOf(this.level))) return;
		result=resultItems.get("result");
		if(result==null) return;
		if(this.defaultValue!=null){
			result.putAll(this.defaultValue);
		}	
		gotoProcess(resultItems, task, result);
	}
	
	public abstract void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result);
}
