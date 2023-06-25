package com.gps.search.gps;
import java.util.List;
import java.util.ArrayList;

public class CityNode {

    private String name;
    private String state;
    private double latitude;
    private double longitude;
    private double heuristic;
    private List<CityNode> neighbors;
    private double g;
    private double f;

    public CityNode(String name, String state, double latitude, double longitude) {
        this.name = name;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.heuristic = 0;
        this.g = 0;
        this.f = 0;
        this.neighbors = new ArrayList<>();
        
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void addNeighbor(CityNode neighbor) {
        this.neighbors.add(neighbor);
    }

    public List<CityNode> getNeighbors() {
        return this.neighbors;
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return this.state;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getHeuristic() {
        return this.heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

}
