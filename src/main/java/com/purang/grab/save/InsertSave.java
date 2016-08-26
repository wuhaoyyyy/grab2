package com.purang.grab.save;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.DbUtils;
import com.purang.grab.util.DistributeUniqueId;
import com.purang.grab.util.StringUtils;

public class InsertSave implements Save{

	private String insertExpression;
	private String selectExpression;
	private String filedownload;
	private String filedownloadpath;
	private Map<String, String> defaultValue=new HashMap<String, String>();
	private Map<String, String> mapValue=new HashMap<String, String>();//假定不同的字段的map key值不会相同。。。  
	
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

	public String getFiledownload() {
		return filedownload;
	}

	public void setFiledownload(String filedownload) {
		this.filedownload = filedownload;
	}

	public String getFiledownloadpath() {
		return filedownloadpath;
	}

	public void setFiledownloadpath(String filedownloadpath) {
		this.filedownloadpath = filedownloadpath;
	}

	public Map<String, String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Map<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Map<String, String> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<String, String> mapValue) {
		this.mapValue = mapValue;
	}

	public void save(Map<String, Object> result){
		//与配置文件的默认值合并，再全部转为list   不用putall 在result里则不替换。    生成sql要求sql的配置必需在resultmap里，否者替换不了[] 导入报错
		for(String key:defaultValue.keySet()){
			if(result.get(key)==null||result.get(key).equals("")){
				result.put(key, defaultValue.get(key));
			}
		}
		
		CommonUtils.mapValueToList(result);
		List list=(List)result.get(result.keySet().iterator().next());
		int listsize=list.size();
		for(int i=0;i<listsize;i++){
			if(StringUtils.isNotBlank(selectExpression)){
				String select=CommonUtils.getSingleSql(selectExpression,result,mapValue,i,null);
				try {
					int selectcount=DbUtils.select(select);
					if(selectcount>0) {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(result);
				}
			}
			String id=String.valueOf(DistributeUniqueId.getValue());

			String insertsql=insertExpression;
			Map<String,String> singleMap=CommonUtils.getSingleMap(result,i);
			//文件 默认文件名insert的id
			if(StringUtils.isNotBlank(filedownload)){
				String filedownloadurl=(String) singleMap.get(filedownload);
				if(StringUtils.isNotBlank(filedownloadurl)){
					String path=filedownloadpath;
					for(String key:singleMap.keySet()){
						path=path.replace("["+key+"]",(String) singleMap.get(key));
					}
					try{
						String file=CommonUtils.fileDownloadHttpGet(filedownloadurl, path, id);
//						String file=CommonUtils.fileDownloadHttpPost(filedownloadurl, path, id,singleMap);
						if(!StringUtils.isNotBlank(file)){
							file="";
						}
						insertsql=insertsql.replace("[ftp]","'"+file+"'");
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			insertsql=CommonUtils.getSingleSql(insertsql,result,mapValue,i,id);
			try {
				DbUtils.insert(insertsql);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(result);
			}
		}
	}
}
