package utilities;

import java.io.File;

public class FileUtil {
	/** 
     * @author LoveQuietly 
     */  
      
      
    /** 
     * 得到具体的文件夹信息得到的类似于 xx.exe 1235 前面是文件名，后面是文件长度 
     * @return 文件信息数组 
     */  
    public static String[] getFileArray() {  
        File file = new File("F://jar/");  
        String array[] = file.list();  
        for (int i = 0; i < array.length; i++) {  
            File mfile = new File("F://jar/" + array[i]);  
            array[i] += " " + mfile.length();  
        }  
        return array;  
    }  
  
    /** 
     * 自定义的将数组转成字符串 
     * @param array 数组 
     * @return  字符串 
     */  
    public static String ArrayToString(String array[]) {  
        String result = new String("");  
        for (int i = 0; i < array.length; i++) {  
            result += array[i] + " ";  
        }  
        return result;  
    }  
}
