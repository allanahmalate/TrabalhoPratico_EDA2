import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RepresentationPanel extends JPanel {
    private final Graph graph;
    private CardLayout contentLayout;
    private JPanel contentPanel;

    public RepresentationPanel(Graph graph) {
        this.graph = graph;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UkraineGraphSystem.FUNDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createButtonBar(), BorderLayout.NORTH);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2));

        contentPanel.add(createMatrixPanel(), "matrix");
        contentPanel.add(createListPanel(), "list");
        contentPanel.add(createDistanceMatrixPanel(), "distances");

        add(contentPanel, BorderLayout.CENTER);

        contentLayout.show(contentPanel, "matrix");
    }

    private JPanel createButtonBar() {
        JPanel buttonBar = new JPanel();
        buttonBar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonBar.setBackground(UkraineGraphSystem.FUNDO_CLARO);

        JButton matrixBtn = createTopButton("Matriz de Adjacência", "matrix");
        JButton listBtn   = createTopButton("Lista de Adjacência", "list");
        JButton distBtn   = createTopButton("Matriz de Distâncias", "distances");

        buttonBar.add(matrixBtn);
        buttonBar.add(listBtn);
        buttonBar.add(distBtn);

        return buttonBar;
    }

    private JButton createTopButton(String text, String card) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 110, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
            }
        });

        btn.addActionListener(e -> contentLayout.show(contentPanel, card));
        return btn;
    }

    private JPanel createMatrixPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Matriz de Adjacência (0 = sem conexão, 1 = com conexão)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        panel.add(title, BorderLayout.NORTH);

        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        String[] shortCities = Arrays.stream(cities)
                .map(s -> s.length() > 7 ? s.substring(0, 7) + "." : s)
                .toArray(String[]::new);

        int n = cities.length;
        int[][] matrix = graph.getAdjacencyMatrix();
        int[][] binary = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                binary[i][j] = (matrix[i][j] > 0) ? 1 : 0;
            }
        }

        JTable table = new JTable(n + 1, n + 1) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table.setFont(new Font("Consolas", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setGridColor(new Color(200, 220, 255));

        table.setValueAt("", 0, 0);
        for (int i = 0; i < n; i++) {
            table.setValueAt(shortCities[i], 0, i + 1);
            table.setValueAt(shortCities[i], i + 1, 0);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table.setValueAt(binary[i][j], i + 1, j + 1);
            }
        }

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(i == 0 ? 90 : 70);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        table.setFocusable(false);

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Lista de Adjacência");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        panel.add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Node node : graph.getNodes()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html><b>").append(node.getName()).append("</b> to ");

            List<Edge> edges = graph.getEdgesOf(graph.getNodes().indexOf(node));
            for (int i = 0; i < edges.size(); i++) {
                Edge e = edges.get(i);
                Node other = e.getOtherNode(node);
                String shortName = other.getName().length() > 9 ?
                        other.getName().substring(0, 9) + "." : other.getName();
                sb.append(shortName).append(" (").append(e.getWeight()).append(" km)");
                if (i < edges.size() - 1) sb.append(", ");
            }
            sb.append("</html>");

            JLabel line = new JLabel(sb.toString());
            line.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            line.setForeground(UkraineGraphSystem.CINZA_TEXTO);
            line.setAlignmentX(Component.LEFT_ALIGNMENT);
            line.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
            listPanel.add(line);
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDistanceMatrixPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Matriz de Distâncias Mínimas (km) – Algoritmo de Dijkstra");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        panel.add(title, BorderLayout.NORTH);

        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        String[] shortCities = Arrays.stream(cities)
                .map(s -> s.length() > 7 ? s.substring(0, 7) + "." : s)
                .toArray(String[]::new);

        int n = cities.length;
        int[][] distances = calculateAllDistances();

        JTable table = new JTable(n + 1, n + 1) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table.setFont(new Font("Consolas", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setGridColor(new Color(200, 220, 255));

        table.setValueAt("", 0, 0);
        for (int i = 0; i < n; i++) {
            table.setValueAt(shortCities[i], 0, i + 1);
            table.setValueAt(shortCities[i], i + 1, 0);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    table.setValueAt("-", i + 1, j + 1);
                } else if (distances[i][j] == Integer.MAX_VALUE) {
                    table.setValueAt("infinity", i + 1, j + 1);
                } else {
                    table.setValueAt(distances[i][j], i + 1, j + 1);
                }
            }
        }

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(i == 0 ? 85 : 65);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        table.setFocusable(false);

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
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
                        int d = 0;
                        for (int k = 0; k < path.size() - 1; k++) {
                            Edge e = graph.getEdge(path.get(k).getName(), path.get(k + 1).getName());
                            if (e != null) d += e.getWeight();
                        }
                        distances[i][j] = d;
                    }
                }
            }
        }
        return distances;
    }
}