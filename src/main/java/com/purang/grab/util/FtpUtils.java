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
			//ftpClient.enterLocalPassiveMode();
			
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
    		if(!getConnection().storeFile(remoteFile, is)){
    			System.out.println("upload error");
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
    		OutputStream os = ftpClient.storeFileStream(remoteFile);
            byte[] buffer=new byte[50];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                os.write(buffer,0,ch);  
            }  
            is.close();  
            os.flush();  
            os.close();  
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
}
