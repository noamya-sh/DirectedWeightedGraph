package api;

import org.junit.jupiter.api.Test;

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
        d.load("bbb.json");
        System.out.println(d.shortestPathDist(1,5));
        System.out.println(((GraphAlgo) d).dik(1,5));
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