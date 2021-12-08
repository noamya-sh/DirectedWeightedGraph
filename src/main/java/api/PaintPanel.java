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
    public String con = null;
    DirectedWeightedGraphAlgorithms dg;
    HashMap<Integer,Point2D> l;
    int center= -1;
    int w=1280,h=720;
    public String path;


    public int brushSize = 10;
    private int mouseX = -1;
    private int mouseY = -1;
    private boolean mousePressed = false;

    public PaintPanel(String s) {
        super();
        this.setBackground(new Color(20,50,50));
        this.l = new HashMap<>();
        this.dg = new GraphAlgo();
        this.path=s;
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
    public PaintPanel(DirectedWeightedGraphAlgorithms dg,HashMap<Integer,Point2D> points,int center){
        this.l=points;
        this.dg=dg;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.center=center;
        this.setBackground(new Color(20,50,50));

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
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    private double cal(double max,double min,double val,int h){
        double diff= max-min;
        return (((val-min)/diff)*h)*0.6+0.2*h;//+(0.1*h);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
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
        for (Point2D p : l.values())
            p.setLocation(cal(maxx,minx,p.getX(),this.w),cal(maxy,miny,p.getY(),this.h));
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
//            if (x2 > x1)
//                g.drawString(w,(int)((x1+x2)/2) + 10,(int)((y1+y2)/2) + 10);
//            else
//                g.drawString(w,(int)((x1+x2)/2) - 10,(int)((y1+y2)/2) -10);
            g.setColor(new Color(153, 204, 255));
            g2d.setColor(new Color(153, 204, 255));
            g2d.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(x1,y1,x2,y2);
        }
        for (var e:l.entrySet()){
            if (e.getKey()!=center){
                g.setColor(new Color(204, 204, 0));
                //e.getValue().setLocation(cal(maxx,minx,e.getValue().getX(),this.w),cal(maxy,miny,e.getValue().getY(),this.h));
                g.fillOval((int)e.getValue().getX()-5,(int)e.getValue().getY()-5,10,10);
                g.setColor(new Color(0, 0, 0));
                g.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
                g.drawString(""+e.getKey(),(int)e.getValue().getX()-5,(int)e.getValue().getY()-7);
            }
            else if (e.getKey()==center){
                g.setColor(new Color(255, 100, 102));
                e.getValue().setLocation(cal(maxx,minx,e.getValue().getX(),this.w),cal(maxy,miny,e.getValue().getY(),this.h));
                g.fillOval((int)e.getValue().getX()-6,(int)e.getValue().getY()-6,12,12);
                g.setFont(new Font("Ink Free", Font.BOLD, 20));
                g.drawString("Center",(int)e.getValue().getX()-6,(int)e.getValue().getY()+26);
                g.setFont(new Font("Eras Demi ITC", Font.BOLD, 12));
                g.setColor(new Color(0, 0, 0));
                g.drawString(""+e.getKey(),(int)e.getValue().getX()-5,(int)e.getValue().getY()-7);
            }
            //g.drawString(Integer.toString(l.get() ),(int)p.getX(),(int)p.getY());
        }
        if (con!= null){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            g.drawString(con,getWidth()/2-100,getHeight()/2-500);
        }
//        }
    }

}
