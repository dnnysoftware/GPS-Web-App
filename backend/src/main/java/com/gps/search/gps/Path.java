package com.gps.search.gps;

import java.util.List;

public class Path {
    
    private List<CityNode> path;
    private String description;

    public Path(List<CityNode> path, String description) {
        this.path = path;
        this.description = description;
    }

    public List<CityNode> getPath() {
        return this.path;
    }

    public String getDescription() {
        return this.description;
    }

    public void setPath(List<CityNode> newPath) {
        this.path = newPath;
    }

    public void setDescription(String newDesc) {
        this.description = newDesc;
    }
    


}
