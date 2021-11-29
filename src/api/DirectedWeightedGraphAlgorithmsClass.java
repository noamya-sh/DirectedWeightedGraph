package api;
//import json;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
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
        for (NodeData n:graph.NodeHash.values())
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
        int len=this.graph.NodeHash.size();
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
//        DirectedWeightedGraphAlgorithmsClass d= new DirectedWeightedGraphAlgorithmsClass();
//        Edge e=new Edge(3,5,10);
//        Node n5=new Node(5);
//        Node n4=new Node(4);
//        Node n3=new Node(3);
//        Node n2=new Node(2);
//        Node n1=new Node(1);
//        DirectedWeightedGraph g = d.getGraph();
//        g.addNode(n1);g.addNode(n2);g.addNode(n3);g.addNode(n4);g.addNode(n5);
//        g.connect(3,5,10);g.connect(1,2,10);g.connect(2,4,10);

//        d.save("bcc.json");
        System.out.println(5+"-"+6);
////        d.load("G1.json");
//        Iterator it = d.getGraph().edgeIter();
//        while(it.hasNext()){
//            Edge h = (Edge) it.next();
//            System.out.println(h);
//            it.remove();
        //System.out.println(d.shortestPath(3,7));

    }
}
