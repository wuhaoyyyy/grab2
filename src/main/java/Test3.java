
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class Test3 {

	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {    
            HttpGet httpget = new HttpGet("http://www.chinamoney.com.cn/fe/CMS5_G20306002Resource?info=20148387;res=14615987673451992362710;download=");
            httpget.setHeader("Accept-Encoding", "deflate");  
            System.out.println("executing request " + httpget.getURI());  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
            	Header[] headers = response.getAllHeaders();
            	for(int i=0;i<headers.length;i++) {
//            		System.out.println(headers[i].getName() +"=="+ new String(headers[i].getValue().getBytes(), "GBK"));
//            		System.out.println(headers[i].getName() +"=="+ new String(headers[i].getValue().getBytes("ISO-8859-1"),"UTF-8"));

            		System.out.println(headers[i].getName() +"=="+ new String(headers[i].getValue().getBytes("ISO-8859-1"), "GBK"));
            	}
            	response.removeHeaders("Content-Length");
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
//                    System.out.println("Response content  length: " + entity.getContentLength()); 
//                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
	}

}
