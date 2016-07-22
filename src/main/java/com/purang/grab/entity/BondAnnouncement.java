package com.purang.grab.entity;

import com.purang.grab.common.persistence.BaseEntity;

/**
 * 债券公告表Entity
 * @author rick
 * @version 2016-06-13
 */
public class BondAnnouncement extends BaseEntity<BondAnnouncement> {
	
	private static final long serialVersionUID = 1L;

	private String mainInnerCode;		// 债劵主内部编码
	private String innerCode;		// 债券内部编码
	private String announceDate;		// 公告日期
	private String announceSource;		// 公告来源
	private String announceType1;		// 公告类型：原始披漏一级
	private String announceType2;		// 公告类型：原始披漏二级
	private String bondType1;		// 债券类型:原始披露一级
	private String bondType2;		// 债券类型：原始披露二级
	private String announceTitle1;		// 公告标题一级
	private String link1;		// 链接地址一级
	private String announceTitle2;		// 公告标题二级
	private String link2;		// 链接地址二级
	private String publisherId;		// 机构ID
	private String valid;		// 是否有效
	private String attachment;		// 附件
	private String publishState;		// 发布状态

	
	public BondAnnouncement() {
		super();
	}

	public BondAnnouncement(String id){
		super(id);
	}
	
	public String getMainInnerCode() {
		return mainInnerCode;
	}

	public void setMainInnerCode(String mainInnerCode) {
		this.mainInnerCode = mainInnerCode;
	}
	
	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
	public String getAnnounceDate() {
		return announceDate;
	}

	public void setAnnounceDate(String announceDate) {
		this.announceDate = announceDate;
	}
	
	public String getAnnounceSource() {
		return announceSource;
	}

	public void setAnnounceSource(String announceSource) {
		this.announceSource = announceSource;
	}
	
	public String getAnnounceType1() {
		return announceType1;
	}

	public void setAnnounceType1(String announceType1) {
		this.announceType1 = announceType1;
	}
	
	public String getAnnounceType2() {
		return announceType2;
	}

	public void setAnnounceType2(String announceType2) {
		this.announceType2 = announceType2;
	}
	
	public String getBondType1() {
		return bondType1;
	}

	public void setBondType1(String bondType1) {
		this.bondType1 = bondType1;
	}
	
	public String getBondType2() {
		return bondType2;
	}

	public void setBondType2(String bondType2) {
		this.bondType2 = bondType2;
	}
	
	public String getAnnounceTitle1() {
		return announceTitle1;
	}

	public void setAnnounceTitle1(String announceTitle1) {
		this.announceTitle1 = announceTitle1;
	}
	
	public String getLink1() {
		return link1;
	}

	public void setLink1(String link1) {
		this.link1 = link1;
	}
	
	public String getAnnounceTitle2() {
		return announceTitle2;
	}

	public void setAnnounceTitle2(String announceTitle2) {
		this.announceTitle2 = announceTitle2;
	}
	
	public String getLink2() {
		return link2;
	}

	public void setLink2(String link2) {
		this.link2 = link2;
	}
	
	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getPublishState() {
		return publishState;
	}

	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}

}