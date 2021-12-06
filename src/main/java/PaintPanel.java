import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.GraphAlgo;
import api.NodeData;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class PaintPanel extends JPanel implements MouseInputListener {
    DirectedWeightedGraphAlgorithms dg;
    List<Point2D> l;
    int w,h;

    public int brushSize = 10;
    private int mouseX = -1;
    private int mouseY = -1;
    private boolean mousePressed = false;

    public PaintPanel(int width,int height) {
        super();
        this.l = new LinkedList<>();
        this.dg = new GraphAlgo();
        this.h=height;this.w=width;
        dg.load("src/main/java/G2.json");
        Iterator<NodeData> it = dg.getGraph().nodeIter();
        while (it.hasNext()){
            NodeData n = it.next();
            l.add(new Point2D.Double(n.getLocation().x(),n.getLocation().y()));
        }
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mousePressed = true;
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        this.repaint(this.mouseX, this.mouseY, this.brushSize, this.brushSize*2);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    private double cal(double max,double min,double val,int h){
        double diff= max-min;
        return (((val-min)/diff)*h)*0.80+0.1*h;//+(0.1*h);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double maxx=Integer.MIN_VALUE,minx=Integer.MAX_VALUE;
        double maxy=Integer.MIN_VALUE,miny=Integer.MAX_VALUE;
        for (Point2D p : l){
            if (p.getX()>maxx)
                maxx=p.getX();
            if (p.getX()<minx)
                minx=p.getX();
            if (p.getY()>maxy)
                maxy=p.getY();
            if (p.getY()<miny)
                miny=p.getY();

        }

        for (Point2D p : l){
            g.setColor(new Color(255, 255, 21));
            g.fillOval((int)cal(maxx,minx,p.getX(),this.w)-10,(int)cal(maxy,miny,p.getY(),this.h)-10,15,15);
        }
//        int red = (int) (Math.random() * 255);
//        int green = (int) (Math.random() * 255);
//        int blue = (int) (Math.random() * 255);
//        g.setColor(new Color(red, green, blue));
//        if (this.mousePressed) {
////            g.fillRect(this.mouseX, this.mouseY, this.brushSize * 2, this.brushSize * 2);
//            g.fillOval(this.mouseX, this.mouseY, this.brushSize * 2, this.brushSize * 2);
//        }
    }

}
