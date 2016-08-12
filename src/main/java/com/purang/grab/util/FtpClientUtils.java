package com.purang.grab.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClientUtils {
	private static Log taskLog = LogFactory.getLog("grabtask");
	private static FtpClientPool ftpClientPool;
	private static FtpClientFactory ftpClientFactory;
    private static FtpClientConfig ftpClientConfig;
    public static String ftpserver;
    public static String ftpport;
    public static String ftpuser;
    public static String ftppsw;
    public static String ftppoolsize;
    public static String ftpencode="GBK";;

    static{
        ftpserver=CommonUtils.getConfig("ftp.server");
    	ftpport=CommonUtils.getConfig("ftp.port");
    	ftpuser=CommonUtils.getConfig("ftp.user");
    	ftppsw=CommonUtils.getConfig("ftp.psw");
    	ftppoolsize=CommonUtils.getConfig("ftp.poolsize");
    	ftpencode=CommonUtils.getConfig("ftp.encode");
    	ftpClientConfig=new FtpClientConfig();
    	ftpClientConfig.setHost(ftpserver);
    	ftpClientConfig.setPort(Integer.valueOf(ftpport));
    	ftpClientConfig.setUsername(ftpuser);
    	ftpClientConfig.setPassword(ftppsw);
    	ftpClientConfig.setEncoding(ftpencode);
    	ftpClientFactory=new FtpClientFactory(ftpClientConfig);
    	try {
			ftpClientPool=new FtpClientPool(Integer.valueOf(ftppoolsize), ftpClientFactory);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static FTPClient getConnection(){
    	try {
			return ftpClientPool.borrowObject();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /*
     * 上传文件
     */
    public static void upload(long l,String url,InputStream is, String fileDir ,String fielName) {
    	FTPClient ftpClient=getConnection();
    	Mkdirs(ftpClient, fileDir);
    	taskLog.info("文件下载..."+l/1024+"M,下载地址:"+url+"..."+downloadCount.getAndIncrement());
    	if(downloadCount.get()>1000000) {
    		downloadCount.set(0);
        	completeCount.set(0);
    	}
    	try {
    		if(!ftpClient.storeFile(fielName, is)){
    			//ftpClient.getReplyString()
    			taskLog.info("f");
    			System.out.println(ftpClient.getReplyString());
    		}
    		else{
    			taskLog.info("文件下载完成..."+completeCount.getAndIncrement());
    	    	try {
    	    		is.close();
    				ftpClient.disconnect();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    	    	//if(completeCount.get()>1000000) completeCount.set(0);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    
    
    
    
    
    
    
    
    
	/*
	 * 创建目录
	 */
	public static Boolean Mkdirs(FTPClient ftpClient,String path){
        Boolean success = false;
        String[] subDirs = path.split("/");
        
        String LOCAL_CHARSET = "GBK";
        String SERVER_CHARSET = "ISO-8859-1";

        if(path.substring(0, 0).equalsIgnoreCase("/")){
            subDirs[0] = "/" + subDirs[0];
        }
        boolean tmpMkdirs = false;
        try {
            // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
            if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                LOCAL_CHARSET = "UTF-8";
            }
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            String orginPath = ftpClient.printWorkingDirectory();
            for(String subDir : subDirs){
                //encoding
                String strSubDir = new String(subDir.getBytes(LOCAL_CHARSET),SERVER_CHARSET);
                tmpMkdirs = ftpClient.makeDirectory(strSubDir);
                boolean tmpDoCommand = ftpClient.sendSiteCommand("chmod 755 " + strSubDir);
                ftpClient.changeWorkingDirectory(strSubDir);
                success = success || tmpMkdirs;
            }
            //ftpClient.changeWorkingDirectory(orginPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;
    }

}