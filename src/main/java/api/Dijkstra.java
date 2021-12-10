package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**This class implement Dijkstra's algorithm for assignment.
 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * **/
public class Dijkstra {
    private Graph graph;
    private HashMap<Integer, Double> dist;
    private HashMap<Integer,NodeData> prev;
    private HashMap<Integer,NodeData> q;

    public Dijkstra(Graph g){
        this.graph=g;
    }
    /**Init 3 HashMaps for run algorithm.
     * for each Node initialize its distance from the vertex src to be infinityת
     * and its previous vertex to be null.**/
    public void init(){
        HashMap<Integer,Double> dist = new HashMap<>();
        HashMap<Integer,NodeData> prev = new HashMap<>();
        HashMap<Integer,NodeData> q = new HashMap<>();
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()){
            NodeData n = it.next();
            int ind = n.getKey();
            dist.put(ind, (double) Integer.MAX_VALUE);
            prev.put(ind,null);
            q.put(ind,n);
        }
        this.dist=dist;
        this.prev=prev;
        this.q=q;
    }
    /**@return HashMap with all distances of Nodes from src Node*/
    public HashMap<Integer, Double> getDist(int src) {
        fullDij(src);
        return dist;
    }
    /**@return HashMap with all previous Nodes of each Node */
    public HashMap<Integer, NodeData> getPrev() {
        return prev;
    }
    /**@return the Node with the smallest distance.**/
    private NodeData findmin(HashMap<Integer,NodeData> q){
        double min = Integer.MAX_VALUE; NodeData u =null;
        for (NodeData n: q.values())
            if (dist.get(n.getKey())<min){
                min=dist.get(n.getKey());
                u=n;
            }
        return u;
    }
    /**This function for find the shortPath from src Node to dest Node.
     * Each time you pull out the Node with the smallest distance,
     * and update its neighbors with the help of the Edges coming out of it.
     * stop when reach dest Node.
     * @return List contain every previous Nodes of dest Node.**/
    public List<NodeData> getPath(int src,int dest){
        init();
        dist.replace(src,0.0);
        while (!q.isEmpty()){
            NodeData u=findmin(q);
            if (u == null)
                return null;
            q.remove(u.getKey());
            if (u.getKey() == dest){
                List<NodeData> l = new LinkedList<>();
                if (prev.get(u.getKey())!=null || u.getKey()==src)
                    while (prev.get(u.getKey())!=null){
                        l.add(0,u);
                        u = prev.get(u.getKey());
                    }
                l.add(0,graph.getNodeHash().get(src));
                return l;
            }
            update(u);
        }
        return null;
    }
    /**This function for find the shortPath from src Node to dest Node.
     * Each time you pull out the Node with the smallest distance,
     * and update its neighbors with the help of the Edges coming out of it.
     * stop when reach dest Node.
     * @return sum the distance from src Node to dest Node.**/
    public double getPathDist(int src,int dest){
        init();
        dist.replace(src,0.0);
        while (!q.isEmpty()){
            NodeData u=findmin(q);
            if (u == null)
                return -1;
            q.remove(u.getKey());
            if (u.getKey() == dest){
                return dist.get(u.getKey());
            }
            update(u);
        }
        return -1;
    }
    /**The main work of Dijkstra algorithm.
     * Out the Node with min distance and update his neighbors.**/
    private void fullDij(int src){
        init();
        dist.replace(src,0.0);
        while (!q.isEmpty()){
            NodeData u=findmin(q);
            q.remove(u.getKey());
            update(u);
        }
    }
    /**For find 'center' Node we search the max result of path start from src Node.
     * @return max distance from src Node.**/
    public double getMaxPath(int src){
        fullDij(src);
        double max=Integer.MIN_VALUE;
        for (double d : dist.values())
            if (d > max) max = d;
        return max;
    }
    /**This function update the distance and 'previous' of each neighbors of Node n.**/
    private void update(NodeData n){
        Iterator<EdgeData> it = graph.edgeIter(n.getKey());
        while (it.hasNext()){
            Edge e = (Edge) it.next();
            if (q.containsKey(e.getDest())){
                double t = dist.get(n.getKey())+e.getWeight();
                if (t < dist.get(e.getDest())){
                    dist.replace(e.getDest(),t);
                    prev.replace(e.getDest(),n);
                }
            }
        }
    }
}
