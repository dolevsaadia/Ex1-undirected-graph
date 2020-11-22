import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
public class testwgraphalgo {

    @Test
    void testEmptyGraph() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(g);
        assertTrue(algo.isConnected());
        assertNull(algo.shortestPath(1, 0));
        assertEquals(algo.shortestPathDist(1, 0),-1);
        assertTrue(algo.save("testEmptyGraph"));

    }
    @Test
    void testOneNodeGraph() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms algo = new WGraph_Algo();

        algo.init(g);
        algo.getGraph().addNode(0);

        assertTrue(algo.isConnected());
        assertNull(algo.shortestPath(1, 0));
        assertEquals(algo.shortestPathDist(1, 0),-1);
        assertTrue(algo.save("testOneNodeGraph"));

    }
    @Test
    void testGraph() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms algo = new WGraph_Algo();

        algo.init(g);
        algo.getGraph().addNode(1);
        algo.getGraph().addNode(2);
        assertTrue(algo.save("testGraph"));
        assertFalse(algo.isConnected());
        assertNull(algo.shortestPath(1, 2));
        assertEquals(algo.shortestPathDist(2, 1),-1);
        algo.getGraph().connect(2, 1, 56.876);
        assertTrue(algo.isConnected());
        assertEquals(algo.shortestPathDist(1, 2),56.876);
        assertEquals(algo.shortestPathDist(2, 1),56.876);
        assertNotNull(algo.shortestPath(1, 2));
        assertEquals(algo.shortestPath(1, 2).size(),2);
        assertTrue(algo.save("testGraph_1"));

    }



    @Test
    void testGraph1() {
        weighted_graph g = new WGraph_DS(),g1;
        weighted_graph_algorithms algo = new WGraph_Algo();

        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.connect(1, 3, 9.5);
        g.connect(1, 2, 1.5);
        g.connect(1, 6, 2.1);
        g.connect(2, 3, 1.0);
        g.connect(2, 4, 3.5);
        g.connect(3, 6, 4.7);
        g.connect(3, 4, 3.5);
        g.connect(4, 5, 6.7);
        g.connect(5, 6, 9.8);

        assertTrue(algo.isConnected());
        algo.init(g);
        assertTrue(algo.isConnected());
        g.addNode(8);
        assertFalse(algo.isConnected());
        g.removeNode(8);
        assertTrue(algo.isConnected());
        assertNotNull(g.getNode(1));
        assertEquals(g.getEdge(3, 1),9.5);
        g.connect(3, 6, 4.7);
        g.connect(3, 4, 3.5);
        g1=algo.copy();
        assertEquals(g1.nodeSize(),g.nodeSize());

        g1.removeNode(3);
        assertTrue(algo.isConnected());
        algo.init(g1);
        assertTrue(algo.isConnected());
        assertNotEquals(g1.nodeSize(),g.nodeSize());
        assertNotEquals(g1.edgeSize(),g.edgeSize());
        assertEquals(g1.nodeSize(),5);
        assertEquals(g.nodeSize(),6);
        algo.init(g);
        assertEquals(algo.shortestPathDist(1, 3),9.5);
        assertEquals(algo.shortestPathDist(1, 6),2.1);
        assertEquals(algo.shortestPathDist(8, 4),-1.0);
        assertTrue(algo.save("A"));
        assertTrue(algo.load("A"));
        assertEquals(algo.shortestPathDist(1, 5),11.9);
        algo.init(g1);
        assertTrue(algo.save("newfile1"));
        assertEquals(algo.getGraph().nodeSize(),5);
        assertTrue(algo.load("A"));
        assertNotEquals(algo.getGraph().nodeSize(),5);
        assertNotEquals(algo.getGraph().edgeSize(),5);
        assertEquals(algo.getGraph().nodeSize(),6);
        assertEquals(algo.getGraph().edgeSize(),9);
        assertTrue(algo.load("testEmptyGraph"));
        assertEquals(algo.getGraph().nodeSize(),0);
        assertEquals(algo.getGraph().edgeSize(),0);
        assertTrue(algo.load("testOneNodeGraph"));
        assertEquals(algo.getGraph().nodeSize(),1);
        assertEquals(algo.getGraph().edgeSize(),0);
        assertTrue(algo.load("testGraph"));
        assertEquals(algo.getGraph().nodeSize(),2);
        assertEquals(algo.getGraph().edgeSize(),0);
        assertTrue(algo.load("testGraph1"));
        assertTimeoutPreemptively(Duration.ofMillis(100), ()-> algo.isConnected());

    }

    @Test
    public void test_Big_Graph() {
        //create graph with 14,499,870 connected and 1,000,000 nodes
        weighted_graph g = new WGraph_DS();
        int size =  1000*1000;
        int ten=1;
        for (int i = 0; i <size; i++) {
            g.addNode(i);
        }
        for(int i=0;i<size;i++) {
            g.addNode(i);
        }
        for (int i=2;i<size;i++) {
            int j=i-1;
            int k=i-2;
            g.connect(i,j,i+j);
            g.connect(i,k,i+k);
            g.connect(j,k,j+k);
        }

        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(g);
        assertTrue(algo.isConnected());


    }
}
