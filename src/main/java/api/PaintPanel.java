package api;

import api.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class PaintPanel extends JPanel implements MouseInputListener {
    DirectedWeightedGraphAlgorithms dg;
    HashMap<Integer,Point2D> l;
    int w=1280,h=720;

    public int brushSize = 10;
    private int mouseX = -1;
    private int mouseY = -1;
    private boolean mousePressed = false;

    public PaintPanel(String s) {
        super();
        this.setBackground(Color.BLUE);
        this.l = new HashMap<>();
        this.dg = new GraphAlgo();
        //this.h=height;this.w=width;
        dg.load(s);
        Iterator<NodeData> it = dg.getGraph().nodeIter();
        while (it.hasNext()){
            NodeData n = it.next();
            l.put(n.getKey(),new Point2D.Double(n.getLocation().x(),n.getLocation().y()));
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
        return (((val-min)/diff)*h)*0.5+0.1*h;//+(0.1*h);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double maxx=Integer.MIN_VALUE,minx=Integer.MAX_VALUE;
        double maxy=Integer.MIN_VALUE,miny=Integer.MAX_VALUE;
        for (Point2D p : l.values()){
            if (p.getX()>maxx)
                maxx=p.getX();
            if (p.getX()<minx)
                minx=p.getX();
            if (p.getY()>maxy)
                maxy=p.getY();
            if (p.getY()<miny)
                miny=p.getY();

        }

        for (Point2D p : l.values()){
            g.setColor(new Color(189, 255, 102));
            p.setLocation(cal(maxx,minx,p.getX(),this.w),cal(maxy,miny,p.getY(),this.h));
            g.fillOval((int)p.getX()-10,(int)p.getY()-10,10,10);
            //g.drawString(Integer.toString(l.get() ),(int)p.getX(),(int)p.getY());
        }
        Iterator<EdgeData> it = dg.getGraph().edgeIter();
        while(it.hasNext()){
            EdgeData e = it.next();
            int x1 = (int)l.get(e.getSrc()).getX();
            int y1 = (int)l.get(e.getSrc()).getY();
            int x2 = (int)l.get(e.getDest()).getX();
            int y2 = (int)l.get(e.getDest()).getY();
            g.setColor(new Color(255, 177, 102));

            String w = String.format("%.2f", e.getWeight());
            g.setFont(new Font("Ink Free", Font.PLAIN, 10));
            if (x2 > x1)
                g.drawString(w,(int)((x1+x2)/2) + 10,(int)((y1+y2)/2) + 10);
            else
                g.drawString(w,(int)((x1+x2)/2) - 10,(int)((y1+y2)/2) -10);
            g.setColor(new Color(153, 204, 255));
            g.drawLine(x1,y1,x2,y2);
        }

//        }
    }

}
