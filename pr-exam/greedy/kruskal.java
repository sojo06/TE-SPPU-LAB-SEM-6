package greedy;

import java.util.*;

// Edge class
class Nodee {
    int src;
    int dest;
    int weight;

    public Nodee(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

public class kruskal {

    // Find parent (DSU)
    public static int find(int parent[], int x) {

        if (parent[x] == x) {
            return x;
        }

        return find(parent, parent[x]);
    }

    // Union operation
    public static void union(int parent[], int x, int y) {

        int parentX = find(parent, x);
        int parentY = find(parent, y);

        parent[parentX] = parentY;
    }

    public static void kruskalMST(List<Nodee> edges, int v) {

        // Sort edges by weight
        Collections.sort(edges,
                (a, b) -> a.weight - b.weight);

        int parent[] = new int[v];

        // Initialize DSU
        for (int i = 0; i < v; i++) {
            parent[i] = i;
        }

        int totalCost = 0;

        System.out.println("Edges in MST:");

        for (Nodee edge : edges) {

            int srcParent = find(parent, edge.src);
            int destParent = find(parent, edge.dest);

            // If cycle not formed
            if (srcParent != destParent) {

                union(parent, edge.src, edge.dest);

                totalCost += edge.weight;

                System.out.println(
                        edge.src + " -> " +
                                edge.dest +
                                " Weight = " +
                                edge.weight
                );
            }
        }

        System.out.println(
                "Total Cost = " + totalCost
        );
    }

    public static void main(String[] args) {

        int v = 5;

        List<Nodee> edges = new ArrayList<>();

        edges.add(new Nodee(0,1,2));
        edges.add(new Nodee(0,3,6));
        edges.add(new Nodee(1,2,3));
        edges.add(new Nodee(1,3,8));
        edges.add(new Nodee(1,4,5));
        edges.add(new Nodee(2,4,7));
        edges.add(new Nodee(3,4,9));

        kruskalMST(edges, v);
    }
}