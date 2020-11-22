
import java.io.Serializable;
import java.util.*;

/**
 * This class creates an undirected weighted graph that holds  nodes (vertex)
 * You can add new nodes, connect a edge two nodes , delete nodes, delete edge between two nodes,
 * you can check if a rib exists, you can get a particular node,
 * you can get the list of all the neighbors of a particular node and the list of nodes.
 * Each node has the option to keep its neighbors in HashMap of HashMap- (Edges) and all the node that we add to the graph save in HashMap- (Vertex).
 * @author Dolev saadia
 *
 */

public class WGraph_DS implements weighted_graph,Serializable {

    private static final long serialVersionUID = 1L;

    // HashMap in HashMap hold all the edges in graph
    private HashMap<node_info, HashMap<node_info, Double>> Edges;
    // This HashMap hold all the nodes in the graph.
    private HashMap<Integer, node_info> Vertex;
    //count the number of edge
    private int edge_size;
    //count the changes in the graph
    private int Mc;

    ///Constructor///
    public WGraph_DS() {
        //init edges hashmap
        Edges = new HashMap<node_info, HashMap<node_info, Double>>();
        //init veertex hashmap
        Vertex = new HashMap<Integer, node_info>();
        //defaul values
        edge_size = 0;
        Mc = 0;
    }

    /**
     * return the node_info by the node id if key node exist.
     * run time- O(1).
     *
     * @param key - the node id
     * @return the Vertex by the node_id,return null if the node didn't create .
     */
    public node_info getNode(int key) {
        //go to hashmap end fet the vertex by key return null if is not exsist
        return Vertex.get(key);
    }

    /**
     * return true if node1 exist and node2 exist and if there is an edge between node1 and node2
     * run time- O(1).
     *
     * @param node1
     * @param node2
     * @return-true or false
     */

    @Override
    public boolean hasEdge(int node1, int node2) {
//check if contains
        if (Edges.containsKey(getNode(node1))) {
            if (Edges.get(getNode(node1)).containsKey(getNode(node2)))
                return true;
        }
        return false;
    }

    /**
     * check if node1 exist and node2 exist and if there is an edge between node1 and node2
     * In case there is no such edge - should return -1
     * run time- O(1).
     *
     * @param node1
     * @param node2
     * @return- The weight if the edge (node1, node1) exist else return -1.
     */
    @Override
    public double getEdge(int node1, int node2) {
        //check if has edge
        if (hasEdge(node1, node2)) {
            //if yes return the weight
            return Edges.get(getNode(node1)).get(getNode(node2));
        }
        //if not has edge return -1
        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * key must be positive.
     * run time- O(1).
     * if there is already a node with such a key no action be performed.
     * This is an action that is done on the graph, so mode count need to be updated(+1).
     *
     * @param key -Vertex.
     */
    @Override
    public void addNode(int key) {
        //check if is not exsist elreaady
        if (!Vertex.containsKey(getNode(key))) {
            //add anew node with the key
            Vertex.put(key, new nodeInfo(key));
            Mc++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * check if node1 exist and node2 exist and if there is an edge between node1 and node2
     * this method should run in O(1) time.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     *
     * @param node1
     * @param node2
     * @param w     - The weight between node1 and node2.
     */
    @Override
    public void connect(int node1, int node2, double w) {
//check if the vertex exsist
        if (Vertex.containsKey(node1) && Vertex.containsKey(node2) && node1 != node2) {
            // if the edge  already exists - the method simply updates the weight of the edge.
            if (hasEdge(node1, node2)) {
                //if this  weight not equal to the new equal we update mc and the wieght.
                if (Edges.get(Vertex.get(node1)).get(Vertex.get(node2)) != w) {
                    Edges.get(Vertex.get(node1)).put(Vertex.get(node2), w);
                    Edges.get(Vertex.get(node2)).put(Vertex.get(node1), w);
                    Mc++;

                }
                //and return
                return;
                //else we make a new hashmap for vertex is not exsist in edges vertex
            } else if (!Edges.containsKey(Vertex.get(node1)))
                Edges.put(Vertex.get(node1), new HashMap<node_info, Double>());
            if (!Edges.containsKey(Vertex.get(node2)))
                Edges.put(Vertex.get(node2), new HashMap<node_info, Double>());
//and now there exsist in edges vertex and put the nodes
            Edges.get(Vertex.get(node1)).put(Vertex.get(node2), w);
            Edges.get(Vertex.get(node2)).put(Vertex.get(node1), w);
            edge_size++;
            Mc++;
        }
        return;
    }


    /**
     * This method return a pointer (shallow copy) for the
     * Collection representing all the nodes in the graph.
     * run time O(1).
     *
     * @return Collection of all the node in this graph.
     */
    @Override
    public Collection<node_info> getV() {
        return Vertex.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * run time O(1).
     *
     * @return Collection<node_info> else return empty Collection.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (Vertex.containsKey(node_id) && Edges.containsKey(getNode(node_id))) {

            return Edges.get(Vertex.get(node_id)).keySet();
        }
        return new ArrayList<>();
    }

    /**
     * Removes all edges which starts or ends at this node.
     * Delete the node (with the given ID) from the graph.
     * update the number of rib and mode count.
     * Run time  O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        node_info temp = Vertex.get(key);
        //check if temp is empty
        if (temp==null) {
            return null;
        }
        // make a iteraror for run over collection
        Iterator<node_info> i = getV().iterator();
        //while is have next
        while (i.hasNext()) {
            //save the next mode
            node_info nextnode = i.next();
            //and remove the conection beetween them
            if (hasEdge(key, nextnode.getKey())) {
                removeEdge(key, nextnode.getKey());
            }
        }
        //and remove the node
        Edges.remove(key);
        Vertex.remove(key);
        return temp;
    }

    /**
     * Delete the edge from the graph if exist,
     * run time O(1).
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            //and if node1 and node2 connected
            Edges.get(getNode(node1)).remove(getNode(node2));
            //delete node1 from node2 neigbors
            Edges.get(getNode(node2)).remove(getNode(node1));


            //num of conections -1
            this.edge_size--;
            //more one actions on the graph
            this.Mc++;
        }
    }
    /**
     * equals function -  if two graph are equal return true
     * @return true if this Edges equal to other Edges and this Vertex equal to other Vertex
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WGraph_DS)) {
            return false;
        }
        WGraph_DS Object2 =  (WGraph_DS) obj;
        if (Vertex.equals(Object2.Vertex)||(Edges.equals(Object2.Edges))) {
            return true;
        }
        else
            return false;

    }
    //Maybe use in the future
    public int hashCode() {
        return Objects.hash(Vertex,Edges);
    }




    /**
     * return the number of vertices (nodes) in the graph.
     * run time O(1).
     *
     * @return - int number of node in the graph.
     */
    @Override
    public int nodeSize() {
        return Vertex.size();
    }

    /**
     * return the number of edges (unidirectional graph).
     * run time O(1).
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return edge_size;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change(remove node and edge,add node, connect between two nodes and update weight between two nodes that already exist)
     * in the inner state of the graph should cause an increment in the Mc
     *
     * @return - int mode count.
     */
    @Override
    public int getMC() {
        return Mc;
    }

    /**
     * This inner class allows you to create nodes with unique key and do various actions like delete creation and more.
     */
    public class nodeInfo implements node_info, Serializable {

        private static final long serialVersionUID = 1L;
        //node key
        private int key;
        //use for save the keys nodes  for shotestpath
        private String info;
        //use for keep the weight for dijakstra
        private double tag;
        // constacturs with key
        public nodeInfo(int k) {
            //key must be possitive
            if (k<0){
                return;
            }
            key = k;
            tag = 0;
            info = "";
        }
        //copy constacturs
        public nodeInfo(node_info n) {
            key = n.getKey();
            tag = n.getTag();
            info = n.getInfo();
        }
        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         * @return
         */
        @Override
        public int getKey() {
            //return node keys
            // TODO Auto-generated method stub
            return key;
        }
        /**
         * return the remark (meta data) associated with this node.
         * @return
         */
        @Override
        public String getInfo() {
            //to get keys nodes after dijakstra
            // TODO Auto-generated method stub
            return info;
        }
        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s
         */
        @Override
        public void setInfo(String s) {
            //to set keys node after we move on him in dijakstra algo
            info = s;
        }
        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return
         */
        @Override
        public double getTag() {
            //to get double weight
            // TODO Auto-generated method stub
            return tag;
        }
        /**
         * set Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         */
        @Override
        //to set double weight
        public void setTag(double t) {
            tag = t;
        }
        /**
         * equals function -  if two graph are equal return true
         * @return true if this node equal to other node
         */
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof nodeInfo)) {
                return false;
            }
            nodeInfo Object2 = (nodeInfo) object;
            if (key != Object2.key) {
                return false;
            }
            else
                return true;
        }
    }


}