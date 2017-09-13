package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

import utilities.FileUtil;
import utilities.TextUtil;

public class SockServ {
	
		 public static void main(String[] args) {  
		        new SockServ().init();  
		    }  
		  
		    /* 
		     * 静态代码块初始化服务器生成一个实例 
		     */  
		    static {  
		        try {  
		            serverSocket = new ServerSocket(7007);  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }  
		    }  
		  
		    // 服务器对象  
		    static ServerSocket serverSocket;  
		  
		    /** 
		     * init 不断监听进来的socket 
		     */  
		    public void init() {  
		        try {  
		            while (true) {  
		                Socket socket = serverSocket.accept();  
		                System.out.println("Connected");  
		                treatInfo(socket);  
		            }  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }  
		    }  
		  
		    /** 
		     * 具体逻辑处理 
		     * @param socket 
		     */  
		    public void treatInfo(final Socket socket) {  
		        Thread thread = new Thread(new Runnable() {  
		  
		            @Override  
		            public void run() {  
		                // TODO Auto-generated method stub  
		                try {  
		                    InputStream in = socket.getInputStream();  
//		                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		                    String command = br.readLine();
//		                    System.out.println("command = " + command);

		                    OutputStream out = socket.getOutputStream();  
		                    DataInputStream datains = new DataInputStream(in);  
		                    DataOutputStream dataout = new DataOutputStream(out);  
		                    String command = datains.readUTF();  

		                    
		                    System.out.println("command = " + command);
//
		                    if (command.equals("file")) {  
		                        String file = FileUtil.ArrayToString(FileUtil  
		                                .getFileArray());  
		                        System.out.println("Get FileList");  
		                        dataout.writeUTF(file);  
		                        close(in, out, dataout, datains, socket);  
		                    } else if (command.contains("upload")) {  
//		                        int finished = TextUtil.getFinished(command);  

		                        String filename = TextUtil.getFilename(command);  
		                        System.out.println("filename = " + filename);
		                        upload(filename, socket,  in, out, datains,  
		                                dataout);  
		                    } else {  
		                        System.out.println(command);  
//		                        download(TextUtil.getFilename(command), socket,  
//		                                finished, in, out, datains, dataout);  
		  
		                    }  
		                } catch (IOException e) {  
		                    // TODO Auto-generated catch block  
		                    e.printStackTrace();  
		                }  
		  
		            }  
		  
		        });  
		        thread.start();  
		    }  
		    
		    public void download(String filename, Socket socket, int finished,  
		            InputStream in, OutputStream out, DataInputStream datain,  
		            DataOutputStream dataout) {  
		        try {  
		            RandomAccessFile raf = new RandomAccessFile("F://jar/" + filename,  
		                    "rwd");  
		            byte buff[] = new byte[8192];  
		            raf.skipBytes(finished);  
		            while (true) {  
		                int read = 0;  
		                if (raf != null) {  
		                    read = raf.read(buff);  
		                }  
		                if (read == -1) {  
		                    break;  
		                }  
		                out.write(buff, 0, read);  
		            }  
		            out.flush();  
		            close(in, out, dataout, datain, socket);  
		            System.out.println(filename + " finished");  
		        } catch (IOException e) {  
		            // e.printStackTrace();  
		            System.out.println("遭遇不可抗力，用户强行关闭！");  
		            close(in, out, dataout, datain, socket);  
		        } finally {  
		        }  
		    }  
		    
		    public void upload(String filename, Socket socket, 
		            InputStream in, OutputStream out, DataInputStream datain,  
		            DataOutputStream dataout) {  
		        createFile(filename);  
		        System.out.println("upload->" + filename);  
		        try {  
		        	File file = new File("C:\\xampp\\htdocs\\uploads\\" + filename);
		        	String path = file.getAbsolutePath();
		        	System.out.println("path = " + path);
		        	if (file.exists() == false) {
		        		file.createNewFile();
		        	} else {
//		        		file.delete();
//		        		file.createNewFile();
		        	}
		        	RandomAccessFile raf = new RandomAccessFile(file, "rwd");
//		            RandomAccessFile raf = new RandomAccessFile("C:\\xampp\\htdocs\\uploads"  
//		                    + filename, "rwd");  
//		            raf.skipBytes(finished);  
		            byte buff[] = new byte[8192];  
		            while (true) {  
		                System.out.println("upload");  
		                int read = 0;  
		                if (raf != null) {  
		                    read = in.read(buff);  
		                }  
		                if (read == -1) {  
		                    break;  
		                }  
		                raf.write(buff, 0, read);  
		            }  
		            raf.close();  
		            close(in, out, dataout, datain, socket);  
		            System.out.println(filename + " upload finished");  
		        } catch (FileNotFoundException e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		        } catch (IOException e) {  
		            System.out.println("遭遇不可抗力，用户强行关闭！");  
		            close(in, out, dataout, datain, socket);  
		        }  
		    }  
		  
		    /** 
		     * 生成文件 
		     * @param filename 文件名 
		     */  
		    public void createFile(String filename) {  
		        File file = new File("F:\\mygithub\\SocketServer" + filename);  
		        if (!file.exists()) {  
		            try {  
		                file.createNewFile();  
		            } catch (IOException e) {  
		                // TODO Auto-generated catch block  
		                e.printStackTrace();  
		            }  
		        }  
		    }  	

		    public void close(InputStream in, OutputStream out,  
		            DataOutputStream dataout, DataInputStream datain, Socket socket) {  
		        try {  
		            in.close();  
		            out.close();  
		            dataout.close();  
		            datain.close();  
		            socket.close();  
		        } catch (IOException e) {  
		            // TODO Auto-generated catch block  
		            e.printStackTrace();  
		        }  
		    }  
	}

