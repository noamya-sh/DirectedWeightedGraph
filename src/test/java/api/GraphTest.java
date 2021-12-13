package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void getNode() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        Location g = new Location(30, 30);
        Location h = new Location(30, 50);
        Location i = new Location(70, 30);
        Location j = new Location(40, 100);
        Location k = new Location(20, 10);
        a.setLocation(g);
        b.setLocation(h);
        c.setLocation(i);
        d.setLocation(j);
        e.setLocation(k);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        Assertions.assertEquals(c, f.getNode(2));
        Assertions.assertEquals(e, f.getNode(4));

    }

    @Test
    void getEdge() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1, 4, 45);
        f.connect(2, 1, 17);
        f.connect(3, 0, 8);
        Assertions.assertEquals(f.getEdgeHash().get("1_4"), f.getEdge(1, 4));
        Assertions.assertEquals(f.getEdgeHash().get("3_0"), f.getEdge(3, 0));
    }

    @Test
    void addNode() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(17);
        Node c = new Node(2);
        Node d = new Node(37);
        Node e = new Node(48);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        Assertions.assertEquals(d, f.getNode(37));
        Assertions.assertEquals(e, f.getNode(48));
        Assertions.assertEquals(b, f.getNode(17));
    }

    @Test
    void connect() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1, 4, 45);
        f.connect(2, 1, 17);
        f.connect(3, 0, 8);
        Assertions.assertEquals(f.getEdgeHash().get("1_4"), f.getEdge(1, 4));//Because we got equality then the 'connect' works
        Assertions.assertEquals(f.getEdgeHash().get("3_0"), f.getEdge(3, 0));
    }

    @Test
    void nodeIter() {
        Graph g = new Graph();
        Iterator<NodeData> ite = g.nodeIter();
        while (ite.hasNext()) {
            g.addNode(new Node(8));
            Exception ex = assertThrows(RuntimeException.class, () -> ite.hasNext());
            assertEquals("ERROR", ex.getMessage());
        }
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        g.addNode(new Node(4));
        g.connect(2, 3, 2);
        g.connect(3, 4, 2);
        Iterator<NodeData> it = g.nodeIter();
        while (it.hasNext()) {
            NodeData n = it.next();
            if (n.getKey() == 3)
                it.remove();
        }
        assertEquals(0, g.edgeSize());
        assertEquals(2, g.nodeSize());
    }

    @Test
    void edgeIter() {
        Graph g = new Graph();
        Iterator<EdgeData> ite = g.edgeIter();
        while (ite.hasNext()) {
            g.addNode(new Node(1));
            Exception ex = assertThrows(RuntimeException.class, () -> ite.hasNext());
            assertEquals("ERROR", ex.getMessage());
        }
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        g.addNode(new Node(4));
        g.connect(2, 3, 2);
        g.connect(3, 4, 2);
        Iterator<EdgeData> it = g.edgeIter();
        while (it.hasNext()) {
            EdgeData e = it.next();
            if (e.getSrc() == 3)
                it.remove();
        }
        assertEquals(1, g.edgeSize());
    }

    @Test
    void testEdgeIter() {
        Graph g = new Graph();
        g.addNode(new Node(7));
        g.addNode(new Node(8));
        g.connect(7, 8, 2);
        Iterator<EdgeData> ite = g.edgeIter(7);
        g.addNode(new Node(1));
        Throwable except = assertThrows(RuntimeException.class, () -> ite.next());
        assertEquals("ERROR", except.getMessage());


        g.addNode(new Node(3));
        g.addNode(new Node(4));
        g.addNode(new Node(5));
        g.connect(7, 3, 2);
        g.connect(7, 4, 3);
        g.connect(7, 5, 1);
        Iterator<EdgeData> it = g.edgeIter(7);
        while (it.hasNext()) {
            EdgeData e = it.next();
            if (e.getDest() == 4)
                it.remove();
        }
        assertEquals(3, g.edgeSize());

    }

    @Test
    void removeNode() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        Location g = new Location(30, 30);
        Location h = new Location(30, 50);
        Location i = new Location(70, 30);
        Location j = new Location(40, 100);
        Location k = new Location(20, 10);
        a.setLocation(g);
        b.setLocation(h);
        c.setLocation(i);
        d.setLocation(j);
        e.setLocation(k);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1, 4, 45);
        f.connect(2, 1, 17);
        f.connect(3, 0, 8);
        f.connect(0, 2, 7);
        //f.removeNode(3);
        f.removeNode(0);
        f.removeNode(1);
        //Assertions.assertEquals(null,f.NodeHash.get(3));
        assertNull(f.getNodeHash().get(0));
        assertNull(f.getNodeHash().get(1));
        assertNull(f.getEdgeHash().get("1_4"));
        assertNull(f.getEdgeHash().get("0_2"));

    }

    @Test
    void removeEdge() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1, 4, 45);
        f.connect(2, 1, 17);
        f.connect(3, 0, 8);
        f.connect(0, 2, 7);
        f.removeEdge(0, 2);
        f.removeEdge(1, 4);
        f.removeEdge(3, 0);
        assertNull(f.getEdgeHash().get("1_4"));
        assertNull(f.getEdgeHash().get("0_2"));
        assertNull(f.getEdgeHash().get("3_0"));
    }

    @Test
    void nodeSize() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        Node y = new Node(5);
        Node z = new Node(6);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        Assertions.assertEquals(5, f.nodeSize());
        f.addNode(y);
        f.addNode(z);
        f.removeNode(2);
        Assertions.assertEquals(6, f.nodeSize());

    }

    @Test
    void edgeSize() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1, 4, 45);
        f.connect(2, 1, 17);
        f.connect(3, 0, 8);
        f.connect(0, 2, 7);
        assertEquals(4, f.edgeSize());
        f.removeNode(1);
        f.removeEdge(3, 0);
        assertEquals(1, f.edgeSize());


    }

    @Test
    void getMC() {
        Graph f = new Graph();
        Node a = new Node(0);
        Node b = new Node(1);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1, 4, 45);
        f.connect(2, 1, 17);
        f.connect(3, 0, 8);
        f.connect(0, 2, 7);
        Assertions.assertEquals(9, f.getMC());
        f.removeNode(1);
        f.removeEdge(3, 0);
        Assertions.assertEquals(11, f.getMC());
    }
}