package utilities;

import java.io.File;

public class FileUtil {
	/** 
     * @author LoveQuietly 
     */  
      
      
    /** 
     * �õ�������ļ�����Ϣ�õ��������� xx.exe 1235 ǰ�����ļ������������ļ����� 
     * @return �ļ���Ϣ���� 
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
     * �Զ���Ľ�����ת���ַ��� 
     * @param array ���� 
     * @return  �ַ��� 
     */  
    public static String ArrayToString(String array[]) {  
        String result = new String("");  
        for (int i = 0; i < array.length; i++) {  
            result += array[i] + " ";  
        }  
        return result;  
    }  
}
