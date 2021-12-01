package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class myGraph extends JFrame {

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu runAlgoMenu;
    JMenu helpMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    public myGraph() throws Exception {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screensize);
        this.setSize(screensize.width / 2, screensize.height / 2);
        //jf.setResizable(false);
        this.getContentPane().setBackground(new Color(21, 55, 47));
        //new window();
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        runAlgoMenu = new JMenu(("RunAlgo"));
        helpMenu = new JMenu("Help");

        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        JMenuItem SPDItem = new JMenuItem("ShortestPathDist");
        JMenuItem SPITEM = new JMenuItem("shortestPath");
        JMenuItem CITEM = new JMenuItem("center");
        JMenuItem TspITEM = new JMenuItem("tsp");
        JMenuItem IcITEM = new JMenuItem("isConnected");

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        //fileMenu.add(algoItem);
        fileMenu.add(exitItem);
        runAlgoMenu.add(IcITEM);
        runAlgoMenu.add(SPDItem);
        runAlgoMenu.add(SPITEM);
        runAlgoMenu.add(CITEM);
        runAlgoMenu.add(TspITEM);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(runAlgoMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        this.add(new myPannel());
        String file="ff";

        this.setVisible(true);
    }

    static class myPannel extends JPanel implements MouseListener {
        LinkedList<Point2D> points = new LinkedList<>();

        public myPannel() {
            this.addMouseListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Point2D prev=null;
            for (Point2D p:points) {
                g.setColor(new Color(23, 26, 171));
                g.fillOval((int)p.getX()-10,(int)p.getY()-10,15,15);
                if(prev!=null){
                    Double dist = p.distance(prev);
                    String distS = dist.toString().substring(0,dist.toString().indexOf(".")+2);
                    g.drawLine((int)p.getX(),(int)p.getY(),(int)prev.getX(),(int)prev.getY());
                    g.drawString(distS, (int)((p.getX()+prev.getX())/2),(int)((p.getY()+prev.getY())/2));
                }
                prev=p;

            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            Point2D p = new Point(e.getX(), e.getY());
            points.add(p);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static void main(String[] args) throws Exception {
        new myGraph();
    }
}
