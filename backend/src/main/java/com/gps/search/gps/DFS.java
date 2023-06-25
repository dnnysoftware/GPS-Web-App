package com.gps.search.gps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DFS {

    private CityNode startCity;
    private CityNode endCity;

    public DFS(CityNode startCity, CityNode endCity) {
        this.startCity = startCity;
        this.endCity = endCity;
    }

    /**
     * the dfs algorithm 
     * @return a list of CityNodes of the order of the path
     */
    public List<CityNode> traverse() {
        Stack<CityNode> stack = new Stack<>();
        Map<CityNode, CityNode> parentMap = new HashMap<>();

        stack.push(startCity);
        parentMap.put(startCity, null);

        while (!stack.isEmpty()) {
            CityNode current = stack.pop();

            if (current == endCity) {
                break;
            }

            for (CityNode neighbor : current.getNeighbors()) {
                if (!parentMap.containsKey(neighbor)) {
                    stack.push(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        List<CityNode> path = new ArrayList<>();
        CityNode current = endCity;

        while (current != null) {
            path.add(0, current);
            current = parentMap.get(current);
        }

        return path;
    }

    public List<CityNode> getPath() {
        List<CityNode> path = traverse();
        if (!path.contains(endCity)) { 
            return null;
        }
        return path;
    }
}
