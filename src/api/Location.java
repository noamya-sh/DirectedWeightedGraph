package api;

public class Location implements GeoLocation {
    public double x,y,z;

    public Location(){
        this.x=0;
        this.y=0;
        this.z=0;
    }

    public Location(GeoLocation l){
        this.x=l.x();
        this.y=l.y();
        this.z=l.z();
    }

    @Override
    public String toString() {
        return "{" + x +
                ", " + y +
                ", " + z +
                '}';
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
