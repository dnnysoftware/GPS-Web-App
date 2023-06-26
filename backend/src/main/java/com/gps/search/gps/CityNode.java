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

    /**
     * Get the distance from the start CityNode to the current CityNode
     * @return the distance from start to current
     */
    public double getG() {
        return g;
    }

    /**
     * Sets the current distance from the start CityNode to the current CityNode
     * @param g the distance from start to the current CityNode
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * Gets the heuristic function for A* f(n) = g(n) + h(n)
     * @return the heuristic function
     */
    public double getF() {
        return f;
    }

    /**
     * Gets the heuristic function for A* f(n) = g(n) + h(n)
     * @param f the value of the heuristic function
     */
    public void setF(double f) {
        this.f = f;
    }

    /**
     * Adds a neighbor to a CityNode
     * @param neighbor the CityNode neighbor of this CityNode object
     */
    public void addNeighbor(CityNode neighbor) {
        this.neighbors.add(neighbor);
    }

    /**
     * Gets all the neighbors of a particular CityNode
     * @return the CityNode neighbors
     */
    public List<CityNode> getNeighbors() {
        return this.neighbors;
    }

    /**
     * Gets the name of the CityNode
     * @return the state capital name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the state that the capital is in
     * @return the state abreviation
     */
    public String getState() {
        return this.state;
    }

    /**
     * Get the latitude of the CityNode
     * @return the latitude of the CityNode
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Get the longitude of the CityNode
     * @return the longitude of the CityNode
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Gets the hSLD (Straight Line Distance) heuristic from current Node to the end CityNode
     * @return the hSLD of the CityNode
     */
    public double getHeuristic() {
        return this.heuristic;
    }

    /**
     * Set the hSLD (Straight Line Distance) heuristic from current Node to the end CityNode
     * @param heuristic the hSLD of the CityNode
     */
    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

}
