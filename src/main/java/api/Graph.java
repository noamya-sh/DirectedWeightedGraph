package api;

import java.util.*;

public class Graph implements DirectedWeightedGraph{
    private int MC;
    private HashMap<String,EdgeData> EdgeHash;
    private HashMap<Integer,NodeData> NodeHash;

    public Graph(){
        this.EdgeHash =new HashMap<>();
        this.NodeHash =new HashMap<>();
        this.MC=0;
    }
    /**Copy constructor of DirectedWeightedGraphClass**/
    public Graph(Graph d){
        HashMap<Integer,NodeData> NodeHashCopy =new HashMap<>();
        HashMap<String,EdgeData> EdgeHashCopy =new HashMap<>();
        for (var entry: d.NodeHash.entrySet())
            NodeHashCopy.put(entry.getKey(),new Node(entry.getValue()));
        for (var entry: d.EdgeHash.entrySet()) {
            Edge e = new Edge(entry.getValue());
            EdgeHashCopy.put(entry.getKey(), e);
            /*Update Hashmaps in src Node and dest Node**/
            ((Node) NodeHashCopy.get(e.getSrc())).getEdges().put(e.getDest(),e); //insert to edges (hash) in src Node
            ((Node) NodeHashCopy.get(e.getDest())).getNeighbors().put(e.getSrc(),e.getDest()); //insert to neighbors list of dest Node
        }
        this.EdgeHash = EdgeHashCopy;
        this.NodeHash = NodeHashCopy;
        this.MC=0;
    }
    public HashMap<String, EdgeData> getEdgeHash() {
        return EdgeHash;
    }

    public HashMap<Integer, NodeData> getNodeHash() {
        return NodeHash;
    }
    @Override
    public String toString() {
        return "Edges:" + EdgeHash.values() + ",Nodes:" + NodeHash.values()+",MC:" +MC;
    }

    @Override
    public NodeData getNode(int key) {
        return NodeHash.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return EdgeHash.get(src+"_"+dest);
    }

    @Override
    public void addNode(NodeData n) {
        this.MC++;
        NodeHash.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge e = new Edge(src,dest,w);
        if (!EdgeHash.containsKey(src+"_"+dest)){
            EdgeHash.put(src+"_"+dest,e);
            ((Node) NodeHash.get(src)).getEdges().put(dest,e);
            ((Node) NodeHash.get(dest)).getNeighbors().put(src,dest);
        }
        else {
            /*Override the existing Edge and replace it with a new one*/
            EdgeHash.replace(src+"_"+dest,e);
            ((Node) NodeHash.get(src)).getEdges().replace(dest,e);
        }
        this.MC++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        /*throw exception when the graph change during the iterator run*/
        return new Iterator<>() {
            Iterator<NodeData> it = NodeHash.values().iterator();
            NodeData n = null;
            final int test = MC;

            @Override
            public boolean hasNext() {
                if (test != MC)
                    throw new RuntimeException("ERROR");
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if (test != MC)
                    throw new RuntimeException("ERROR");
                n = it.next();
                return n;
            }

            @Override
            public void remove() {
                /*remove during iterator run*/
                for (EdgeData e : ((Node) n).getEdges().values()) {
                    ((Node) NodeHash.get(e.getDest())).getNeighbors().remove(n.getKey());
                    EdgeHash.remove(n.getKey() + "_" + e.getDest());
                }
                //remove edges from Nodes that they have edges to this Node
                for (Integer src : ((Node) n).getNeighbors().keySet()) {
                    ((Node) NodeHash.get(src)).getEdges().remove(n.getKey());
                    EdgeHash.remove(src + "_" + n.getKey());
                }
                it.remove();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        /*throw exception when the graph change while the iterator run*/
        return new Iterator<>() {
            Iterator<EdgeData> it = EdgeHash.values().iterator();
            EdgeData e = null;
            final int test = MC;

            @Override
            public boolean hasNext() {
                if (test != MC)
                    throw new RuntimeException("ERROR");
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (test != MC)
                    throw new RuntimeException("ERROR");
                e = it.next();
                return e;
            }

            @Override
            public void remove() {
                ((Node) NodeHash.get(e.getSrc())).getEdges().remove(e.getDest());
                ((Node) NodeHash.get(e.getDest())).getNeighbors().remove(e.getSrc());
                it.remove();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<>() {

            Iterator<EdgeData> it = ((Node) NodeHash.get(node_id)).getEdges().values().iterator();
            EdgeData e = null;
            final int test = MC;

            @Override
            public boolean hasNext() {
                if (test != MC)
                    throw new RuntimeException("ERROR");
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (test != MC)
                    throw new RuntimeException("ERROR");
                e = it.next();
                return e;
            }

            @Override
            public void remove() {
                EdgeHash.remove(e.getSrc() + "_" + e.getDest());
                ((Node) NodeHash.get(e.getDest())).getNeighbors().remove(e.getSrc());
                it.remove();
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        //remove from neighbors of dest edges, and remove from EdgeHash.
        for (EdgeData e:((Node) NodeHash.get(key)).getEdges().values()){
            ((Node) NodeHash.get(e.getDest())).getNeighbors().remove(key);
            EdgeHash.remove(key+"_"+e.getDest());
        }
        //remove edges from Nodes that they have edges to this Node
        for (Integer src:((Node) NodeHash.get(key)).getNeighbors().keySet()){
            ((Node) NodeHash.get(src)).getEdges().remove(key);
            EdgeHash.remove(src+"_"+key);
        }
        this.MC++;
        return NodeHash.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        /*remove from src Node and dest Node**/
        ((Node) NodeHash.get(src)).getEdges().remove(dest);
        ((Node) NodeHash.get(dest)).getNeighbors().remove(src);
        this.MC++;
        return EdgeHash.remove(src+"_"+dest);
    }

    @Override
    public int nodeSize() {
        return NodeHash.size();
    }

    @Override
    public int edgeSize() {
        return EdgeHash.size();
    }

    @Override
    public int getMC() {
        return this.MC;
    }
}
