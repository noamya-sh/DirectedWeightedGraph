package api;


/**Implement EdgeData Interface designed to represent a graph. **/
public class Edge implements EdgeData {
    private int src,dest;
    private double Weight;
    private String info;
    private int Tag;

    public Edge(EdgeData e){
        this.src=e.getSrc();
        this.dest=e.getDest();
        this.Weight=e.getWeight();
        this.info=e.getInfo();
        this.Tag=e.getTag();
    }
    public Edge(int src,int dest,double Weight) {
        this.src = src;
        this.dest = dest;
        this.Weight = Weight;
        this.info="src:"+this.src+", w:"+this.Weight+", dest:"+this.dest;
        this.Tag=0;
    }

    @Override
    public String toString() {
        return
                "{src:" + src + ",w:" + Weight + ",dest:" + dest +'}';
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return this.Weight;
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
