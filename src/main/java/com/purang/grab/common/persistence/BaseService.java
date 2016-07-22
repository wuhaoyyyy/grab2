package com.purang.grab.common.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service基类
 * 
 */
public abstract class BaseService<D extends BaseDao<T>, T extends BaseEntity<T>> {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	public void insert(T entity){
		entity.preInsert();
		dao.insert(entity);
	}
	public void insertList(List<T> entityList){
		for(T entity:entityList){
			entity.preInsert();
		}
		dao.insertList(entityList);
	}
}
