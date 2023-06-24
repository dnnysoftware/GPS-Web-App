package com.gps.search.gps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFS {

    private CityNode startCity;
    private CityNode endCity;

    public BFS(CityNode startCity, CityNode endCity) {
        this.startCity = startCity;
        this.endCity = endCity;
    }

    /**
     * the bfs algorithm 
     * @return a list of CityNodes of the order of the path
     */
    public List<CityNode> traverse() {
        Queue<CityNode> queue = new LinkedList<>();
        Map<CityNode, CityNode> parentMap = new HashMap<>();

        queue.add(startCity);
        parentMap.put(startCity, null);

        while (!queue.isEmpty()) {
            CityNode current = queue.poll();

            if (current == endCity) {
                break;
            }

            for (CityNode neighbor : current.getNeighbors()) {
                if (!parentMap.containsKey(neighbor)) {
                    queue.add(neighbor);
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
