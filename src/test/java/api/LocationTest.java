package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location l = new Location("5.0,2.0,0.0");

    @Test
    void x() {
        assertEquals(5.0, l.x());
    }

    @Test
    void y() {
        assertEquals(2.0, l.y());
    }

    @Test
    void z() {
        assertEquals(0.0, l.z());
    }

    @Test
    void distance() {
        Location l2 = new Location("3.0,2.0,0.0");
        assertEquals(2, l.distance(l2));
    }
}