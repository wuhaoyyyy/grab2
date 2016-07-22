package com.purang.grab.site;

import java.util.Iterator;
import java.util.Map;
import us.codecraft.webmagic.Site;

public class CommonSite extends Site {
	
	public Boolean getUseGzip(){
		return super.isUseGzip();
	}
	
	public void setHeaders(Map<String,String> headers){
		Iterator<String> iterator=headers.keySet().iterator();
	    while(iterator.hasNext()){
	    	String key=iterator.next();
	    	String value=headers.get(key);
	    	this.addHeader(key, value);
	    }
	}		

}
