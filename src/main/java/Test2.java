import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream in = null;     
        OutputStream out = null;  

        try {  
            URL url = new URL("http://www.chinamoney.com.cn/index.html");    
            in = url.openStream();          
            out = System.out;  
            byte[] buffer = new byte[4096];  
            if(out==System.out){new String();}  
            int bytes_read;  
            while((bytes_read = in.read(buffer)) != -1){  
                out.write(buffer, 0, bytes_read);}                       
}  
        
        catch (Exception e) {  
            System.err.println(e);  
            System.err.println("Usage: java GetURL <URL> [<filename>]");  
        }  
        finally {   
            try { in.close(); out.close(); } catch (Exception e) {}  
        }  
    }  

}
