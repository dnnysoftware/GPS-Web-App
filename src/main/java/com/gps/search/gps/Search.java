package com.gps.search.gps;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.lang.Math;


public class Search {


    private final String cityFile = "src/main/resources/static/city.dat";
    private final String edgeFile = "src/main/resources/static/edge.dat";
    private Map<String, CityNode> cityData;
    private String startCity;
    private String endCity;

    /**
     * The constructor for all searching which contains the start city name, end city name
     * and the map of city data which is the name of the city as the key and it's corresponding
     * node as the value.
     */
    public Search() {
        this.startCity = null;
        this.endCity = null;
        this.cityData = new HashMap<>();
    }

    /**
     * Sets the start state city
     * @param city the starting city name
     */
    public void setStartCity(String city) {
        this.startCity = city;
    }


    /**
     * Sets the finishing state city
     * @param city the ending city name
     */
    public void setEndCity(String city) {
        this.endCity = city;
    }

    /**
     * Calculates the distance in miles between nodes
     * @param cityA a city
     * @param cityB to another city
     * @return the distance between city nodes based on latitude and longitude
     */
    public static double calculateDistance(CityNode cityA, CityNode cityB) {
        return Math.round(Math.sqrt(Math.pow(cityA.getLatitude() - cityB.getLatitude(), 2) + 
        Math.pow(cityA.getLongitude() - cityB.getLongitude(), 2)) * 100);
    }

    /**
     * Adds the heuristic value to a CityNode based on the latitude and longitude
     * of that particular node to the latitude and longitude of the goal state node
     * to derived the straight line distance heuristic.
     */
    public void addHeuristicToCityNode() {
        CityNode endCity = this.cityData.get(this.endCity);
        for (Map.Entry<String, CityNode> entry : this.cityData.entrySet()) {
            CityNode nodeCity = entry.getValue();
            double hSLD = calculateDistance(nodeCity, endCity);
            nodeCity.setHeuristic(hSLD);
        }
    }

    /**
     * Sort neighbors of each CityNode alphabetically descending for DFS purpose
     */
    public void sortDescendingNeighbors() {
        for (CityNode cityNode : this.cityData.values()) {
            List<CityNode> neighbors = cityNode.getNeighbors();
            neighbors.sort(Comparator.comparing(CityNode::getName, Comparator.reverseOrder()));
        }
    }

    /**
     * Sort neighbors of each CityNode alphabetically acscending for BFS purpose
    */
    public void sortAscendingNeighbors() {
        for (CityNode cityNode : cityData.values()) {
            List<CityNode> neighbors = cityNode.getNeighbors();
            neighbors.sort(Comparator.comparing(CityNode::getName));
        }
    }

    /**
     * Parse the city.dat file based on variable spaces between columns using regex
     * In order to initialize each CityNode into the cityData HashMap.
     */
    public void parseCities() {
        try {
            BufferedReader fr = new BufferedReader(new FileReader(this.cityFile));
            String line;
            while ((line = fr.readLine()) != null) {
                String[] city = line.split("\\s+");
                cityData.put(city[0], new CityNode(city[0], city[1], Double.parseDouble(city[2]), Double.parseDouble(city[3])));
            }
            fr.close();
        } catch(Exception e) {
            System.err.println("File not found: city.dat");
            System.exit(0);
        }
    }

    /**
     * Parse the edge.dat file based on variable spaces between columns using regex
     * In order to update each CityNode neighbors into the cityData HashMap.
     */
    public void parseEdges() {
        try {
            BufferedReader fr = new BufferedReader(new FileReader(this.edgeFile));
            String line;
            while ((line = fr.readLine()) != null) {
                String[] edges = line.split("\\s+");
                CityNode cityA = this.cityData.get(edges[0]);
                CityNode cityB = this.cityData.get(edges[1]);
                cityA.addNeighbor(cityB);
                cityB.addNeighbor(cityA);
            }
            fr.close();
        } catch(Exception e) {
            System.err.println("File not found: edge.dat");
            System.exit(0);
        }
    }


    /**
     * Processes the derived search data into proper string format
     * @param path the path of the city nodes from start to end
     * @param searchType the type of search used
     * @return an organized string
     */
    public String outputProcessing(List<CityNode> path, String searchType) {
        int total = 0;
        String output = "\n" + searchType + "\n";
        for (CityNode node : path) {
            output += (node.getName() + "\n");
        }
        for (int i = 0; i < path.size() - 1; i++) {
            total += calculateDistance(path.get(i), path.get(i+1));
        }
        output += (String.format("That took %d hops to find.\n", path.size() - 1));
        output += (String.format("Total distance = %d miles.\n\n", total));
        return output;
    }

    /**
     * Sets up the BFS algorithm
     * @return the formatted string result for BFS
     */
    public String processBFS() {
        sortAscendingNeighbors();
        CityNode startCity = this.cityData.get(this.startCity);
        CityNode endCity = this.cityData.get(this.endCity);
        BFS bfs = new BFS(startCity, endCity);
        List<CityNode> bfsPath = bfs.traverse();
        String bsfOutput = outputProcessing(bfsPath, "Breadth-First Search Results: ");
        return bsfOutput;
    }

    /**
     * Sets up the DFS algorithm
     * @return the formatted string result for DFS
    */
    public String processDFS() {
        sortDescendingNeighbors();
        CityNode startCity = this.cityData.get(this.startCity);
        CityNode endCity = this.cityData.get(this.endCity);
        DFS dfs = new DFS(startCity, endCity);
        List<CityNode> dfsPath = dfs.traverse();
        String dsfOutput = outputProcessing(dfsPath, "Depth-First Search Results: ");
        return dsfOutput;
    }

    /**
     * Sets up the A* algorithm
     * @return the formatted string result for A*
     */
    public String processAStar() {
        CityNode startCity = this.cityData.get(this.startCity);
        CityNode endCity = this.cityData.get(this.endCity);
        AStar aStar = new AStar(startCity, endCity);
        List<CityNode> aStarPath = aStar.traverse();
        String aStarOutput = outputProcessing(aStarPath, "A* Search Results: ");
        return aStarOutput;
    }

    /**
     * Calls all possible search methods to search by
     * @return a composite string of search results
     */
    public String searchMethods(String type) {
        try {
            String path;
            if (type.equals("BFS")) {
                path = processBFS();
            } else if (type.equals("DFS")) {
                path = processDFS();
            } else {
                path = processAStar();
            }
            return path;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}