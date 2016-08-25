package com.purang.grab.util;
import java.util.NoSuchElementException;  
import java.util.concurrent.ArrayBlockingQueue;  
import java.util.concurrent.BlockingQueue;  
import java.util.concurrent.TimeUnit;  
  



import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.pool.ObjectPool; 
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
/** 
 * ftp连接池  
 * 
 */  
public class FtpClientPool implements ObjectPool {  
      
    private static Logger logger = LoggerFactory.getLogger(FTPClient.class);  
      
    private static final int DEFAULT_POOL_SIZE = 10;  
      
    private BlockingQueue<FTPClient> pool;  
      
    private FtpClientFactory factory;  
      
    public FtpClientPool(FtpClientFactory factory) throws Exception {  
        this(DEFAULT_POOL_SIZE, factory);  
    }  
      
    public FtpClientPool(int poolSize, FtpClientFactory factory) throws Exception {  
        this.factory = factory;  
        this.pool = new ArrayBlockingQueue<FTPClient>(poolSize * 2);  
        initPool(poolSize);  
    }  
      
    /** 
     * 初始化连接池 
     * @param maxPoolSize 
     *                  最大连接数 
     * @throws Exception 
     */  
    private void initPool(int maxPoolSize) throws Exception {  
        int count = 0;  
        while(count < maxPoolSize) {  
            this.addObject();  
            count++;  
        }  
    }  
      
    /** 
     * 从连接池中获取对象 
     */  
    @Override  
    public FTPClient borrowObject() throws Exception, NoSuchElementException, IllegalStateException { 
    	logger.info("---------------------before take"+String.valueOf(pool.size())); 
        FTPClient client = pool.take();  
    	logger.info("---------------------after take"+String.valueOf(pool.size()));
        if(client == null) {  
            client = factory.makeObject();  
            addObject();  
        } else if(!factory.validateObject(client)) {  
            invalidateObject(client);  
            client = factory.makeObject();  
            addObject();  
        }  
          
        return client;  
    }  
      
    /** 
     * 返还一个对象(链接) 
     */  
    @Override  
    public void returnObject(Object obj) throws Exception {  
		FTPClient client=(FTPClient)obj;
        if ((client != null) && !pool.offer(client,3,TimeUnit.SECONDS)) {  
           try {  
                factory.destroyObject(client);  
           } catch (Exception e) {  
                throw e;  
           }  
        }  
        else{
        	logger.error("---------------------pool.offer fail");
        }
    	logger.info("---------------------after return"+String.valueOf(pool.size()));
//        if (client != null) {  
//        	pool.put(client);
//            try {  
//                 factory.destroyObject(client);  
//            } catch (Exception e) {  
//                 throw e;  
//            }  
//         }  
    }  
      
    /** 
     * 移除无效的对象(FTP客户端) 
     */  
    @Override  
    public void invalidateObject(Object obj) throws Exception {   
		FTPClient client=(FTPClient)obj;
          pool.remove(client);  
    }  
      
    /** 
     * 增加一个新的链接，超时失效 
     */  
    @Override  
    public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {  
    	logger.info("---------------------after addObject"+String.valueOf(pool.size()));
        pool.offer(factory.makeObject(), 3, TimeUnit.SECONDS);  
    }  
      
    /** 
     * 获取空闲链接数
     */  
    @Override  
    public int getNumIdle() {  
        return 0;  
    }  
      
    /** 
     * 获取正在被使用的链接数 
     */  
    @Override  
    public int getNumActive() {  
        return 0;  
    }  
      
    @Override  
    public void clear() throws Exception, UnsupportedOperationException {  
          
    }  
      
    /** 
     * 关闭连接池 
     */  
    @Override  
    public void close() {  
        try {  
            while(pool.iterator().hasNext()) {  
                   FTPClient client = pool.take();  
                   factory.destroyObject(client);  
              }  
        } catch(Exception e) {  
            logger.error("close ftp client pool failed...{}", e);  
        }  
          
    }

	@Override
	public void setFactory(PoolableObjectFactory factory)
			throws IllegalStateException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}
  
}  