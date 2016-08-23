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

public class FinaLicensePipeline extends AbstractPipeline {

	@Override
	public void gotoProcess(ResultItems resultItems, Task task, Map<String, Object> result) {
		int listsize=CommonUtils.mapValueToList(result);
		CommonUtils.mapValueToList(result);
		//判断P13009 流水号是否存在
		for(int i=0;i<listsize;i++){
			Map singleMap=CommonUtils.getSingleMap(result,i);
			if(!StringUtils.isNotBlank(singleMap.get("f1").toString())) return;
			String sql="select count(1) from P13009 where f1='"+singleMap.get("f1")+"'";
			int count=DbUtils.select(sql);
			if(!StringUtils.isNotBlank(singleMap.get("f1old").toString())){
				if(count>0) return;
				else{
					String id=String.valueOf(DistributeUniqueId.getValue());
					DbUtils.insert(getInsertSql(singleMap));
				}
			}
			else{
				String oldNumber=(String) singleMap.get("f1old");	
				String selectSql="select count(1) from P13009 where f1='"+oldNumber+"' and f16='1'";
				if(DbUtils.select(selectSql)>0){
					DbUtils.update("update P13009 set f16='0',fv='"+CommonUtils.getAutoValue("[(auto)id]")+"',fu='"+CommonUtils.getAutoValue("[(auto)date]")+"' where f1='"+oldNumber+"' and f16='1'");
					DbUtils.insert(getInsertSql(singleMap));//插入主表
					DbUtils.insert(getInsertChangeSql(singleMap));//插入变更表
				}
				else{
					if(count>0) return;
					DbUtils.insert(getInsertSql(singleMap));//插入主表
					DbUtils.insert(getInsertChangeSql(singleMap));//插入变更表
				}
				
			}
		}
	}
	
	private String getInsertSql(Map singleMap){
		String id=String.valueOf(DistributeUniqueId.getValue());
		String insertsql="insert into P13009(fv,ft,f1,f2,f3,f4,f6,f7,f8,f9,f10,f11,f12,f13,f14,f15,f16,f17,fs,fa,fn,fp,fu) values("+
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
				"'"+singleMap.get("f17")+"',"+
				"'2',"+//已审核
				"'"+CommonUtils.getAutoValue("[(auto)username]")+"',"+
				"'"+CommonUtils.getAutoValue("[(auto)userid]")+"',"+
				"'"+CommonUtils.getAutoValue("[(auto)date]")+"',"+
				"'"+CommonUtils.getAutoValue("[(auto)date]")+"'"+
				")";
		return insertsql;
	}

	private String getInsertChangeSql(Map singleMap){
		String id=String.valueOf(DistributeUniqueId.getValue());
		String insertsql="insert into P13010(fv,ft,f1,f2,f3,f4,f6,f7,f8,fs,fa,fn,fp,fu) values("+
				"'"+id+"',"+
				"'"+id+"',"+
				"'"+singleMap.get("f1old")+"',"+
				"'"+singleMap.get("f1")+"',"+
				"'"+singleMap.get("f2old")+"',"+
				"'"+singleMap.get("f3old")+"',"+
				"'"+singleMap.get("orgType1")+"',"+
				"'"+singleMap.get("orgType2")+"',"+
				"'"+singleMap.get("f8old")+"',"+
				"'2',"+//已审核
				"'"+CommonUtils.getAutoValue("[(auto)username]")+"',"+
				"'"+CommonUtils.getAutoValue("[(auto)userid]")+"',"+
				"'"+CommonUtils.getAutoValue("[(auto)date]")+"',"+
				"'"+CommonUtils.getAutoValue("[(auto)date]")+"'"+
				")";
		return insertsql;
	}
}
