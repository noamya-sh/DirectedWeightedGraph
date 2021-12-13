package api;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class PaintPanel extends JPanel {
    GraphAlgo dg;
    HashMap<Integer,Point2D> l;
    int center= -1;
    int w=(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() , h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private List<NodeData> dEdges;

    public PaintPanel(String s) {
        super();
        this.l = new HashMap<>();
        this.dg = new GraphAlgo();
        dg.load(s);
        initPoints();
    }
    public PaintPanel(DirectedWeightedGraph d) {
        super();
        this.dg=new GraphAlgo();
        this.dg.init(d);
        initPoints();
    }
    private void initPoints(){
        this.l=new HashMap<>();
        Iterator<NodeData> in = dg.getGraph().nodeIter();
        while (in.hasNext()) {
            NodeData n = in.next();
            l.put(n.getKey(), new Point2D.Double(n.getLocation().x(), n.getLocation().y()));
        }
        this.setBackground(new Color(0,51,51));
    }
    public void setCenter(int center){
        this.dEdges = null;
        this.center = center;
    }
    private double cal(double max,double min,double val,int h){
        double diff= Math.abs(max-min);
        //return (h/diff)*val;//*0.5;
        return (((val-min)/diff)*h)*0.6+0.2*h;//+(0.1*h);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        double maxx = Integer.MIN_VALUE, minx = Integer.MAX_VALUE;
        double maxy = Integer.MIN_VALUE, miny = Integer.MAX_VALUE;
        for (Point2D p : l.values()) {
            if (p.getX() > maxx)
                maxx = p.getX();
            if (p.getX() < minx)
                minx = p.getX();
            if (p.getY() > maxy)
                maxy = p.getY();
            if (p.getY() < miny)
                miny = p.getY();
            }
        for (Point2D p : l.values())
            p.setLocation(cal(maxx, minx, p.getX(), this.w), cal(maxy, miny, p.getY(), this.h));
        List<Polygon> pol2 = new ArrayList<>();
        HashMap<String,EdgeData> copy = new HashMap<>(((Graph) (dg.getGraph())).getEdgeHash());
        if (dEdges != null) {
            Iterator<NodeData> ed = dEdges.iterator();
            NodeData src = ed.next();
            while (ed.hasNext()) {
                NodeData dest = ed.next();
                copy.remove(src.getKey() + "_" + dest.getKey());
                src = dest;
            }
        }

        List<Polygon> pol = new ArrayList<>();
        for (EdgeData e : copy.values()) {
            int x1 = (int) l.get(e.getSrc()).getX();
            int y1 = (int) l.get(e.getSrc()).getY();
            int x2 = (int) l.get(e.getDest()).getX();
            int y2 = (int) l.get(e.getDest()).getY();
            g.setColor(new Color(102, 102, 255));
            String w = String.format("%.2f", e.getWeight());
            g.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
            if (x2 > x1)
                g.drawString(w,((x1+x2)/2) + 5,((y1+y2)/2) + 5);
            else
                g.drawString(w,((x1+x2)/2) - 10,((y1+y2)/2) -5);
            Color c = new Color(153, 204, 255);
            pol.add(drawArrowLine(g2d,x1, y1, x2, y2,10,5,c,2));
        }
        for (Polygon p :pol){
            g.setColor(new Color(102,0,51));
            g.fillPolygon(p);
        }
        if (dEdges != null) {
            Iterator<NodeData> ed = dEdges.iterator();
            NodeData src = ed.next();
            while (ed.hasNext()) {
                NodeData dest = ed.next();
                int x1 = (int) l.get(src.getKey()).getX();
                int y1 = (int) l.get(src.getKey()).getY();
                int x2 = (int) l.get(dest.getKey()).getX();
                int y2 = (int) l.get(dest.getKey()).getY();
                Color c = new Color(204, 150, 0);
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                pol2.add(drawArrowLine(g2d,x1, y1, x2, y2,14,9, c,3));
                src = dest;
            }
        }
        for (Polygon p :pol2){
            g.setColor(new Color(76,153,0));
            g.fillPolygon(p);
        }
        for (var e : l.entrySet()) {
            if (e.getKey() != center) {
                g.setColor(new Color(102,0,0));

                g.fillOval((int) e.getValue().getX() - 10, (int) e.getValue().getY() - 10, 20, 20);
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
                g.drawString("" + e.getKey(), (int) e.getValue().getX() - 5, (int) e.getValue().getY() + 5);
            } else {
                g.setColor(new Color(204, 150, 0));
                g.fillOval((int) e.getValue().getX() - 10, (int) e.getValue().getY() - 10, 20, 20);
                g.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
                g.drawString("Center", (int) e.getValue().getX() - 6, (int) e.getValue().getY() - 35);
                g.setColor(new Color(0, 0, 0));
                g.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
                g.drawString("" + e.getKey(), (int) e.getValue().getX() - 4, (int) e.getValue().getY() + 5);
            }
        }
    }
    /**Function to create an arrow whose head touches the end of the Node.
     * @return head of arrow polygon **/
    private Polygon drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int width, int height, Color c, int s) {
        int dx = x2 - x1, dy = y2 - y1;
        double dis = Math.sqrt(dx*dx + dy*dy);
        double xm = dis - width, xn = xm, ym = height, yn = -height, x;
        double sin = dy / dis, cos = dx / dis;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        x2 = (int) (xm+xn)/2; y2 = (int) (ym+yn)/2;
        dx = x2 - x1;
        dy = y2 - y1;
        dis = Math.sqrt(dx*dx + dy*dy);
        xm = dis - width; xn = xm; ym = height; yn = -height;
        sin = dy / dis; cos = dx / dis;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;
        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;
        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(s, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(c);
        g2d.drawLine(x1, y1, x2, y2);
        return new Polygon(xpoints,ypoints, 3);
    }
        public void drawEdges (List < NodeData > ans) {
            this.center = -1;
            this.dEdges = ans;
        }
    }

