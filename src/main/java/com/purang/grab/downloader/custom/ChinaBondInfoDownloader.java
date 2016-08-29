package com.purang.grab.downloader.custom;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.purang.grab.util.CommonUtils;
import com.purang.grab.util.DbUtils;
import com.purang.grab.util.DistributeUniqueId;
import com.purang.grab.util.FtpUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.proxy.Proxy;

/**
 * 
 * 直接判断是否下载文件
 *
 */
public class ChinaBondInfoDownloader extends HttpClientDownloader {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();
    private CloseableHttpClient getHttpClient(Site site, Proxy proxy) {
        if (site == null) {
            return httpClientGenerator.getClient(null, proxy);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site, proxy);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }
    @Override
    public Page download(Request request, Task task) {
        Site site = null;
        if (task != null) {
            site = task.getSite();
        }
        Set<Integer> acceptStatCode;
        String charset = null;
        Map<String, String> headers = null;
        if (site != null) {
            acceptStatCode = site.getAcceptStatCode();
            charset = site.getCharset();
            headers = site.getHeaders();
        } else {
            acceptStatCode = Sets.newHashSet(200);
        }
        logger.info("downloading page {}", request.getUrl());
        CloseableHttpResponse httpResponse = null;
        int statusCode=0;
        try {
            HttpHost proxyHost = null;
            Proxy proxy = null; //TODO
            if (site.getHttpProxyPool() != null && site.getHttpProxyPool().isEnable()) {
                proxy = site.getHttpProxyFromPool();
                proxyHost = proxy.getHttpHost();
            } else if(site.getHttpProxy()!= null){
                proxyHost = site.getHttpProxy();
            }
            
            HttpUriRequest httpUriRequest = getHttpUriRequest(request, site, headers, proxyHost);
            httpResponse = getHttpClient(site, proxy).execute(httpUriRequest);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            request.putExtra(Request.STATUS_CODE, statusCode);
            
        	
            if (statusAccept(acceptStatCode, statusCode)) {
                Page page = handleResponse(request, charset, httpResponse, task);
                onSuccess(request);
                
                
                Header[] responseHeaders = httpResponse.getAllHeaders();// add header map
                Map<String,String> headerMap=new HashMap<String, String>();
            	for(int i=0;i<responseHeaders.length;i++) {
            		headerMap.put(responseHeaders[i].getName(), new String(responseHeaders[i].getValue().getBytes("ISO-8859-1"),"UTF-8"));
            	}
            	page.putField("responseHeader", headerMap);
            	if(headerMap.get("Content-Disposition")!=null){
            		page.putField("responseHttpEntity", httpResponse.getEntity());
        		}
                return page;
            } else {
                return null;
            }
        } catch (IOException e) {
            if (site.getCycleRetryTimes() > 0) {
                return addToCycleRetry(request, site);
            }
            onError(request);
            return null;
        } finally {
        	request.putExtra(Request.STATUS_CODE, statusCode);
            if (site.getHttpProxyPool()!=null && site.getHttpProxyPool().isEnable()) {
                site.returnHttpProxyToPool((HttpHost) request.getExtra(Request.PROXY), (Integer) request
                        .getExtra(Request.STATUS_CODE));
            }
            try {
                if (httpResponse != null) {
                    EntityUtils.consume(httpResponse.getEntity());
                }
            } catch (IOException e) {
            }
        }
    }
}
