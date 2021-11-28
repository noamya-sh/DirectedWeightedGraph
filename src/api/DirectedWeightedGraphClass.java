package api;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class DirectedWeightedGraphClass implements  DirectedWeightedGraph{
//    public ArrayList<EdgeData> ed;
//    public ArrayList<NodeData> nd;
    public HashMap<Integer[],EdgeData> EdgeList;
    public HashMap<Integer,NodeData> NodeList;
    public HashMap<Integer, HashMap<Integer,EdgeData>> naiber;

    public DirectedWeightedGraphClass(){
        this.EdgeList =new HashMap<>();
        this.NodeList =new HashMap<>();
    }
    /**Copy constructor of DirectedWeightedGraphClass**/
    public DirectedWeightedGraphClass(DirectedWeightedGraphClass d){
        HashMap<Integer,NodeData> NodeListCopy =new HashMap<>();
        HashMap<Integer[],EdgeData> EdgeListCopy =new HashMap<>();
        for (var entry: d.NodeList.entrySet())
            NodeListCopy.put(entry.getKey(),new Node(entry.getValue()));
        for (var entry: d.EdgeList.entrySet())
            EdgeListCopy.put(entry.getKey().clone(),new Edge(entry.getValue()));
        this.EdgeList =EdgeListCopy;
        this.NodeList =NodeListCopy;
    }

    @Override
    public String toString() {
        return "Edges:" + EdgeList.values() +
                ", Nodes:" + NodeList.values();
    }

    @Override
    public NodeData getNode(int key) {
        return NodeList.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {

        return EdgeList.get(new int[]{src, dest});
    }

    @Override
    public void addNode(NodeData n) {
        NodeList.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        EdgeList.put(new Integer[]{src,dest},new Edge(src,dest,w));
        if (NodeList.get(src) instanceof Node && !((Node) NodeList.get(src)).isConnect())
            ((Node) NodeList.get(src)).setConnect(true);
        if (NodeList.get(dest) instanceof Node && !((Node) NodeList.get(dest)).isConnect())
            ((Node) NodeList.get(dest)).setConnect(true);
    }

    @Override
    public Iterator<NodeData> nodeIter() {

        return this.NodeList.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return this.EdgeList.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        NodeList.remove(key);
        return NodeList.get(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeList.remove(new int[]{src, dest});
        return EdgeList.get(new int[]{src, dest});
    }

    @Override
    public int nodeSize() {
        return NodeList.size();
    }

    @Override
    public int edgeSize() {
        return EdgeList.size();
    }

    @Override
    public int getMC() {
        return 0;
    }
}
