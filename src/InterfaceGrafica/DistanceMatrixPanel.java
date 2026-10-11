import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DistanceMatrixPanel extends JPanel {
    private Graph graph;
    private JTable distanceTable;
    private GraphPanel graphPanel;

    public DistanceMatrixPanel(Graph graph) {
        this.graph = graph;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UkraineGraphSystem.FUNDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        add(createHeader(), BorderLayout.NORTH);

        // Split pane: Tabela + Grafo
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.4);

        // Painel superior: Tabela de distâncias
        JPanel topPanel = createDistanceTablePanel();
        splitPane.setTopComponent(topPanel);

        // Painel inferior: Visualização do grafo com comboio
        JPanel bottomPanel = createGraphPanel();
        splitPane.setBottomComponent(bottomPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel title = new JLabel("Matriz de Distâncias Mínimas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);

        JLabel desc = new JLabel("Distâncias mínimas entre todas as cidades (Algoritmo de Dijkstra)");
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

    private JPanel createDistanceTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel title = new JLabel("Tabela de Distâncias Mínimas (km)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        panel.add(title, BorderLayout.NORTH);

        // Calcular matriz de distâncias
        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        String[] shortCities = Arrays.stream(cities)
            .map(s -> s.length() > 7 ? s.substring(0, 7) + "." : s)
            .toArray(String[]::new);

        int n = cities.length;
        int[][] distances = calculateAllDistances();

        distanceTable = new JTable(n + 1, n + 1) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        distanceTable.setFont(new Font("Consolas", Font.PLAIN, 12));
        distanceTable.setRowHeight(26);
        distanceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        distanceTable.setGridColor(new Color(200, 220, 255));

        // Header
        distanceTable.setValueAt("", 0, 0);
        for (int i = 0; i < n; i++) {
            distanceTable.setValueAt(shortCities[i], 0, i + 1);
            distanceTable.setValueAt(shortCities[i], i + 1, 0);
        }

        // Dados
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceTable.setValueAt("-", i + 1, j + 1);
                } else if (distances[i][j] == Integer.MAX_VALUE) {
                    distanceTable.setValueAt("∞", i + 1, j + 1);
                } else {
                    distanceTable.setValueAt(distances[i][j], i + 1, j + 1);
                }
            }
        }

        // Estilização
        for (int i = 0; i < distanceTable.getColumnCount(); i++) {
            distanceTable.getColumnModel().getColumn(i).setPreferredWidth(i == 0 ? 85 : 65);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < distanceTable.getColumnCount(); i++) {
            distanceTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        distanceTable.setFocusable(false);

        JScrollPane scroll = new JScrollPane(distanceTable);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Painel de controles
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        controlPanel.setBackground(Color.WHITE);

        JLabel originLabel = new JLabel("Origem:");
        originLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        JComboBox<String> originBox = new JComboBox<>(cities);
        originBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        originBox.setPreferredSize(new Dimension(150, 30));

        JLabel destLabel = new JLabel("Destino:");
        destLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JComboBox<String> destBox = new JComboBox<>(cities);
        destBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        destBox.setPreferredSize(new Dimension(150, 30));
        destBox.setSelectedIndex(4); // ODESSA

        JButton showPathBtn = new JButton("Mostrar Caminho");
        showPathBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        showPathBtn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        showPathBtn.setForeground(Color.WHITE);
        showPathBtn.setFocusPainted(false);
        showPathBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        controlPanel.add(originLabel);
        controlPanel.add(originBox);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(destLabel);
        controlPanel.add(destBox);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(showPathBtn);

        panel.add(controlPanel, BorderLayout.NORTH);

        // GraphPanel para visualização
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(800, 350));
        panel.add(graphPanel, BorderLayout.CENTER);

        // ActionListener para mostrar caminho
        showPathBtn.addActionListener(e -> {
            String origin = (String) originBox.getSelectedItem();
            String dest = (String) destBox.getSelectedItem();

            if (origin.equals(dest)) {
                JOptionPane.showMessageDialog(this,
                    "Origem e destino devem ser diferentes!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int startIdx = graph.getNodeIndex(origin);
            int endIdx = graph.getNodeIndex(dest);

            List<Node> path = ShortestPath.getShortestPathByIndex(graph, startIdx, endIdx);

            if (path.isEmpty() || path.size() == 1) {
                JOptionPane.showMessageDialog(this,
                    "Não há caminho entre " + origin + " e " + dest,
                    "Sem Caminho", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int totalDistance = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                Edge edge = graph.getEdge(path.get(i).getName(), path.get(i + 1).getName());
                if (edge != null) totalDistance += edge.getWeight();
            }

            graphPanel.highlightPath(path);
            //graphPanel.startTrainAnimation(path);

            JOptionPane.showMessageDialog(this,
                "Distância: " + totalDistance + " km\nComboio está percorrendo o caminho!",
                "Caminho Encontrado", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private int[][] calculateAllDistances() {
        int n = graph.getNodes().size();
        int[][] distances = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                } else {
                    List<Node> path = ShortestPath.getShortestPathByIndex(graph, i, j);
                    if (path.isEmpty() || path.size() == 1) {
                        distances[i][j] = Integer.MAX_VALUE;
                    } else {
                        int dist = 0;
                        for (int k = 0; k < path.size() - 1; k++) {
                            Edge e = graph.getEdge(path.get(k).getName(), path.get(k + 1).getName());
                            if (e != null) dist += e.getWeight();
                        }
                        distances[i][j] = dist;
                    }
                }
            }
        }

        return distances;
    }
}