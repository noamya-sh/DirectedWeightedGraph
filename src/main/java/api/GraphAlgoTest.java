package api;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class GraphAlgoTest {

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
    }

    @Test
    void mini() {
    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraphAlgorithms d = new GraphAlgo();
        d.load("src/main/java/G1.json");
        System.out.println(d.center());
//        d.load("src/main/java/G2.json");
//        System.out.println(d.center());
//        d.load("src/main/java/G3.json");
////        f = new Dijkstra((Graph) d.getGraph());
//        System.out.println(d.center());
//        d.load("bbb.json");
        List<NodeData> c = new LinkedList<>();
        c.add(d.getGraph().getNode(1));
        c.add(d.getGraph().getNode(3));
        c.add(d.getGraph().getNode(5));
        System.out.println(d.tsp(c));
//        System.out.println(f.getPathDist(1,4));
//        System.out.println(f.getPath(1,4));
//        System.out.println(((GraphAlgo) d).dijPath(1,5));
//        System.out.println(f.getMaxPath(1));
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
        DirectedWeightedGraphAlgorithms d = new GraphAlgo();
        d.load("src/G1.json");
        System.out.println(d.center());
        d.load("src/G2.json");
        System.out.println(d.center());
        d.load("src/G3.json");
        System.out.println(d.center());
        d.load("bbb.json");
        System.out.println(d.center());
    }
}