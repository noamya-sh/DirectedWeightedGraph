package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class window extends JFrame implements ActionListener {
    JPanel mainPanel = new JPanel();
    PaintPanel panel;
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

    JTextField tex;
    JTextField tf;
    JTextField tf2;
    JButton bot;
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
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(getToolkit().getScreenSize());
        //this.setLayout(new FlowLayout());
        //this.setLayout(null);


        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screensize);
        //this.setSize(screensize.width / 2, screensize.height / 2);
        //jf.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        //this.add(this.panel);
        this.setVisible(true);
        repaint();


        loadIcon = new ImageIcon("./resources/load.jpg");
        saveIcon = new ImageIcon("./resources/save.png");
        exitIcon = new ImageIcon("./resources/exit.jpg");

        loadIcon = scaleImageIcon(loadIcon,20,20);
        saveIcon = scaleImageIcon(saveIcon,20,20);
        exitIcon = scaleImageIcon(exitIcon,20,20);

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
        Container contain = getContentPane();

        if(e.getSource()==loadItem) {
            //this.panel=new PaintPanel();
            JFileChooser fc=new JFileChooser();
            fc.setCurrentDirectory(new File("src/main/java"));
            int option=fc.showOpenDialog(this);
            if (option==JFileChooser.APPROVE_OPTION){
                contain.removeAll();
                File f=fc.getSelectedFile();
                PaintPanel p =new PaintPanel(f.getPath());
                d=(GraphAlgo) p.dg;
                this.panel = p;
                contain.add(p);
                contain.validate();
                contain.repaint();
                //this.add(this.panel);

                this.setVisible(true);
            }

        }

        if(e.getSource()==saveItem) {
            JFileChooser fc=new JFileChooser();
            fc.setCurrentDirectory(new File("src/main/java"));
            int option=fc.showSaveDialog(this);
            if (option==JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                d.save(file.getPath());
            }
        }
        if(e.getSource()==SPDItem) {
            this.tf = new JTextField();
            tf.setBounds(50, 50, 100, 20);
            this.panel.add(tf);
            tf.addActionListener(this);
            this.b = new JButton("find shorest path");
            b.setBounds(50, 100, 200, 20);
            this.panel.add(b);
            b.addActionListener(this);
//            this.l = new JLabel("hello");
//            l.setBounds(80, 50, 70, 20);
//            this.add(l);

        }
//        if (e.getSource()== b) {
//            String s = tf.getText();
//            JLabel j=new JLabel();
//            j.setBounds(250,20,400,400);
//            j.setFont(new Font("Serif",Font.BOLD,30));
//            j.setText(""+d.shortestPathDist(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2))));
//            this.panel.add(j);
//            this.panel.remove(tf);
//            this.panel.remove(b);
//            this.setVisible(false);
//            this.setVisible(true);
//            //this.panel.remove(j);
//            System.out.println(d.shortestPathDist(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2))));
//        }
//
//        System.out.println("h");
//
//        if(e.getSource()==exitItem) {
//            System.exit(0);
//        }
        if(e.getSource()==CITEM){
            JLabel j=new JLabel();
            j.setBounds(580,580,200,200);
            j.setFont(new Font("Serif",Font.BOLD,30));
//            if (contain.)
            if (this.panel!=null && this.d.getGraph().nodeSize()>0){
            //j.setText(""+d.center());

                d=(GraphAlgo) panel.dg;
                int key = d.center().getKey();
                //this.panel.center=key;
                PaintPanel p = new PaintPanel(d,this.panel.l,key);
                //p.center=key;

                contain.removeAll();
                contain.add(p);
                this.panel=p;
                JLabel title = new JLabel("The center NodeData: "+key, JLabel.CENTER);
                title.setFont(new Font("Eras Demi ITC", Font.PLAIN, 30));
                title.setPreferredSize(new Dimension(getWidth() / 2, 50));
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                this.setVisible(true);
                System.out.println(d.center());}
        }
//        if(e.getSource()==SPITEM){
//            this.tf2 = new JTextField();
//            tf2.setBounds(50, 50, 100, 20);
//            this.panel.add(tf2);
//            tf2.addActionListener(this);
//            this.b1 = new JButton("go");
//            b1.setBounds(50, 100, 100, 20);
//            this.panel.add(b1);
//            b1.addActionListener(this);
//        }
//        if(e.getSource()==b1){
//            String s = tf2.getText();
//            JLabel j=new JLabel();
//            j.setBounds(80,80,50,400);
//            j.setFont(new Font("Serif",Font.BOLD,30));
//            j.setText(""+d.shortestPath(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2))));
//            this.panel.add(j);
//            this.panel.remove(tf2);
//            this.panel.remove(b1);
//            this.setVisible(false);
//            this.setVisible(true);
//            System.out.println(d.shortestPath(Character.getNumericValue(s.charAt(0)),Character.getNumericValue(s.charAt(2))));
//        }
//        if(e.getSource()==IcITEM){
//            JLabel j=new JLabel();
//            j.setBounds(80,80,400,400);
//            j.setFont(new Font("Serif",Font.BOLD,30));
//            //j.setText(""+d.isConnected());
//            PaintPanel p = new PaintPanel(this.panel.path);
//            p.con = "Is connected? "+d.isConnected();
//            //this.panel.con = "Is connected? "+d.isConnected();
//            this.panel.add(p);
//            this.setVisible(false);
//            this.setVisible(true);
//            //System.out.println(d.isConnected());
//        }
        if (e.getSource()==addNode){
            this.tex = new JTextField();
            tex.setBounds(50, 50, 100, 20);
            this.add(tex);
            tex.addActionListener(this);
            this.b2 = new JButton("go");
            b2.setBounds(50, 100, 100, 20);
            this.add(b2);
            b2.addActionListener(this);
        }
        if (e.getSource()==removeNode){
            this.tex = new JTextField();
            tex.setBounds(50, 50, 100, 20);
            this.add(tex);
            tex.addActionListener(this);
            this.b3 = new JButton("go");
            b3.setBounds(50, 100, 100, 20);
            this.add(b3);
            b3.addActionListener(this);
        }
        if (e.getSource()==addEdge){
            this.tex = new JTextField();
            tex.setBounds(50, 50, 100, 20);
            this.add(tex);
            tex.addActionListener(this);
            this.b4 = new JButton("go");
            b4.setBounds(50, 100, 100, 20);
            this.add(b4);
            b4.addActionListener(this);
        }
        if (e.getSource()==removeEdge){
            this.tex = new JTextField();
            tex.setBounds(50, 50, 100, 20);
            this.add(tex);
            tex.addActionListener(this);
            this.b5 = new JButton("go");
            b5.setBounds(50, 100, 100, 20);
            this.add(b5);
            b5.addActionListener(this);
        }

    }

    public static void main(String[] args) {
        new window();
    }

}