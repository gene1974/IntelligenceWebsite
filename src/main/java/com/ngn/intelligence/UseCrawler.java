package com.ngn.intelligence;

//import java.io.*;
//import java.util.Date;

public class UseCrawler {
    /*public static void main(String[] args) throws IOException{
        process_scripts(new String[] {"python", "/mnt/c/Users/huang/Desktop/Graduation/Platform/intelligence/src/main/python/Melissa.py", "zipcode", "02138"});
        //process_scripts(new String[] {"python", "/mnt/c/Users/huang/Desktop/Graduation/Platform/intelligence/src/main/python/Snopes.py", "latest"});
    }*/

    public static String process_snopes_searching(String searching_text){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "searching", searching_text});
    }
    public static String process_snopes_latest(){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "latest"});
    }
    public static String process_snopes_hot(){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "hot"});
    }
    public static String process_snopes_fact(){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "fact"});
    }
    public static String process_snopes_collections(){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "collections"});
    }
    public static String process_snopes_archives(){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "archives"});
    }
    public static String process_snopes_news(){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Snopes.py", "news"});
    }
    public static String process_map_us(String zipcode){
        return ProcessScript.process_scripts(new String[] {"python", "src/main/python/Melissa.py", "zipcode", zipcode});
    }
    public static String process_map_chn(String zipcode){
        return "";
        //return process_scripts(new String[] {"python", "src/main/python/Melissa.py", "zipcode", zipcode});
    }
}