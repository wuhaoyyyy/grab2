package com.purang.grab.pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result) {
		System.out.println(result);
		if(save==null) return;
		save.save(result);
	}
}
