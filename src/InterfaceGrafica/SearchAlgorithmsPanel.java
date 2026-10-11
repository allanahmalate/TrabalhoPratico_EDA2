import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SearchAlgorithmsPanel extends JPanel {
    private Graph graph;
    private GraphPanel graphPanel;

    // Tabela analítica
    private JTable table;
    private DefaultTableModel tableModel;

    public SearchAlgorithmsPanel(Graph graph) {
        this.graph = graph;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UkraineGraphSystem.FUNDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        add(createHeader(), BorderLayout.NORTH);

        // Painel principal com scroll para tudo
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));

        // --- Controles ---
        mainPanel.add(createControlPanel(), BorderLayout.NORTH);

        // --- GraphPanel dentro de scroll ---
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(800, 520));
        JScrollPane graphScroll = new JScrollPane(graphPanel);
        graphScroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        mainPanel.add(graphScroll, BorderLayout.CENTER);

        // --- Tabela analítica abaixo do grafo ---
        String[] columns = {"Passos do Algoritmo", "Estado da Pilha/Fila", "Percursos"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

	     // --- Aumentar tamanho da fonte das células ---
	     table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	     table.setRowHeight(28);
	
	     // --- Alterar cor do cabeçalho ---
	     JTableHeader header = table.getTableHeader();
	     header.setFont(new Font("Segoe UI", Font.BOLD, 16));
	     header.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
	
	     JScrollPane tableScroll = new JScrollPane(table);
	     tableScroll.setPreferredSize(new Dimension(800, 200));

        mainPanel.add(tableScroll, BorderLayout.SOUTH);

        // --- Scroll principal para o painel inteiro ---
        JScrollPane mainScroll = new JScrollPane(mainPanel);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel title = new JLabel("Algoritmos de Busca");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);

        JLabel desc = new JLabel("BFS (Busca em Largura) e DFS (Busca em Profundidade)");
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

        JLabel cityLabel = new JLabel("Cidade Inicial:");
        cityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        JComboBox<String> cityBox = new JComboBox<>(cities);
        cityBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cityBox.setPreferredSize(new Dimension(180, 30));
        cityBox.setSelectedItem("KYIV");

        JButton bfsBtn = createAlgorithmButton("BFS");
        JButton dfsBtn = createAlgorithmButton("DFS");
        JButton resetBtn = createResetButton();

        bfsBtn.addActionListener(e -> {
            String city = (String) cityBox.getSelectedItem();
            int idx = graph.getNodeIndex(city);
            graphPanel.animateBFS(idx);
            runBFS(idx);
        });

        dfsBtn.addActionListener(e -> {
            String city = (String) cityBox.getSelectedItem();
            int idx = graph.getNodeIndex(city);
            graphPanel.animateDFS(idx);
            runDFS(idx);
        });

        resetBtn.addActionListener(e -> {
            graphPanel.showAllEdges();
            graphPanel.highlightPath(null);
            tableModel.setRowCount(0); // limpa tabela
        });

        panel.add(cityLabel);
        panel.add(cityBox);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(bfsBtn);
        panel.add(dfsBtn);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(resetBtn);

        return panel;
    }

    private JButton createAlgorithmButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 110, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
            }
        });

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

    // ----- Métodos BFS/DFS para preencher tabela -----
    private void runBFS(int startIdx) {
        tableModel.setRowCount(0); // limpa tabela
        List<Node> nodes = graph.getNodes();
        boolean[] visited = new boolean[nodes.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIdx);
        visited[startIdx] = true;

        List<String> percurso = new ArrayList<>();
        int step = 1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            percurso.add(nodes.get(u).getName());

            List<String> filaEstado = new ArrayList<>();
            for (int idx : queue) filaEstado.add(nodes.get(idx).getName());

            tableModel.addRow(new Object[]{
                    "Step " + step,
                    filaEstado.toString(),
                    percurso.toString()
            });
            step++;

            for (Edge e : graph.getEdgesOf(u)) {
                int v = graph.getNodeIndex(e.getOtherNode(nodes.get(u)).getName());
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
    }

    private void runDFS(int startIdx) {
        tableModel.setRowCount(0);
        List<Node> nodes = graph.getNodes();
        boolean[] visited = new boolean[nodes.size()];
        Stack<Integer> stack = new Stack<>();
        stack.push(startIdx);

        List<String> percurso = new ArrayList<>();
        int step = 1;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                percurso.add(nodes.get(u).getName());

                List<String> pilhaEstado = new ArrayList<>();
                for (int idx : stack) pilhaEstado.add(nodes.get(idx).getName());

                tableModel.addRow(new Object[]{
                        "Step " + step,
                        pilhaEstado.toString(),
                        percurso.toString()
                });
                step++;

                List<Edge> edges = new ArrayList<>(graph.getEdgesOf(u));
                Collections.reverse(edges);
                for (Edge e : edges) {
                    int v = graph.getNodeIndex(e.getOtherNode(nodes.get(u)).getName());
                    if (!visited[v]) stack.push(v);
                }
            }
        }
    }
}
