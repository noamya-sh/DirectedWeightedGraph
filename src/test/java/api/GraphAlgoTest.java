package api;

import org.junit.jupiter.api.Test;

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
        assertTrue(g.isConnected());
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
        assertEquals(g.shortestPathDist(3,2),3.5);
        assertEquals(g.shortestPathDist(0,3),4.5);
        assertEquals(g.shortestPathDist(0,4),-1);
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}