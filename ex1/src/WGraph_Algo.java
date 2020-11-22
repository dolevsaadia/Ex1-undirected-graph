
import java.io.*;
import java.util.*;

/**
 *This class knows how to do operations on graphs.
 *We can check if the graph is Connected if you can go any node to another node in the graph
 *This class uses a BFS algorithm  run time O(n+m).
 *This class uses a Dijkstra algorithm tO(E*log(V)) fo shortestpath and shotestpathdis.
 *We can save the graph on text file and load the graph from text file.
 * @author Dolev saadia
 */


public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph Wg;
    private static int _count_vis;
    //////Constructor///////////////
    public WGraph_Algo()
    {
        Wg = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g)
    {
        Wg = g;

    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return Wg;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copygraph = new WGraph_DS();
        for (node_info nd : Wg.getV()) {
            copygraph.addNode(nd.getKey());
            copygraph.getNode(nd.getKey()).setTag(nd.getTag());
            copygraph.getNode(nd.getKey()).setInfo(nd.getInfo());


            for (node_info nei : Wg.getV(nd.getKey())) {
                if (!copygraph.hasEdge(nd.getKey(), nei.getKey())) {

                    if (copygraph.getNode(nei.getKey()) == null) {
                        copygraph.addNode(nei.getKey());
                        copygraph.connect(nd.getKey(), copygraph.getNode(nei.getKey()).getKey(), Wg.getEdge(nd.getKey(), nei.getKey()));
                    } else {
                        copygraph.connect(nd.getKey(), copygraph.getNode(nei.getKey()).getKey(), Wg.getEdge(nd.getKey(), nei.getKey()));
                    }
                }
            }
        }
        return copygraph;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (Wg.nodeSize() == 0) {
            return true;
        }
        //send the  node in the graph to BFS
        int p = BFSAlgo(Wg.getV().iterator().next().getKey());
        //if is equal to sum of node in the graph is connected
        if (p == Wg.nodeSize())
            return true;
        return false;
    }
    /**
     * BFS algorithem to check if all nodes are conected togetther if ihe run all nodes is connected
     * else not connected
     * @return true
     */
    private int BFSAlgo(int src) {

        for (node_info i : Wg.getV()) {
            i.setInfo("0");
        }
        //Queue to hold the neighbors
        Queue<node_info> neighbors = new LinkedList<node_info>();
        // the counter of nodes
        int pa = 1;
        //mark node viseted
        Wg.getNode(src).setInfo("1");
        //add the  node
        neighbors.add(Wg.getNode(src));
        // add all the not viseted after wi maek them and check their neighbors
        while (neighbors.size() != 0) {

            node_info n = neighbors.remove();
            //check until we dont have neighbors not viseted
            for (node_info i : Wg.getV(n.getKey())) {

                if (i.getInfo() == "0") {
                    i.setInfo("1");
                    pa++;
                    if (pa == Wg.nodeSize())
                        return pa;
                    neighbors.add(i);
                }
            }
        }
        //return the counter of nodes are connected from the first node
        return pa;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(Wg.getNode(src)==null||Wg.getNode(dest)==null)
            return-1;
        if(src==dest) {
            return 0.0;
        }

        dijkstra(src);
        //if there no path we return -1 like Infinite
        if(Wg.getNode(dest).getInfo()=="")
            return -1;
        //the distance from the source to the destination save in destination tag
        return Wg.getNode(dest).getTag();
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(Wg.getNode(src)==null||Wg.getNode(dest)==null)
            return null;
        List<node_info>  List = new ArrayList<node_info>();
        if(src==dest) {
            List.add(Wg.getNode(src));
            return List;
        }
        dijkstra(src);
        //if the parent of node dest equal to null we returns null.
        if(Wg.getNode(dest).getInfo()=="")
            return null;
        //Create a list that will store the cheapest list from the destination node to the source node.
        node_info n = Wg.getNode(dest);
        String[] s = Wg.getNode(dest).getInfo().split("&");
        //for each arr to add to the list
        for (String key : s) {

            List.add(Wg.getNode(Integer.parseInt(key)));
        }
        return List;
    }
    /**


    /**
     * Saves this undirected weighted graph to the file name
     * if the save successfully The default path to this file is in the project folder.
     * @param   file - The file name.
     * @return  - if the file was successfully saved return true else false
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream File = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(File);
            obj.writeObject(Wg);
            obj.close();
            File.close();
            return true;
        }
        catch (Exception e) {
            e.getCause();
            return false;
        }
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
        public boolean load(String file) {
            try {
                FileInputStream File = new FileInputStream(file);
                ObjectInputStream obj = new ObjectInputStream(File);
                init((weighted_graph) obj.readObject());
                obj.close();
                File.close();
                return true;

            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
    /**
     * inner class allows as to compare between two node distance in the quore for sorting.
     */
    public static class NodeComparator implements Comparator<node_info>
    {
        public int compare(node_info x, node_info y)
        {
            if (x.getTag() < y.getTag())
            {
                return -1;
            }
            if (x.getTag()> y.getTag()){
                return 1;
            }
            return 0;
        }
    }
    /**
     * This algorithm makes it possible to go over a weighted undirected graph
     * And update the distance in wich node from src
     * @param src - the source node
     */
    private void  dijkstra(int src) {
        for (node_info i : Wg.getV()) {
            i.setTag(Double.MAX_VALUE);
            i.setInfo("");
        }
        PriorityQueue<node_info> p = new PriorityQueue<node_info>(new NodeComparator());
        Wg.getNode(src).setTag(0.0);
        String temp ="";
        temp+= Wg.getNode(src).getKey();
        Wg.getNode(src).setInfo(temp);
        p.add(Wg.getNode(src));

        while(!p.isEmpty()) {
            //update the n node at the cheapest node and delete it from the Priority Queue p
            node_info n = p.poll();
            for(node_info nei: Wg.getV(n.getKey())) {
                //If we did not visited in this node we will enter to the if
                if(nei.getInfo()=="") {
                    //Calculate the updated price(Tag) of n node + the price of the side between n and its neighbor	(neighbor)
                    double W =n.getTag()+Wg.getEdge(n.getKey(), nei.getKey());
                    if(nei.getTag()>W) {
                        //System.out.println(total+"-->"+neighbor.getKey());

                        //updated tag,PriorityQueue p and parent of node neighbor.
                        nei.setTag(W);
                        nei.setInfo(n.getInfo()+"&"+nei.getKey());
                        p.add(nei);
                    }
                }
                //when we finish to visit in the node n we don't wont to visit in this node again
            }
        }
    }

}