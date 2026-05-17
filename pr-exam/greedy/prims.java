package greedy;

import java.util.*;

class Node {
    public int weight;
    public int vertex;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
}

public class prims {

    public static void primMst(Map<Integer, List<Node>> graph, int v) {

        boolean[] visited = new boolean[v];

        PriorityQueue<Node> pq = new PriorityQueue<>(
                (a, b) -> a.weight - b.weight
        );

        pq.add(new Node(0, 0));

        int totalCost = 0;

        while (!pq.isEmpty()) {

            Node curr = pq.poll();

            if (visited[curr.vertex]) {
                continue;
            }

            visited[curr.vertex] = true;

            totalCost += curr.weight;

            System.out.println(
                    "Vertex: " + curr.vertex +
                            " Weight: " + curr.weight
            );

            for (Node adj : graph.get(curr.vertex)) {

                // Correct condition
                if (!visited[adj.vertex]) {

                    pq.add(new Node(adj.vertex, adj.weight));
                }
            }
        }

        System.out.println("Total Cost = " + totalCost);
    }

    public static void main(String[] args) {

        int v = 5;

        Map<Integer, List<Node>> graph = new HashMap<>();

        for (int i = 0; i < v; i++) {
            graph.put(i, new ArrayList<>());
        }

        // vertex , weight

        graph.get(0).add(new Node(1, 2));
        graph.get(1).add(new Node(0, 2));

        graph.get(0).add(new Node(3, 6));
        graph.get(3).add(new Node(0, 6));

        graph.get(1).add(new Node(2, 3));
        graph.get(2).add(new Node(1, 3));

        graph.get(1).add(new Node(3, 8));
        graph.get(3).add(new Node(1, 8));

        graph.get(1).add(new Node(4, 5));
        graph.get(4).add(new Node(1, 5));

        graph.get(2).add(new Node(4, 7));
        graph.get(4).add(new Node(2, 7));

        graph.get(3).add(new Node(4, 9));
        graph.get(4).add(new Node(3, 9));

        primMst(graph, v);
    }
}