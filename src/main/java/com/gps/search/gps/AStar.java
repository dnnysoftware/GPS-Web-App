package com.gps.search.gps;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar {

    private CityNode startCity;
    private CityNode endCity;

    public AStar(CityNode startCity, CityNode endCity) {
        this.startCity = startCity;
        this.endCity = endCity;
    }

    /**
     * the A* algorithm 
     * @return a list of CityNodes of the order of the path
     */
    public List<CityNode> traverse() {
        PriorityQueue<CityNode> openList = new PriorityQueue<>(Comparator.comparingDouble(node -> node.getF()));
        List<CityNode> closedList = new ArrayList<>();
        Map<CityNode, CityNode> parentMap = new HashMap<>();
    
        startCity.setG(0);
        startCity.setF(startCity.getHeuristic());
        openList.add(startCity);
    
        while (!openList.isEmpty()) {
            CityNode current = openList.poll();
            // If we have reached the end node, construct and return the path
            if (current.equals(endCity)) {
                List<CityNode> path = new ArrayList<>();
                CityNode node = endCity;
                while (node != null) {
                    path.add(0, node);
                    node = parentMap.get(node);
                }
                return path;
            }
            closedList.add(current);
            // Evaluate each neighbor of the current node
            for (CityNode neighbor : current.getNeighbors()) {
                if (closedList.contains(neighbor)) {
                    continue;
                }
                double tentativeG = current.getG() + Search.calculateDistance(current, neighbor);
                boolean tentativeIsBetter = false;
                if (!openList.contains(neighbor)) {
                    tentativeIsBetter = true;
                } else if (tentativeG < neighbor.getG()) {
                    tentativeIsBetter = true;
                }
                if (tentativeIsBetter) {
                    parentMap.put(neighbor, current);
                    neighbor.setG(tentativeG);
                    neighbor.setF(tentativeG + neighbor.getHeuristic());
                    if (openList.contains(neighbor)) {
                        // Update the priority of the node in the open list
                        openList.remove(neighbor);
                    }
                    openList.add(neighbor);
                }
            }
        }
        // If we get here, there is no path from start to end
        return null;
    }

}
