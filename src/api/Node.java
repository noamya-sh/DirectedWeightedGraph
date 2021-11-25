package api;

public class Node implements NodeData {
    Location pos;
    int id;
    double Weight;
    String info;
    int Tag;

    public Node(){
        this.pos = new Location();
        this.id =0;
//        this.Weight=0;
//        this.info="";
//        this.Tag=0;
    }

    public Node(Node n){
        this.pos =n.pos;
        this.id =n.id;
        this.Weight=n.Weight;
        this.info=n.info;
        this.Tag=n.Tag;
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
}
