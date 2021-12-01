package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class window extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu runAlgoMenu;
    JMenu helpMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem algoItem;
    ImageIcon loadIcon;
    ImageIcon saveIcon;
    ImageIcon exitIcon;

    window(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 530);
        this.setLayout(new FlowLayout());


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screensize);
        this.setSize(screensize.width / 2, screensize.height / 2);
        //jf.setResizable(false);
        this.getContentPane().setBackground(new Color(21, 55, 47));
//        this.add(new myGraph.myPannel());

//        class myPannel extends JPanel implements MouseListener {
//            LinkedList<Point2D> points = new LinkedList<>();
//
//            public myPannel() {
//                this.addMouseListener(this);
//            }
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Point2D prev=null;
//                for (Point2D p:points) {
//                    g.setColor(new Color(234, 26, 171));
//                    g.fillOval((int)p.getX()-10,(int)p.getY()-10,20,20);
//                    if(prev!=null){
//                        Double dist = p.distance(prev);
//                        String distS = dist.toString().substring(0,dist.toString().indexOf(".")+2);
//                        g.drawLine((int)p.getX(),(int)p.getY(),(int)prev.getX(),(int)prev.getY());
//                        g.drawString(distS, (int)((p.getX()+prev.getX())/2),(int)((p.getY()+prev.getY())/2));
//                    }
//                    prev=p;
//
//                }
//        loadIcon = new ImageIcon("./resources/load.jpg");
//        saveIcon = new ImageIcon("./resources/save.png");
//        exitIcon = new ImageIcon("./resources/exit.jpg");

//        loadIcon = scaleImageIcon(loadIcon,20,20);
//        saveIcon = scaleImageIcon(saveIcon,20,20);
//        exitIcon = scaleImageIcon(exitIcon,20,20);

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




        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        loadItem.setIcon(loadIcon);
        saveItem.setIcon(saveIcon);
        exitItem.setIcon(exitIcon);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        //fileMenu.add(algoItem);
        fileMenu.add(exitItem);
        runAlgoMenu.add(IcITEM);
        runAlgoMenu.add(SPDItem);
        runAlgoMenu.add(SPITEM);
        runAlgoMenu.add(CITEM);
        runAlgoMenu.add(TspITEM);

        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
        editMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit
        runAlgoMenu.setMnemonic(KeyEvent.VK_R);
        helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + h for help
        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(runAlgoMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    public static ImageIcon scaleImageIcon(ImageIcon imageIcon,int width,int height){
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);  // transform it back
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==loadItem) {

            System.out.println("*beep boop* you loaded a file");
        }
        if(e.getSource()==saveItem) {
            DirectedWeightedGraphAlgorithmsClass d= new DirectedWeightedGraphAlgorithmsClass();
            Node n= new Node(0);
            Node b= new Node(1);
            n.setWeight(6);
            Edge ee=new Edge();
            d.getGraph().addNode(n);
            d.getGraph().addNode(b);
            d.getGraph().connect(0,1,54);
            d.save("fff");
            System.out.println("*beep boop* you saved a file");
        }
        if(e.getSource()==exitItem) {
            System.exit(0);
        }
    }




    public static void main(String[] args) {
        new window();
    }
}
