import java.util.*;

public class AStar3x3 {

    static int[][] goal = {
        {1, 2, 3},
        {8, -1, 4},
        {7, 6, 5}
    };

    static class Node {
        int[][] state;
        int g, h, f;
        Node parent;

        Node(int[][] s, int g, Node p) {
            state = s;
            this.g = g;
            h = heuristic(s);
            f = g + h;
            parent = p;
        }
    }

    public static void main(String[] args) {

        int[][] start = {
            {2, 8, 3},
            {1, 6, 4},
            {7, -1, 5}
        };

        aStar(start);
    }

    static void aStar(int[][] start) {

        ArrayList<Node> open = new ArrayList<>();
        HashSet<String> closed = new HashSet<>();

        open.add(new Node(start, 0, null));

        while (!open.isEmpty()) {

            // choose node with minimum f
            Node current = open.get(0);
            for (Node n : open)
                if (n.f < current.f)
                    current = n;

            open.remove(current);

            if (Arrays.deepEquals(current.state, goal)) {
                printPath(current);
                return;
            }

            closed.add(key(current.state));

            int[] blank = findBlank(current.state);
            int x = blank[0], y = blank[1];

            int[][] moves = {
                {x - 1, y}, // up
                {x + 1, y}, // down
                {x, y - 1}, // left
                {x, y + 1}  // right
            };

            for (int[] m : moves) {
                int nx = m[0], ny = m[1];

                if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {

                    int[][] next = copy(current.state);

                    // swap BLANK with neighbour
                    next[x][y] = next[nx][ny];
                    next[nx][ny] = -1;

                    if (!closed.contains(key(next))) {
                        open.add(new Node(next, current.g + 1, current));
                    }
                }
            }
        }
    }

    static int heuristic(int[][] s) {
        int h = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(s[i][j]!=goal[i][j]) h++;
            }
        }
        return h;
    }

    static int[] findBlank(int[][] s) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (s[i][j] == -1)
                    return new int[]{i, j};
        return null;
    }

    static int[][] copy(int[][] s) {
        int[][] c = new int[3][3];
        for (int i = 0; i < 3; i++)
            c[i] = s[i].clone();
        return c;
    }

    static String key(int[][] s) {
        return Arrays.deepToString(s);
    }

    static void printPath(Node n) {
        if (n == null) return;
        printPath(n.parent);
        print(n.state);
        System.out.println("f(n)= "+n.f + " g(n)= "+ n.g + "      h(n)= "+n.h);


    }

    static void print(int[][] s) {
        System.out.println("----");
        for (int[] r : s) {
            for (int v : r)
                System.out.print(v + " ");
            System.out.println();
        }
    }
}
