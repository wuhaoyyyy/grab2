package com.purang.grab.util;

import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPReply;  
import org.apache.commons.pool.PoolableObjectFactory;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * 连接池工厂类
 * 
 */  
public class FtpClientFactory implements PoolableObjectFactory {  
      
    private static Logger logger = LoggerFactory.getLogger(FtpClientFactory.class);  
      
    private FtpClientConfig config;  
      
    public FtpClientFactory(FtpClientConfig config) {  
        this.config = config;  
    }  
  
    @Override  
    public FTPClient makeObject() throws Exception {  
        FTPClient ftpClient = new FTPClient();  
        ftpClient.setConnectTimeout(config.getClientTimeout());  
        try {  
               ftpClient.connect(config.getHost(), config.getPort());  
               int reply = ftpClient.getReplyCode();  
               if (!FTPReply.isPositiveCompletion(reply)) {  
                    ftpClient.disconnect();  
                    logger.warn("FTPServer refused connection");  
                    return null;  
               }  
               boolean result = ftpClient.login(config.getUsername(), config.getPassword());  
               if (!result) {  
                   logger.warn("ftpClient login failed... username is {}", config.getUsername());  
               }  
               ftpClient.setFileType(config.getTransferFileType());  
               ftpClient.setBufferSize(1024);  
               ftpClient.setControlEncoding(config.getEncoding());  
               if (config.getPassiveMode().equals("true")) {  
                    ftpClient.enterLocalPassiveMode();  
               }  
          } catch (Exception e) {  
               logger.error("create ftp connection failed...{}", e);  
               throw e;  
          }   
          
          return ftpClient;  
    }  

	@Override
	public void destroyObject(Object obj) throws Exception {
		FTPClient ftpClient=(FTPClient)obj;
		try {  
            if(ftpClient != null && ftpClient.isConnected()) {  
                ftpClient.logout();  
            }  
        } catch (Exception e) {  
            logger.error("ftp client logout failed...{}", e);  
            throw e;  
        } finally {  
            if(ftpClient != null) {  
                ftpClient.disconnect();  
            }  
        }  
		
	}

	@Override
	public boolean validateObject(Object obj) {
		FTPClient ftpClient=(FTPClient)obj;
        try {  
               return ftpClient.sendNoOp();  
          } catch (Exception e) {  
              logger.error("Failed to validate client: {}", e);  
          }  
        return false;  
	}

	@Override
	public void activateObject(Object obj) throws Exception {
	}

	@Override
	public void passivateObject(Object obj) throws Exception {
		
	}  
  
}  