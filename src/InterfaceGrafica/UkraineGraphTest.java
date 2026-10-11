import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

// === FLATLAF ===
import com.formdev.flatlaf.FlatLightLaf;

public class UkraineGraphTest extends JFrame {
    private Graph graph;
    private GraphPanel canvas;
    private MinimumSpanningTree mst;

    private static final Color AZUL_UCRANIA = new Color(0, 87, 183);
    private static final Color AMARELO_UCRANIA = new Color(255, 221, 0);
    private static final Color CINZA_TEXTO = new Color(60, 60, 60);
    private static final Color FUNDO_CLARO = new Color(250, 252, 255);

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new UkraineGraphTest().setVisible(true);
        });
    }

    public UkraineGraphTest() {
        graph = createGraph();
        mst = new MinimumSpanningTree(graph);
        initUI();
    }

    private void initUI() {
        setTitle("OBlast");
        setSize(1450, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebarContent = new JPanel();
        sidebarContent.setLayout(new BoxLayout(sidebarContent, BoxLayout.Y_AXIS));
        sidebarContent.setBackground(AZUL_UCRANIA);
        sidebarContent.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        JLabel title = new JLabel("Oblasts");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(AMARELO_UCRANIA);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Análise");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 35, 20));
        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(25));
        header.add(createSeparator());
        sidebarContent.add(header);

        addMenuItem(sidebarContent, "Matriz de Adjacência", this::showMatrixGraphically);
        addMenuItem(sidebarContent, "Lista de Adjacência", this::showAdjacencyListGraphically);
        addMenuItem(sidebarContent, "Kruskal (MST)", this::runKruskalWithRemoval);
        addMenuItem(sidebarContent, "Prim (MST)", this::runPrimWithRemoval);
        addMenuItem(sidebarContent, "Gerar Relatório PDF", this::generatePDFReport);

        addMenuSection(sidebarContent, "Busca", new String[]{"BFS (KYIV)", "DFS (KYIV)"}, new Runnable[]{
                () -> canvas.animateBFS(graph.getNodeIndex("KYIV")),
                () -> canvas.animateDFS(graph.getNodeIndex("KYIV"))
        });

        addMenuSection(sidebarContent, "Caminho Mínimo", new String[]{"Escolher Cidades"}, new Runnable[]{
                this::chooseShortestPath
        });

        sidebarContent.add(Box.createVerticalGlue());
        addMenuItem(sidebarContent, "Ajuda", this::showHelp);
        addMenuItem(sidebarContent, "Sobre", this::showAbout);

        JScrollPane scroll = new JScrollPane(sidebarContent);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        wrapper.setPreferredSize(new Dimension(310, 0));
        return wrapper;
    }

    private void addMenuItem(JPanel p, String text, Runnable action) {
        JLabel item = new JLabel(text);
        item.setFont(new Font("Segoe UI", Font.BOLD, 15));
        item.setForeground(AMARELO_UCRANIA);
        item.setAlignmentX(Component.CENTER_ALIGNMENT);
        item.setBorder(BorderFactory.createEmptyBorder(14, 30, 14, 30));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.setOpaque(true);
        item.setBackground(AZUL_UCRANIA);

        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { action.run(); }
            public void mouseEntered(java.awt.event.MouseEvent e) { item.setBackground(new Color(0, 110, 200)); }
            public void mouseExited(java.awt.event.MouseEvent e) { item.setBackground(AZUL_UCRANIA); }
        });

        p.add(item);
        p.add(Box.createVerticalStrut(3));
    }

    private void addMenuSection(JPanel p, String title, String[] items, Runnable[] actions) {
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(Color.WHITE);
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        t.setBorder(BorderFactory.createEmptyBorder(20, 30, 8, 30));

        JPanel s = new JPanel();
        s.setLayout(new BoxLayout(s, BoxLayout.Y_AXIS));
        s.setOpaque(false);

        for (int i = 0; i < items.length; i++) {
            final int idx = i;
            JLabel item = new JLabel("  " + items[i]);
            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            item.setForeground(AMARELO_UCRANIA);
            item.setAlignmentX(Component.CENTER_ALIGNMENT);
            item.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 30));
            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            item.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) { actions[idx].run(); }
                public void mouseEntered(java.awt.event.MouseEvent e) { item.setForeground(Color.WHITE); }
                public void mouseExited(java.awt.event.MouseEvent e) { item.setForeground(AMARELO_UCRANIA); }
            });

            s.add(item);
        }

        p.add(t);
        p.add(s);
        p.add(createSeparator());
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 150, 255));
        sep.setMaximumSize(new Dimension(280, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sep;
    }

    private JPanel createMainPanel() {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(FUNDO_CLARO);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_UCRANIA, 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel title = new JLabel("Grafo da Linha Ferroviária da Ucrânia");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(AZUL_UCRANIA);

        JLabel desc = new JLabel("Visualização");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        desc.setForeground(CINZA_TEXTO);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);
        text.add(title);
        text.add(Box.createVerticalStrut(6));
        text.add(desc);

        header.add(text, BorderLayout.CENTER);
        main.add(header, BorderLayout.NORTH);

        canvas = new GraphPanel(graph);
        JPanel canvasContainer = new JPanel(new BorderLayout());
        canvasContainer.setBackground(Color.WHITE);
        canvasContainer.setBorder(BorderFactory.createLineBorder(AZUL_UCRANIA, 2));
        canvasContainer.add(canvas, BorderLayout.CENTER);

        JLabel info = new JLabel("Arraste os nós • R = Reset • ESC = Limpar");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        info.setForeground(CINZA_TEXTO);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        canvasContainer.add(info, BorderLayout.SOUTH);

        main.add(canvasContainer, BorderLayout.CENTER);
        return main;
    }

    private void chooseShortestPath() {
        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);

        JComboBox<String> startBox = new JComboBox<>(cities);
        startBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        startBox.setSelectedItem("KYIV");

        JComboBox<String> endBox = new JComboBox<>(cities);
        endBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        endBox.setSelectedItem("ODESSA");

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("Origem:"));
        panel.add(startBox);
        panel.add(new JLabel("Destino:"));
        panel.add(endBox);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Escolher Caminho Mínimo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String startCity = (String) startBox.getSelectedItem();
            String endCity = (String) endBox.getSelectedItem();

            if (startCity.equals(endCity)) {
                JOptionPane.showMessageDialog(this, "Origem e destino devem ser diferentes!", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int start = graph.getNodeIndex(startCity);
            int end = graph.getNodeIndex(endCity);
            List<Node> path = ShortestPath.getShortestPathByIndex(graph, start, end);

            if (path.isEmpty() || path.size() == 1) {
                JOptionPane.showMessageDialog(this,
                        "Não há caminho entre " + startCity + " e " + endCity + "!",
                        "Sem Caminho", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int totalDistance = 0;
            StringBuilder pathStr = new StringBuilder("Caminho: ");
            for (int i = 0; i < path.size(); i++) {
                Node n = path.get(i);
                pathStr.append(n.getName());
                if (i < path.size() - 1) {
                    Node next = path.get(i + 1);
                    Edge e = graph.getEdge(n.getName(), next.getName());
                    if (e != null) totalDistance += e.getWeight();
                    pathStr.append(" → ");
                }
            }
            if (pathStr.length() > 0) pathStr.setLength(pathStr.length() - 4);

            canvas.highlightPath(path);
            //canvas.startTrainAnimation(path);

            JOptionPane.showMessageDialog(this,
                    "<html><b>Caminho Mínimo</b><br>" +
                            pathStr.toString() + "<br><br>" +
                            "<b>Distância total: " + totalDistance + " km</b></html>",
                    "Dijkstra", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // === MST: COMBOIO PATRULHA (VAI E VOLTA) ===
    private void runKruskalWithRemoval() {
        List<Edge> mstEdges = mst.getMSTKruskal();
        canvas.showMST(mstEdges);
        //canvas.startTrainAnimation(getFullMSTPatrolPath(mstEdges));
        JOptionPane.showMessageDialog(this,
                "Kruskal aplicado!\n\n" +
                        "Custo total da MST: " + mst.getCost(mstEdges) + " km\n" +
                        "Comboio patrulha TODAS as linhas (vai e volta)!",
                "MST - Kruskal", JOptionPane.INFORMATION_MESSAGE);
    }

    private void runPrimWithRemoval() {
        List<Edge> mstEdges = mst.getMSTPrim();
        canvas.showMST(mstEdges);
        //canvas.startTrainAnimation(getFullMSTPatrolPath(mstEdges));
        JOptionPane.showMessageDialog(this,
                "Prim aplicado!\n\n" +
                        "Custo total da MST: " + mst.getCost(mstEdges) + " km\n" +
                        "Comboio patrulha TODAS as linhas (vai e volta)!",
                "MST - Prim", JOptionPane.INFORMATION_MESSAGE);
    }

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
                    Node parent = stack.peek();
                    path.add(parent);
                }
            }
        }

        return path;
    }

    private void showMatrixGraphically() {
        String[] fullCities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        String[] shortCities = Arrays.stream(fullCities)
                .map(s -> s.length() > 7 ? s.substring(0, 7) + "." : s)
                .toArray(String[]::new);
        int n = fullCities.length;
        int[][] m = graph.getAdjacencyMatrix();

        JTable table = new JTable(n + 1, n + 1);
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
                table.setValueAt(m[i][j] == 0 ? "-" : m[i][j], i + 1, j + 1);
            }
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(i == 0 ? 90 : 70);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(950, 520));

        JOptionPane.showMessageDialog(this, scroll, "Matriz de Adjacência", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAdjacencyListGraphically() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.setBackground(Color.WHITE);

        for (Node node : graph.getNodes()) {
            JLabel line = new JLabel("<html><b>" + node.getName().substring(0, Math.min(9, node.getName().length())) + "</b> → ");
            StringBuilder sb = new StringBuilder();
            for (Edge e : graph.getEdgesOf(graph.getNodes().indexOf(node))) {
                Node other = e.getOtherNode(node);
                sb.append(other.getName().substring(0, Math.min(7, other.getName().length())) + "." + " (" + e.getWeight() + "), ");
            }
            if (sb.length() > 0) sb.setLength(sb.length() - 2);
            line.setText(line.getText() + sb.toString() + "</html>");
            line.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            line.setForeground(AZUL_UCRANIA);
            panel.add(line);
            panel.add(Box.createVerticalStrut(6));
        }

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(780, 520));

        JOptionPane.showMessageDialog(this, scroll, "Lista de Adjacência", JOptionPane.PLAIN_MESSAGE);
    }

    private void showHelp() {
        JTextArea text = new JTextArea();
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setText(
                "INSTRUÇÕES DE USO\n\n" +
                        "• Arraste os nós para reposicionar\n" +
                        "• Clique no menu para executar\n" +
                        "• MST remove arestas não usadas\n" +
                        "• BFS/DFS: animação\n" +
                        "• Dijkstra: caminho em vermelho + comboio\n" +
                        "• MST: comboio patrulha TODAS as linhas (vai e volta)\n\n" +
                        "TECLAS:\n" +
                        "• R → Resetar grafo\n" +
                        "• ESC → Limpar destaques e comboio\n\n" +
                        "Tabelas são redimensionáveis!"
        );
        text.setEditable(false);
        text.setBackground(FUNDO_CLARO);
        text.setForeground(CINZA_TEXTO);
        text.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setPreferredSize(new Dimension(520, 420));

        JOptionPane.showMessageDialog(this, scroll, "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Grafos UA");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(AZUL_UCRANIA);

        JTextArea desc = new JTextArea(
                "Sistema de Visualização de Grafos\n" +
                        "para Cidades da Ucrânia\n\n" +
                        "Algoritmos:\n" +
                        "• Kruskal & Prim (MST)\n" +
                        "• BFS & DFS\n" +
                        "• Dijkstra\n" +
                        "• Matriz e Lista\n\n" +
                        "Tema: Bandeira da Ucrânia\n" +
                        "Desenvolvido para AED."
        );
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        desc.setEditable(false);
        desc.setBackground(Color.WHITE);
        desc.setForeground(CINZA_TEXTO);

        JLabel icon = new JLabel();
        icon.setIcon(new ImageIcon(createUkraineFlagIcon()));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.add(title);
        center.add(Box.createVerticalStrut(10));
        center.add(desc);

        panel.add(icon, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Sobre", JOptionPane.PLAIN_MESSAGE);
    }

    private BufferedImage createUkraineFlagIcon() {
        int w = 90, h = 55;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(AZUL_UCRANIA);
        g.fillRect(0, 0, w, h/2);
        g.setColor(AMARELO_UCRANIA);
        g.fillRect(0, h/2, w, h/2);
        g.dispose();
        return img;
    }

    private void generatePDFReport() {
        try {
            String filename = "Relatorio_UA_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date()) + ".pdf";
            Document document = new Document(PageSize.A4, 50, 50, 70, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onStartPage(PdfWriter writer, Document document) {
                    try {
                        PdfPTable header = new PdfPTable(2);
                        header.setWidthPercentage(100);
                        header.setWidths(new float[]{15, 85});
                        Image flag = Image.getInstance(createUkraineFlagImage());
                        flag.scaleToFit(45, 28);
                        PdfPCell cell = new PdfPCell(flag);
                        cell.setBorder(Rectangle.NO_BORDER);
                        header.addCell(cell);
                        PdfPCell titleCell = new PdfPCell(new Phrase("Relatório - Grafos ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                        titleCell.setBorder(Rectangle.NO_BORDER);
                        header.addCell(titleCell);
                        document.add(header);
                        document.add(Chunk.NEWLINE);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            });
            document.open();

            document.add(new Paragraph("RELATÓRIO COMPLETO ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Análise das ligacoes das 15 Oblasts Ucranianas", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("1. MATRIZ DE ADJACÊNCIA (km)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            PdfPTable table = createAdjacencyMatrixTable();
            document.add(table);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("2. LISTA DE ADJACÊNCIA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            for (Node n : graph.getNodes()) {
                StringBuilder sb = new StringBuilder(n.getName() + " → ");
                for (Edge e : graph.getEdgesOf(graph.getNodes().indexOf(n))) {
                    Node other = e.getOtherNode(n);
                    sb.append(other.getName()).append(" (").append(e.getWeight()).append(" km), ");
                }
                if (sb.toString().endsWith(", ")) sb.setLength(sb.length() - 2);
                document.add(new Paragraph(sb.toString(), FontFactory.getFont(FontFactory.COURIER, 11)));
            }
            document.add(Chunk.NEWLINE);

            List<Edge> kruskalEdges = mst.getMSTKruskal();
            int custoK = mst.getCost(kruskalEdges);
            document.add(new Paragraph("3. ÁRVORE GERADORA MÍNIMA (KRUSKAL)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Custo total: " + custoK + " km", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            for (Edge e : kruskalEdges) {
                document.add(new Paragraph("• " + e.getFrom().getName() + " — " + e.getTo().getName() + " = " + e.getWeight() + " km"));
            }
            document.add(Chunk.NEWLINE);

            List<Edge> primEdges = mst.getMSTPrim();
            int custoP = mst.getCost(primEdges);
            document.add(new Paragraph("4. ÁRVORE GERADORA MÍNIMA (PRIM)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Custo total: " + custoP + " km", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            for (Edge e : primEdges) {
                document.add(new Paragraph("• " + e.getFrom().getName() + " — " + e.getTo().getName() + " = " + e.getWeight() + " km"));
            }
            document.add(Chunk.NEWLINE);

            int startIdx = graph.getNodeIndex("KYIV");
            int endIdx = graph.getNodeIndex("ODESSA");
            List<Node> path = ShortestPath.getShortestPathByIndex(graph, startIdx, endIdx);

            int totalDistance = 0;
            StringBuilder pathStr = new StringBuilder("Caminho: ");
            for (int i = 0; i < path.size(); i++) {
                Node current = path.get(i);
                pathStr.append(current.getName());
                if (i < path.size() - 1) {
                    Node next = path.get(i + 1);
                    Edge edge = graph.getEdge(current.getName(), next.getName());
                    if (edge != null) {
                        totalDistance += edge.getWeight();
                        pathStr.append(" → ");
                    } else {
                        pathStr.append(" (sem aresta) → ");
                    }
                }
            }

            document.add(new Paragraph("5. MENOR CAMINHO (DIJKSTRA: KYIV → ODESSA)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Distância: " + totalDistance + " km", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            document.add(new Paragraph(pathStr.toString()));
            document.add(Chunk.NEWLINE);

            List<Node> bfsOrder = GraphSearch.bfs(graph, startIdx);
            List<Node> dfsOrder = GraphSearch.dfs(graph, startIdx);

            document.add(new Paragraph("6. BUSCA EM LARGURA (BFS - KYIV)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            StringBuilder bfsStr = new StringBuilder();
            for (Node n : bfsOrder) bfsStr.append(n.getName()).append(" → ");
            if (bfsStr.length() > 0) bfsStr.setLength(bfsStr.length() - 4);
            document.add(new Paragraph(bfsStr.toString()));

            document.add(new Paragraph("7. BUSCA EM PROFUNDIDADE (DFS - KYIV)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            StringBuilder dfsStr = new StringBuilder();
            for (Node n : dfsOrder) dfsStr.append(n.getName()).append(" → ");
            if (dfsStr.length() > 0) dfsStr.setLength(dfsStr.length() - 4);
            document.add(new Paragraph(dfsStr.toString()));

            document.close();

            JOptionPane.showMessageDialog(this,
                    "Relatório gerado com sucesso!\n\nSalvo como:\n" + new java.io.File(filename).getAbsolutePath(),
                    "PDF Gerado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private PdfPTable createAdjacencyMatrixTable() throws DocumentException {
        String[] cities = graph.getNodes().stream().map(Node::getName).toArray(String[]::new);
        int n = cities.length;
        int[][] m = graph.getAdjacencyMatrix();

        PdfPTable table = new PdfPTable(n + 1);
        table.setWidthPercentage(100);
        float[] widths = new float[n + 1];
        widths[0] = 8;
        for (int i = 1; i <= n; i++) widths[i] = 5;
        table.setWidths(widths);

        table.addCell(new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10))));
        for (String c : cities) {
            table.addCell(new PdfPCell(new Phrase(c.substring(0, Math.min(5, c.length())), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9))));
        }

        for (int i = 0; i < n; i++) {
            table.addCell(new PdfPCell(new Phrase(cities[i].substring(0, Math.min(8, cities[i].length())), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9))));
            for (int j = 0; j < n; j++) {
                String val = m[i][j] == 0 ? "-" : String.valueOf(m[i][j]);
                table.addCell(new PdfPCell(new Phrase(val, FontFactory.getFont(FontFactory.COURIER, 10))));
            }
        }
        return table;
    }

    private byte[] createUkraineFlagImage() throws Exception {
        int w = 80, h = 50;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(AZUL_UCRANIA); g.fillRect(0, 0, w, h/2);
        g.setColor(AMARELO_UCRANIA); g.fillRect(0, h/2, w, h/2);
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        return baos.toByteArray();
    }

    private Graph createGraph() {
        Graph g = new Graph();
        String[] c = {"ZHYTOMYR","VINNYTSA","KYIV","KROPYNYTSKYI","ODESSA","CHERNIHIV","SUMMY","POLTAVA","KHARKIV","DNIPRO","DONETSK","LUHANSK","MYKOLAIV","KHERSON","ZAPORIZHZHYA"};
        int[][] pos = {{150,150},{150,300},{350,200},{350,400},{200,500},{450,100},{600,100},{550,250},{700,200},{550,400},{750,400},{850,300},{350,520},{500,550},{650,480}};
        for (int i = 0; i < c.length; i++) g.addNode(c[i], pos[i][0], pos[i][1]);
        int[][] edges = {{0,1,220},{0,2,120},{1,2,260},{1,3,160},{1,4,270},{5,6,120},{5,2,150},{6,8,180},{2,7,290},{7,8,200},{7,3,180},{7,9,250},{8,9,300},{8,10,240},{8,11,200},{3,9,200},{3,12,250},{9,14,100},{9,10,260},{12,4,140},{12,13,150},{13,14,300},{14,10,200},{10,11,120}};
        for (int[] e : edges) g.addEdge(c[e[0]], c[e[1]], e[2]);
        return g;
    }
}