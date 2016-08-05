package com.purang.grab.pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.purang.grab.save.InsertSave;
import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.StringUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class CommonPipeline extends AbstractPipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		HashMap<String, Object> result=resultItems.get("result");
		if(result==null) return;
		HashMap<String, Object> downloadresult=resultItems.get("downloadresult");
		List<HashMap<String,String>> resultMapList=new ArrayList<HashMap<String,String>>();
		save.save(result);
	}
}
