package com.purang.grab.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.purang.grab.common.persistence.BaseService;
import com.purang.grab.dao.BondAnnouncementDao;
import com.purang.grab.entity.BondAnnouncement;

/**
 * 债券公告表Service
 * @author rick
 * @version 2016-06-13
 */
@Service("bondAnnouncementService")
public class BondAnnouncementService extends BaseService<BondAnnouncementDao, BondAnnouncement> {

	public void insert(BondAnnouncement bondAnnouncement) {
		super.insert(bondAnnouncement);
	}
	
}