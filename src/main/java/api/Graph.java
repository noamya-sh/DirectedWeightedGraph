package api;

import java.util.*;

public class Graph implements DirectedWeightedGraph{
    private int MC;
    public HashMap<String,EdgeData> EdgeHash;
    public HashMap<Integer,NodeData> NodeHash;

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
            ((Node) NodeHashCopy.get(e.getSrc())).getEdges().put(e.getDest(),e); //insert to edges (hash) in src Node
            ((Node) NodeHashCopy.get(e.getDest())).getNeighbors().put(e.src,e.dest); //insert to neighbors list of dest Node
        }
        this.EdgeHash = EdgeHashCopy;
        this.NodeHash = NodeHashCopy;
        this.MC=0;
    }

    @Override
    public String toString() {
        return "Edges:" + EdgeHash.values() +
                ", Nodes:" + NodeHash.values()+" MC =" +MC;
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
            EdgeHash.replace(src+"_"+dest,e);
            ((Node) NodeHash.get(src)).getEdges().replace(dest,e);
        }
        this.MC++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<NodeData>() {
            Iterator<NodeData> it = NodeHash.values().iterator();
            NodeData n = null;
            int test=MC;
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if (test!=MC)
                    throw new RuntimeException("ERROR");
                n = it.next();
                return n;
            }
            @Override
            public void remove() {
                for (EdgeData e:((Node) n).getEdges().values()){
                    ((Node) NodeHash.get(e.getDest())).getNeighbors().remove(n.getKey());
                    EdgeHash.remove(n.getKey()+"_"+e.getDest());
                }
                //remove edges from Nodes that they have edges to this Node
                for (Integer src:((Node) n).getNeighbors().keySet()){
                    ((Node) NodeHash.get(src)).getEdges().remove(n.getKey());
                    EdgeHash.remove(src+"_"+n.getKey());
                }
                it.remove();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> it = EdgeHash.values().iterator();
            EdgeData e = null;
            int test=MC;
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (test!=MC)
                    throw new RuntimeException("ERROR");
                e = it.next();
                return e;
            }
            @Override
            public void remove() {
                ((Node)NodeHash.get(e.getSrc())).getEdges().remove(e.getDest());
                ((Node)NodeHash.get(e.getDest())).getNeighbors().remove(e.getSrc());
                it.remove();
            }
//            @Override
//            public void forEachRemaining(){
//
//            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            int test=MC;
            Iterator<EdgeData> it =((Node) NodeHash.get(node_id)).getEdges().values().iterator();
            EdgeData e = null;
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (test!=MC)
                    throw new RuntimeException("ERROR");
                e = it.next();
                return e;
            }
            @Override
            public void remove() {
                EdgeHash.remove(e.getSrc()+"_"+e.getDest());
                ((Node)NodeHash.get(e.getDest())).getNeighbors().remove(e.getSrc());
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


    public static void main(String[] args) {
        Graph d=new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
//        Location l=new Location();
//        l.x=5;
//        l.y=3;
//        b.setLocation(l);
        d.addNode(a);
        d.addNode(b);
        d.addNode(c);
        d.connect(0,1,4);
        d.connect(0,2,4);
        d.connect(1,2,6);
        d.connect(1,0,5);
        d.connect(2,1,4);
        Iterator<NodeData> it = d.nodeIter();
        it.forEachRemaining(System.out::println);
//        while(it.hasNext()){
//            EdgeData n = it.next();
//            if (n.getDest()==1)
//                it.remove();
//        }
//        System.out.println(((Node)d.NodeHash.get(0)).getEdges().values());
//        System.out.println(((Node)d.NodeHash.get(1)).getNeighbors());
//        System.out.println(d.EdgeHash);
        //d.removeEdge(1,2);
//        d.removeNode(1);
//        System.out.println(d);
//        DirectedWeightedGraphAlgorithms dw=new GraphAlgo();
//        dw.init(d);
        //dw.save("try");
    }
}
