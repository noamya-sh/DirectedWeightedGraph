package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class window extends JFrame implements ActionListener {

    PaintPanel panel;
    GraphAlgo d;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, runAlgoMenu, helpMenu;
    JMenuItem loadItem,saveItem,exitItem,SPDItem,CITEM, shortestPath,IcITEM,addNode,removeNode,addEdge,removeEdge,TspITEM;

    public window(DirectedWeightedGraphAlgorithms d){
        super();
        this.d = (GraphAlgo) d;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screensize.width , screensize.height );
        this.getContentPane().setBackground(new Color(255, 255, 255));
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        runAlgoMenu = new JMenu(("RunAlgo"));
        helpMenu = new JMenu("Help");

        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        SPDItem = new JMenuItem("ShortestPathDist");
        shortestPath = new JMenuItem("shortestPath");
        CITEM = new JMenuItem("center");
        TspITEM = new JMenuItem("tsp");
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
        shortestPath.addActionListener(this);
        IcITEM.addActionListener(this);
        removeNode.addActionListener(this);
        removeEdge.addActionListener(this);
        addNode.addActionListener(this);
        addEdge.addActionListener(this);
        TspITEM.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        editMenu.add(addNode);
        editMenu.add(removeNode);
        editMenu.add(addEdge);
        editMenu.add(removeEdge);
        runAlgoMenu.add(IcITEM);
        runAlgoMenu.add(SPDItem);
        runAlgoMenu.add(shortestPath);
        runAlgoMenu.add(CITEM);
        runAlgoMenu.add(TspITEM);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(runAlgoMenu);

        this.setJMenuBar(menuBar);
        this.panel = new PaintPanel(this.d.graph);
        this.add(this.panel);        this.setVisible(true);
    }
    public JPanel select(){
        JPanel p = new JPanel();
        JLabel selSrc = new JLabel("Select src Node:");
        p.add(selSrc);
        JComboBox<Integer> nodes1 = new JComboBox<>();
        for (int id : panel.dg.graph.NodeHash.keySet()) nodes1.addItem(id);
        p.add(nodes1);

        JLabel helpButton = new JLabel("Select dest Node:");
        p.add(helpButton);

        JComboBox<Integer> nodes2 = new JComboBox<>();
        for (int id : panel.dg.graph.NodeHash.keySet()) nodes2.addItem(id);
//            nodes2.setBackground(Color.orange);
        p.add(nodes2);
        JButton find = new JButton("FIND");
        find.setBackground(Color.yellow);
        p.add(find);
        return p;
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
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = select();
            JButton find = (JButton) p.getComponent(4);
            JComboBox nodes1 = (JComboBox) p.getComponent(1);
            JComboBox nodes2 = (JComboBox) p.getComponent(3);
            find.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double a = d.shortestPathDist((int)nodes1.getSelectedItem(),(int)nodes2.getSelectedItem());
                    contain.remove(p);
                    if (a==-1){
                        JLabel title = new JLabel("ERROR: no such path!", JLabel.CENTER);
                        title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                        title.setForeground(Color.RED);
                        title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                        contain.add(title, BorderLayout.PAGE_START);
                        contain.validate();
                        contain.repaint();
                        setVisible(true);
                        return;
                    }
                    JLabel title = new JLabel("The ShortPathDist between "+nodes1.getSelectedItem()+" to "+
                            nodes2.getSelectedItem()+" : "+a, JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(find);
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource()== TspITEM) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            JLabel selSrc = new JLabel("Select Node:");
            p.add(selSrc);
            JComboBox<Integer> nodes1 = new JComboBox<>();
            for (int id : panel.dg.graph.NodeHash.keySet()) nodes1.addItem(id);
            p.add(nodes1);
            JComboBox<Integer> nodes2 = new JComboBox<>();
            JButton add = new JButton("Add Node");
            JButton remove = new JButton("Remove Node");
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = (int) nodes1.getSelectedItem();
                    nodes2.addItem(x);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(add);
            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = (int) nodes1.getSelectedItem();
                    if(((DefaultComboBoxModel) nodes2.getModel()).getIndexOf(x)!=-1)//.getIndexOf(nodes2) == -1 ) {
                        nodes2.removeItem(x);
                        contain.validate();
                        contain.repaint();
                        setVisible(true);
                    }

            });
            p.add(remove);
            JLabel j = new JLabel("The requested cities:");
            p.add(j);
            p.add(nodes2);
            JButton find = new JButton("FIND TSP");
            find.setBackground(Color.yellow);
            find.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<NodeData> send = new ArrayList<>();
                    for (int i = 0; i < nodes2.getItemCount(); i++) send.add(panel.dg.graph.NodeHash.get(nodes2.getItemAt(i)));
                    List<NodeData> ans = panel.dg.tsp(send);
                    contain.remove(p);
                    if (ans==null){
                        JLabel title = new JLabel("ERROR: the graph is not connected!", JLabel.CENTER);
                        title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                        title.setForeground(Color.RED);
                        title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                        contain.add(title, BorderLayout.PAGE_START);
                        contain.validate();
                        contain.repaint();
                        setVisible(true);
                        return;
                    }
                    panel.drawEdges(ans);
                    JLabel title = new JLabel("This Tsp path:", JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(find);
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);

        }
        if(e.getSource()==CITEM){
            if (this.panel!=null && this.d.getGraph().nodeSize()>0){
                d=(GraphAlgo) panel.dg;
                contain.removeAll();
                contain.add(panel);
                NodeData center = d.center();
                if (center!=null){
                    this.panel.setCenter(center.getKey());
                    JLabel title = new JLabel("The center NodeData: "+center.getKey(), JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);

                }
            else{
                    JLabel title = new JLabel("ERROR: the graph is not connected!", JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setForeground(Color.RED);
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                }
                contain.validate();
                contain.repaint();
                this.setVisible(true);
            }
        }
        if(e.getSource()==shortestPath){
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = select();
            JButton find = (JButton) p.getComponent(4);
            JComboBox nodes1 = (JComboBox) p.getComponent(1);
            JComboBox nodes2 = (JComboBox) p.getComponent(3);
            find.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<NodeData> ans = panel.dg.shortestPath((int)nodes1.getSelectedItem(),(int)nodes2.getSelectedItem());
                    contain.remove(p);
                    if (ans==null){
                        JLabel title = new JLabel("ERROR: no such path!", JLabel.CENTER);
                        title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                        title.setForeground(Color.RED);
                        title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                        contain.add(title, BorderLayout.PAGE_START);
                        contain.validate();
                        contain.repaint();
                        setVisible(true);
                        return;
                    }
                    panel.drawEdges(ans);
                    JLabel title = new JLabel("This Short path between "+nodes1.getSelectedItem()+" to "+nodes2.getSelectedItem()+":", JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(find);
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if(e.getSource()==IcITEM) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            String bool = (panel.dg.isConnected()) ? "" : "not ";
            JLabel title = new JLabel("The graph is "+bool+ "connected.", JLabel.CENTER);
            title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
            title.setPreferredSize(new Dimension(getWidth() / 2, 40));
            contain.add(title,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource()==addNode){
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            int x = 0;
            while (panel.dg.graph.NodeHash.containsKey(x)) x++;
            JLabel ID = new JLabel("New ID: "+x);
            p.add(ID);
            JLabel getx = new JLabel("Set X value:");
            p.add(getx);
            JTextField wr1 = new JTextField();
            wr1.setColumns(4);//setSize(new Dimension(100,20));
            p.add(wr1);
            JLabel gety = new JLabel("Set Y value:");
            p.add(gety);
            JTextField wr2 = new JTextField();
            wr2.setColumns(4);//.setSize(new Dimension(100,20));;
            p.add(wr2);
            JButton add = new JButton("ADD");
            add.setBackground(Color.yellow);
            int finalX = x;
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DirectedWeightedGraph d = panel.dg.copy();
                    d.addNode(new Node(finalX,new Location(""+wr1.getText()+","+wr2.getText()+",0.0")));
                    contain.removeAll();
                    panel = new PaintPanel(d);
                    contain.add(panel);
                    JLabel title = new JLabel("Node "+finalX+" successfully added", JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(add);
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource()==removeNode){
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            JLabel selSrc = new JLabel("Select Node to remove:");
            p.add(selSrc);
            JComboBox<Integer> nodes1 = new JComboBox<>();
            for (int id : panel.dg.graph.NodeHash.keySet()) nodes1.addItem(id);
            p.add(nodes1);
            JButton remove = new JButton("REMOVE");
            remove.setBackground(Color.yellow);
            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DirectedWeightedGraph d = panel.dg.copy();
                    d.removeNode((int)nodes1.getSelectedItem());
                    contain.removeAll();
                    panel = new PaintPanel(d);
                    contain.add(panel);
                    JLabel title = new JLabel("Node "+(int)nodes1.getSelectedItem()+" successfully removed", JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(remove);
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource()==addEdge){
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = select();
            JLabel w = new JLabel("Set weight:");
            p.add(w,4);
            JTextField setw = new JTextField();
            setw.setColumns(4);
            p.add(setw,5);
            JButton add = (JButton) p.getComponent(6);
            add.setText("ADD");
            JComboBox nodes1 = (JComboBox) p.getComponent(1);
            JComboBox nodes2 = (JComboBox) p.getComponent(3);
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DirectedWeightedGraph d = panel.dg.copy();
                    d.connect((int)nodes1.getSelectedItem(),(int)nodes2.getSelectedItem(),Double.parseDouble(setw.getText()));
                    contain.removeAll();
                    panel = new PaintPanel(d);
                    contain.add(panel);
                    JLabel title = new JLabel("Edge from "+nodes1.getSelectedItem()+" to "+ nodes2.getSelectedItem()+ " successfully added", JLabel.CENTER);
                    title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
                    title.setPreferredSize(new Dimension(getWidth() / 2, 40));
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource()==removeEdge){
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            JLabel selSrc = new JLabel("Select src Node:");
            p.add(selSrc);
            JComboBox<Integer> nodes1 = new JComboBox<>();
            for (int id : panel.dg.graph.NodeHash.keySet()) nodes1.addItem(id);
            p.add(nodes1);
            JComboBox<Integer> nodes2 = new JComboBox<>();
            nodes1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = (int) nodes1.getSelectedItem();
                    nodes2.removeAllItems();
                    for (int id : ((Node) panel.dg.graph.NodeHash.get(x)).getEdges().keySet()) nodes2.addItem(id);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            JLabel helpButton = new JLabel("Select dest Node:");
            p.add(helpButton);
            p.add(nodes2);
            JButton remove = new JButton("REMOVE");
            remove.setBackground(Color.yellow);
            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DirectedWeightedGraph d = panel.dg.copy();
                    d.removeEdge((int)nodes1.getSelectedItem(),(int)nodes2.getSelectedItem());
                    contain.removeAll();
                    panel = new PaintPanel(d);
                    contain.add(panel);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                }
            });
            p.add(remove);
            contain.add(p,BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }

    }

    public static void main(String[] args) {
//        new window(alg);
    }

}