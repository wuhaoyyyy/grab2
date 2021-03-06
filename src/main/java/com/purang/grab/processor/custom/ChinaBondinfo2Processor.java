package com.purang.grab.processor.custom;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;

import us.codecraft.webmagic.Page;

import com.purang.grab.processor.AbstractProcessor;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.DbUtils;
import com.purang.grab.util.DistributeUniqueId;
import com.purang.grab.util.FtpClientUtils;
import com.purang.grab.util.FtpUtils;
import com.purang.grab.util.StringUtils;

public class ChinaBondinfo2Processor extends AbstractProcessor {

	@Override
	public void gotoProcess(Page page, Map<String, Object> result) {
		

		Map<String,String> defaultV=new HashMap<String, String>();
		defaultV.put("valid", "0");
		defaultV.put("fsPublishStatus", "2");
		defaultV.put("announceType2", "");
		defaultV.put("bondType2", "");
		defaultV.put("ftp", "");
		CommonUtils.combineMap(result, defaultV);
		int count=CommonUtils.mapValueToList(result);
		Map<String,String> headerMap=page.getResultItems().get("responseHeader");
		if(headerMap.get("Content-Disposition")!=null){
			String selectExpression="select count(1) from  P12005  p where p.f3=[pubdate] and p.f4=[announceSource] and p.f5=[announceType1] and p.f7=[bondType1] and p.f9=[title1] and p.f10=[linkurl1] and p.f11=[title2]";
			String insertExpression="INSERT INTO P12005 (fv, ft, f3, f4, f5, f7, f6, f8, f9, f10, f11, f12, f15, fs, fp, fu, fa, fn,f14) VALUES([(auto)id],[(auto)id],[pubdate],[announceSource],[announceType1],[bondType1],[announceType2],[bondType2],[title1],[linkurl1],[title2],[linkurl2],[ftp],[fsPublishStatus],[(auto)date],[(auto)date],[(auto)userid],[(auto)username],[valid])";
			for(int i=0;i<count;i++){
				String select=CommonUtils.getSingleSql(selectExpression,result,null,i,null);
				try {
					int selectcount=DbUtils.select(select);
					if(selectcount>0) {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String id=String.valueOf(DistributeUniqueId.getValue());
				String insertsql=insertExpression;
				
				
				String file=CommonUtils.fileDownloadHttpGet(page.getRequest().getUrl(), "/bondannounce/"+((List)result.get("pubdate")).get(i)+"/", id);
				if(!StringUtils.isNotBlank(file)){
					file="";
				}
				insertsql=insertsql.replace("[ftp]",file);
				insertsql=CommonUtils.getSingleSql(insertsql,result,null,i,id);
				try {
					DbUtils.insert(insertsql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		else{
			
		}
	}
}
