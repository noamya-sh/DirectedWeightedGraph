package api;



public class Edge implements EdgeData {
    public int src,dest;
    public double Weight;
    public String info;
    public int Tag;

    public Edge(){
        this.src=0;
        this.dest=0;
        this.Weight=0;
        this.info="src:"+this.src+", w:"+this.Weight+", dest:"+this.dest;
        this.Tag=0;
    }

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
    }

    @Override
    public String toString() {
        return
                "{src:" + src +
                ", w:" + Weight +
                        ", dest:" + dest +
                '}';
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
