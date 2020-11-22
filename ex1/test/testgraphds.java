
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class testgraphds {
    @Test
    void testAddNode() {
        weighted_graph graph =new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);

        assertNull(graph.getNode(0));
        assertNotNull(graph.getNode(1));
        assertNotNull(graph.getNode(2));
        assertNotNull(graph.getNode(3));
        assertNull(graph.getNode(-4));
        assertNotNull(graph.getNode(4));
        assertEquals(graph.nodeSize(),4);
        assertEquals(graph.edgeSize(),0);
        assertNotEquals(graph.edgeSize(), 6);
    }


    @Test
    void testConnect() {
        weighted_graph graph =new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.connect(1, 2, 19.08);
        graph.connect(1, 3, 5.7);
        graph.connect(1, 0, 1.3);
        assertEquals(graph.getEdge(1, 2),19.08);
        graph.connect(3, 1, 0.6);
        assertNotEquals(graph.getEdge(1, 3), 5.7);
        assertEquals(graph.getEdge(1, 3),0.6);
        assertEquals(graph.edgeSize(),3);
        assertEquals(graph.getEdge(0,2 ),-1);
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 1));

    }
    @Test
    void testgraph() {
        weighted_graph graph =new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(1, 2, 4.6);
        graph.connect(1, 3, 0.4);
        graph.connect(3, 2, 2.2);
        graph.connect(4, 1, 1.53);
        graph.connect(4, 2, 1.7);
        assertEquals(graph.getEdge(4, 1),1.53);
        graph.connect(2, 1, 45.6);
        assertEquals(graph.getEdge(1, 2),45.6);
        assertEquals(graph.edgeSize(),5);
        assertEquals(graph.nodeSize(),4);
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 1));
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 2));
        assertEquals(graph.getNode(2),graph.removeNode(2));
        assertEquals(graph.edgeSize(),2);
        assertEquals(graph.nodeSize(),3);
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 1));
        assertFalse(graph.hasEdge(2, 3));
        assertFalse(graph.hasEdge(3, 2));
        graph.addNode(2);
        assertFalse(graph.hasEdge(1, 2));
        graph.connect(1, 2, 222.3);
        assertTrue(graph.hasEdge(2, 1));
        assertTrue(graph.hasEdge(1, 2));
        assertEquals(graph.edgeSize(),3);
        assertEquals(graph.nodeSize(),4);

    }
}