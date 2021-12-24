package com.ngn.intelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSON;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;


@Controller
@SpringBootApplication
public class IntelligenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligenceApplication.class, args);
	}

    @GetMapping(value = "")
    public String init() {
        System.out.println("[INFO]Welcome!");
        return "00_Platform";
    }

    @GetMapping(value = "home")
    public String init_home() {
        System.out.println("[INFO]Welcome!");
        return "00_Platform";
    }


    @GetMapping(value = "tools")
    public String init_tools() {
        return "01_tools";
    }

    @GetMapping(value = "tools/Snopes")
    public String init_tools_snopes() {
        return "tools/Snopes";
    }
    
    @PostMapping(value = "tools/Snopes/searching")
    @ResponseBody
    public String tools_snopes_searching(@RequestParam("snopes_searching_text") String snopes_searching) throws IOException{
        return UseCrawler.process_snopes_searching(snopes_searching);
    }
    @PostMapping(value = "tools/Snopes/latest")
    @ResponseBody
    public String tools_snopes_latest(){
        return UseCrawler.process_snopes_latest();
    }
    @PostMapping(value = "tools/Snopes/hot")
    @ResponseBody
    public String tools_snopes_hot(){
        return UseCrawler.process_snopes_hot();
    }
    @PostMapping(value = "tools/Snopes/fact")
    @ResponseBody
    public String tools_snopes_fact(){
        return UseCrawler.process_snopes_fact();
    }
    @PostMapping(value = "tools/Snopes/collections")
    @ResponseBody
    public String tools_snopes_collections(){
        return UseCrawler.process_snopes_collections();
    }
    @PostMapping(value = "tools/Snopes/news")
    @ResponseBody
    public String tools_snopes_news(){
        return UseCrawler.process_snopes_news();
    }
    @PostMapping(value = "tools/Snopes/archives")
    @ResponseBody
    public String tools_snopes_archives(){
        return UseCrawler.process_snopes_archives();
    }



    @GetMapping(value = "tools/Map")
    public String init_tools_map() {
        return "tools/Map";
    }
    @PostMapping(value = "tools/Map/chn")
    @ResponseBody
    public String tools_map_chn(@RequestParam("chn_zipcode") String zipcode) throws IOException{
        return UseCrawler.process_map_chn(zipcode);
    }
    @PostMapping(value = "tools/Map/us")
    @ResponseBody
    public String tools_map_us(@RequestParam("us_zipcode") String zipcode) throws IOException{
        return UseCrawler.process_map_us(zipcode);
    }


    //Service
    @GetMapping(value = "service")
    public String init_service() {
        return "03_service";
    }
    //Grobid
    @GetMapping(value = "service/Grobid")
    public String init_service_grobid() {
        return "service/Grobid";
    }
    @PostMapping(value = "service/Grobid/header")
    @ResponseBody
    public JSON service_grobid_header(MultipartFile file) {
        return UseGrobid.header(file);
    }
    /*@PostMapping(value = "service/Grobid/fulltext")
    @ResponseBody
    public JSON service_grobid_fulltext(MultipartFile file) {
        return UseGrobid.fulltext(file);
    }
    @PostMapping(value = "service/Grobid/reference")
    @ResponseBody
    public JSON service_grobid_reference(MultipartFile file) {
        return UseGrobid.reference(file);
    }*/
    


    @GetMapping(value = "database")
    public String init_database() {
        return "02_database";
    }
    @GetMapping(value = "tools/GoogleTrends")
    public String init_tools_googletrends() {
        return "tools/GoogleTrends";
    }
    @GetMapping(value = "tools/TalkWalker")
    public String init_tools_talkwalker() {
        return "tools/TalkWalker";
    }


    @GetMapping(value = "application")
    public String init_application() {
        return "04_application";
    }

    @GetMapping(value = "contact")
    public String init_contact() {
        return "05_contact";
    }

}
