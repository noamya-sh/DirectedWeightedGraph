package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class window extends JFrame implements ActionListener {

    PaintPanel panel;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, runAlgoMenu, helpMenu;
    JMenuItem loadItem, saveItem, SPDItem, CITEM, shortestPath, IcITEM, addNode, removeNode, addEdge, removeEdge, TspITEM;

    public window(DirectedWeightedGraphAlgorithms d) {
        super();
        init();
        this.panel = new PaintPanel(d.getGraph());
        this.add(this.panel);
        this.setVisible(true);
    }

    public window() {
        super();
        init();
        this.panel = new PaintPanel(new Graph());
        this.add(this.panel);
        this.setVisible(true);
    }

    public JPanel select() {
        JPanel p = new JPanel();
        JLabel selSrc = miniLabel("Select src Node:");
        p.add(selSrc);
        JComboBox<Integer> nodes1 = new JComboBox<>();
        for (int id : ((Graph) panel.dg.getGraph()).getNodeHash().keySet()) nodes1.addItem(id);
        p.add(nodes1);
        JLabel helpButton = miniLabel("Select dest Node:");
        p.add(helpButton);
        JComboBox<Integer> nodes2 = new JComboBox<>();
        for (int id : ((Graph) panel.dg.getGraph()).getNodeHash().keySet()) nodes2.addItem(id);
        p.add(nodes2);
        JButton find = miniButton("FIND");
        p.add(find);
        return p;
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screensize.width, screensize.height);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        runAlgoMenu = new JMenu(("RunAlgo"));
        helpMenu = new JMenu("Help");
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        SPDItem = new JMenuItem("ShortestPathDist");
        shortestPath = new JMenuItem("shortestPath");
        CITEM = new JMenuItem("center");
        TspITEM = new JMenuItem("tsp");
        IcITEM = new JMenuItem("isConnected");
        addNode = new JMenuItem(("addNode"));
        removeNode = new JMenuItem(("removeNode"));
        addEdge = new JMenuItem(("addEdge"));
        removeEdge = new JMenuItem(("removeEdge"));

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
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
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(getWidth() / 2, 40));
        return label;
    }

    private JLabel miniLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
        return label;
    }

    private JButton miniButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
        b.setBackground(Color.yellow);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Container contain = getContentPane();

        /*Order actions for each button in the menu*/
        if (e.getSource() == loadItem) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("src/main/java"));
            int option = fc.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                contain.removeAll();
                File f = fc.getSelectedFile();
                PaintPanel p = new PaintPanel(f.getPath());
                this.panel = p;
                contain.add(p);
                contain.validate();
                contain.repaint();
                //this.add(this.panel);

                this.setVisible(true);
            }
        }
        if (e.getSource() == saveItem) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("src/main/java"));
            int option = fc.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                panel.dg.save(file.getPath());
            }
        }
        if (e.getSource() == SPDItem) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = select();
            JButton find = (JButton) p.getComponent(4);
            JComboBox nodes1 = (JComboBox) p.getComponent(1);
            JComboBox nodes2 = (JComboBox) p.getComponent(3);
            find.addActionListener(e19 -> {
                double a = panel.dg.shortestPathDist((int) nodes1.getSelectedItem(), (int) nodes2.getSelectedItem());
                contain.remove(p);
                if (a == -1) {
                    JLabel title = createLabel("ERROR: no such path!");
                    title.setForeground(Color.RED);
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                    return;
                }
                JLabel title = createLabel("The ShortPathDist between " + nodes1.getSelectedItem() + " to " + nodes2.getSelectedItem() + " : " + a);
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(find);
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource() == TspITEM) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            JLabel selSrc = miniLabel("Select Node:");
            p.add(selSrc);
            JComboBox<Integer> nodes1 = new JComboBox<>();
            for (int id : ((Graph) panel.dg.getGraph()).getNodeHash().keySet()) nodes1.addItem(id);
            p.add(nodes1);
            JComboBox<Integer> nodes2 = new JComboBox<>();
            JButton add = miniButton("Add Node");
            JButton remove = miniButton("Remove Node");
            add.addActionListener(e110 -> {
                int x = (int) nodes1.getSelectedItem();
                nodes2.addItem(x);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(add);
            remove.addActionListener(e18 -> {
                int x = (int) nodes1.getSelectedItem();
                if (((DefaultComboBoxModel) nodes2.getModel()).getIndexOf(x) != -1)//.getIndexOf(nodes2) == -1 ) {
                    nodes2.removeItem(x);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(remove);
            JLabel j = miniLabel("The requested cities:");
            p.add(j);
            p.add(nodes2);
            JButton find = miniButton("FIND TSP");
            find.addActionListener(e14 -> {
                List<NodeData> send = new ArrayList<>();
                for (int i = 0; i < nodes2.getItemCount(); i++)
                    send.add(((Graph) panel.dg.getGraph()).getNodeHash().get(nodes2.getItemAt(i)));
                List<NodeData> ans = panel.dg.tsp(send);
                contain.remove(p);
                if (ans == null) {
                    JLabel title = createLabel("ERROR: Impossible, the graph is not connected!");
                    title.setForeground(Color.RED);
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                    return;
                }
                panel.drawEdges(ans);
                JLabel title = createLabel("This Tsp path:");
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(find);
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);

        }
        if (e.getSource() == CITEM) {
            if (this.panel != null) {
                contain.removeAll();
                contain.add(panel);
                NodeData center = panel.dg.center();
                if (center != null) {
                    this.panel.setCenter(center.getKey());
                    JLabel title = createLabel("The center NodeData: " + center.getKey());
                    contain.add(title, BorderLayout.PAGE_START);

                } else {
                    JLabel title = createLabel("ERROR: the graph is not connected!");
                    title.setForeground(Color.RED);
                    contain.add(title, BorderLayout.PAGE_START);
                }
                contain.validate();
                contain.repaint();
                this.setVisible(true);
            }
        }
        if (e.getSource() == shortestPath) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = select();
            JButton find = (JButton) p.getComponent(4);
            JComboBox nodes1 = (JComboBox) p.getComponent(1);
            JComboBox nodes2 = (JComboBox) p.getComponent(3);
            find.addActionListener(e15 -> {
                List<NodeData> ans = panel.dg.shortestPath((int) nodes1.getSelectedItem(), (int) nodes2.getSelectedItem());
                contain.remove(p);
                if (ans == null) {
                    JLabel title = createLabel("ERROR: no such path!");
                    title.setForeground(Color.RED);
                    contain.add(title, BorderLayout.PAGE_START);
                    contain.validate();
                    contain.repaint();
                    setVisible(true);
                    return;
                }
                panel.drawEdges(ans);
                JLabel title = createLabel("This Short path between " + nodes1.getSelectedItem() + " to " + nodes2.getSelectedItem() + ":");
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(find);
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource() == IcITEM) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            String bool = (panel.dg.isConnected()) ? "" : "not ";
            JLabel title = createLabel("The graph is " + bool + "connected.");
            contain.add(title, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource() == addNode) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            int x = 0;
            while (((Graph) panel.dg.getGraph()).getNodeHash().containsKey(x)) x++;
            JLabel ID = miniLabel("New ID: " + x + " ");
            p.add(ID);
            JLabel getx = miniLabel("Set X value:");
            p.add(getx);
            JTextField wr1 = new JTextField();
            wr1.setColumns(4);
            p.add(wr1);
            JLabel gety = miniLabel("Set Y value:");
            p.add(gety);
            JTextField wr2 = new JTextField();
            wr2.setColumns(4);
            p.add(wr2);
            JButton add = miniButton("ADD");
            int finalX = x;
            add.addActionListener(e13 -> {
                DirectedWeightedGraph d = panel.dg.copy();
                d.addNode(new Node(finalX, new Location("" + wr1.getText() + "," + wr2.getText() + ",0.0")));
                contain.removeAll();
                panel = new PaintPanel(d);
                contain.add(panel);
                JLabel title = createLabel("Node " + finalX + " successfully added");
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(add);
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource() == removeNode) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            JLabel selSrc = miniLabel("Select Node to remove:");
            p.add(selSrc);
            JComboBox<Integer> nodes1 = new JComboBox<>();
            for (int id : ((Graph) panel.dg.getGraph()).getNodeHash().keySet()) nodes1.addItem(id);
            p.add(nodes1);
            JButton remove = miniButton("REMOVE");
            remove.addActionListener(e1 -> {
                DirectedWeightedGraph d = panel.dg.copy();
                d.removeNode((int) nodes1.getSelectedItem());
                contain.removeAll();
                panel = new PaintPanel(d);
                contain.add(panel);
                JLabel title = createLabel("Node " + nodes1.getSelectedItem() + " successfully removed");
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(remove);
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource() == addEdge) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = select();
            JLabel w = miniLabel("Set weight:");
            p.add(w, 4);
            JTextField setw = new JTextField();
            setw.setColumns(4);
            p.add(setw, 5);
            JButton add = (JButton) p.getComponent(6);
            add.setText("ADD");
            JComboBox nodes1 = (JComboBox) p.getComponent(1);
            JComboBox nodes2 = (JComboBox) p.getComponent(3);
            add.addActionListener(e12 -> {
                if (setw.getText() == null)
                    return;
                DirectedWeightedGraph d = panel.dg.copy();
                d.connect((int) nodes1.getSelectedItem(), (int) nodes2.getSelectedItem(), Double.parseDouble(setw.getText()));
                contain.removeAll();
                panel = new PaintPanel(d);
                contain.add(panel);
                JLabel title = createLabel("Edge from " + nodes1.getSelectedItem() + " to " + nodes2.getSelectedItem() + " successfully added");
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
        if (e.getSource() == removeEdge) {
            this.panel.drawEdges(null);
            contain.removeAll();
            contain.add(this.panel);
            JPanel p = new JPanel();
            JLabel selSrc = miniLabel("Select src Node:");
            p.add(selSrc);
            JComboBox<Integer> nodes1 = new JComboBox<>();
            for (int id : ((Graph) panel.dg.getGraph()).getNodeHash().keySet()) nodes1.addItem(id);
            p.add(nodes1);
            JComboBox<Integer> nodes2 = new JComboBox<>();
            nodes1.addActionListener(e17 -> {
                int x = (int) nodes1.getSelectedItem();
                nodes2.removeAllItems();
                for (int id : ((Node) ((Graph) panel.dg.getGraph()).getNodeHash().get(x)).getEdges().keySet())
                    nodes2.addItem(id);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            JLabel helpButton = miniLabel("Select dest Node:");
            p.add(helpButton);
            p.add(nodes2);
            JButton remove = miniButton("REMOVE");
            remove.setBackground(Color.yellow);
            remove.addActionListener(e16 -> {
                DirectedWeightedGraph d = panel.dg.copy();
                d.removeEdge((int) nodes1.getSelectedItem(), (int) nodes2.getSelectedItem());
                contain.removeAll();
                panel = new PaintPanel(d);
                contain.add(panel);
                JLabel title = createLabel("Edge from " + nodes1.getSelectedItem() + " to " + nodes2.getSelectedItem() + " successfully removed");
                contain.add(title, BorderLayout.PAGE_START);
                contain.validate();
                contain.repaint();
                setVisible(true);
            });
            p.add(remove);
            contain.add(p, BorderLayout.PAGE_START);
            contain.validate();
            contain.repaint();
            this.setVisible(true);
        }
    }
}