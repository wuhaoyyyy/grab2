package com.purang.grab.util;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DbUtils {

	public static int select(String sql){
		SqlSessionFactory ssf =(SqlSessionFactory) ApplicationContextUtils.getInstance().getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession(); 
		HashMap<String, String> selectMap=new HashMap<String, String>();
		selectMap.put("select", sql);
		int selectcount=session.selectOne("com.purang.grab.dao.CommonDao.select", selectMap);
		session.close();
		return selectcount;
	}

	public static void insert(String sql){
		SqlSessionFactory ssf =(SqlSessionFactory) ApplicationContextUtils.getInstance().getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession(); 
		HashMap<String, String> insertMap=new HashMap<String, String>();
		insertMap.put("insert", sql);
		try {
			session.insert("com.purang.grab.dao.CommonDao.insert", insertMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
	}

	public static void update(String sql){
		SqlSessionFactory ssf =(SqlSessionFactory) ApplicationContextUtils.getInstance().getBean("sqlSessionFactory");
		SqlSession session = ssf.openSession(); 
		HashMap<String, String> updateMap=new HashMap<String, String>();
		updateMap.put("update", sql);
		try {
			session.update("com.purang.grab.dao.CommonDao.update",sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
	}
}
