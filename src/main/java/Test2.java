import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purang.grab.util.CommonUtils;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		List<String> result=new ArrayList<String>();
//		result.add("a");
//		result.add("b");
//		for(String text:result){
//			text="c";
//		}
//		System.out.println(result);
//		
//		Map map=new HashMap<String, Object>();
//		map.put("a", "aa");
//		map.put("b", new ArrayList<>());
//		List l=new ArrayList<>();
//		l.add("qq");
//		l.add("ww");
//		map.put("l", l);
//		CommonUtils.mapValueToList(map);
//		System.out.println(map);
		
		File file=new File("F://grabfiles//bondannounce");
		delete(file);
		
//		InputStream in = null; 
//        OutputStream out = null;  
//
//        try {  
//            URL url = new URL("http://www.chinamoney.com.cn/index.html");    
//            in = url.openStream();          
//            out = System.out;  
//            byte[] buffer = new byte[4096];  
//            if(out==System.out){new String();}  
//            int bytes_read;  
//            while((bytes_read = in.read(buffer)) != -1){  
//                out.write(buffer, 0, bytes_read);}                       
//        }  
//        
//        catch (Exception e) {  
//            System.err.println(e);  
//            System.err.println("Usage: java GetURL <URL> [<filename>]");  
//        }  
//        finally {   
//            try { in.close(); out.close(); } catch (Exception e) {}  
//        }  
    }  
	public static void delete(File file){
		File[] files=file.listFiles();
		if(files==null) return;
		for(File f:files){
			delete(f);
			f.delete();
		}
	}

}
