package com.gps.search.gps;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.lang.Math;


public class Search {


    private final String cityFile = "city.dat";
    private final String edgeFile = "edge.dat";
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
     * Reads an input file containing only two rows or takes in input via stdin
     * @param inputFile the name of the input file
     */
    public void readInput(String inputFile) {
        try {
            if (!inputFile.equals("-")){
                BufferedReader fr = new BufferedReader(new FileReader(inputFile));
                String line;
                int lineCount = 0;
                ArrayList<String> startEnd = new ArrayList<>();
                while ((line = fr.readLine()) != null && lineCount < 2) {
                    startEnd.add(line);
                    lineCount++;
                }
                fr.close();
                setStartCity(startEnd.get(0));
                setEndCity(startEnd.get(1));
            } else {
                CityNode scn = null;
                CityNode ecn = null;
                String ec = null;
                String sc = null;
                try {
                    System.out.print("Enter your starting city: ");
                    Scanner s = new Scanner(System.in);
                    sc = s.nextLine().trim();
                    System.out.print("Enter your ending city: ");
                    Scanner e = new Scanner(System.in);
                    ec = e.nextLine().trim();
                    s.close();
                    e.close();
                    scn = this.cityData.get(sc);
                    ecn = this.cityData.get(ec);
                    setStartCity(scn.getName());
                    setEndCity(ecn.getName());
                } catch(Exception e) {
                    if (scn != null) {
                        try {
                            scn.getName();
                            System.err.println("No such city: " + ec);
                        } catch (Exception ex) {
                            System.err.println("No such city: " + sc);
                        }
                    } else if (ecn != null) {
                        try {
                            ecn.getName();
                            System.err.println("No such city: " + sc);
                        } catch (Exception ex) {
                            System.err.println("No such city: " + ec);
                        }
                    } else {
                        System.err.println("No such cities: " + sc + " and " + ec);
                    }
                    System.exit(0);
                }
            }
        } catch(Exception e) {
            System.err.println("File not found: %s".formatted(inputFile));
            System.exit(0);
        }
        
    }
    
    /**
     * Writes data to an output file or to stdout 
     * @param outputFile the outfile being written to
     * @param data the data that is being written
     */
    public void writeOutput(String outputFile, String data) {
        try {
            if (!outputFile.equals("-")) {
                File file = new File(outputFile);
                if (!file.exists()) {
                    file.createNewFile();
                }
                BufferedWriter fw = new BufferedWriter(new FileWriter(file));
                fw.write(data);
                fw.close();
            } else {
                System.out.println(data);
            }
        } catch(Exception e) {
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
    public String searchMethods() {
        try {
            String bsfOutput = processBFS();
            String dsfOutput = processDFS();
            String aStarOutput = processAStar();
            return bsfOutput + dsfOutput + aStarOutput;
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return "";
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            Search searcher = new Search();
            searcher.parseCities();
            searcher.parseEdges();
            searcher.readInput(args[0]);
            searcher.addHeuristicToCityNode();
            String output = searcher.searchMethods();
            searcher.writeOutput(args[1], output);
        } else {
            System.err.println("Missing one or both command line arguments");
            System.exit(0);
        }
    }

}