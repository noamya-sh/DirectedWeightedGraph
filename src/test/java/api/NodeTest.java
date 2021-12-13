package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Location l = new Location("5.0,2.0,0.0");
    Node n = new Node(0, l);
    Location l2 = new Location("4.0,9.0,0.0");

    @Test
    void getKey() {
        assertEquals(0, n.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(l, n.getLocation());
    }

    @Test
    void setLocation() {
        assertNotEquals(n.getLocation(), l2);
        n.setLocation(l2);
        assertEquals(l2, n.getLocation());
    }
}