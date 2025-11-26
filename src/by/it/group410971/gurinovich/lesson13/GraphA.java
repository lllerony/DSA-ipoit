package by.it.group410971.gurinovich.lesson13;

import java.util.*;

public class GraphA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Map<String, List<String>> graph = parseInput(input);

        List<String> result = topologicalSort(graph);

        for (String node : result) {
            System.out.print(node + " ");
        }
    }

    private static Map<String, List<String>> parseInput(String input) {
        Map<String, List<String>> graph = new HashMap<>();

        String[] edges = input.split(",\\s*");

        for (String edge : edges) {
            String[] parts = edge.split("\\s*->\\s*");
            if (parts.length == 2) {
                String from = parts[0].trim();
                String to = parts[1].trim();

                graph.putIfAbsent(from, new ArrayList<>());
                graph.putIfAbsent(to, new ArrayList<>());

                graph.get(from).add(to);
            }
        }

        return graph;
    }

    private static List<String> topologicalSort(Map<String, List<String>> graph) {
        List<String> result = new ArrayList<>();

        Map<String, Integer> inDegree = new HashMap<>();

        for (String node : graph.keySet()) {
            inDegree.put(node, 0);
        }

        for (String node : graph.keySet()) {
            for (String neighbor : graph.get(node)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        PriorityQueue<String> queue = new PriorityQueue<>();

        for (String node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);

            for (String neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }
}
