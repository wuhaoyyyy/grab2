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

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtils {
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
			ftpClient.setControlEncoding("GBK");
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
			ftpClient.setControlEncoding("GBK");
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
	public void isDirExist(FTPClient ftpClient, String dir) {
		try {
			ftpClient.cwd(dir); // 想不到什么好办法来判断目录是否存在，只能用异常了(比较笨).请知道的告诉我一声`
		} catch (IOException e1) {
			try {
				ftpClient.sendCommand("MKD " + dir + "/r/n");
				ftpClient.getReply();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
