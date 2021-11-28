package api;
//import json;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraphClass graph;

    DirectedWeightedGraphAlgorithmsClass(){
        this.graph =new DirectedWeightedGraphClass();
    }

    @Override
    public String toString() {
        return "" + graph + "";
    }

    @Override
    public void init(DirectedWeightedGraph g) {

    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraphClass DA=new DirectedWeightedGraphClass(graph);
        return DA;
    }


    @Override
    public boolean isConnected() {
        for (NodeData n:graph.NodeList.values())
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

    @Override
    public double shortestPathDist(int src, int dest) {
        int len=this.graph.NodeList.size();
        double[][]mat=new double[len][len];
        for(int k=0;k<10;k++){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(mat[i][k]!=0 && mat[k][j]!=0 &&mat[i][j]!=0){
                        mat[i][j]=Math.min(mat[i][j],mat[i][k]+mat[k][j]);
                    }
                }
            }
        }
        return mat[src][dest];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        //Gson gson = new Gson();
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw=new FileWriter(file);
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

            GsonBuilder gsonBuilder= new GsonBuilder();
            //gsonBuilder.registerTypeAdapter(DirectedWeightedGraphClass.class,)
            Gson gson = gsonBuilder.create();
            FileReader fr= new FileReader(file);

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
        DirectedWeightedGraphAlgorithmsClass d= new DirectedWeightedGraphAlgorithmsClass();
        Edge e=new Edge(3,5,10);
        Node n=new Node();
        d.getGraph().addNode(n);
        d.getGraph().connect(3,5,10);
        //d.save("bbb.json");
        //System.out.println(d);
        d.load("G1.json");

        //System.out.println(d.shortestPath(3,7));

    }
}
