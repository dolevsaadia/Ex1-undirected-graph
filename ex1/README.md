This project is to create a undirected graph.
class node: is inner class in Wgraph_DS
every node have a diffrent key for the new  node, each node has a data structure that keep nodes are connected to him 
This data structure allows us to add and remove connectons on the graph  


1) Return the key (id) associated with this node -getKey
2) This method returns a collection with all the Neighbor nodes of this node_data -getNi 
3) return true if this key are connected  to this node- hasNi 
4) This method adds the node_data  to this node_data -
5) Connect an edge between node to this node- connect
6) return the remark (meta data) associated with this node-setInfo
7) get if visit ppr not .-getInfo
8) use to mark if is visited or not -set info
9) use to know if is the weight -getTag

class WGraph_DS :

graph is collection of nodes so that the collection of nodes is stored in data structure HashMapt and  the key to each node is the value of that node 
the node in the graph is a collections of  vertices are connected together

1)Delete the node (with the given ID) from the graph -removeNode
2)Delete the edge from the graph -removeEdge
3)get collection representing all the nodes connected to node_id- getV(int node_id 
4)This method return a pointer (shallow copy) for the collection epresenting all the nodes in the graph -()getV
5)Connect an edge between node src to node dest and Connect an edge between node dest to node src connect
6)add a new node to the graph with the given node_info-addNode
7)check if there connected -hasEdge
8)return the node_info by the node_id -getNodev

Class WGraph_Algo:

 this class  are hold graph and allow us to do actions on the graph in this actions we need to run all nodes are connected for that we used BFS algorithm and dijakstra
BFS algorithm - used to move or search on graph- in run time o(n+v) when n is the number of nodes and v is the number of edge  

1)bring the shortest path number itreation between two nodes-shortestPathDist use dijakstra algorithem
2)a list of the nodes with the shortest between two nodes use dijakstra algporithem -shortestPath
3)check if is possible to get from any node to any other node use bfs algorithem-isConnected
4)deep copy for graph -copy
5)init function shellow copy for the graph.


In this class we use data structure that keep the shortest Path between two node
LinkedList<node_data> shortedpath-Linkedlist with short Path between two nodes




