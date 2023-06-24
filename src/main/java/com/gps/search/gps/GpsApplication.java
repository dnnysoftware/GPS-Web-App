package com.gps.search.gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class GpsApplication {


    private Search searcher;

    public static void main(String[] args) {
      SpringApplication.run(GpsApplication .class, args);
    }


    @GetMapping("/search")
    public String search(@RequestParam(value = "start", defaultValue = "Boston") String start,
                        @RequestParam(value = "end", defaultValue = "Boston") String end,
                        @RequestParam(value = "type", defaultValue = "A_STAR") String type) {
      this.searcher = new Search();
      this.searcher.parseCities();
      this.searcher.parseEdges();
      this.searcher.setStartCity(start);
      this.searcher.setEndCity(end);
      this.searcher.addHeuristicToCityNode();
      String data = this.searcher.searchMethods(type);
      return String.format(data);
    }

}
