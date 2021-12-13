package api;

import java.util.*;

/**
 * This class implement Dijkstra's algorithm for assignment.
 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 **/
public class Dijkstra {
    private Graph graph;
    private HashMap<Integer, Double> dist;
    private HashMap<Integer, NodeData> prev;
    private PriorityQueue<NodeData> queue;

    public Dijkstra(Graph g) {
        this.graph = g;
    }

    /**
     * Init 2 HashMaps and priority queue for run algorithm.
     * for each Node initialize its distance from the vertex src to be infinity.
     * and its previous vertex to be null.
     **/
    public void init() {
        HashMap<Integer, Double> dist = new HashMap<>();
        HashMap<Integer, NodeData> prev = new HashMap<>();
        Comparator<NodeData> comparator = new Comparator<NodeData>() {
            @Override
            public int compare(NodeData o1, NodeData o2) {
                if (dist.get(o1.getKey()) > dist.get(o2.getKey()))
                    return 1;
                if (dist.get(o1.getKey()) < dist.get(o2.getKey()))
                    return -1;
                return 0;
            }
        };
        this.queue = new PriorityQueue<>(comparator);
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            NodeData n = it.next();
            int ind = n.getKey();
            dist.put(ind, (double) Integer.MAX_VALUE);
            prev.put(ind, null);

        }
        this.dist = dist;
        this.prev = prev;
    }

    /**
     * @return HashMap with all distances of Nodes from src Node
     */
    public HashMap<Integer, Double> getDist(int src) {
        fullDij(src);
        return dist;
    }

    /**
     * @return HashMap with all previous Nodes of each Node
     */
    public HashMap<Integer, NodeData> getPrev() {
        return prev;
    }


    /**
     * This function for find the shortPath from src Node to dest Node.
     * Each time you pull out the Node with the smallest distance,
     * and update its neighbors with the help of the Edges coming out of it.
     * stop when reach dest Node.
     *
     * @return List contain every previous Nodes of dest Node.
     **/
    public List<NodeData> getPath(int src, int dest) {
        init();
        dist.replace(src, 0.0);
        queue.add(graph.getNode(src));
        while (!queue.isEmpty()) {
            NodeData u = queue.remove();
            if (dist.get(u.getKey()) == Integer.MAX_VALUE)
                return null;
            if (u.getKey() == dest) {
                List<NodeData> path = new LinkedList<>();
                if (prev.get(u.getKey()) != null || u.getKey() == src)
                    while (prev.get(u.getKey()) != null) {
                        path.add(0, u);
                        u = prev.get(u.getKey());
                    }
                path.add(0, graph.getNodeHash().get(src));
                return path;
            }
            update(u);
        }
        return null;
    }

    /**
     * This function for find the shortPath from src Node to dest Node.
     * Each time you pull out the Node with the smallest distance,
     * and update its neighbors with the help of the Edges coming out of it.
     * stop when reach dest Node.
     *
     * @return sum the distance from src Node to dest Node.
     **/
    public double getPathDist(int src, int dest) {
        init();
        dist.replace(src, 0.0);
        queue.add(graph.getNode(src));
        while (!queue.isEmpty()) {
            NodeData u = queue.remove();
            if (dist.get(u.getKey()) == Integer.MAX_VALUE)
                return -1;
            if (u.getKey() == dest) {
                return dist.get(u.getKey());
            }
            update(u);
        }
        return -1;
    }

    /**
     * The main work of Dijkstra algorithm.
     * Out the Node with min distance and update his neighbors.
     **/
    private void fullDij(int src) {
        init();
        dist.replace(src, 0.0);
        queue.add(graph.getNode(src));
        while (!queue.isEmpty()) {
            NodeData u = queue.remove();
            if (dist.get(u.getKey()) == Integer.MAX_VALUE)
                return;
            update(u);
        }
    }

    /**
     * For find 'center' Node we search the max result of path start from src Node.
     *
     * @return max distance from src Node.
     **/
    public double getMaxPath(int src) {
        fullDij(src);
        double max = Integer.MIN_VALUE;
        for (double d : dist.values())
            if (d > max) max = d;
        return max;
    }

    /**
     * This function update the distance and 'previous' of each neighbors of Node n.
     **/
    private void update(NodeData n) {
        Iterator<EdgeData> it = graph.edgeIter(n.getKey());
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            double t = dist.get(n.getKey()) + e.getWeight();
            if (t < dist.get(e.getDest())) {
                dist.replace(e.getDest(), t);
                prev.replace(e.getDest(), n);
                queue.add(graph.getNode(e.getDest()));
            }

        }
    }
}
