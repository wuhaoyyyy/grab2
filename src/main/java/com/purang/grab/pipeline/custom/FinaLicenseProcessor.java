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
import com.purang.grab.util.DbUtils;
import com.purang.grab.util.DistributeUniqueId;
import com.purang.grab.util.FtpUtils;
import com.purang.grab.util.StringUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class FinaLicenseProcessor extends AbstractPipeline {

	@Override
	public void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result) {
		if(result.get("f1old")==null){
			//判断P13009 流水号是否存在
			SqlSessionFactory ssf =(SqlSessionFactory) ApplicationContextUtils.getInstance().getBean("sqlSessionFactory");
			SqlSession session = ssf.openSession(); 
			int listsize=CommonUtils.mapValueToList(result);
			for(int i=0;i<listsize;i++){
				Map singleMap=CommonUtils.getSingleMap(result,i);
				String sql="select count(1) from P13009 where f1='"+singleMap.get("f1")+"'";
				int count=DbUtils.select(sql);
				if(count>0) return;
				else{
					String id=String.valueOf(DistributeUniqueId.getValue());
					String insertsql="insert into P13009(fv,ft,f1,f2,f3,f4,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17) values("+
							"'"+id+"',"+
							"'"+id+"',"+
							"'"+singleMap.get("f1")+"',"+
							"'"+singleMap.get("f2")+"',"+
							"'"+singleMap.get("f3")+"',"+
							"'"+singleMap.get("f4")+"',"+
							"'"+singleMap.get("orgType1")+"',"+
							"'"+singleMap.get("orgType2")+"',"+
							"'"+singleMap.get("f8")+"',"+
							"'"+singleMap.get("f9")+"',"+
							"'"+singleMap.get("f10")+"',"+
							"'"+singleMap.get("f11")+"',"+
							"'"+singleMap.get("f12")+"',"+
							"'"+singleMap.get("f13")+"',"+
							"'"+singleMap.get("f14")+"',"+
							"'"+singleMap.get("f15")+"',"+
							"'1',"+
							"'"+singleMap.get("f17")+"'"+
							")";
					DbUtils.select(insertsql);
				}
			}
		}
		else{
			
		}
	}
}
