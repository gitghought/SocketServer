package utilities;

public class TextUtil {
      
    /** 
     * 获取文件的名字 
     * @param value 解析的字符串 
     * @return  文件名 
     */  
    public static String getFilename(String value){  
        String array[] = value.split(" ");  
        return array[1];  
    }  
}
