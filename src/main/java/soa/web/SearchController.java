package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        HashMap<String, Object> header = new HashMap<>();
        if(q.contains("max")) {
            int start = q.indexOf("max");
            int numIndex = start + 4;
            String num = q.substring(numIndex);
            int number = Integer.parseInt(num); //number has the max limit
            q = q.substring(0, start-1);        //q contains only the word asked
            header.put("CamelTwitterCount", number);
        }
        header.put("CamelTwitterKeywords",q);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", header);

    }
}