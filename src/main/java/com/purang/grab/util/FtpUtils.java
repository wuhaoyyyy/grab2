package com.purang.grab.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {

	private static Log taskLog = LogFactory.getLog("grabtask");
	private static int downloadCount=0;
    private static String encode="GBK";
    private static FTPClient ftpClient;
    public static String ftpserver;
    public static String ftpport;
    public static String ftpuser;
    public static String ftppsw;

    static{
        ftpserver=CommonUtils.getConfig("ftp.server");
    	ftpport=CommonUtils.getConfig("ftp.port");
    	ftpuser=CommonUtils.getConfig("ftp.user");
    	ftppsw=CommonUtils.getConfig("ftp.psw");
    	ftpClient=new FTPClient();
		try {
			ftpClient.connect(ftpserver, Integer.valueOf(ftpport));
			ftpClient.login(ftpuser, ftppsw);
			ftpClient.setSendBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding(encode);
//			ftpClient.enterLocalPassiveMode();
			
			FTPFile[] f= ftpClient.listDirectories();
			for(FTPFile ftpFile:f){
				System.out.println(ftpFile.getName());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static FTPClient getConnection(){
    	FTPClient ftpClient=new FTPClient();
    	try {
			ftpClient.connect(ftpserver, Integer.valueOf(ftpport));
			ftpClient.login(ftpuser, ftppsw);
			ftpClient.setSendBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding(encode);
			
//			ftpClient.enterRemotePassiveMode();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return ftpClient;
    }

    public static void upload(InputStream is, String remoteFile) {
    	FTPClient ftpClient=getConnection();
    	try {
    		if(!ftpClient.storeFile(remoteFile, is)){
    			//ftpClient.getReplyString()
    			System.out.println(ftpClient.getReplyString());
    		}
    		else{
    			
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	try {
    		is.close();
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

    public static void upload(InputStream is, String fileDir ,String fielName) {
    	FTPClient ftpClient=getConnection();
    	Mkdirs(ftpClient, fileDir);
    	try {
    		if(!ftpClient.storeFile(fielName, is)){
    			//ftpClient.getReplyString()
    			System.out.println(ftpClient.getReplyString());
    		}
    		else{
    			taskLog.info("文件下载完成..."+downloadCount++);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	try {
    		is.close();
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


    public static void uploadStream(InputStream is, String remoteFile) {
    	try {
    		FTPClient ftpClientt=getConnection();
    		OutputStream os = ftpClientt.storeFileStream(remoteFile);
            byte[] buffer=new byte[1024];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                os.write(buffer,0,ch);  
            }  
            is.close();  
            os.flush();  
            os.close();  
            ftpClientt.completePendingCommand();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getEncodeFormat(String s){
    	try {
			return new String(s.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public OutputStream getFtpFile(FTPClient ftpClient, String dir, String fileName){
    	try {
			FTPFile[] ftpFiles = ftpClient.listFiles(dir);
			for(FTPFile ftpFile:ftpFiles){
				if(ftpFile.getName().equals(fileName)) return ftpClient.storeFileStream(dir+"/"+fileName);
			}
			
			return null;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /** 判断Ftp目录是否存在,如果不存在则创建目录 */
	public static void changeDir(FTPClient ftpClient, String dir) {
		try {
			ftpClient.makeDirectory(dir);
			ftpClient.changeWorkingDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean Mkdirs(FTPClient ftpClient,String path){
        Boolean success = false;
        String[] subDirs = path.split("/");
        
        String LOCAL_CHARSET = "GBK";
        String SERVER_CHARSET = "ISO-8859-1";
        
        //check if is absolute path
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
