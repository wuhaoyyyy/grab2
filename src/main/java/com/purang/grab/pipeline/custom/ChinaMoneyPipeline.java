package com.purang.grab.pipeline.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.purang.grab.pipeline.AbstractPipeline;
import com.purang.grab.save.InsertSave;
import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.FtpUtils;
import com.purang.grab.util.StringUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class ChinaMoneyPipeline extends AbstractPipeline {

	@Override
	public void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result) {
		save.save(result);
	}
}
