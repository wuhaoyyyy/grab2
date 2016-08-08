import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


public class Test7 {

	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {    
            HttpPost httppost = new HttpPost("http://www.shclearing.com/shchapp/web/disclosureForTrsServer/search");
            httppost.setHeader("Accept-Encoding", "deflate");  
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
            nvps.add(new BasicNameValuePair("channelId", "449"));  
            nvps.add(new BasicNameValuePair("start", "0"));  
            nvps.add(new BasicNameValuePair("limit", "20"));  
            httppost.setEntity(new UrlEncodedFormEntity(nvps));  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
            	response.removeHeaders("Content-Length");
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    System.out.println("Response content  length: " + entity.getContentLength()); 
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
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
