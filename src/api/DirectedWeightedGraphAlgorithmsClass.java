package api;
//import json;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraphClass graph;

    DirectedWeightedGraphAlgorithmsClass() {
        this.graph = new DirectedWeightedGraphClass();
    }

    @Override
    public String toString() {
        return "" + graph + "";
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (DirectedWeightedGraphClass) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraphClass DA = new DirectedWeightedGraphClass(graph);
        return DA;
    }


    @Override
    public boolean isConnected() {
        for (NodeData n : graph.NodeHash.values())
            if (((Node) n).getEdges().size()==0 && ((Node) n).getNeighbors().size()==0)
                return false;
//        for(int i = 0; i<this.graph.NodeList.size(); i++){
//            for (int j = 0; j<this.graph.NodeList.size(); j++){
//                if(this.shortestPathDist(i,j)==0)
//                    return false;
//            }
//        }
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

                        int x=l[i][j].size();
                        l[i][j].add(x-1,this.graph.NodeHash.get(k));
                    }
                }
            }
        }
        return l[src][dest];
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
        }double min=Integer.MAX_VALUE;
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
        //if is connect
        double[][] mat = matShortPath();
        Queue<NodeData> q = new LinkedList<>();
        for (NodeData n:cities) {
            n.setTag(0);
            q.add(n);
        }
        NodeData n = q.poll();
        while (!q.isEmpty()){
            NodeData u = q.poll();

        }

        return null;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new Gson();
        GsonBuilder gsonBuildr = new GsonBuilder();
        JsonSerializer<DirectedWeightedGraphClass> serializer = new JsonSerializer<DirectedWeightedGraphClass>() {
            @Override
            public JsonElement serialize(DirectedWeightedGraphClass d, Type type, JsonSerializationContext jsonSerializationContext) {
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
        gsonBuildr.registerTypeAdapter(DirectedWeightedGraphClass.class,serializer);
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
            JsonDeserializer<DirectedWeightedGraphClass> deserializer = new JsonDeserializer<DirectedWeightedGraphClass>() {
                @Override
                public DirectedWeightedGraphClass deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    DirectedWeightedGraphClass d = new DirectedWeightedGraphClass();
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
            gsonBuilder.registerTypeAdapter(DirectedWeightedGraphClass.class,deserializer);
            Gson gson = gsonBuilder.create();
            FileReader fr = new FileReader(file);
            DirectedWeightedGraphClass DWG;
            DWG = gson.fromJson(fr, DirectedWeightedGraphClass.class);
            this.graph = DWG;
//            System.out.println(DWG);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithmsClass d = new DirectedWeightedGraphAlgorithmsClass();
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
        g.connect(2,3,70);

        d.load("output.json");
        System.out.println(d.graph);
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

