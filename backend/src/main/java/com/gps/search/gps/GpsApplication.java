package com.gps.search.gps;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
@RestController
public class GpsApplication {


    private Search searcher;

    public static void main(String[] args) {
      SpringApplication.run(GpsApplication .class, args);
    }


    @GetMapping(value = "/search", produces = "application/json")
    public String search(@RequestParam(value = "start", defaultValue = "Boston") String start,
                        @RequestParam(value = "end", defaultValue = "Boston") String end,
                        @RequestParam(value = "type", defaultValue = "A_STAR") String type) {
        this.searcher = new Search();
        this.searcher.parseCities();
        this.searcher.parseEdges();
        this.searcher.setStartCity(start);
        this.searcher.setEndCity(end);
        this.searcher.addHeuristicToCityNode();
        Path data = this.searcher.searchMethods(type);
        try {
            CustomPathWrapper wrapper = new CustomPathWrapper(data);
            ObjectMapper mapper = new ObjectMapper();
            String postJson = mapper.writeValueAsString(wrapper);
            return postJson;
        } catch (Exception e) {
            System.out.println("Couldn't format the data to JSON");
            return "";
        }
    }
}

class CustomPathWrapper {
    private String description;
    private List<CityNodeWrapper> path;

    public CustomPathWrapper(Path path) {
        this.description = path.getDescription();
        this.path = path.getPath()
                .stream()
                .map(cityNode -> new CityNodeWrapper(cityNode.getName(), cityNode.getLatitude(), cityNode.getLongitude()))
                .collect(Collectors.toList());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CityNodeWrapper> getPath() {
        return path;
    }

    public void setPath(List<CityNodeWrapper> path) {
        this.path = path;
    }
}

class CityNodeWrapper {
    private String name;
    private double latitude;
    private double longitude;

    public CityNodeWrapper(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
