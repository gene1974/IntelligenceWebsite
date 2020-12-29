package com.ngn.intelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.io.IOException;


@Controller
@SpringBootApplication
public class IntelligenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligenceApplication.class, args);
	}

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String process_controller_home() {
        System.out.println("Welcome!");
        return "00_Platform";
    }


    @RequestMapping(value = "tools", method = RequestMethod.GET)
    public String init_tools() {
        return "01_tools";
    }

    @RequestMapping(value = "tools/Snopes", method = RequestMethod.GET)
    public String init_tools_snopes() {
        return "tools/Snopes";
    }
    @RequestMapping(value = "tools/Snopes/searching", method = RequestMethod.POST)
    @ResponseBody
    public String tools_snopes_searching(@RequestParam("snopes_searching_text") String snopes_searching) throws IOException{
        System.out.println("You searched " + snopes_searching);
        String result = ProcessPython.process_snopes_searching(snopes_searching);
        System.out.println("Searching complete!");
        return result;
    }



    @RequestMapping(value = "tools/Melissa", method = RequestMethod.GET)
    public String init_tools_melissa() {
        return "tools/Melissa";
    }
    @RequestMapping(value = "tools/GoogleTrends", method = RequestMethod.GET)
    public String init_tools_googletrends() {
        return "tools/GoogleTrends";
    }
    @RequestMapping(value = "tools/TalkWalker", method = RequestMethod.GET)
    public String init_tools_talkwalker() {
        return "tools/TalkWalker";
    }

    @RequestMapping(value = "database", method = RequestMethod.GET)
    public String init_database() {
        return "02_database";
    }

    @RequestMapping(value = "service", method = RequestMethod.GET)
    public String init_service() {
        return "03_service";
    }

    @RequestMapping(value = "application", method = RequestMethod.GET)
    public String init_application() {
        return "04_application";
    }

    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public String init_contact() {
        return "05_contact";
    }

}
