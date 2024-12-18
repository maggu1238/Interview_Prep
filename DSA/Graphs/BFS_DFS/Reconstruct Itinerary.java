/*Given a list of airline tickets where tickets[i] = [fromi, toi] represent a departure airport and an arrival airport of a single flight, reconstruct the itinerary in the correct order and return it.

The person who owns these tickets always starts their journey from "JFK". Therefore, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should prioritize the one with the smallest lexical order when considering a single string.

 You may assume all tickets form at least one valid itinerary. You must use all the tickets exactly once.*/

 

import java.util.*;

class Solution {
    private void dfs(String node, List<String> res, Map<String, List<String>> adjList) {
        while (adjList.containsKey(node) && !adjList.get(node).isEmpty()) {
            // Remove the first destination (smallest lexicographically due to sorting)
            String neighbour = adjList.get(node).remove(0);
            dfs(neighbour, res, adjList);
        }
        res.add(node);
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        Map<String, List<String>> adjList = new HashMap<>();

        // Build adjacency list
        for (List<String> ticket : tickets) {
            String src = ticket.get(0);
            String dst = ticket.get(1);
            adjList.putIfAbsent(src, new ArrayList<>());
            adjList.get(src).add(dst);
        }

        // Sort the destinations for each source to ensure lexicographical order
        for (List<String> destinations : adjList.values()) {
            Collections.sort(destinations);
        }

        // Perform DFS starting from "JFK"
        dfs("JFK", res, adjList);

        // Reverse the result to get the correct itinerary order
        Collections.reverse(res);
        return res;
    }
}
