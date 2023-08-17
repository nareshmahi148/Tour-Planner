import java.util.*;

class edge {
    int source;
    int destination;
    int weight;

    edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class gr {
    int vertices;
    final static int INF = Integer.MAX_VALUE / 10;
    int[][] adj_matrix;
    int[][] dist_matrix;
    int[][] next_node_matrix;
    int fin_dest;
    int min_cost = Integer.MAX_VALUE;
    int N;
    ArrayList<Integer> final_ar;

    gr(int vertices) {
        N = vertices;
        this.vertices = vertices;
        adj_matrix = new int[N][N];
        for (int i = 0; i < N; i++)
            Arrays.fill(adj_matrix[i], INF);
    }

    void addEgde(int source, int destination, int weight) {
        adj_matrix[source][destination] = weight;
        adj_matrix[destination][source] = weight;
    }

    void floyd_warshall() {
        dist_matrix = new int[N][N];
        next_node_matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(next_node_matrix[i], -1);
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                dist_matrix[i][j] = adj_matrix[i][j];
        for (int i = 0; i < N; i++) {
            dist_matrix[i][i] = 0;
            next_node_matrix[i][i] = -1;
        }
        for (int k = 0; k < N; k++)
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (dist_matrix[i][k] + dist_matrix[k][j] < dist_matrix[i][j]) {
                        dist_matrix[i][j] = dist_matrix[i][k] + dist_matrix[k][j];
                        next_node_matrix[i][j] = k;
                    }

    }

    void printGraph() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(dist_matrix[i][j] + " ");
            System.out.println("\n ");
        }

    }

    void path(int start, int fin, HashSet<Integer> hm) {
        this.fin_dest = fin;
        for (int i = 0; i < N; i++) {
            if (hm.contains(i)) {
                ArrayList<Integer> ar = new ArrayList<Integer>();
                HashSet<Integer> hm2 = (HashSet<Integer>) hm.clone();
                hm2.remove(i);
                ar.add(i);
                // System.out.println(hm2+" "+dist_matrix[start][i]);
                rec_fun(hm2, i, dist_matrix[start][i], ar);
            }
        }
    }

    void rec_fun(HashSet<Integer> hm, int pre_idx, int cost, ArrayList<Integer> arr) {
        if (hm.isEmpty()) {
            cost = dist_matrix[pre_idx][this.fin_dest] + cost;
            if (cost < min_cost) {
                final_ar = arr;
                min_cost = cost;
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            if (hm.contains(i)) {
                HashSet<Integer> hm2 = (HashSet<Integer>) hm.clone();
                hm2.remove(i);
                ArrayList<Integer> ar = new ArrayList<Integer>();
                // Collections.copy(ar,arr);
                for (Integer a : arr) {
                    ar.add(a);
                }
                ar.add(i);
                // System.out.println(hm2+""+dist_matrix[pre_idx][i]+""+pre_idx+""+i);
                rec_fun(hm2, i, cost + dist_matrix[pre_idx][i], ar);
            }
        }
    }
}

class daamini {
    public static void main(String[] args) {
        int vertices = 9;
        gr graph = new gr(vertices);

        graph.addEgde(0, 1, 4);
        graph.addEgde(0, 7, 8);
        graph.addEgde(1, 7, 11);
        graph.addEgde(1, 2, 8);
        graph.addEgde(2, 3, 7);
        graph.addEgde(2, 5, 4);
        graph.addEgde(2, 8, 2);
        graph.addEgde(3, 4, 9);
        graph.addEgde(3, 5, 14);
        graph.addEgde(4, 5, 10);
        graph.addEgde(5, 6, 2);
        graph.addEgde(6, 7, 1);
        graph.addEgde(6, 8, 6);
        graph.addEgde(7, 8, 7);
        graph.floyd_warshall();
        // graph.printGraph();
        Scanner sc = new Scanner(System.in);
        System.out.print("enter the starting position ");
        int start = sc.nextInt();
        System.out.print("enter the final position you want to reach ");
        int final_dest = sc.nextInt();
        System.out.print("enter the no checkpoints you want to visit ");
        int n = sc.nextInt();
        HashSet<Integer> hm = new HashSet<Integer>();
        if (n != 0) {
            System.out.println("enter the checkpoints");
            for (int i = 0; i < n; i++) {
                hm.add(sc.nextInt());
            }
        }
        if (n == 0) {
            System.out.println("total cost to reach from " + start + " to" + final_dest + " is: "+ graph.dist_matrix[start][final_dest]);
            return;
        }
        graph.path(start, final_dest, hm);
        System.out.println("total cost to visit the destination after visitingthe required checkpoints is :" + graph.min_cost);
        System.out.print("the order in which you travel is: " + start + " -> ");
        for (Integer i : graph.final_ar) {
            System.out.print(i + " -> ");
        }
        System.out.print(final_dest + " ");
    }
}