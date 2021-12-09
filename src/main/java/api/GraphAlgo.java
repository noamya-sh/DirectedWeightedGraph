package api;
//import json;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    Graph graph;

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
        Graph DA = new Graph(graph);
        return DA;
    }

    private HashMap<Integer,Boolean> bfs(Graph g){
        HashMap<Integer,Boolean> test = new HashMap<>();
        Iterator<NodeData> it = g.nodeIter();
        it.forEachRemaining(n->test.put(n.getKey(),false));
        int firstID = test.keySet().stream().findFirst().get();
        test.replace(firstID,true);
        Queue<NodeData> q = new LinkedList<>();
        q.add(g.NodeHash.get(firstID));
        while (!q.isEmpty()){
            Node n = (Node)q.poll();
            Iterator<EdgeData> i = g.edgeIter(n.getKey());
            while (i.hasNext()){
                EdgeData e = i.next();
                if (test.get(e.getDest())==true)
                    continue;
                test.replace(e.getDest(),true);
                q.add(g.NodeHash.get(e.getDest()));
            }
        }
        return test;
    }
    @Override
    public boolean isConnected() {
        HashMap<Integer,Boolean> d = bfs(graph);
        for (Boolean b:d.values()) if (b==false) return false;
        //create transfom of this graph, invert each edge..
        Graph copy = new Graph();
        for (NodeData n : graph.NodeHash.values()) copy.NodeHash.put(n.getKey(),new Node(n.getKey()));
        for (EdgeData e : graph.EdgeHash.values()){
            int dest = e.getSrc(), src = e.getDest();
            ((Node) copy.NodeHash.get(src)).getEdges().put(dest,new Edge(src,dest,e.getWeight()));
        }
        HashMap<Integer,Boolean> r = bfs(copy);
        for (Boolean b:r.values()) if (b==false) return false;
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        Dijkstra d = new Dijkstra(this.graph);
        return d.getPathDist(src,dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        Dijkstra d = new Dijkstra(this.graph);
        return d.getPath(src,dest);
    }
    @Override
    public NodeData center() {
        if(!this.isConnected())
            return null;
        double min=Integer.MAX_VALUE;
        NodeData ans= null;
        Dijkstra d = new Dijkstra(this.graph);
        for (NodeData n : this.graph.NodeHash.values()){
            double max = d.getMaxPath(n.getKey());
            if (max < min){
                min = max;
                ans = n;
            }
        }
        return ans;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (!isConnected() || cities.size()==0)
            return null;
        List<NodeData> ans = new LinkedList<>();
        List<NodeData> temp = new ArrayList<>(cities);
        NodeData p = temp.remove(0);
        ans.add(p);
        while (temp.size()>=1){
            int id = p.getKey();
            Dijkstra d = new Dijkstra(this.graph);
            HashMap<Integer,Double> dist = d.getDist(id);
            NodeData t = null; double min=Integer.MAX_VALUE;
            for(NodeData n : temp){
                if (dist.get(n.getKey())<min){
                    min = dist.get(n.getKey());
                    t = n ;
                }
            }
            List<NodeData> path = new LinkedList<>();
            HashMap<Integer,NodeData> prev = d.getPrev();
            NodeData c = t;
            if (prev.get(t.getKey())!=null || t.getKey()==id)
                while (prev.get(t.getKey())!=null){
                    path.add(0,t);
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
        GsonBuilder gsonBuildr = new GsonBuilder();
        JsonSerializer<Graph> serializer = new JsonSerializer<Graph>() {
            @Override
            public JsonElement serialize(Graph d, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonArray Edges = new JsonArray();
                Iterator<EdgeData> ite = graph.edgeIter();
                while (ite.hasNext()){
                    EdgeData e = ite.next();
                    JsonObject j = new JsonObject();
                    j.addProperty("src",e.getSrc());
                    j.addProperty("w",e.getWeight());
                    j.addProperty("dest",e.getDest());
                    Edges.add(j);
                }
                JsonArray Nodes = new JsonArray();
                Iterator<NodeData> itn = graph.nodeIter();
                while (itn.hasNext()){
                    NodeData n = itn.next();
                    JsonObject j = new JsonObject();
                    j.addProperty("pos",n.getLocation().toString());
                    j.addProperty("id",n.getKey());
                    Nodes.add(j);
                }
                JsonObject json = new JsonObject();
                json.add("Edges",Edges);
                json.add("Nodes",Nodes);
                return json;
            }
        };
        gsonBuildr.registerTypeAdapter(Graph.class,serializer);
//        System.out.println(jsonString);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(gsonBuildr.create().toJson(this.graph));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean load(String file) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            JsonDeserializer<Graph> deserializer = new JsonDeserializer<Graph>() {
                @Override
                public Graph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    Graph d = new Graph();
                    JsonObject jsonObject= json.getAsJsonObject();
                    JsonArray n = jsonObject.getAsJsonArray("Nodes");
                    for (int i = 0; i < n.size(); i++) {
                        JsonObject t = n.get(i).getAsJsonObject();
                        Location l = new Location(t.get("pos").getAsString());
                        Node node = new Node(t.get("id").getAsInt(),l);
                        d.addNode(node);
                    }
                    JsonArray e = jsonObject.getAsJsonArray("Edges");
                    for (int i = 0; i < e.size(); i++) {
                        JsonObject t = e.get(i).getAsJsonObject();
                        d.connect(t.get("src").getAsInt(),t.get("dest").getAsInt(),t.get("w").getAsDouble());
                    }
                    return d;
                }
            };
            gsonBuilder.registerTypeAdapter(Graph.class,deserializer);
            Gson gson = gsonBuilder.create();
            FileReader fr = new FileReader(file);
            Graph DWG;
            DWG = gson.fromJson(fr, Graph.class);
            this.graph = DWG;
//            System.out.println(DWG);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        GraphAlgo d = new GraphAlgo();
//        Edge e = new Edge(3, 5, 10);
        Node n5 = new Node(0);
        Node n4 = new Node(4);
        Node n3 = new Node(3);
        Node n2 = new Node(2);
        Node n1 = new Node(1);
        DirectedWeightedGraph g = d.getGraph();
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        //g.addNode(n4);
        g.addNode(n5);
        g.connect(0, 2, 50);
        g.connect(1, 2, 10);
        g.connect(3, 1, 30);
//        g.connect(2, 0, 30);
        g.connect(2,3,70);

//        d.load("output.json");
        System.out.println(d.isConnected());
//        Boolean b = d.load("src/G1.json");
//        System.out.println(b);
//        Iterator it = d.getGraph().edgeIter();
//        while (it.hasNext()) {
//            Edge h = (Edge) it.next();
            //System.out.println(h);
            //it.remove();
//            System.out.println(d.center());

        }
    }

