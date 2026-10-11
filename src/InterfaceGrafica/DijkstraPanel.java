import javax.swing.*;
import java.awt.*;
import java.util.List;

class DijkstraPanel extends JPanel {
    private Graph graph;
    private GraphPanel graphPanel;
    private JLabel statusLabel;

    public DijkstraPanel(Graph graph) {
        this.graph = graph;
        initUI();
    }

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
        graphPanel.setDijkstraView(true);
        mainPanel.add(graphPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Label de status (embaixo)
        statusLabel = new JLabel(" ");
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(230, 230, 230)); // cinzento suave
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel title = new JLabel("Caminho Mínimo");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);

        JLabel desc = new JLabel("Algoritmo de Dijkstra");
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

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(Color.WHITE);

        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);

        JLabel originLabel = new JLabel("Origem:");
        originLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JComboBox<String> originBox = new JComboBox<>(cities);
        originBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        originBox.setPreferredSize(new Dimension(150, 30));
        originBox.setSelectedItem("KYIV");

        JLabel destLabel = new JLabel("Destino:");
        destLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JComboBox<String> destBox = new JComboBox<>(cities);
        destBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        destBox.setPreferredSize(new Dimension(150, 30));
        destBox.setSelectedItem("ODESSA");

        JButton calcBtn = new JButton("Calcular");
        calcBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calcBtn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        calcBtn.setForeground(Color.WHITE);
        calcBtn.setFocusPainted(false);
        calcBtn.setPreferredSize(new Dimension(110, 35));
        calcBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton resetBtn = new JButton("Resetar");
        resetBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resetBtn.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        resetBtn.setBackground(Color.WHITE);
        resetBtn.setBorder(BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2));
        resetBtn.setFocusPainted(false);
        resetBtn.setPreferredSize(new Dimension(100, 35));
        resetBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        calcBtn.addActionListener(e -> calculatePath(originBox, destBox));
        resetBtn.addActionListener(e -> {
            graphPanel.showAllEdges();
            graphPanel.highlightPath(null);
            statusLabel.setText(" ");  // limpa mensagem
        });

        panel.add(originLabel);
        panel.add(originBox);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(destLabel);
        panel.add(destBox);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(calcBtn);
        panel.add(resetBtn);

        return panel;
    }

    private void calculatePath(JComboBox<String> originBox, JComboBox<String> destBox) {
        String origin = (String) originBox.getSelectedItem();
        String dest = (String) destBox.getSelectedItem();

        if (origin.equals(dest)) {
            statusLabel.setText("Origem e destino devem ser diferentes!");
            return;
        }

        int startIdx = graph.getNodeIndex(origin);
        int endIdx = graph.getNodeIndex(dest);

        List<Node> path = ShortestPath.getShortestPathByIndex(graph, startIdx, endIdx);

        if (path.isEmpty() || path.size() == 1) {
            statusLabel.setText("Não há caminho entre " + origin + " e " + dest);
            return;
        }

        int totalDistance = 0;
        StringBuilder pathStr = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            pathStr.append(path.get(i).getName());
            if (i < path.size() - 1) {
                Edge e = graph.getEdge(path.get(i).getName(), path.get(i + 1).getName());
                if (e != null) totalDistance += e.getWeight();
                pathStr.append(" → ");
            }
        }

        graphPanel.highlightPath(path);
        graphPanel.animateDijkstra(path);

        // Atualiza o JLabel
        statusLabel.setText("Caminho: " + pathStr.toString() + " | Distância total: " + totalDistance + " km");
    }
}
