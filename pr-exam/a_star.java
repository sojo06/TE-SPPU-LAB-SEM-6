import java.util.*;
class Node implements Comparable<Node>{
    public char vertex;
    public int cost;
    Node(char vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }
    public int compareTo(Node b){
        return this.cost - b.cost;
    }
}
public class a_star {
    public static void main(String[] args) {
        Map<Character,List<Node>>graph = new HashMap<>();
        graph.put('A', Arrays.asList(
                new Node('B', 1),
                new Node('C', 3)
        ));

        graph.put('B', Arrays.asList(
                new Node('D', 3),
                new Node('E', 6)
        ));

        graph.put('C', Arrays.asList(
                new Node('F', 5)
        ));

        graph.put('D', Arrays.asList(
                new Node('G', 2)
        ));

        graph.put('E', Arrays.asList(
                new Node('G', 1)
        ));

        graph.put('F', Arrays.asList(
                new Node('G', 2)
        ));
        Map<Character,Integer> h = new HashMap<>();
        h.put('A',7);
        h.put('B',6);
        h.put('C',4);
        h.put('D',2);
        h.put('E',1);
        h.put('F',2);
        h.put('G',0);

        PriorityQueue<Node>pq = new PriorityQueue<>();

        pq.add(new Node('A',h.get('A')));

        // actual cost from start to current node
        Map<Character, Integer> gCost = new HashMap<>();

        // used for prinitng answer
        Map<Character, Character> parent = new HashMap<>();

        gCost.put('A',0);
        parent.put('A','-');
        char goal = 'G';

        // current node is u
        // current node neighb is v
        while(!pq.isEmpty()){
            Node curr = pq.poll();
            char u = curr.vertex;
            if(u==goal) break;
            for(Node adj:graph.get(u)){
                char v = adj.vertex;
                int newcost = gCost.get(u) + adj.cost;
                if(!gCost.containsKey(v) || newcost < gCost.get(v)){
                    gCost.put(v,newcost);
                    int fcost = newcost + h.get(v);
                    pq.add(new Node(v,fcost));
                    parent.put(v,u);
                }
            }
        }
        List<Character>path = new ArrayList<>();
        char current = goal;
        while(current != '-'){
            path.add(current);
            current = parent.get(current);
        }
        Collections.reverse(path);
        System.out.println("Shortest Path:");

        for (int i = 0; i < path.size(); i++) {

            System.out.print(path.get(i));

            if (i != path.size() - 1) {
                System.out.print(" -> ");
            }
        }

        System.out.println();

        System.out.println("Total Cost: " + gCost.get(goal));

    }
}
