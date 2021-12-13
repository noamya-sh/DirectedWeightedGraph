# Directed Weighted Graph implement - Ex2
### Noamya Shani and Eitan Shenkolevski
Our project is about building weighted directed graphs, executing algorithms on them and presenting them graphically (GUI).<br>
In order to carry out the project, we have created five central classes that implement five interfaces - 
1. `Location` implement `GeoLocation`.
2. `Node ` implement `NodeData`.
3. `Edge` implement `EdgeData`.
4. `Graph` implement `DirectedWeightedGraph`
5. `GraphAlgo` implement `DirectedWeightedGraphAlgorithms`.<br><br>
In addition, there is a `Dijkstra` class for graph algorithms and `window` and `paintPanel` classes for the graphical interface.<br>

The class of the weighted directed graph contains functions for adding and deleting nodes and adding and deleting edges as well as iterators that allow us to go over the Nodes,
Edges and Edges by Node.
We implemented the directed graph using 2 Hashmaps - one for the edges and one for the nodes, where each node also contains 2 Hashmaps - 
one for the edges that come out of it, and the other (integer hash) keeps the node keys who come out of them edges that enter it.
so if we need to delete node we will know exactly which edges we need to delete with it . With each addition of node we add it to the hash of nodes,
and with each addition of edge we add it to the hash of edges, to the hash of outgoing edges of the source node and adding key of src node to neighbors hash in dest node.<br><br>
In the algorithm class of the graph we implemented algorithms for checking if the graph is connected, finding the shortest path from src node to dest node, finding the center node and finding the 'tsp' (like 'tsp', not exactly).
In the implementation of the algorithms on the graph we mainly used the Dijkstra algorithm in all kinds of variations and because of the extensive use and for convenience 
we created a separate class of Dijkstra algorithm.
To find the shortestPath we will run Dijkstra until we reach the destination Node. <br>
To find the center Node we run Dijkstra for each node, 
 take the maximum distance and compare it with the other nodes. The node that returns the smallest maximum is the center node.<br>
 To find tsp use a greedy algorithm and each time look for the nearest node (cost of weight to reach) from the list of cities
 and add the route to it until you go through all the cities.<br>
We also implemented algorithms for loading a graph from a json file and saving a graph to a json file.<br><br>

### GUI
![image](https://user-images.githubusercontent.com/77248387/145870252-ab5851a5-a9c2-48cf-925b-061144166193.png)


We built a graphical system using `java swing` that allows the display of directed weighted graphs.<br>
The graphical interface can be activated using 2 options:
1. download `Ex2.jar` file from [here](https://github.com/eitansh28/DirectedWeightedGraph/blob/main/src/main/java/Ex2.jar), and json file from [here](https://github.com/eitansh28/DirectedWeightedGraph/tree/main/src/main/java/Input%20exemples), open `cmd` in directory of Ex2.jar and run the command:<br>
```
java -jar Ex2.jar <json file name>
```
<br>
2. clone all repsitory and run the 'runGUI' function in the 'Ex2' class with input of json file path.<br>

On the top left there menu that allow you to load and save graphs, add and delete nodes and edges as well as the option to execute the algorithms on the given graph.<br> 
![image](https://user-images.githubusercontent.com/77248387/145870828-f70f5da8-7ed6-478f-a4d6-ea49f4c0ac7e.png)![image](https://user-images.githubusercontent.com/77248387/145870608-a551efb4-1d43-4656-915e-41a0d2696b1c.png)![image](https://user-images.githubusercontent.com/77248387/145870660-b2b2d733-4f4e-452a-8a35-fae3af15da57.png)

In some functions the user will have to enter the data he selects and then clicking on the designated button will execute the requested algorithm.

#### Performence
Graph with 1000 nodes, 20000 edges: Running the 'center' on a graph took 8.5 seconds,  'isConnected' test 141 ms.<br>
Graph with  10000 nodes 100000 edges: 'isConnected' 753 ms, and 'shorsestpath' test took 156 ms, 'center' - 9 minute, 21 sec.<br>
Graph with 100,000 nodes 1000000 edges: 'isConnected' took 37 sec and 'shortestpath' took one minute and 33 seconds, 'center' - timeout.<br>
We were unable to create a 1000000 graph due to a heap error.
