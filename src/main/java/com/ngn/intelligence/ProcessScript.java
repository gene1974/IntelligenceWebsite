package com.ngn.intelligence;

import java.io.*;

public class ProcessScript {
    /*
    @param  params 调用的命令行参数
    @return result 脚本stdout的输出重定向，最终显示在html
    @       sysout输出在控制台
    */
    public static String process_scripts(String[] params){
        //System.err.println("[INFO]Begin getting " + params[2] + "...");
        String line = "";
        StringBuilder result = new StringBuilder();
        try{
            Process process = Runtime.getRuntime().exec(params);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = err.readLine()) != null) {  
                System.err.println("stderr: " + line);
            }
            err.close();
            while ((line = in.readLine()) != null) { 
                result.append(line); 
                result.append('\n');
                //System.err.println("stdout: " + line);
            }
            in.close();   
        } catch (IOException e) {
			e.printStackTrace();
		}
        //System.err.println("[SUCCESS]Processing " + params[2] + " complete!");
        return result.toString();
    }

    /*
    public static String process_cache(String[] params){
        String file_name = params[2];
        File file = new File("./src/main/python/result" + file_name);
        if(file.exists()){
            try {
                FileInputStream fis=new FileInputStream("WynnNi.txt");
                BufferedInputStream bis=new BufferedInputStream(new FileInputStream("WynnNi.txt"));
                String content=null;
                 //自定义缓冲区
                byte[] buffer=new byte[10240];
                int flag=0;
                while((flag=bis.read(buffer))!=-1){
                    content+=new String(buffer, 0, flag);
                }
                System.out.println(content);
                //关闭的时候只需要关闭最外层的流就行了
                bis.close();
            } catch (Exception e) {
                    e.printStackTrace();
        }
        if (!file.exists()) {
            File file1 = new File("D:\\xxx\\sss");
            if(!file1 .exists()) {
                file1.mkdirs();//创建目录
                System.out.println("测试文件夹不存在");
            }
            File file2 = new File("D:\\xxx\\sss\\xx.txt");
            if(!file2 .exists()) {
                file2.createNewFile();//创建文件
                System.out.println("测试文件不存在");
            }
        return "";
    }*/
}
