package api;
/**This class implement GeoLocation Interface.
 * the construcor of location can create new Location from String, for load Graph from Json file.**/
public class Location implements GeoLocation {
    private double x,y,z;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Location(){
        this.x=0;
        this.y=0;
        this.z=0;
    }
    public Location(String s){
        String[] sp = s.split(",");
        this.x=Double.parseDouble(sp[0]);
        this.y=Double.parseDouble(sp[1]);
        this.z=Double.parseDouble(sp[2]);
    }
    public Location(int x,int y){
        this.x=x;
        this.y=y;
        this.z=0;
    }
    public Location(GeoLocation l){
        this.x=l.x();
        this.y=l.y();
        this.z=l.z();
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double a=Math.pow(this.x-g.x(),2);
        double b=Math.pow(this.y-g.y(),2);
        double c=Math.pow(this.z-g.z(),2);
        return Math.sqrt(a+b+c);
    }
}
