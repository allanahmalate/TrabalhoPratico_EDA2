// GraphVisualizer.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphVisualizer extends JPanel {
    private Graph graph;
    private List<Node> path;
    private List<Edge> mst;
    private final int P = 50; // padding

    public GraphVisualizer(Graph g) {
        this.graph = g;
        setPreferredSize(new Dimension(1000, 700));
        setBackground(Color.WHITE);
    }

    public void setPath(List<Node> p) { this.path = p; repaint(); }
    public void setMST(List<Edge> m) { this.mst = m; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (Node n : graph.getNodes()) {
            minX = Math.min(minX, n.getX()); maxX = Math.max(maxX, n.getX());
            minY = Math.min(minY, n.getY()); maxY = Math.max(maxY, n.getY());
        }
        double scaleX = (getWidth() - 2*P) / (maxX - minX);
        double scaleY = (getHeight() - 2*P) / (maxY - minY);
        double scale = Math.min(scaleX, scaleY);

        // --- Arestas ---
        for (Edge e : graph.getEdges()) {
            Node a = e.getFrom(), b = e.getTo();
            int x1 = (int)(P + (a.getX() - minX) * scale);
            int y1 = (int)(P + (a.getY() - minY) * scale);
            int x2 = (int)(P + (b.getX() - minX) * scale);
            int y2 = (int)(P + (b.getY() - minY) * scale);

            boolean inPath = isInPath(e);
            boolean inMST = mst != null && mst.contains(e);

            if (inPath) { g2d.setColor(Color.RED); g2d.setStroke(new BasicStroke(3)); }
            else if (inMST) { g2d.setColor(Color.MAGENTA); g2d.setStroke(new BasicStroke(2)); }
            else { g2d.setColor(Color.LIGHT_GRAY); g2d.setStroke(new BasicStroke(1)); }

            g2d.drawLine(x1, y1, x2, y2);

            int mx = (x1 + x2)/2, my = (y1 + y2)/2;
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(e.getWeight() + " km", mx, my - 5);
        }

        // --- Vértices no estilo arredondado ---
        int w = 120;
        int h = 45;
        g2d.setFont(new Font("SansSerif", Font.BOLD, 13));

        for (Node n : graph.getNodes()) {
            int x = (int)(P + (n.getX() - minX) * scale) - w/2;
            int y = (int)(P + (n.getY() - minY) * scale) - h/2;

            // Cor do vértice
            if (path != null && path.contains(n)) {
                g2d.setColor(new Color(250, 180, 100)); // nó no caminho
            } else {
                g2d.setColor(new Color(190, 220, 255)); // nó normal
            }

            g2d.fillRoundRect(x, y, w, h, 25, 25);
            g2d.setColor(Color.BLUE);
            g2d.drawRoundRect(x, y, w, h, 25, 25);

            // Nome centralizado
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(n.getName());
            int textHeight = fm.getAscent();
            int nodeCenterX = (int)(P + (n.getX() - minX) * scale);
            int nodeCenterY = (int)(P + (n.getY() - minY) * scale);
            g2d.setColor(Color.BLACK);
            g2d.drawString(n.getName(), nodeCenterX - textWidth / 2, nodeCenterY + textHeight / 4);
        }
    }

    private boolean isInPath(Edge e) {
        if (path == null || path.size() < 2) return false;
        for (int i = 0; i < path.size() - 1; i++) {
            Node a = path.get(i), b = path.get(i+1);
            if ((e.getFrom().equals(a) && e.getTo().equals(b)) ||
                (e.getFrom().equals(b) && e.getTo().equals(a))) return true;
        }
        return false;
    }

    public static void show(Graph g, String title) {
        JFrame f = new JFrame(title);
        GraphVisualizer v = new GraphVisualizer(g);
        f.add(v);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            try { Thread.sleep(500); } catch (Exception ignored) {}
            int kyiv = g.getNodeIndex("KYIV");
            int odessa = g.getNodeIndex("ODESSA");
            List<Node> path = ShortestPath.getShortestPathByIndex(g, kyiv, odessa);
            v.setPath(path);
            v.setMST(new MinimumSpanningTree(g).getMSTKruskal());
        });
    }
}
