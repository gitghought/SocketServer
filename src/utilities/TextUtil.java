package utilities;

public class TextUtil {
      
    /** 
     * ��ȡ�ļ������� 
     * @param value �������ַ��� 
     * @return  �ļ��� 
     */  
    public static String getFilename(String value){  
        String array[] = value.split(" ");  
        return array[1];  
    }  
}
