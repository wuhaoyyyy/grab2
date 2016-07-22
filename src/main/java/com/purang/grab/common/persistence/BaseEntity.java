package com.purang.grab.common.persistence;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.purang.grab.util.DistributeUniqueId;

/**
 * 数据Entity类
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String id;
	protected static final String FMT_SECOND_INT = "yyyyMMddHHmmss";
	protected static final String FMT_SECOND = "yyyy-MM-dd HH:mm:ss";
	
	protected String remarks; // 备注
	protected Date createDate; // 创建日期
	protected Date updateDate; // 更新日期
	protected String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	protected Long fv;		// fv
	protected Long ft;		// ft
	protected String updateDateString; // 更新日期
	protected String createDateString; // 更新日期

	
	public BaseEntity() {
	}
	public BaseEntity(String id) {
		this.setId(id);
	}
	
	public void preInsert() {
		this.updateDate = new Date();
		this.createDate = this.updateDate;
		this.updateDateString=getString(this.updateDate,FMT_SECOND_INT);
		this.createDateString=getString(this.createDate,FMT_SECOND_INT);
		if(this.ft==null||this.ft.equals(0L)){
			this.fv=DistributeUniqueId.getValue();
			this.ft=DistributeUniqueId.getValue();
			this.id=String.valueOf(this.ft);
		}
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	private String getString(Date date, String fmt) {
		try {
			return (new SimpleDateFormat(fmt)).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getUpdateDateString() {
		return updateDateString;
	}

	public void setUpdateDateString(String updateDateString) {
		this.updateDateString = updateDateString;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public Long getFt() {
		return ft;
	}

	public void setFt(Long ft) {
		this.ft = ft;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
