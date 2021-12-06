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

    private HashMap<Integer,Boolean> dfs(Graph g){
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
        HashMap<Integer,Boolean> d = dfs(graph);
        for (Boolean b:d.values()) if (b==false) return false;
        //create transfom of this graph, invert each edge..
        Graph copy = new Graph();
        for (NodeData n : graph.NodeHash.values()) copy.NodeHash.put(n.getKey(),new Node(n.getKey()));
        for (EdgeData e : graph.EdgeHash.values()){
            int dest = e.getSrc(), src = e.getDest();
            ((Node) copy.NodeHash.get(src)).getEdges().put(dest,new Edge(src,dest,e.getWeight()));
        }
        HashMap<Integer,Boolean> r = dfs(copy);
        for (Boolean b:r.values()) if (b==false) return false;
        return true;
    }

    public double mini(double i, double j){
        if(i<j && i!=0)
            return i;
        return j;
    }
    @Override
    public double shortestPathDist(int src, int dest) {
        int len = this.graph.NodeHash.size();
        double[][] mat = new double[len][len];
        for (EdgeData e : this.graph.EdgeHash.values()) {
            mat[e.getSrc()][e.getDest()] = e.getWeight();
        }
        for (int k = 0; k < mat.length; k++) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    if (mat[i][k] != 0 && mat[k][j] != 0) {
                        mat[i][j] = mini(mat[i][j], mat[i][k] + mat[k][j]);

                    }
                }
            }
        }
        return mat[src][dest];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        int len = this.graph.NodeHash.size();
        List<NodeData> [][]l=new List[len][len];

        double[][] mat = new double[len][len];
        for (EdgeData e : this.graph.EdgeHash.values()) {
            mat[e.getSrc()][e.getDest()] = e.getWeight();
            l[e.getSrc()][e.getDest()]=new LinkedList<>();
            l[e.getSrc()][e.getDest()].add(this.graph.NodeHash.get(e.getSrc()));
            l[e.getSrc()][e.getDest()].add(this.graph.NodeHash.get(e.getDest()));
        }
        for (int k = 0; k < mat.length; k++) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    if (mat[i][k] != 0 && mat[k][j] != 0 ) {
                        mat[i][j] = mini(mat[i][j], mat[i][k] + mat[k][j]);
                        if (l[i][j] == null){
                            l[i][j] = new LinkedList<>();
                            l[i][j].add(this.graph.NodeHash.get(i));
                            l[i][j].add(this.graph.NodeHash.get(j));
                        }
                        if (mat[i][j] == (mat[i][k] + mat[k][j])){
                            int x=l[i][j].size();
                            l[i][j].add(x-1,this.graph.NodeHash.get(k));
                        }
                    }
                }
            }
        }
        return l[src][dest];
    }
//    private List<NodeData>[][] matListShortPath(){
//
//    }
    public List<NodeData> dij(int src, int dest){
        HashMap<Integer,NodeData> q = new HashMap<>();
        double[] dist = new double[this.graph.NodeHash.size()];
        NodeData[] prev = new NodeData[this.graph.NodeHash.size()];
        for (NodeData n:graph.NodeHash.values()){
            int ind = n.getKey();
            dist[ind] = Integer.MAX_VALUE;
            prev[ind] = null;
            q.put(ind,n);
        }
        dist[src]=0;
        while (!q.isEmpty()){
            double min = Integer.MAX_VALUE; NodeData u =null;
            for (NodeData n: q.values())
                if (dist[n.getKey()]<min){
                    min=dist[n.getKey()];
                    u=n;
                }
            q.remove(u.getKey());
            if (u.getKey() == dest){
                List<NodeData> l = new LinkedList<>();
                if (prev[u.getKey()]!=null || u.getKey()==src)
                    while (prev[u.getKey()]!=null){
                        l.add(0,u);
                        u = prev[u.getKey()];
                    }
                l.add(0,graph.NodeHash.get(src));
                return l;
            }
            Iterator<EdgeData> it = graph.edgeIter(u.getKey());
            while (it.hasNext()){
                Edge e = (Edge) it.next();
                if (q.containsKey(e.getDest())){
                    double t = dist[u.getKey()]+e.getWeight();
                    if (t < dist[e.getDest()]){
                        dist[e.getDest()] = t;
                        prev[e.getDest()] = u;
                    }
                }
            }
        }
        return null;//dist[dest];
    }
    private double[][] matShortPath(){
        int len = this.graph.NodeHash.size();
        double[][] mat = new double[len][len];
        for (EdgeData e : this.graph.EdgeHash.values()) {
            mat[e.getSrc()][e.getDest()] = e.getWeight();
        }
        for (int k = 0; k < mat.length; k++) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    if (mat[i][k] != 0 && mat[k][j] != 0) {
                        mat[i][j] = mini(mat[i][j], mat[i][k] + mat[k][j]);

                    }
                }
            }
        }
        return mat;
    }
    @Override
    public NodeData center() {
        if(!this.isConnected())
            return null;
        double[][] mat = matShortPath();
        double min=Integer.MAX_VALUE;
        int ans=-1;
        for (int i = 0; i < mat.length; i++) {
            double temp = Arrays.stream(mat[i]).max().getAsDouble();
            if(temp<min && temp!=0) {
                min = temp;
                ans = i;
            }
        }
        return this.graph.NodeHash.get(ans);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        //add option if is not connect
        if (cities.size()==0)
            return null;

        double[][] mat = matShortPath();
        List<NodeData> ans = new LinkedList<>();
        List<NodeData> temp = new ArrayList<>(cities);
        ans.add(temp.remove(0));
        while (temp.size()>=1){
            int id = ans.get(ans.size()-1).getKey();
            NodeData n = null; double min=Integer.MAX_VALUE;
            for(NodeData t : temp){
                if (mat[id][t.getKey()]<min){
                    min = mat[id][t.getKey()];
                    n = t ;
                }
            }
            ans.add(n);
            temp.remove(n);
        }
        for (NodeData n:cities) {
            n.setTag(0);
        }

        return null;
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

