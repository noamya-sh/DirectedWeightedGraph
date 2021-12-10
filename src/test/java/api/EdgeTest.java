package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    Edge e= new Edge(2,5,2.5);
    @Test
    void getSrc() {
        assertEquals(2,e.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(5,e.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(2.5,e.getWeight());
    }
}