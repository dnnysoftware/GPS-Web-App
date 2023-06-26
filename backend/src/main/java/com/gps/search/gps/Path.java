package com.gps.search.gps;

import java.util.List;

public class Path {
    
    private List<CityNode> path;
    private String description;

    public Path(List<CityNode> path, String description) {
        this.path = path;
        this.description = description;
    }

    /**
     * Gets the full path from start to end CityNodes
     * @return the CityNode path
     */
    public List<CityNode> getPath() {
        return this.path;
    }

    /**
     * Gets the description with the path and the amount of miles it took to do this path
     * @return the textual string
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the full path from start to end CityNodes
     * @return the CityNode path
     */
    public void setPath(List<CityNode> newPath) {
        this.path = newPath;
    }

    /**
     * Sets the description with the path and the amount of miles it took to do this path
     * @return the textual string
     */
    public void setDescription(String newDesc) {
        this.description = newDesc;
    }
    


}
