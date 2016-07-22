package com.purang.grab.common.persistence;

import java.util.List;

/**
 * DAO支持类实现
 * 
 */
public interface BaseDao<T> {
	/**
	 * 插入数据
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	public int insertList(List<T> entityList);
}