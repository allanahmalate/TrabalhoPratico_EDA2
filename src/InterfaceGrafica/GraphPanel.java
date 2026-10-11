import javax.swing.*;

import com.itextpdf.awt.geom.Point2D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphPanel extends JPanel {
    private Graph graph;
    private Map<Node, Point> positions;
    private Node draggedNode;
    private Point dragOffset;
    private Set<Edge> visibleEdges;
    private List<Node> highlightPath = new ArrayList<>();
    private List<Edge> highlightMST = new ArrayList<>();
    private javax.swing.Timer timer;
    private List<Node> animNodes = new ArrayList<>();
    private List<Edge> animEdges = new ArrayList<>();
    private Set<Node> treatedNodes = new HashSet<>();
    private Set<Edge> treatedEdges = new HashSet<>();
    
    private Image trainImage;      // ícone do comboio
    private Point2D.Double trainPos; // posição atual do comboio
    private int trainStepIndex;      // índice do passo atual no caminho
    private boolean trainMoving;     // se o comboio está em movimento
    private boolean dijkstraView = false;


    private boolean mstView = false; // por padrão false

    
    public GraphPanel(Graph g) {
        this.graph = g;
        this.visibleEdges = new HashSet<>(g.getEdges());
        positions = new HashMap<>();
        for (Node n : g.getNodes()) positions.put(n, new Point((int)n.getX(), (int)n.getY()));
        setBackground(Color.WHITE);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) { startDrag(e); }
            public void mouseReleased(java.awt.event.MouseEvent e) { stopDrag(); }
        });

        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) { doDrag(e); }
        });

        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_R) showAllEdges();
                else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    highlightPath(null);
                    showAllEdges();
                }
            }
        });
        
        try {
            trainImage = javax.imageio.ImageIO.read(new java.io.File("train.png")); // caminho do ícone
        } catch (Exception e) {
            e.printStackTrace();
        }
        trainPos = new Point2D.Double();
        trainMoving = false;

    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minX = positions.values().stream().mapToDouble(p -> p.x).min().orElse(0);
        double maxX = positions.values().stream().mapToDouble(p -> p.x).max().orElse(1);
        double minY = positions.values().stream().mapToDouble(p -> p.y).min().orElse(0);
        double maxY = positions.values().stream().mapToDouble(p -> p.y).max().orElse(1);

        //double scale = Math.min((getWidth()-100)/(maxX-minX), (getHeight()-100)/(maxY-minY)) * 1;
        int panelPadding = 50; // espaço nas bordas
        double scaleX = (getWidth() - 2*panelPadding) / (maxX - minX);
        double scaleY = (getHeight() - 2*panelPadding) / (maxY - minY);
        double scale = Math.min(scaleX, scaleY);

        if (mstView) {
            scale *= 1.2; // aumenta 40% apenas para MST
        }

        if (dijkstraView) {
            scale *= 1.2; // aumenta 20% para Dijkstra também
        }
        
        // Calcular offsets para centralizar
        int offsetX = (int)((getWidth() - (maxX - minX) * scale) / 2);
        int offsetY = (int)((getHeight() - (maxY - minY) * scale) / 2);

        // === ARESTAS ===
        for (Edge e : visibleEdges) {
            Point p1 = positions.get(e.getFrom()), p2 = positions.get(e.getTo());
            if (p1 == null || p2 == null) continue;

            int x1 = (int)(offsetX + (p1.x - minX) * scale);
            int y1 = (int)(offsetY + (p1.y - minY) * scale);
            int x2 = (int)(offsetX + (p2.x - minX) * scale);
            int y2 = (int)(offsetY + (p2.y - minY) * scale);

            boolean inPath = isInPath(e, highlightPath);
            boolean inMST = highlightMST.contains(e);
            boolean inAnim = animEdges.contains(e);

            g2.setStroke(new BasicStroke(inPath || inAnim ? 5.5f : inMST ? 4.5f : 3.2f));
            Color edgeColor = new Color(90, 90, 90); // default preto suavizado

            if (treatedEdges.contains(e)) {
                edgeColor = UkraineGraphSystem.AZUL_UCRANIA; // MST/tratadas
            } else if (inPath || inAnim) {
                edgeColor = UkraineGraphSystem.AZUL_UCRANIA; // caminho mínimo azul
            } else if (inMST) {
                edgeColor = new Color(180, 40, 120);
            } else {
                edgeColor = new Color(90, 90, 90); // default
            }

            g2.setColor(edgeColor);
            
            g2.drawLine(x1, y1, x2, y2);

            // Peso da aresta
            int mx = (x1+x2)/2, my = (y1+y2)/2;
            g2.setColor(Color.WHITE);
            //g2.fillRoundRect(mx-16, my-10, 52, 30, 8, 8);
            g2.fillRoundRect(mx - 24, my - 15, 47, 30, 20, 20);
            g2.setColor(inPath || inAnim ? Color.RED : Color.DARK_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            String weightText = e.getWeight() + " km";
            FontMetrics fm = g2.getFontMetrics();
            int textX = mx - fm.stringWidth(weightText)/2;
            int textY = my + 2;
            g2.drawString(weightText, textX, textY);
        }

        // === VÉRTICES ===
        int w = 110, h = 35;
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));

        Set<String> donbasRegion = new HashSet<>(Arrays.asList("KHERSON","LUHANSK","ZAPORIZHZHYA","DONETSK"));

        for (Node n : graph.getNodes()) {
            Point p = positions.get(n);
            int x = (int)(offsetX + (p.x - minX) * scale) - w/2;
            int y = (int)(offsetY + (p.y - minY) * scale) - h/2;

            // Cor de fundo
            if (treatedNodes.contains(n) || highlightPath.contains(n)) {
                g2.setColor(Color.YELLOW); // nó tratado ou no caminho mínimo
            } else if (donbasRegion.contains(n.getName())) {
                g2.setColor(new Color(140,180,250));
            } else {
                g2.setColor(new Color(190,220,255));
            }
            g2.fillRoundRect(x, y, w, h, 25, 25);


            // Borda
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, w, h, 25, 25);

            // Texto centrado
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(n.getName());
            int textHeight = fm.getAscent();
            g2.setColor(Color.BLACK);
            g2.drawString(n.getName(), x + w/2 - textWidth/2, y + h/2 + textHeight/4);
        }
        
        if (trainMoving && trainImage != null) {
            int trainW = 30, trainH = 30; // tamanho do comboio
            g2.drawImage(trainImage, (int)trainPos.x - trainW/2, (int)trainPos.y - trainH/2, trainW, trainH, null);
        }
    }


    private boolean isInPath(Edge e, List<Node> path) {
        for (int i = 0; i < path.size()-1; i++) {
            Node a = path.get(i), b = path.get(i+1);
            if ((e.getFrom().equals(a) && e.getTo().equals(b)) ||
                    (e.getFrom().equals(b) && e.getTo().equals(a))) return true;
        }
        return false;
    }

    public void showMST(List<Edge> mst) {
        visibleEdges = new HashSet<>(mst);
        highlightMST = mst;
        highlightPath.clear();
        stopAnim();

        // Limpa estados anteriores
        treatedEdges.clear();
        treatedNodes.clear();

        // Pintar arestas de azul
        treatedEdges.addAll(mst);

        // Pintar nós de amarelo
        for (Edge e : mst) {
            treatedNodes.add(e.getFrom());
            treatedNodes.add(e.getTo());
        }

        repaint();
    }



    public void showAllEdges() {
        visibleEdges = new HashSet<>(graph.getEdges());
        highlightMST.clear();
        highlightPath.clear();
        stopAnim();
        resetTreatedNodes(); // <--- reseta os nós tratados
        repaint();
        
    }

    public void highlightPath(List<Node> path) {
        showAllEdges();
        highlightPath = path != null ? new ArrayList<>(path) : new ArrayList<>();
        repaint();
    }

    public void animateBFS(int start) { animate(GraphSearch.bfs(graph, start), true); }
    public void animateDFS(int start) { animate(GraphSearch.dfs(graph, start), true); }

    private void animate(List<Node> nodes, boolean edges) {
//    	treatedNodes.clear();
        showAllEdges();
        stopAnim();
        animNodes.clear();
        animEdges.clear();
        List<Node> seq = new ArrayList<>(nodes);
        timer = new javax.swing.Timer(700, e -> {
            if (seq.isEmpty()) {
                ((javax.swing.Timer)e.getSource()).stop();
                return;
            }
            Node n = seq.remove(0);
            animNodes.add(n);
            markAsTreated(n); // agora o fundo ficará amarelo
            if (edges && animNodes.size() > 1) {
                Node prev = animNodes.get(animNodes.size()-2);
                for (Edge ed : graph.getEdgesOf(graph.getNodes().indexOf(n))) {
                    if (ed.getOtherNode(n).equals(prev)) {
                    	animEdges.add(ed);
                    	markEdgeAsTreated(ed);
                        break;
                    }
                }
            }
            repaint();
        });
        timer.start();
    }

    private void stopAnim() {
        if (timer != null && timer.isRunning()) timer.stop();
        animNodes.clear();
        animEdges.clear();
    }

    private void startDrag(java.awt.event.MouseEvent e) {
        double minX = positions.values().stream().mapToDouble(p -> p.x).min().orElse(0);
        double maxX = positions.values().stream().mapToDouble(p -> p.x).max().orElse(1);
        double minY = positions.values().stream().mapToDouble(p -> p.y).min().orElse(0);
        double maxY = positions.values().stream().mapToDouble(p -> p.y).max().orElse(1);
        double scale = Math.min((getWidth()-100)/(maxX-minX), (getHeight()-100)/(maxY-minY));

        
        for (Node n : graph.getNodes()) {
            Point p = positions.get(n);
            int x = (int)(50 + (p.x - minX) * scale);
            int y = (int)(50 + (p.y - minY) * scale);
            if (Math.hypot(e.getX() - x, e.getY() - y) < 32) {
                draggedNode = n;
                dragOffset = new Point(e.getX() - x, e.getY() - y);
                break;
            }
        }
    }

    private void doDrag(java.awt.event.MouseEvent e) {
        if (draggedNode != null) {
            double minX = positions.values().stream().mapToDouble(p -> p.x).min().orElse(0);
            double maxX = positions.values().stream().mapToDouble(p -> p.x).max().orElse(1);
            double minY = positions.values().stream().mapToDouble(p -> p.y).min().orElse(0);
            double maxY = positions.values().stream().mapToDouble(p -> p.y).max().orElse(1);
            double scale = Math.min((getWidth()-100)/(maxX-minX), (getHeight()-100)/(maxY-minY));
            Point p = positions.get(draggedNode);
            p.x = (int)(minX + (e.getX() - 50 - dragOffset.x) / scale);
            p.y = (int)(minY + (e.getY() - 50 - dragOffset.y) / scale);
            repaint();
        }
    }

    private void stopDrag() { draggedNode = null; }

    // Marca um nó como tratado
    public void markAsTreated(Node n) {
        treatedNodes.add(n);
        repaint();
    }

    // Limpa todos os nós tratados e arestas
    public void resetTreatedNodes() {
        treatedNodes.clear();
        treatedEdges.clear();
        repaint();
    }


    public void markEdgeAsTreated(Edge e) {
        treatedEdges.add(e);
        repaint();
    }

    public void resetTreatedEdges() {
        treatedEdges.clear();
        repaint();
    }
    
    public void setMSTView(boolean mstView) {
        this.mstView = mstView;
        repaint();
    }
    
    public void setDijkstraView(boolean val) {
        this.dijkstraView = val;
        repaint();
    }

    
    public void animateDijkstra(List<Node> path) {
        if (path == null || path.size() < 2) return;

        stopAnim(); // para qualquer animação anterior
        trainMoving = true;
        trainStepIndex = 0;

        // Se ainda não tiver posição inicial, define no primeiro nó
        Point start = getPixelPos(path.get(0));
        trainPos.x = start.x;
        trainPos.y = start.y;

        timer = new javax.swing.Timer(40, null); // 40ms por frame → mais lento e suave
        timer.addActionListener(e -> {
            if (trainStepIndex >= path.size() - 1) {
                trainMoving = false;
                ((javax.swing.Timer)e.getSource()).stop();
                repaint();
                return;
            }

            Node from = path.get(trainStepIndex);
            Node to = path.get(trainStepIndex + 1);

            Point pFrom = getPixelPos(from);
            Point pTo = getPixelPos(to);

            // Interpolação linear
            double dx = pTo.x - pFrom.x;
            double dy = pTo.y - pFrom.y;
            int steps = 20; // número de frames por aresta
            double stepX = dx / steps;
            double stepY = dy / steps;

            // Atualiza posição do comboio
            trainPos.x += stepX;
            trainPos.y += stepY;

            // Se chegou ao nó final da aresta
            if (Math.hypot(trainPos.x - pTo.x, trainPos.y - pTo.y) < 2) {
                trainStepIndex++;
                trainPos.x = pTo.x; // garante que não passe do ponto
                trainPos.y = pTo.y;
            }

            repaint();
        });

        timer.start();
    }

    // Método helper para pegar posição pixelizada de um nó
    private Point getPixelPos(Node n) {
        double minX = positions.values().stream().mapToDouble(p -> p.x).min().orElse(0);
        double maxX = positions.values().stream().mapToDouble(p -> p.x).max().orElse(1);
        double minY = positions.values().stream().mapToDouble(p -> p.y).min().orElse(0);
        double maxY = positions.values().stream().mapToDouble(p -> p.y).max().orElse(1);

        int panelPadding = 50;
        double scaleX = (getWidth() - 2*panelPadding) / (maxX - minX);
        double scaleY = (getHeight() - 2*panelPadding) / (maxY - minY);
        double scale = Math.min(scaleX, scaleY);

        // ✅ aplica fator MST ou Dijkstra como no paintComponent
        if (mstView) scale *= 1.2;
        if (dijkstraView) scale *= 1.2; 

        int offsetX = (int)((getWidth() - (maxX - minX) * scale) / 2);
        int offsetY = (int)((getHeight() - (maxY - minY) * scale) / 2);

        int x = (int)(offsetX + (positions.get(n).x - minX) * scale);
        int y = (int)(offsetY + (positions.get(n).y - minY) * scale);
        return new Point(x, y);
    }

}
