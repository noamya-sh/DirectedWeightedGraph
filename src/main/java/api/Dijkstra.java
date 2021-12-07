package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Dijkstra {
    Graph graph;
    HashMap<Integer, Double> dist;
    HashMap<Integer,NodeData> prev;
    HashMap<Integer,NodeData> q;

    public Dijkstra(Graph g){
        this.graph=g;
    }
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

    public HashMap<Integer, Double> getDist(int src) {
        fullDij(src);
        return dist;
    }

    public HashMap<Integer, NodeData> getPrev() {
        return prev;
    }

    private NodeData findmin(HashMap<Integer,NodeData> q){
        double min = Integer.MAX_VALUE; NodeData u =null;
        for (NodeData n: q.values())
            if (dist.get(n.getKey())<min){
                min=dist.get(n.getKey());
                u=n;
            }
        return u;
    }
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
                l.add(0,graph.NodeHash.get(src));
                return l;
            }
            refresh(u);
        }
        return null;//dist[dest];
    }
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
            refresh(u);
        }
        return -1;
    }
    private void fullDij(int src){
        init();
        dist.replace(src,0.0);
        while (!q.isEmpty()){
            NodeData u=findmin(q);
            q.remove(u.getKey());
            refresh(u);
        }
    }
    public double getMaxPath(int src){
        fullDij(src);
        double max=Integer.MIN_VALUE;
        for (double d : dist.values())
            if (d > max) max = d;
        return max;
    }
    private void refresh(NodeData n){
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
