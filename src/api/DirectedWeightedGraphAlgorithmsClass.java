package api;
//import json;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
            if (!((Node) n).isConnect())
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



    @Override
    public NodeData center() {
//        if(!this.isConnected())
//            return null;
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
        return null;
    }

    @Override
    public boolean save(String file) {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(gson.toJson(this.graph));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(gson.toJson(this.graph));
        return true;
    }

    @Override
    public boolean load(String file) {
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            //gsonBuilder.registerTypeAdapter(DirectedWeightedGraphClass.class,)
            Gson gson = gsonBuilder.create();
            FileReader fr = new FileReader(file);

            DirectedWeightedGraphClass DWG;
            DWG = gson.fromJson(fr, DirectedWeightedGraphClass.class);
            System.out.println(DWG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //System.out.println(DWG);
        return true;
    }


    public static void main(String[] args) {
        DirectedWeightedGraphAlgorithmsClass d = new DirectedWeightedGraphAlgorithmsClass();
        Edge e = new Edge(3, 5, 10);
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
        g.connect(3, 1, 20);
        //g.connect(2,3,70);

        d.save("ddd.json");

////        d.load("G1.json");
//        Iterator it = d.getGraph().edgeIter();
//        while (it.hasNext()) {
//            Edge h = (Edge) it.next();
            //System.out.println(h);
            //it.remove();
            System.out.println(d.center());

        }
    }

