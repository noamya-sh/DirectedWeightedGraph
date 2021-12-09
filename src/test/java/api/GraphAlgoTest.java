package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgoTest {

    @Test
    void init() {
        GraphAlgo g = new GraphAlgo();
        Graph f = new Graph();
        f.addNode(new Node(0));
        f.addNode(new Node(1));
        f.connect(0,1,5);
        Graph o = (Graph) g.getGraph();
        g.init(f);
        assertFalse(o==g.getGraph());
    }

    @Test
    void getGraph() {
        GraphAlgo g = new GraphAlgo();
        Graph f = new Graph();
        f.addNode(new Node(0));
        f.addNode(new Node(1));
        f.connect(0,1,5);
        g.init(f);
        assertTrue(f==g.getGraph());
    }

    @Test
    void copy() {
        GraphAlgo g = new GraphAlgo();
        Graph f = (Graph) g.copy();
        assertTrue(f!=g.getGraph());
    }

    @Test
    void isConnected() {
        GraphAlgo g = new GraphAlgo();
        Graph f = new Graph();
        f.addNode(new Node(0));
        f.addNode(new Node(1));
        f.connect(0,1,5);
        f.connect(1,0,5);
        g.init(f);
        //assertTrue(g.isConnected());
        //GraphAlgo g=new GraphAlgo();
        g.load("src/test/10000Nodes.json");
        System.out.println(g.isConnected());
    }

    @Test
    void shortestPathDist() {
        GraphAlgo g = new GraphAlgo();
        Graph f = new Graph();
        f.addNode(new Node(0));
        f.addNode(new Node(1));
        f.addNode(new Node(2));
        f.addNode(new Node(3));
        f.addNode(new Node(4));
        f.connect(0,1,5);
        f.connect(1,0,1);
        f.connect(0,2,0.5);
        f.connect(1,2,3);
        f.connect(2,3,4);
        f.connect(3,1,2);
        f.connect(3,2,8);
        f.connect(0,3,7);
        g.init(f);
        assertEquals(3.5,g.shortestPathDist(3,2));
        assertEquals(4.5,g.shortestPathDist(0,3));
        assertEquals(-1,g.shortestPathDist(0,4));
    }

    @Test
    void shortestPath() {
        GraphAlgo g = new GraphAlgo();
        Graph f = new Graph();
        f.addNode(new Node(0));
        f.addNode(new Node(1));
        f.addNode(new Node(2));
        f.addNode(new Node(3));
        f.addNode(new Node(4));
        f.connect(0,1,5);
        f.connect(1,0,1);
        f.connect(0,2,0.5);
        f.connect(1,2,3);
        f.connect(2,3,4);
        f.connect(3,1,2);
        f.connect(3,2,8);
        f.connect(0,3,7);
        f.connect(3,4,10);
        f.connect(4,3,5);
        g.init(f);
        List<NodeData>l=new LinkedList<NodeData>();
        l.add(f.NodeHash.get(3));
        l.add(f.NodeHash.get(1));
        l.add(f.NodeHash.get(0));
        l.add(f.NodeHash.get(2));
        assertEquals(l,g.shortestPath(3,2));
        List<NodeData>m=new LinkedList<NodeData>();
        m.add(f.NodeHash.get(0));
        m.add(f.NodeHash.get(2));
        m.add(f.NodeHash.get(3));
        assertEquals(m,g.shortestPath(0,3));
        List<NodeData>n=new LinkedList<NodeData>();
        n.add(f.NodeHash.get(0));
        n.add(f.NodeHash.get(2));
        n.add(f.NodeHash.get(3));
        n.add(f.NodeHash.get(4));
        assertEquals(n,g.shortestPath(0,4));
    }

    @Test
    void center() {
        GraphAlgo g=new GraphAlgo();
        g.load("src/main/java/G1.json");
        Assertions.assertEquals(g.getGraph().getNode(8),g.center());
        g.load("src/main/java/G2.json");
        Assertions.assertEquals(g.getGraph().getNode(0),g.center());
        g.load("src/main/java/G3.json");
        Assertions.assertEquals(g.getGraph().getNode(40),g.center());
    }

    @Test
    void tsp() {
        GraphAlgo g = new GraphAlgo();
        Graph f = new Graph();
        f.addNode(new Node(0));
        f.addNode(new Node(1));
        f.addNode(new Node(2));
        f.addNode(new Node(3));
        f.addNode(new Node(4));
        f.connect(0,1,5);
        f.connect(1,0,1);
        f.connect(0,2,0.5);
        f.connect(1,2,3);
        f.connect(2,3,4);
        f.connect(3,1,2);
        f.connect(3,2,8);
        f.connect(0,3,7);
        f.connect(3,4,10);
        f.connect(4,3,5);
        g.init(f);
        List<NodeData>n=new LinkedList<NodeData>();
        n.add(f.NodeHash.get(0));
        n.add(f.NodeHash.get(4));
        List<NodeData>k=new LinkedList<NodeData>();
        k.add(f.NodeHash.get(0));
        k.add(f.NodeHash.get(2));
        k.add(f.NodeHash.get(3));
        k.add(f.NodeHash.get(4));
        Assertions.assertEquals(k,g.tsp(n));
        List<NodeData>b=new LinkedList<NodeData>();
        b.add(f.NodeHash.get(3));
        b.add(f.NodeHash.get(2));
        b.add(f.NodeHash.get(1));
        List<NodeData>v=new LinkedList<NodeData>();
        v.add(f.NodeHash.get(3));
        v.add(f.NodeHash.get(1));
        v.add(f.NodeHash.get(0));
        v.add(f.NodeHash.get(2));
        Assertions.assertEquals(v,g.tsp(b));
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}