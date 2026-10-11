import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MSTPanel extends JPanel {

    private Graph graph;
    private GraphPanel graphPanel;
    private MinimumSpanningTree mst;
    private JLabel statusLabel;

    public MSTPanel(Graph graph) {
        this.graph = graph;
        this.mst = new MinimumSpanningTree(graph);
        initUI();
    }

    // ======================= Inicialização da UI =======================
    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UkraineGraphSystem.FUNDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeader(), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        mainPanel.add(createControlPanel(), BorderLayout.NORTH);

        graphPanel = new GraphPanel(graph);
        graphPanel.setMSTView(true); // agora este grafo fica maior
        mainPanel.add(graphPanel, BorderLayout.CENTER);


        add(mainPanel, BorderLayout.CENTER);
        
        // Label de status
        statusLabel = new JLabel(" ");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(230, 230, 230)); // cinzento suave
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        mainPanel.add(statusLabel, BorderLayout.SOUTH);

    }

    // ======================= Cabeçalho =======================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel title = new JLabel("Árvore de Cobertura Mínima (MST)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);

        JLabel desc = new JLabel("Algoritmos de Kruskal e Prim");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        desc.setForeground(UkraineGraphSystem.CINZA_TEXTO);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(desc);

        header.add(textPanel, BorderLayout.CENTER);

        return header;
    }

    // ======================= Painel de Controles =======================
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(Color.WHITE);

        JButton kruskalBtn = createButton("Kruskal");
        JButton primBtn = createButton("Prim");
        JButton resetBtn = createResetButton();

        kruskalBtn.addActionListener(e -> runKruskal());
        primBtn.addActionListener(e -> runPrim());
        resetBtn.addActionListener(e -> {
        	graphPanel.showAllEdges();
        	statusLabel.setText(" ");  // <-- limpa mensagem
        });

        panel.add(kruskalBtn);
        panel.add(primBtn);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(resetBtn);

        return panel;
    }

    // ======================= Execução de Algoritmos =======================
    private void runKruskal() {
        List<Edge> mstEdges = mst.getMSTKruskal();
        graphPanel.showMST(mstEdges);
        List<Node> patrolPath = getFullMSTPatrolPath(mstEdges);
        // graphPanel.startTrainAnimation(patrolPath);

        statusLabel.setText("Kruskal aplicado — custo total: " + mst.getCost(mstEdges) + " km");

    }

    private void runPrim() {
        List<Edge> mstEdges = mst.getMSTPrim();
        graphPanel.showMST(mstEdges);
        List<Node> patrolPath = getFullMSTPatrolPath(mstEdges);
        // graphPanel.startTrainAnimation(patrolPath);

        statusLabel.setText("Prim aplicado — custo total: " + mst.getCost(mstEdges) + " km");

    }

    // ======================= Geração do Caminho de Patrulha =======================
    private List<Node> getFullMSTPatrolPath(List<Edge> mstEdges) {
        if (mstEdges.isEmpty()) return new ArrayList<>();

        Map<Node, List<Node>> tree = new HashMap<>();
        for (Node n : graph.getNodes()) tree.put(n, new ArrayList<>());

        for (Edge e : mstEdges) {
            tree.get(e.getFrom()).add(e.getTo());
            tree.get(e.getTo()).add(e.getFrom());
        }

        List<Node> path = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node root = mstEdges.get(0).getFrom();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.peek();
            if (!visited.contains(current)) {
                visited.add(current);
                path.add(current);
            }

            boolean foundUnvisited = false;
            for (Node neighbor : tree.get(current)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    foundUnvisited = true;
                    break;
                }
            }

            if (!foundUnvisited) {
                stack.pop();
                if (!stack.isEmpty()) {
                    path.add(stack.peek());
                }
            }
        }

        return path;
    }

    // ======================= Botões =======================
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createResetButton() {
        JButton btn = new JButton("Resetar");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
