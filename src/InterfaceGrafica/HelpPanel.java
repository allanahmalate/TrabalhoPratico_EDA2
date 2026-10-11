import javax.swing.*;
import java.awt.*;

// ============== HELP PANEL ==============
class HelpPanel extends JPanel {
    private static final Color UKRAINE_BLUE = new Color(0, 87, 183);
    private static final Color UKRAINE_YELLOW = new Color(255, 215, 0);
    private static final Color BG_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_DARK = new Color(30, 41, 59);
    private static final Color TEXT_GRAY = new Color(100, 116, 139);
    private static final Color LIGHT_GRAY = new Color(248, 250, 252);
    private static final Color BORDER_GRAY = new Color(226, 232, 240);
    private static final Color CARD_BG = new Color(249, 250, 251);
    private static final Color ACCENT_LIGHT = new Color(219, 234, 254);

    public HelpPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(LIGHT_GRAY);

        // Content panel
        JPanel contentPanel = createContentPanel();

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Footer
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_GRAY);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(LIGHT_GRAY);
        content.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Header
        content.add(createHeader());
        content.add(Box.createVerticalStrut(25));

        // Welcome
        content.add(createWelcomeCard());
        content.add(Box.createVerticalStrut(25));

        // Quick Guide
        content.add(createSectionTitle("Início Rápido"));
        content.add(Box.createVerticalStrut(12));
        content.add(createQuickGuide());
        content.add(Box.createVerticalStrut(25));

        // Navigation
        content.add(createSectionTitle("Menu de Navegação"));
        content.add(Box.createVerticalStrut(12));
        content.add(createNavigationGrid());
        content.add(Box.createVerticalStrut(25));

        // Main features
        content.add(createSectionTitle("Funcionalidades Principais"));
        content.add(Box.createVerticalStrut(12));
        content.add(createFeaturesGrid());
        content.add(Box.createVerticalStrut(25));

        // Tips
        content.add(createSectionTitle("Dicas Úteis"));
        content.add(Box.createVerticalStrut(12));
        content.add(createTipsPanel());

        mainPanel.add(content, BorderLayout.NORTH);

        return mainPanel;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(LIGHT_GRAY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Flag
        JPanel flagPanel = new JPanel();
        flagPanel.setLayout(new BoxLayout(flagPanel, BoxLayout.Y_AXIS));
        flagPanel.setBackground(LIGHT_GRAY);
        flagPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel blueStripe = new JPanel();
        blueStripe.setBackground(UKRAINE_BLUE);
        blueStripe.setPreferredSize(new Dimension(100, 20));
        blueStripe.setMaximumSize(new Dimension(100, 20));

        JPanel yellowStripe = new JPanel();
        yellowStripe.setBackground(UKRAINE_YELLOW);
        yellowStripe.setPreferredSize(new Dimension(100, 20));
        yellowStripe.setMaximumSize(new Dimension(100, 20));

        flagPanel.add(blueStripe);
        flagPanel.add(yellowStripe);

        JLabel title = new JLabel("Central de Ajuda SATU");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(UKRAINE_BLUE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Sistema de Abastecimento e Transporte da Ucrânia");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(flagPanel);
        header.add(Box.createVerticalStrut(18));
        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        return header;
    }

    private JPanel createWelcomeCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(ACCENT_LIGHT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UKRAINE_BLUE, 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel welcome = new JLabel("Bem-vindo ao SATU!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcome.setForeground(UKRAINE_BLUE);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea text = new JTextArea(
                "O SATU utiliza teoria de grafos para analisar a rede ferroviária ucraniana com 15 Oblasts. " +
                        "Use esta ajuda para explorar todos os recursos disponíveis."
        );
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setForeground(TEXT_DARK);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(false);
        text.setEditable(false);
        text.setBorder(null);

        card.add(welcome);
        card.add(Box.createVerticalStrut(10));
        card.add(text);

        return card;
    }

    private JLabel createSectionTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(UKRAINE_BLUE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createQuickGuide() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_WHITE); // background branco
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // borda preta
                BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] steps = {
                "1. Escolha uma funcionalidade no menu lateral",
                "2. Selecione Oblast de origem e destino",
                "3. Clique em 'Calcular' para ver resultados",
                "4. Exporte para PDF se necessário"
        };

        for (String step : steps) {
            JLabel label = new JLabel(step);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(TEXT_DARK);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createVerticalStrut(6));
        }

        return panel;
    }


    private JPanel createNavigationGrid() {
        JPanel grid = new JPanel(new GridLayout(0, 2, 15, 15));
        grid.setBackground(LIGHT_GRAY);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[][] items = {
                {"Home", "Mapa e visualização 3D"},
                {"Grafo Inicial", "Rede ferroviária completa"},
                {"Representação", "Matriz ou Lista"},
                {"Matriz Distâncias", "Tabela de distâncias"},
                {"Busca", "BFS e DFS"},
                {"Árvore Cobertura", "Kruskal e Prim"},
                {"Caminho Mínimo", "Algoritmo Dijkstra"},
                {"Complexidades", "Análise de desempenho"},
                {"Relatório PDF", "Exportar resultados"}
        };

        for (String[] item : items) {
            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setBackground(LIGHT_GRAY);
            wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
            wrapper.add(createNavCard(item[0], item[1]));
            grid.add(wrapper);
        }

        return grid;
    }

    private JPanel createNavCard(String title, String desc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(descLabel);

        return card;
    }

    private JPanel createFeaturesGrid() {
        JPanel grid = new JPanel(new GridLayout(0, 2, 15, 15));
        grid.setBackground(LIGHT_GRAY);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[][] features = {
                {"Visualização Grafo", "15 Oblasts conectados por ferrovias com distâncias reais em km"},
                {"BFS/DFS", "Busca em largura explora níveis. Profundidade vai até o fim do ramo"},
                {"Dijkstra", "Calcula rota mais curta entre dois pontos. Ideal para logística"},
                {"Kruskal/Prim", "Conecta todos os Oblasts com menor distância total de linhas"},
                {"Matriz Adjacências", "Tabela 25x25 com todas as distâncias. Acesso rápido O(1)"},
                {"Lista Adjacências", "Otimizada para memória. Armazena apenas conexões existentes"}
        };

        for (String[] feature : features) {
            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setBackground(LIGHT_GRAY);
            wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
            wrapper.add(createFeatureCard(feature[0], feature[1]));
            grid.add(wrapper);
        }

        return grid;
    }

    private JPanel createFeatureCard(String title, String desc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createEmptyBorder(15, 18, 15, 18));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // centralizado

        JTextArea descArea = new JTextArea(desc);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descArea.setForeground(TEXT_DARK);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setBorder(null);
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT); // opcional, deixa descrição centralizada também

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(descArea);

        return card;
    }


    private JPanel createTipsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GRAY, 1),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] tips = {
                "Use Dijkstra para rotas militares estratégicas com menor distância",
                "BFS encontra caminhos com menos paradas (menos Oblasts intermediários)",
                "Matriz de Distâncias permite consultar qualquer conexão rapidamente",
                "Árvore Geradora identifica conexões ferroviárias mais críticas",
                "Gere PDF para documentar e compartilhar análises de rotas",
                "Menu 'Programadores', 'Sobre' e 'Ajuda' têm informações adicionais"
        };

        for (String tip : tips) {
            JLabel label = new JLabel(tip);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            label.setForeground(TEXT_DARK);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createVerticalStrut(8));
        }

        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BG_WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_GRAY),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        JLabel text = new JLabel("SATU 2025 • Cada rota importa. Cada decisão salva.");
        text.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        text.setForeground(TEXT_GRAY);

        footer.add(text);

        return footer;
    }
}