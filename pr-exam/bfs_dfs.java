import java.lang.classfile.instruction.LineNumber;
import java.util.*;
public class bfs_dfs {
    public Map<Integer,ArrayList<Integer>>graph = new HashMap<>();
    public void addEdge(int src,int dest){
        graph.putIfAbsent(src,new ArrayList<>());
        graph.putIfAbsent(dest,new ArrayList<>());
        graph.get(src).add(dest);
        graph.get(dest).add(src);
    }

    public void dfs(int vertex,Set<Integer>visited){
        visited.add(vertex);
        System.out.print(vertex + " ");
        for(int adj:graph.get(vertex)){
            if(!visited.contains(adj)){
                dfs(adj,visited);
            }
        }
    }
    public void bfs(int start){
        Queue<Integer>q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        q.add(start);
        visited.add(start);
        while(!q.isEmpty()){
            int curr = q.poll();
            System.out.println(curr +" ");
            for(int adj:graph.get(curr)){
                if(!visited.contains(adj)){
                    q.add(adj);
                    visited.add(adj);
                }
            }

        }
    }



    public static void main(String[] args) {
        bfs_dfs g = new bfs_dfs();
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);

        System.out.println("Depth First Search (DFS):");

        Set<Integer> visited = new HashSet<>();

        g.dfs(0, visited);

        System.out.println();

        System.out.println("Breadth First Search (BFS):");

        g.bfs(0);

    }
}
