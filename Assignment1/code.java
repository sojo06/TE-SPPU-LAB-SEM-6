import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class code {

    public static void DFS(Map<Integer, ArrayList<Integer>> adj,
                           ArrayList<Boolean> visited,
                           int node) {

        visited.set(node, true);
        System.out.println( node);

        for (Integer child : adj.get(node)) {
            if (!visited.get(child)) {
                DFS(adj, visited, child);
            }
        }
    }

    public static void recursiveBFS(Map<Integer, ArrayList<Integer>> adj,
                                    ArrayList<Boolean> visited,
                                    Queue<Integer> q,
                                    int level) {

        if (q.isEmpty()) return;

        int size = q.size();
        System.out.print("Level " + level + ": ");

        for (int i = 0; i < size; i++) {
            int node = q.poll();
            System.out.print(node + " ");

            for (Integer child : adj.get(node)) {
                if (!visited.get(child)) {
                    visited.set(child, true);
                    q.add(child);
                }
            }
        }
        System.out.println();

        recursiveBFS(adj, visited, q, level + 1);
    }

    public static void BFS(Map<Integer, ArrayList<Integer>> adj,
                           ArrayList<Boolean> visited,
                           int start) {

        Queue<Integer> q = new LinkedList<>();
        visited.set(start, true);
        q.add(start);

        recursiveBFS(adj, visited, q, 0);
    }

    public static ArrayList<Boolean> resetVisited(int n) {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            visited.add(false);
        }
        return visited;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = 15; // 15 comments
        Map<Integer, ArrayList<Integer>> adj = new HashMap<>();

        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(0).add(3);

        adj.get(1).add(4);
        adj.get(1).add(5);

        adj.get(2).add(6);
        adj.get(2).add(7);

        adj.get(4).add(8);
        adj.get(5).add(9);
        adj.get(5).add(10);

        adj.get(6).add(11);
        adj.get(8).add(12);

        adj.get(3).add(13);
        adj.get(13).add(14);

        int choice;

        do {
            System.out.println("\n--- Reddit Comment System (15 Nodes) ---");
            System.out.println("1. Display comments level-wise (BFS)");
            System.out.println("2. Deeply analyze a comment chain (DFS)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nComments shown using BFS:");
                    BFS(adj, resetVisited(n), 0);
                    break;

                case 2:
                    System.out.print("\nEnter comment ID to analyze: ");
                    int start = sc.nextInt();
                    System.out.println("Deep analysis using DFS:");
                    DFS(adj, resetVisited(n), start);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);

        sc.close();
    }
}
