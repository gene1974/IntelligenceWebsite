package com.ngn.intelligence;


import java.io.*;
import org.grobid.core.*;
import org.grobid.core.data.*;
import org.grobid.core.factory.*;
import org.grobid.core.utilities.*;
import org.grobid.core.engines.Engine;
import org.grobid.core.main.GrobidHomeFinder;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class UseGrobid{
    public static boolean upload_file(MultipartFile file) {
        String filePath = "./file/";
        System.err.println("[INFO]开始上传文件...");
        try {
            write_file(file.getBytes(), filePath, file.getOriginalFilename());
        } catch (Exception e) {
        	e.printStackTrace();
        	System.err.println("[INFO]文件上传失败!");
        	return false;
        }
        System.err.println("[INFO]文件上传成功!");
        return true;
    }
    public static void write_file(byte[] file, String filePath, String fileName) throws Exception { 
        File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
    public static JSON header(MultipartFile file){
        try {
            String filePath = "./file/";
            upload_file(file);
            String pdfPath = CheckOCR(filePath, file.getOriginalFilename());
            String pGrobidHome = "/home/gene/NGN/Graduation/Platform/intelligence/grobid/grobid-home";
            GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
            GrobidProperties.getInstance(grobidHomeFinder);
            System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
            Engine engine = GrobidFactory.getInstance().createEngine();
            BiblioItem resHeader = new BiblioItem();

            String tei = engine.processHeader(pdfPath, 1, resHeader);
            BufferedWriter out = new BufferedWriter(new FileWriter(pdfPath + ".header.xml"));
            out.write(tei);
            out.close();
            String html = ProcessScript.process_scripts(new String[] {"python", "src/main/python/Parser.py", "header", pdfPath + ".header.xml"});
            JSONObject json = new JSONObject();
            json.put("html", html);
            //json.put("html", "test");
            return json;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        } 
    }
    /*public static JSON fulltext(String pdfPath){
        try {
            String pGrobidHome = "/home/gene/NGN/Graduation/Platform/intelligence/grobid/grobid-home";
            GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
            GrobidProperties.getInstance(grobidHomeFinder);
            System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
            Engine engine = GrobidFactory.getInstance().createEngine();
            BiblioItem resFulltext = new BiblioItem();

            String tei = engine.processFulltext(pdfPath, 1, resFulltext);
            BufferedWriter out = new BufferedWriter(new FileWriter(pdfPath + ".fulltext.xml"));
            out.write(tei);
            out.close();
            String html = ProcessScript.process_scripts(new String[] {"python", "src/main/python/Parser.py", "fulltext", pdfPath + ".fulltext.xml"});
            JSONObject json = new JSONObject();
            json.put("html", html);
            return json;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        } 
    }
    public static JSON reference(String pdfPath){
        try {
            String pGrobidHome = "/home/gene/NGN/Graduation/Platform/intelligence/grobid/grobid-home";
            GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
            GrobidProperties.getInstance(grobidHomeFinder);
            System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());
            Engine engine = GrobidFactory.getInstance().createEngine();
            BiblioItem resHeader = new BiblioItem();

            String tei = engine.processRawReference(pdfPath, 1);
            BufferedWriter out = new BufferedWriter(new FileWriter(pdfPath + ".header.xml"));
            out.write(tei);
            out.close();
            String html = ProcessScript.process_scripts(new String[] {"python", "src/main/python/Parser.py", "header", pdfPath + ".header.xml"});
            JSONObject json = new JSONObject();
            json.put("html", html);
            return json;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        } 
    }*/

    public static String CheckOCR(String file_path, String pdf_name){
        System.err.println("[INFO] 检查PDF格式...");
        String result = ProcessScript.process_scripts(new String[] {"python", "src/main/python/PdfChecker.py", file_path + pdf_name});
        System.out.println(result);
        if(result.equals("Image-only\n")){
            System.err.println("[INFO] 开始应用OCR...");
            ProcessScript.process_scripts(new String[] {"ocrmypdf", file_path + pdf_name, file_path + "Ocr_" + pdf_name});
            System.err.println("[INFO] 应用OCR成功！");
            return file_path + "Ocr_" + pdf_name;
        }
        else{
            System.err.println("[INFO] PDF格式检查成功！");
            return file_path + pdf_name;
        }
    }
}

class html{

}