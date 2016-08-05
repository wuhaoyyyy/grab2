package com.purang.grab.save;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.CommonUtils;

public class InsertSave implements Save{

	private String insertExpression;
	private String selectExpression;
	private HashMap<String, String> defaultValue=new HashMap<String, String>();
	private HashMap<String, String> mapValue=new HashMap<String, String>();//假定不同的字段的map key值不会相同。。。  
	
	public String getInsertExpression() {
		return insertExpression;
	}

	public void setInsertExpression(String insertExpression) {
		this.insertExpression = insertExpression;
	}

	public String getSelectExpression() {
		return selectExpression;
	}

	public void setSelectExpression(String selectExpression) {
		this.selectExpression = selectExpression;
	}

	public HashMap<String, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(HashMap<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}

	public HashMap<String, String> getMapValue() {
		return mapValue;
	}

	public void setMapValue(HashMap<String, String> mapValue) {
		this.mapValue = mapValue;
	}

	public void save(HashMap<String, Object> result){
		//与配置文件的默认值合并，再全部转为list
		result.putAll(defaultValue);
		CommonUtils.mapValueToList(result);
		
		SqlSessionFactory ssf =(SqlSessionFactory) ApplicationContextUtils.getInstance().getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession();  
		
		List list=(List)result.get(result.keySet().iterator().next());
		int listsize=list.size();
		for(int i=0;i<listsize;i++){
			String select=CommonUtils.getSingleSql(selectExpression,result,mapValue,i);
			HashMap<String, String> selectMap=new HashMap<String, String>();
			selectMap.put("select", select);
			int selectcount=session.selectOne("com.purang.grab.dao.CommonDao.select", selectMap);
			if(selectcount>0) {
				session.close();
				return;
			}

			String insert=CommonUtils.getSingleSql(insertExpression,result,mapValue,i);
			HashMap<String, String> insertMap=new HashMap<String, String>();
			insertMap.put("insert", insert);
			session.insert("com.purang.grab.dao.CommonDao.insert", insertMap);
		}
		session.close();
	}
}
