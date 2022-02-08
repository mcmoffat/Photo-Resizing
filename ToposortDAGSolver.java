package graphs;

import java.util.*;

public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;
    private final V start;

    ArrayList<V> reachable = new ArrayList<V>();
    HashSet<V> listed = new HashSet<V>();

    public ToposortDAGSolver(Graph<V> graph, V start) { 
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        this.start = start; 

        //List all reachable vertices in depth-first search post-order. Then, Collections.reverse the list.
        dfs(start, graph);
        // Initializing the reachable ArrayList with the start node given
        reachable.add(start);
        // Reversing the collection
        Collections.reverse(reachable);
        edgeTo.put(start, null);
        distTo.put(start, 0.0);
        for (V n : reachable) {  
            for (Edge<V> e : graph.neighbors(n)) {
                V to = e.to();
                V from = e.from();
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(n) + e.weight(); 
                if (newDist < oldDist) {
                edgeTo.put(to, e);
                distTo.put(to, newDist);
                }
            }
        }
    }

    // Depth first search for finding all of the nodes and mapping them in the reachable ArrayList
    public void dfs(V node, Graph<V> graph) { 
        for (Edge<V> e : graph.neighbors(node)) { 
            if (!listed.contains(e.to())) { 
                listed.add(e.to());
                dfs(e.to(), graph);
                reachable.add(e.to());
            }
        }
    }


    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from();
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
