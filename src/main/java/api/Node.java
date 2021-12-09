package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node implements NodeData {
    private Location pos;
    private int id;
    private double Weight;
    private String info;
    private int Tag;
    private HashMap<Integer,EdgeData> edges;
    private HashMap<Integer,Integer> neighbors;

    public Node(){
        this.pos = new Location();
        this.id =0;
        this.edges=new HashMap<>();
        this.neighbors=new HashMap<>();
//        this.Weight=0;
//        this.info="";
//        this.Tag=0;
    }
    public Node(int id){
        this.pos = new Location();
        this.id =id;
        this.edges=new HashMap<>();
        this.neighbors=new HashMap<>();
//        this.Weight=0;
//        this.info="";
//        this.Tag=0;
    }

    /**Copy constructor
     * @param n Node copy from it**/
    public Node(NodeData n){
        this.pos =new Location(n.getLocation());
        this.id =n.getKey();
        this.Weight=n.getWeight();
        this.info=n.getInfo();
        this.Tag=n.getTag();
        this.edges=new HashMap<>();
        this.neighbors =new HashMap<>(((Node) n).getNeighbors());
        //not copy boolean connect flag
    }

    public Node(int id, Location l) {
        this.pos=l;
        this.id=id;
        this.edges=new HashMap<>();
        this.neighbors=new HashMap<>();
//        this.Weight=0;
//        this.info="";
//        this.Tag=0;
    }

    @Override
    public String toString() {
        return "{pos:" + pos +
                ", id:" + id +
                '}';
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return this.pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.pos.x=p.x();
        this.pos.y=p.y();
        this.pos.z=p.z();
    }

    @Override
    public double getWeight() {
        return this.Weight;
    }

    @Override
    public void setWeight(double w) {
        this.Weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.Tag;
    }

    @Override
    public void setTag(int t) {
        this.Tag=t;
    }

    public HashMap<Integer, EdgeData> getEdges() {
        return this.edges;
    }

    public HashMap<Integer,Integer> getNeighbors() {
        return this.neighbors;
    }
}
