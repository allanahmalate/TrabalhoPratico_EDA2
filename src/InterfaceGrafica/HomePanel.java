package InterfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;

public class HomePanel extends JPanel {
    
    public HomePanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Cabeçalho principal
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(headerPanel);

        contentPanel.add(Box.createVerticalStrut(25));

        // Seção do Mapa
        JPanel mapSectionPanel = createMapSectionPanel();
        mapSectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(mapSectionPanel);

        contentPanel.add(Box.createVerticalStrut(25));

        // Seção do Globo 3D
        JPanel globeSectionPanel = createGlobeSectionPanel();
        globeSectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(globeSectionPanel);

        contentPanel.add(Box.createVerticalStrut(25));

        // Seção de Informações
        JPanel infoPanel = createInfoPanel();
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(infoPanel);

        contentPanel.add(Box.createVerticalStrut(20));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel leftFlag = createFlagLabel(100, 70);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        
        JLabel title1 = new JLabel("SISTEMA DE ABASTECIMENTO E");
        title1.setFont(new Font("Segoe UI", Font.BOLD, 26)); // título maior
        title1.setForeground(new Color(0, 87, 183));
        title1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("TRANSPORTE MILITAR DA UCRÂNIA");
        title2.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title2.setForeground(new Color(0, 87, 183));
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Slogan mais moderno e elegante
        JLabel subtitle = new JLabel("CADA ROTA IMPORTA. CADA DECISÃO SALVA.");
        subtitle.setFont(new Font("Roboto", Font.ITALIC, 15)); // fonte clean e itálica para destaque
        subtitle.setForeground(new Color(60, 60, 60));           // cinza escuro, legível
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);


     
        titlePanel.add(Box.createVerticalGlue());
        titlePanel.add(title1);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(title2);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitle);
        titlePanel.add(Box.createVerticalGlue());

        JLabel rightFlag = createFlagLabel(100, 70);

        panel.add(leftFlag, BorderLayout.WEST);
        panel.add(titlePanel, BorderLayout.CENTER);
        panel.add(rightFlag, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMapSectionPanel() {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));

        JLabel sectionTitle = new JLabel("MAPA FERROVIÁRIO DA UCRÂNIA");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(0, 87, 183));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 12, 0));

//        JPanel mapPanel = new JPanel(new BorderLayout());
//        mapPanel.setBackground(Color.WHITE);
//        mapPanel.setPreferredSize(new Dimension(100, 300));
//        mapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
//        mapPanel.setBorder(BorderFactory.createCompoundBorder(
//            BorderFactory.createLineBorder(new Color(0, 87, 183), 2),
//            BorderFactory.createEmptyBorder(10, 10, 10, 10)
//        ));
//        mapPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JLabel mapLabel = new JLabel();
//        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        mapLabel.setVerticalAlignment(SwingConstants.CENTER);
//
//        try {
//            File mapFile = new File("mapa.jpeg");
//            if (mapFile.exists()) {
//                ImageIcon icon = new ImageIcon(mapFile.getAbsolutePath());
//                mapLabel.setIcon(icon);
//            } else {
//                mapLabel.setIcon(createMapPlaceholder());
//            }
//        } catch (Exception e) {
//            mapLabel.setIcon(createMapPlaceholder());
//        }
//
//        mapPanel.add(mapLabel, BorderLayout.CENTER);
        
        
   

        
              
        JPanel mapPanel = new JPanel(new BorderLayout());
        mapPanel.setBackground(Color.WHITE);

        // Definir altura fixa e largura máxima menor para colar mais nas bordas
        mapPanel.setPreferredSize(new Dimension(0, 300)); // largura mínima flexível
        mapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        // Bordas azul com padding mínimo
        mapPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 87, 183), 2),
            BorderFactory.createEmptyBorder(4, 8, 4, 8) // pouco espaço só para respirar
        ));

        mapPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
 

//        JLabel mapLabel = new JLabel();
//        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        mapLabel.setVerticalAlignment(SwingConstants.CENTER);
//
//        // Faz a imagem preencher todo o espaço disponível do mapLabel
//        mapLabel.setPreferredSize(null);
//        mapLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
//        mapLabel.setMinimumSize(new Dimension(100, 200)); // opcional, evita ficar muito pequeno
//
//        // Carregar imagem
//        try {
//            File mapFile = new File("mapa.jpeg");
//            if (mapFile.exists()) {
//                ImageIcon icon = new ImageIcon(mapFile.getAbsolutePath());
//                mapLabel.setIcon(icon);
//            } else {
//                mapLabel.setIcon(createMapPlaceholder());
//            }
//        } catch (Exception e) {
//            mapLabel.setIcon(createMapPlaceholder());
//        }
//
//        mapPanel.add(mapLabel, BorderLayout.CENTER);
        
        JPanel mapLabel = new JPanel() {
            private Image mapImage;

            {
                try {
                    File mapFile = new File("mapa.jpeg");
                    if (mapFile.exists()) {
                        mapImage = new ImageIcon(mapFile.getAbsolutePath()).getImage();
                    } else {
                        mapImage = createMapPlaceholder().getImage();
                    }
                } catch (Exception e) {
                    mapImage = createMapPlaceholder().getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (mapImage != null) {
                    int padding = 8; // espaço entre imagem e borda azul
                    int w = getWidth() - 2 * padding;
                    int h = getHeight() - 2 * padding;
                    g.drawImage(mapImage, padding, padding, w, h, this);
                }
            }
        };

        mapPanel.add(mapLabel, BorderLayout.CENTER);

        
        
        

        sectionPanel.add(sectionTitle);
        sectionPanel.add(mapPanel);

        return sectionPanel;
    }

    private JPanel createGlobeSectionPanel() {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));

        JLabel sectionTitle = new JLabel("VISUALIZAÇÃO GLOBAL INTERATIVA 3D");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(0, 87, 183));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 12, 0));

        JPanel globePanel = new JPanel(new BorderLayout());
        globePanel.setBackground(Color.WHITE);
        globePanel.setPreferredSize(new Dimension(100, 350));
        globePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        globePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 87, 183), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        globePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        SimpleGlobe3D globe = new SimpleGlobe3D();
        globePanel.add(globe, BorderLayout.CENTER);

        sectionPanel.add(sectionTitle);
        sectionPanel.add(globePanel);

        return sectionPanel;
    }

    private JPanel createInfoPanel() {   	
    	// Painel principal do texto com estilo igual ao mapa
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	panel.setBackground(Color.WHITE);
    	panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));

    	// Título azul, consistente com outras seções
    	JLabel title = new JLabel("DESCRIÇÃO DO GRAFO FERROVIÁRIO");
    	title.setFont(new Font("Segoe UI", Font.BOLD, 18));
    	title.setForeground(new Color(0, 87, 183)); // azul igual ao mapa
    	title.setAlignmentX(Component.LEFT_ALIGNMENT);
    	title.setBorder(BorderFactory.createEmptyBorder(0, 5, 12, 0));

    	// Painel interno com borda azul e padding
    	JPanel textPanel = new JPanel(new BorderLayout());
    	textPanel.setBackground(Color.WHITE);
    	textPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
    	textPanel.setBorder(BorderFactory.createCompoundBorder(
    	    BorderFactory.createLineBorder(new Color(0, 87, 183), 2), // borda azul
    	    BorderFactory.createEmptyBorder(15, 15, 15, 15) // padding interno
    	));
    	textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    	// Texto descritivo
    	JTextArea description = new JTextArea();
    	description.setText(
    	    "O grafo da linha ferroviária da Ucrânia representa de forma clara como as cidades estão conectadas por trilhos, " +
    	    "permitindo a análise estratégica de rotas e a otimização do transporte de passageiros e cargas.\n\n" +
    	    "Algoritmos clássicos de grafos podem ser aplicados para extrair informações valiosas:\n\n" +
    	    "- Dijkstra: identifica o caminho mais curto entre duas cidades.\n" +
    	    "- Prim: auxilia na criação de uma rede mínima de trilhos, economizando recursos.\n" +
    	    "- BFS e DFS: exploram a conectividade e identificam cidades-chave na logística.\n\n" +
    	    "Estes conceitos demonstram como sistemas ferroviários reais podem ser planejados de forma eficiente, " +
    	    "segura e sustentável, apoiando decisões críticas para o desenvolvimento e a logística do país."
    	);
    	description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    	description.setForeground(new Color(50, 50, 50));
    	description.setLineWrap(true);
    	description.setWrapStyleWord(true);
    	description.setEditable(false);
    	description.setOpaque(false);
    	description.setFocusable(false);

    	// JScrollPane para rolagem vertical
    	JScrollPane scrollPane = new JScrollPane(description);
    	scrollPane.setBorder(BorderFactory.createEmptyBorder()); // sem borda
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    	textPanel.add(scrollPane, BorderLayout.CENTER);

    	// Adicionar título e painel de texto ao painel principal
    	panel.add(title);
    	panel.add(textPanel);

        return panel;
    }

    private JLabel createFlagLabel(int width, int height) {
        JLabel flag = new JLabel();
        flag.setPreferredSize(new Dimension(width, height));

        try {
            File flagFile = new File("flag1.jpg");
            if (flagFile.exists()) {
                ImageIcon icon = new ImageIcon(flagFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                flag.setIcon(new ImageIcon(img));
            } else {
                flag.setIcon(createFlagIcon(width, height));
            }
        } catch (Exception e) {
            flag.setIcon(createFlagIcon(width, height));
        }

        return flag;
    }

    private ImageIcon createFlagIcon(int w, int h) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(new Color(0, 87, 183));
        g.fillRect(0, 0, w, h/2);
        
        g.setColor(new Color(255, 215, 0));
        g.fillRect(0, h/2, w, h/2);
        
        g.setColor(new Color(150, 150, 150));
        g.setStroke(new BasicStroke(2));
        g.drawRect(1, 1, w-2, h-2);
        
        g.dispose();
        return new ImageIcon(img);
    }

    private ImageIcon createMapPlaceholder() {
        int w = 800, h = 280;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient = new GradientPaint(0, 0, new Color(220, 235, 250), 0, h, new Color(180, 210, 240));
        g.setPaint(gradient);
        g.fillRect(0, 0, w, h);

        g.setColor(new Color(0, 87, 183, 100));
        int[] xPoints = {180, 350, 530, 680, 720, 700, 590, 510, 360, 240, 180};
        int[] yPoints = {145, 110, 100, 115, 145, 172, 190, 182, 177, 163, 145};
        g.fillPolygon(xPoints, yPoints, xPoints.length);

        g.setColor(new Color(0, 87, 183));
        g.setStroke(new BasicStroke(3));
        g.drawPolygon(xPoints, yPoints, xPoints.length);

        g.setColor(new Color(220, 0, 0));
        int[][] cities = {{280, 125}, {420, 118}, {570, 135}, {510, 162}, {360, 155}};
        for (int[] city : cities) {
            g.fillOval(city[0] - 6, city[1] - 6, 12, 12);
            g.setColor(Color.WHITE);
            g.fillOval(city[0] - 2, city[1] - 2, 4, 4);
            g.setColor(new Color(220, 0, 0));
        }

        g.setColor(new Color(80, 80, 80));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g.drawString("Coloque o arquivo 'mapa.jpeg' no diretório do projeto", 240, h - 18);

        g.dispose();
        return new ImageIcon(img);
    }

    // GLOBO 3D COM MELHOR IDENTIFICAÇÃO DA UCRÂNIA
    class SimpleGlobe3D extends JPanel {
        private double rotY = Math.toRadians(30); // Começa focado na Europa
        private double rotX = Math.toRadians(20);
        private Timer timer;
        private Point lastMouse;
        private boolean dragging = false;
        private double zoom = 1.2;

        public SimpleGlobe3D() {
            setBackground(new Color(5, 10, 25));
            
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    lastMouse = e.getPoint();
                    dragging = true;
                }
                public void mouseReleased(MouseEvent e) {
                    dragging = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (lastMouse != null) {
                        rotY += (e.getX() - lastMouse.x) * 0.01;
                        rotX -= (e.getY() - lastMouse.y) * 0.01;
                        rotX = Math.max(-1.5, Math.min(1.5, rotX));
                        lastMouse = e.getPoint();
                        repaint();
                    }
                }
            });

            addMouseWheelListener(e -> {
                zoom *= (e.getWheelRotation() < 0) ? 1.1 : 0.9;
                zoom = Math.max(0.5, Math.min(2.5, zoom));
                repaint();
            });

            timer = new Timer(40, e -> {
                if (!dragging) {
                    rotY += 0.002;
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int cx = w / 2;
            int cy = h / 2;
            int r = (int)(Math.min(w, h) / 3 * zoom);

            // Estrelas
            drawStars(g2, w, h);
            
            // Oceano com gradiente
            RadialGradientPaint ocean = new RadialGradientPaint(
                cx - r/3, cy - r/3, r * 1.5f,
                new float[]{0f, 0.7f, 1f},
                new Color[]{new Color(60, 120, 180), new Color(30, 80, 140), new Color(15, 50, 100)}
            );
            g2.setPaint(ocean);
            g2.fillOval(cx - r, cy - r, r * 2, r * 2);
            
            // Continentes
            drawContinents(g2, cx, cy, r);
            
            // Grid
            drawGrid(g2, cx, cy, r);
            
            // Atmosfera
            g2.setStroke(new BasicStroke(2f));
            for (int i = 0; i < 8; i++) {
                float ratio = (float)i / 8;
                int ar = (int)(r * (1.0 + 0.05 * ratio));
                g2.setColor(new Color(100, 150, 255, (int)(80 * (1 - ratio))));
                g2.drawOval(cx - ar, cy - ar, ar * 2, ar * 2);
            }
            
            // Marcador Ucrânia - MELHORADO
            drawUkraine(g2, cx, cy, r);
            
            // Info
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            g2.drawString("🖱 Arraste para rotacionar | 🔍 Scroll para zoom: " + String.format("%.1fx", zoom), 15, h - 15);
        }

        private void drawStars(Graphics2D g2, int w, int h) {
            java.util.Random rand = new java.util.Random(123);
            for (int i = 0; i < 300; i++) {
                int x = rand.nextInt(w);
                int y = rand.nextInt(h);
                int brightness = 100 + rand.nextInt(155);
                g2.setColor(new Color(255, 255, 255, brightness));
                int size = (brightness > 200) ? 2 : 1;
                g2.fillRect(x, y, size, size);
            }
        }

        private void drawContinents(Graphics2D g2, int cx, int cy, int r) {
            // Europa
            g2.setColor(new Color(70, 140, 60));
            drawShape(g2, cx, cy, r, new double[][]{{-10,36},{5,45},{20,55},{35,58},{40,50},{20,38},{0,35}});
            
            // África
            g2.setColor(new Color(140, 120, 70));
            drawShape(g2, cx, cy, r, new double[][]{{-17,37},{10,35},{45,10},{48,-10},{25,-34},{-5,-25},{-18,15}});
            
            // Ásia  
            g2.setColor(new Color(75, 135, 65));
            drawShape(g2, cx, cy, r, new double[][]{{40,10},{80,30},{120,48},{145,35},{120,12},{80,5}});
            
            // América do Norte
            g2.setColor(new Color(65, 125, 55));
            drawShape(g2, cx, cy, r, new double[][]{{-165,70},{-110,62},{-95,48},{-85,25},{-95,18},{-135,45}});
            
            // América do Sul
            g2.setColor(new Color(55, 115, 50));
            drawShape(g2, cx, cy, r, new double[][]{{-80,12},{-70,0},{-65,-20},{-52,-50},{-42,-40},{-58,0}});
            
            // Austrália
            g2.setColor(new Color(160, 110, 65));
            drawShape(g2, cx, cy, r, new double[][]{{113,-11},{145,-20},{153,-35},{130,-42},{115,-30}});
        }

        private void drawShape(Graphics2D g2, int cx, int cy, int r, double[][] coords) {
            java.util.List<int[]> pts = new java.util.ArrayList<>();
            for (double[] c : coords) {
                int[] p = project(c[1], c[0], r, cx, cy);
                if (p != null) pts.add(p);
            }
            if (pts.size() >= 3) {
                int[] xs = new int[pts.size()];
                int[] ys = new int[pts.size()];
                for (int i = 0; i < pts.size(); i++) {
                    xs[i] = pts.get(i)[0];
                    ys[i] = pts.get(i)[1];
                }
                g2.fillPolygon(xs, ys, pts.size());
            }
        }

        private void drawGrid(Graphics2D g2, int cx, int cy, int r) {
            g2.setColor(new Color(255, 255, 255, 30));
            g2.setStroke(new BasicStroke(0.7f));
            
            for (double lat = -60; lat <= 75; lat += 15) {
                for (double lon = -180; lon < 180; lon += 5) {
                    int[] p1 = project(lat, lon, r, cx, cy);
                    int[] p2 = project(lat, lon + 5, r, cx, cy);
                    if (p1 != null && p2 != null) {
                        g2.drawLine(p1[0], p1[1], p2[0], p2[1]);
                    }
                }
            }
            
            for (double lon = -180; lon <= 180; lon += 20) {
                for (double lat = -75; lat < 75; lat += 5) {
                    int[] p1 = project(lat, lon, r, cx, cy);
                    int[] p2 = project(lat + 5, lon, r, cx, cy);
                    if (p1 != null && p2 != null) {
                        g2.drawLine(p1[0], p1[1], p2[0], p2[1]);
                    }
                }
            }
        }

        private void drawUkraine(Graphics2D g2, int cx, int cy, int r) {
            // Coordenadas aproximadas da Ucrânia: Lat 49°N, Lon 32°E
            double lat = 49.0;
            double lon = 32.0;
            
            int[] pos = project(lat, lon, r, cx, cy);
            if (pos != null) {
                // Animação de pulso
                double pulse = 0.6 + 0.4 * Math.sin(System.currentTimeMillis() / 400.0);
                
                // Halo externo dourado (maior)
                int haloSize = (int)(50 * pulse * zoom);
                g2.setColor(new Color(255, 215, 0, (int)(40 * pulse)));
                g2.fillOval(pos[0] - haloSize/2, pos[1] - haloSize/2, haloSize, haloSize);
                
                // Halo médio
                int haloSize2 = (int)(35 * pulse * zoom);
                g2.setColor(new Color(255, 215, 0, (int)(80 * pulse)));
                g2.fillOval(pos[0] - haloSize2/2, pos[1] - haloSize2/2, haloSize2, haloSize2);
                
                // Anel azul (bandeira da Ucrânia)
                g2.setStroke(new BasicStroke(3.5f));
                g2.setColor(new Color(0, 87, 183));
                int ring = (int)(20 * zoom);
                g2.drawOval(pos[0] - ring/2, pos[1] - ring/2, ring, ring);
                
                // Ponto central dourado (bandeira da Ucrânia)
                g2.setColor(new Color(255, 215, 0));
                int dot = (int)(14 * zoom);
                g2.fillOval(pos[0] - dot/2, pos[1] - dot/2, dot, dot);
                
                // Borda branca no centro
                g2.setColor(Color.WHITE);
                int innerDot = (int)(6 * zoom);
                g2.fillOval(pos[0] - innerDot/2, pos[1] - innerDot/2, innerDot, innerDot);
                
                // Label "UCRÂNIA"
                if (zoom > 0.6) {
                    g2.setFont(new Font("Segoe UI", Font.BOLD, (int)(13 * Math.min(zoom, 1.5))));
                    
                    // Sombra do texto
                    g2.setColor(new Color(0, 0, 0, 150));
                    g2.drawString("UCRÂNIA", pos[0] + 16, pos[1] + 6);
                    
                    // Texto principal
                    g2.setColor(new Color(255, 215, 0));
                    g2.drawString("UCRÂNIA", pos[0] + 15, pos[1] + 5);
                }
                
                // Linhas radiantes
                g2.setStroke(new BasicStroke(1.5f));
                for (int i = 0; i < 8; i++) {
                    double angle = i * Math.PI / 4 + System.currentTimeMillis() / 1000.0;
                    int lineLen = (int)(25 * zoom);
                    int x1 = pos[0] + (int)(Math.cos(angle) * 15 * zoom);
                    int y1 = pos[1] + (int)(Math.sin(angle) * 15 * zoom);
                    int x2 = pos[0] + (int)(Math.cos(angle) * (15 + lineLen) * zoom);
                    int y2 = pos[1] + (int)(Math.sin(angle) * (15 + lineLen) * zoom);
                    g2.setColor(new Color(255, 215, 0, (int)(100 * pulse)));
                    g2.drawLine(x1, y1, x2, y2);
                }
            }
        }

        private int[] project(double lat, double lon, int r, int cx, int cy) {
            double latR = Math.toRadians(lat);
            double lonR = Math.toRadians(lon);
            
            double x = Math.cos(latR) * Math.sin(lonR);
            double y = Math.sin(latR);
            double z = Math.cos(latR) * Math.cos(lonR);
            
            double y2 = y * Math.cos(rotX) - z * Math.sin(rotX);
            double z2 = y * Math.sin(rotX) + z * Math.cos(rotX);
            
            double x3 = x * Math.cos(rotY) - z2 * Math.sin(rotY);
            double z3 = x * Math.sin(rotY) + z2 * Math.cos(rotY);
            
            if (z3 < 0) return null;
            
            return new int[]{cx + (int)(x3 * r), cy - (int)(y2 * r)};
        }
    }
}
