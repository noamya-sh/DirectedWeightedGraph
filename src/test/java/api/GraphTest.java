package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {


    @Test
    void getNode() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        Location g=new Location(30,30);
        Location h=new Location(30,50);
        Location i=new Location(70,30);
        Location j=new Location(40,100);
        Location k=new Location(20,10);
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
        Assertions.assertEquals(c,f.getNode(2));
        Assertions.assertEquals(e,f.getNode(4));

    }

    @Test
    void getEdge() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1,4,45);
        f.connect(2,1,17);
        f.connect(3,0,8);
        Assertions.assertEquals(f.EdgeHash.get("1_4"),f.getEdge(1,4));
        Assertions.assertEquals(f.EdgeHash.get("3_0"),f.getEdge(3,0));
    }

    @Test
    void addNode() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(17);
        Node c=new Node(2);
        Node d=new Node(37);
        Node e=new Node(48);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        Assertions.assertEquals(d,f.getNode(37));
        Assertions.assertEquals(e,f.getNode(48));
        Assertions.assertEquals(b,f.getNode(17));
    }

    @Test
    void connect() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1,4,45);
        f.connect(2,1,17);
        f.connect(3,0,8);
        Assertions.assertEquals(f.EdgeHash.get("1_4"),f.getEdge(1,4));//Because we got equality then the 'connect' works
        Assertions.assertEquals(f.EdgeHash.get("3_0"),f.getEdge(3,0));
    }

    @Test
    void nodeIter() {
        Graph g=new Graph();
        Iterator<NodeData> ite = g.nodeIter();
        while (ite.hasNext()){
            g.addNode(new Node(8));
        }
        System.out.println(ite.next());
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        Location g=new Location(30,30);
        Location h=new Location(30,50);
        Location i=new Location(70,30);
        Location j=new Location(40,100);
        Location k=new Location(20,10);
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
        f.connect(1,4,45);
        f.connect(2,1,17);
        f.connect(3,0,8);
        f.connect(0,2,7);
        //f.removeNode(3);
        f.removeNode(0);
        f.removeNode(1);
        //Assertions.assertEquals(null,f.NodeHash.get(3));
        Assertions.assertEquals(null,f.NodeHash.get(0));
        Assertions.assertEquals(null,f.NodeHash.get(1));
        Assertions.assertEquals(null,f.EdgeHash.get("1_4"));
        Assertions.assertEquals(null,f.EdgeHash.get("0_2"));
        //Assertions.assertEquals(null,f.EdgeHash.get("3_0"));
        //Assertions.assertEquals(null,f.EdgeHash.get("2_1"));
    }

    @Test
    void removeEdge() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1,4,45);
        f.connect(2,1,17);
        f.connect(3,0,8);
        f.connect(0,2,7);
        f.removeEdge(0,2);
        f.removeEdge(1,4);
        f.removeEdge(3,0);
        Assertions.assertEquals(null,f.EdgeHash.get("1_4"));
        Assertions.assertEquals(null,f.EdgeHash.get("0_2"));
        Assertions.assertEquals(null,f.EdgeHash.get("3_0"));
    }

    @Test
    void nodeSize() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        Node y=new Node(5);
        Node z=new Node(6);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        Assertions.assertEquals(5,f.nodeSize());
        f.addNode(y);
        f.addNode(z);
        f.removeNode(2);
        Assertions.assertEquals(6,f.nodeSize());

    }

    @Test
    void edgeSize() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1,4,45);
        f.connect(2,1,17);
        f.connect(3,0,8);
        f.connect(0,2,7);
        Assertions.assertEquals(4,f.edgeSize());
        f.removeNode(1);
        f.removeEdge(3,0);
        Assertions.assertEquals(2,f.edgeSize());


    }

    @Test
    void getMC() {
        Graph f = new Graph();
        Node a=new Node(0);
        Node b=new Node(1);
        Node c=new Node(2);
        Node d=new Node(3);
        Node e=new Node(4);
        f.addNode(a);
        f.addNode(b);
        f.addNode(c);
        f.addNode(d);
        f.addNode(e);
        f.connect(1,4,45);
        f.connect(2,1,17);
        f.connect(3,0,8);
        f.connect(0,2,7);
        Assertions.assertEquals(9,f.getMC());
        f.removeNode(1);
        f.removeEdge(3,0);
        Assertions.assertEquals(11,f.getMC());
    }
}