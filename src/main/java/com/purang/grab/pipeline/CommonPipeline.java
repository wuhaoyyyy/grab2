package com.purang.grab.pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.purang.grab.util.ApplicationContextUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class CommonPipeline extends AbstractPipeline {

	
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		HashMap<String, Object> result=resultItems.get("result");
		HashMap<String, Object> downloadresult=resultItems.get("downloadresult");
		
		
		
		List<HashMap<String,String>> resultMapList=new ArrayList<HashMap<String,String>>();
		
		
		if(result!=null){
			boolean isList=false;
			int listsize=0;
			for(String key:result.keySet()){
				Object value=result.get(key);
				if(value instanceof List){
					isList=true;
					listsize=((List)value).size();
				}
			}
			if(isList){
				for(String key:result.keySet()){
					Object value=result.get(key);
					if(value instanceof String){
						List<String> l=new ArrayList<String>();
						for(int i=0;i<listsize;i++){
							l.add((String) value);
						}
						result.put(key, l);
					}
				}
			}
			if(isList){
				for(String key:result.keySet()){
					Object value=result.get(key);
					result.put(key, value.toString());
				}
			}
			
			SqlSessionFactory ssf =(SqlSessionFactory) ApplicationContextUtils.getInstance().getBean("sqlSessionFactory");
			SqlSession session = ssf.openSession();  
			session.selectOne("com.purang.grab.dao.CommonDao.grab_insert_p12002", result);
			session.close();
//			System.out.println(result.toString());
		}
		
	}
}
