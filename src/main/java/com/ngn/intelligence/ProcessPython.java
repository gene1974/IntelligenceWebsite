package com.ngn.intelligence;

import java.io.*;

public class ProcessPython {
    /*public static void main(String[] args) throws IOException{
        process_snopes_searching("python");
    }*/
    public static String process_snopes_searching(String searching_text){
        String line = "", result = "";
        try{
            //Process process = Runtime.getRuntime().exec(new String[] {"python", "./Scripts/snopes_searching.py", searching_text});
            Process process = Runtime.getRuntime().exec(new String[] {"python", "src/main/resources/static/Scripts/snopes_searching.py", searching_text});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = err.readLine()) != null) {  
                System.out.println(line);
            }
            err.close();
            while ((line = in.readLine()) != null) {  
                result += line; 
                result += '\n';
            }
            in.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
        return result;
    }
}
