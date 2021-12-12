package api;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class contains DirectedWeightedGraph and perform functions on it.
 **/
public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private Graph graph;

    public GraphAlgo() {
        this.graph = new Graph();
    }

    @Override
    public String toString() {
        return "" + graph + "";
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new Graph(graph);
    }

    /**
     * BFS algorithm for check if this graph connected.
     *
     * @return Hashmap contain boolean variable for each Node that
     * declares whether can to reach it.
     **/
    private HashMap<Integer, Boolean> bfs(Graph g) {
        HashMap<Integer, Boolean> test = new HashMap<>();
        Iterator<NodeData> it = g.nodeIter();
        it.forEachRemaining(n -> test.put(n.getKey(), false));
        int firstID = test.keySet().stream().findFirst().get();
        test.replace(firstID, true);
        Queue<NodeData> q = new LinkedList<>();
        q.add(g.getNodeHash().get(firstID));
        while (!q.isEmpty()) {
            Node n = (Node) q.poll();
            Iterator<EdgeData> i = g.edgeIter(n.getKey());
            while (i.hasNext()) {
                EdgeData e = i.next();
                if (test.get(e.getDest()))
                    continue;
                test.replace(e.getDest(), true);
                q.add(g.getNodeHash().get(e.getDest()));
            }
        }
        return test;
    }

    @Override
    public boolean isConnected() {
        /*perform BFS searches on source graph and on reverse graph*/
        HashMap<Integer, Boolean> d = bfs(graph);
        for (Boolean b : d.values()) if (!b) return false;
        //create transfom of this graph, invert each edge..
        Graph copy = new Graph();
        for (NodeData n : graph.getNodeHash().values()) copy.getNodeHash().put(n.getKey(), new Node(n.getKey()));
        for (EdgeData e : graph.getEdgeHash().values()) {
            int dest = e.getSrc(), src = e.getDest();
            ((Node) copy.getNodeHash().get(src)).getEdges().put(dest, new Edge(src, dest, e.getWeight()));
        }
        HashMap<Integer, Boolean> r = bfs(copy);
        for (Boolean b : r.values()) if (!b) return false;
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        /*perform Dijkstra algorithm*/
        Dijkstra d = new Dijkstra(this.graph);
        return d.getPathDist(src, dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        /*perform Dijkstra algorithm*/
        Dijkstra d = new Dijkstra(this.graph);
        return d.getPath(src, dest);
    }

    @Override
    public NodeData center() {
        if (!this.isConnected())
            return null;
        /*for each Node do Dijkstra algorithm to find the max distance from its.
         * The Node with the minimal of max distance,is the center Node.*/
        double min = Integer.MAX_VALUE;
        NodeData ans = null;
        Dijkstra d = new Dijkstra(this.graph);
        for (NodeData n : this.graph.getNodeHash().values()) {
            double max = d.getMaxPath(n.getKey());
            if (max < min) {
                min = max;
                ans = n;
            }
        }
        return ans;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities.size() == 0)
            return null;
        List<NodeData> ans = new LinkedList<>();
        List<NodeData> temp = new ArrayList<>(cities);
        NodeData p = temp.remove(0);
        ans.add(p);
        /*Greedy algorithm -
         * 1. Start with Node,
         * 2. while temp is not empty: find the node closest to it and add the Nodes' path to ans.
         * the search by Dijkstra algorithm.*/
        while (temp.size() >= 1) {
            int id = p.getKey();
            Dijkstra d = new Dijkstra(this.graph);
            HashMap<Integer, Double> dist = d.getDist(id);
            NodeData t = null;
            double min = Integer.MAX_VALUE;
            for (NodeData n : temp) {
                if (dist.get(n.getKey()) < min) {
                    min = dist.get(n.getKey());
                    t = n;
                }
            }
            if (t == null)
                return null;
            List<NodeData> path = new LinkedList<>();
            HashMap<Integer, NodeData> prev = d.getPrev();
            NodeData c = t;
            if (prev.get(t.getKey()) != null || t.getKey() == id)
                while (prev.get(t.getKey()) != null) {
                    path.add(0, t);
                    t = prev.get(t.getKey());
                }
            p = c;
            temp.remove(c);
            ans.addAll(path);
        }
        return ans;
    }

    @Override
    public boolean save(String file) {
        /*Build Json pattern as input exemples (G1/G2/G3.json) in src/main/java/ */
        GsonBuilder gsonBuildr = new GsonBuilder();
        JsonSerializer<Graph> serializer = (d, type, jsonSerializationContext) -> {
            JsonArray Edges = new JsonArray();
            Iterator<EdgeData> ite = graph.edgeIter();
            while (ite.hasNext()) {
                EdgeData e = ite.next();
                JsonObject j = new JsonObject();
                j.addProperty("src", e.getSrc());
                j.addProperty("w", e.getWeight());
                j.addProperty("dest", e.getDest());
                Edges.add(j);
            }
            JsonArray Nodes = new JsonArray();
            Iterator<NodeData> itn = graph.nodeIter();
            while (itn.hasNext()) {
                NodeData n = itn.next();
                JsonObject j = new JsonObject();
                j.addProperty("pos", n.getLocation().toString());
                j.addProperty("id", n.getKey());
                Nodes.add(j);
            }
            JsonObject json = new JsonObject();
            json.add("Edges", Edges);
            json.add("Nodes", Nodes);
            return json;
        };
        gsonBuildr.registerTypeAdapter(Graph.class, serializer);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(gsonBuildr.create().toJson(this.graph));
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        /*Load Json files as input exemples (G1/G2/G3.json) in src/main/java/ */
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            JsonDeserializer<Graph> deserializer = (json, type, jsonDeserializationContext) -> {
                Graph d = new Graph();
                JsonObject jsonObject = json.getAsJsonObject();
                JsonArray n = jsonObject.getAsJsonArray("Nodes");
                for (int i = 0; i < n.size(); i++) {
                    JsonObject t = n.get(i).getAsJsonObject();
                    Location l = new Location(t.get("pos").getAsString());
                    Node node = new Node(t.get("id").getAsInt(), l);
                    d.addNode(node);
                }
                JsonArray e = jsonObject.getAsJsonArray("Edges");
                for (int i = 0; i < e.size(); i++) {
                    JsonObject t = e.get(i).getAsJsonObject();
                    d.connect(t.get("src").getAsInt(), t.get("dest").getAsInt(), t.get("w").getAsDouble());
                }
                return d;
            };
            gsonBuilder.registerTypeAdapter(Graph.class, deserializer);
            Gson gson = gsonBuilder.create();
            FileReader fr = new FileReader(file);
            Graph DWG;
            DWG = gson.fromJson(fr, Graph.class);
            this.graph = DWG;
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }
}

