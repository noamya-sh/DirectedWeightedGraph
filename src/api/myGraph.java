package api;

import javax.swing.*;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
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
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screensize);
        f.setSize(screensize.width / 2, screensize.height / 2);
        //jf.setResizable(false);
        f.getContentPane().setBackground(new Color(21, 55, 47));
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

//        {}
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                DirectedWeightedGraphAlgorithms dd = new DirectedWeightedGraphAlgorithmsClass();
//                dd.save("output.json");
//            }
//        });
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
        f.setJMenuBar(menuBar);
        myPannel p = new myPannel();
        f.add(p);
        String file="ff";

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DirectedWeightedGraphAlgorithms dd = new DirectedWeightedGraphAlgorithmsClass();
                dd.init(p.d);
                dd.save("output.json");
//                SwingUtilities.updateComponentTreeUI(f);
            }
        });
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DirectedWeightedGraphAlgorithms dd = new DirectedWeightedGraphAlgorithmsClass();
                dd.load("output.json");
                LinkedList<Point> l= new LinkedList<>();
                Iterator<NodeData> it = dd.getGraph().nodeIter();
                while (it.hasNext()){
                    NodeData n =it.next();
                    int x = (int)n.getLocation().x();
                    int y = (int)n.getLocation().y();
                    l.add(new Point(x,y));
                }
                myPannel pp = new myPannel(l);
//                f.remove(0);
                f.add(pp);
//                f.remove(p);
                f.invalidate();
                f.validate();
                f.repaint();
            }
        });
        f.setVisible(true);
    }

    static class myPannel extends JPanel implements MouseListener {
        public static int ID = 0;
        LinkedList<Point> points = new LinkedList<>();
        LinkedList<Node> nodes = new LinkedList<>();
        DirectedWeightedGraphClass d = new DirectedWeightedGraphClass();
        public myPannel() {
            this.addMouseListener(this);
        }
        public myPannel(LinkedList<Point> points){
            this.points=points;
            this.addMouseListener(this);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Point prev=null;
            Node pn =null;
            int c = 0;
            for (Point p:points) {

                g.setColor(new Color(100, 100, 21));
                g.fillOval((int)p.getX()-10,(int)p.getY()-10,15,15);
                if(prev!=null){
                    Double dist = p.distance(prev);
                    String distS = dist.toString().substring(0,dist.toString().indexOf(".")+2);
                    g.drawLine((int)p.getX(),(int)p.getY(),(int)prev.getX(),(int)prev.getY());
                    g.drawString(distS, (int)((p.getX()+prev.getX())/2),(int)((p.getY()+prev.getY())/2));
                    d.connect(c-1,c,Double.parseDouble(distS));
                }
                c++;
                prev=p;

            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(d.NodeHash);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            points.add(p);
            Location l = new Location(e.getX(), e.getY());
            Node n =new Node(ID++,l);
            d.addNode(n);
            nodes.add(n);
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
