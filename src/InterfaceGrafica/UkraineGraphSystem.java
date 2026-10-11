package InterfaceGrafica;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class UkraineGraphSystem extends JFrame {
    private Graph graph;
    private CardLayout cardLayout;
    private JPanel centerPanel;
    private SidebarPanel sidebar;
    
    // Cores da Ucrânia
    public static final Color AZUL_UCRANIA = new Color(0, 87, 183);
    public static final Color AMARELO_UCRANIA = new Color(255, 221, 0);
    public static final Color CINZA_TEXTO = new Color(60, 60, 60);
    public static final Color FUNDO_CLARO = new Color(250, 252, 255);
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new UkraineGraphSystem().setVisible(true);
        });
    }
    
    public UkraineGraphSystem() {
        graph = createGraph();
        initUI();
    }
    
    private void initUI() {
        setTitle("SATU - Sistema de Abastecimento e Transporte da Ucrânia");
    	setSize(1280, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Sidebar à esquerda
        sidebar = new SidebarPanel(this);
        add(sidebar, BorderLayout.WEST);
        
        // Área central com CardLayout
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBackground(FUNDO_CLARO);
        
        // Adicionar todos os painéis
        centerPanel.add(new HomePanel(), "home");
        centerPanel.add(new GraphViewPanel(graph), "graph");
        centerPanel.add(new RepresentationPanel(graph), "representation");
        centerPanel.add(new DistanceMatrixPanel(graph), "distance");
        centerPanel.add(new SearchAlgorithmsPanel(graph), "search");
        centerPanel.add(new MSTPanel(graph), "mst");
        centerPanel.add(new DijkstraPanel(graph), "dijkstra");
        centerPanel.add(new ComplexityPanel(), "complexity");
        centerPanel.add(new ProgrammersPanel(), "programmers");
        centerPanel.add(new AboutPanel(), "about");
        centerPanel.add(new HelpPanel(), "help");
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Mostrar Home por padrão
        showPanel("home");
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(centerPanel, panelName);
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    private Graph createGraph() {
        Graph g = new Graph();
        String[] c = {"ZHYTOMYR","VINNYTSA","KYIV","KROPYNYTSKYI","ODESSA",
                      "CHERNIHIV","SUMMY","POLTAVA","KHARKIV","DNIPRO",
                      "DONETSK","LUHANSK","MYKOLAIV","KHERSON","ZAPORIZHZHYA"};
        int[][] pos = {{110,120},{150,300},{350,200},{350,400},{180,450},
                       {430,100},{650,100},{550,250},{750,200},{580,420},
                       {810,400},{880,300},{290,590},{510,605},{700,530}};
        
        for (int i = 0; i < c.length; i++) {
            g.addNode(c[i], pos[i][0], pos[i][1]);
        }
        
        int[][] edges = {{0,1,220},{0,2,120},{1,2,260},{1,3,160},{1,4,270},
                         {5,6,120},{5,2,150},{6,8,180},{2,7,290},{7,8,200},
                         {7,3,180},{7,9,250},{8,9,300},{8,10,240},{8,11,200},
                         {3,9,200},{3,12,250},{9,14,100},{9,10,260},{12,4,140},
                         {12,13,150},{13,14,300},{14,10,200},{10,11,120}};
        
        for (int[] e : edges) {
            g.addEdge(c[e[0]], c[e[1]], e[2]);
        }
        
        return g;
    }
}