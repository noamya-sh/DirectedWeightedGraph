package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

public class window extends JFrame implements ActionListener {

    GraphAlgo d=new GraphAlgo();
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu runAlgoMenu;
    JMenu helpMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem SPDItem;
    JMenuItem CITEM;
    JMenuItem SPITEM;
    JMenuItem IcITEM;
    JMenuItem addNode;
    JMenuItem removeNode;
    JMenuItem addEdge;
    JMenuItem removeEdge;

    JTextField tf;
    JButton b;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;

    JLabel l;
    ImageIcon loadIcon;
    ImageIcon saveIcon;
    ImageIcon exitIcon;

    window(){
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(320, 530);
        //this.setLayout(new FlowLayout());
        this.setLayout(null);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screensize);
        this.setSize(screensize.width / 2, screensize.height / 2);
        //jf.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.add(new draw());
        repaint();

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
        SPDItem = new JMenuItem("ShortestPathDist");
        SPITEM = new JMenuItem("shortestPath");
        CITEM = new JMenuItem("center");
        JMenuItem TspITEM = new JMenuItem("tsp");
        IcITEM = new JMenuItem("isConnected");
        addNode =new JMenuItem(("addNode"));
        removeNode =new JMenuItem(("removeNode"));
        addEdge =new JMenuItem(("addEdge"));
        removeEdge =new JMenuItem(("removeEdge"));


        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        SPDItem.addActionListener(this);
        CITEM.addActionListener(this);
        SPITEM.addActionListener(this);
        IcITEM.addActionListener(this);

//        loadItem.setIcon(loadIcon);
//        saveItem.setIcon(saveIcon);
//        exitItem.setIcon(exitIcon);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        editMenu.add(addNode);
        editMenu.add(removeNode);
        editMenu.add(addEdge);
        editMenu.add(removeEdge);
        runAlgoMenu.add(IcITEM);
        runAlgoMenu.add(SPDItem);
        runAlgoMenu.add(SPITEM);
        runAlgoMenu.add(CITEM);
        runAlgoMenu.add(TspITEM);

//        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
//        editMenu.setMnemonic(KeyEvent.VK_E); // Alt + e for edit
//        runAlgoMenu.setMnemonic(KeyEvent.VK_R);
//        helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + h for help
//        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
//        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
//        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(runAlgoMenu);
        //menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

//    public static ImageIcon scaleImageIcon(ImageIcon imageIcon,int width,int height){
//        Image image = imageIcon.getImage(); // transform it
//        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//        return new ImageIcon(newimg);  // transform it back
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==loadItem) {
            d.load("src/main/java/G2.json");
            System.out.println(d.center());
            this.add(new draw());
            repaint();
            this.setVisible(true);
            System.out.println("*beep boop* you loaded a file");
        }
        if(e.getSource()==saveItem) {
            GraphAlgo d= new GraphAlgo();
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
        if(e.getSource()==SPDItem) {
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            this.add(tf);
            tf.addActionListener(this);
            this.b = new JButton("go");
            b.setBounds(50, 100, 100, 20);
            this.add(b);
            b.addActionListener(this);
//            this.l = new JLabel("hello");
//            l.setBounds(80, 50, 70, 20);
//            this.add(l);

        }
            if (e.getSource()==b) {
                String s = tf.getText();
                System.out.println(d.shortestPathDist(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2))));
            }
            //System.out.println(d.shortestPathDist((int) s.charAt(0),(int) s.charAt(2)));

            System.out.println("h");

        if(e.getSource()==exitItem) {
            System.exit(0);
        }
        if(e.getSource()==CITEM){
            System.out.println(d.center());
        }
        if(e.getSource()==SPITEM){
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            //tf.setText("hhh");
            this.add(tf);
            //System.out.println(tf.getText());
            tf.addActionListener(this);
            this.b1 = new JButton("go");
            b1.setBounds(50, 100, 100, 20);
            this.add(b1);
            b1.addActionListener(this);
        }
        if(e.getSource()==b1){
            String s = tf.getText();
            System.out.println(d.shortestPath(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2))));
        }
        if(e.getSource()==IcITEM){
            System.out.println(d.isConnected());
        }
        if (e.getSource()==addNode){
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            this.add(tf);
            tf.addActionListener(this);
            this.b2 = new JButton("go");
            b2.setBounds(50, 100, 100, 20);
            this.add(b2);
            b2.addActionListener(this);
        }
        if (e.getSource()==removeNode){
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            this.add(tf);
            tf.addActionListener(this);
            this.b3 = new JButton("go");
            b3.setBounds(50, 100, 100, 20);
            this.add(b3);
            b3.addActionListener(this);
        }
        if (e.getSource()==addEdge){
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            this.add(tf);
            tf.addActionListener(this);
            this.b4 = new JButton("go");
            b4.setBounds(50, 100, 100, 20);
            this.add(b4);
            b4.addActionListener(this);
        }
        if (e.getSource()==removeEdge){
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            this.add(tf);
            tf.addActionListener(this);
            this.b5 = new JButton("go");
            b5.setBounds(50, 100, 100, 20);
            this.add(b5);
            b5.addActionListener(this);
        }

    }

public class draw extends JPanel implements MouseListener {
        LinkedList<Point>l=new LinkedList();

        public draw() {
            this.addMouseListener(this);
            //d.load("src/main/java/G1.json");
            Iterator<NodeData> it = d.getGraph().nodeIter();
            while (it.hasNext()) {
                NodeData n = it.next();
                int x = (int) n.getLocation().x();
                int y = (int) n.getLocation().y();
                l.add(new Point(x, y));
                repaint();
            }
        }
    @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //LinkedList<Point> l = new LinkedList<>();
//        Iterator<NodeData> it = d.getGraph().nodeIter();
//        while (it.hasNext()) {
//            NodeData n = it.next();
//            int x = (int) n.getLocation().x();
//            int y = (int) n.getLocation().y();
//            l.add(new Point(x, y));
//        }
        for (Point p:l) {
            g.setColor(Color.BLUE);
            g.fillOval(p.x,p.y,5,5);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("gggggggg");
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

    public static void main(String[] args) {
        new window();
    }

}