package com.ngn.intelligence;

import java.io.*;
import java.util.Date;

public class ProcessPython {
    /*public static void main(String[] args) throws IOException{
        process_scripts(new String[] {"python", "/mnt/c/Users/huang/Desktop/Graduation/Platform/intelligence/src/main/python/Melissa.py", "zipcode", "02138"});
        //process_scripts(new String[] {"python", "/mnt/c/Users/huang/Desktop/Graduation/Platform/intelligence/src/main/python/Snopes.py", "latest"});
    }*/

    public static String process_snopes_searching(String searching_text){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "searching", searching_text});
    }
    public static String process_snopes_latest(){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "latest"});
    }
    public static String process_snopes_hot(){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "hot"});
    }
    public static String process_snopes_fact(){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "fact"});
    }
    public static String process_snopes_collections(){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "collections"});
    }
    public static String process_snopes_archives(){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "archives"});
    }
    public static String process_snopes_news(){
        return process_scripts(new String[] {"python", "src/main/python/Snopes.py", "news"});
    }
    public static String process_melissa_zipcode(String zipcode){
        return process_scripts(new String[] {"python", "src/main/python/Melissa.py", "zipcode", zipcode});
    }

    //sysout输出在控制台
    //result显示在html
    public static String process_scripts(String[] params){
        System.out.println("[INFO]Begin getting " + params[2] + "...");
        String line = "";
        StringBuilder result = new StringBuilder();
        Date now = new Date();
        try{
            Process process = Runtime.getRuntime().exec(params);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = err.readLine()) != null) {  
                System.out.println(line);
            }
            err.close();
            while ((line = in.readLine()) != null) {   
                //System.out.println(line);
                result.append(line); 
                result.append('\n');
            }
            in.close();        
            /*if(process.exitValue() != 0){
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = err.readLine()) != null) {  
                    System.out.println(line);
                }
                err.close();
                System.out.println("[Error]Error occurs in python service.");
                return "Error occurs in python service.";
            }*/
        } catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("[SUCCESS]Processing " + params[2] + " complete!");
        return result.toString();
    }

    /*public static String process_file(String params){
        return "";
    }*/
}