import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

// ============== ABOUT PROJECT PANEL ==============
class AboutPanel extends JPanel {
    private static final Color UKRAINE_BLUE = new Color(0, 87, 183);
    private static final Color BG_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_DARK = new Color(30, 41, 59);
    private static final Color TEXT_GRAY = new Color(100, 116, 139);
    private static final Color LIGHT_GRAY = new Color(248, 250, 252);
    private static final Color BORDER_GRAY = new Color(226, 232, 240);
    
    public AboutPanel() {
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(BG_WHITE);
        
        // Header
        JPanel header = createHeader();
        
        // Content
        JPanel contentPanel = createContentPanel();
        
        // Container que junta header + content
        JPanel scrollContent = new JPanel(new BorderLayout());
        scrollContent.setBackground(BG_WHITE);
        scrollContent.add(header, BorderLayout.NORTH);
        scrollContent.add(contentPanel, BorderLayout.CENTER);
        
        // ScrollPane para toda a tela
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Footer fixo na parte inferior
        JPanel footer = createFooter();
        add(footer, BorderLayout.SOUTH);
    }
    
    private JPanel createHeader() {
        // Painel externo com margem
        JPanel outerHeader = new JPanel(new BorderLayout());
        outerHeader.setBackground(BG_WHITE);
        outerHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        
        // Painel interno com borda azul
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UKRAINE_BLUE, 4),
            BorderFactory.createEmptyBorder(35, 50, 35, 50)
        ));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BG_WHITE);
        
        JLabel titleLabel = new JLabel("Sobre o Sistema");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Descrevendo o Sistema");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(TEXT_GRAY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        header.add(titlePanel, BorderLayout.WEST);
        outerHeader.add(header, BorderLayout.CENTER);
        
        return outerHeader;
    }
    
    private JPanel createContentPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        // Sobre o Sistema
        content.add(createInfoSection());
        content.add(Box.createVerticalStrut(25));
        
        // Tecnologias Usadas
        content.add(createTechSection());
        content.add(Box.createVerticalStrut(25));
        
        // Equipe
        content.add(createTeamSection());
        
        return content;
    }
    
    private JPanel createInfoSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG_WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UKRAINE_BLUE, 3),
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 270));
        
        JLabel titleLabel = new JLabel("Sobre o Sistema");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea textArea = new JTextArea(
            "Sistema desenvolvido para simulação e análise da infraestrutura ferroviária ucraniana, " +
            "aplicando algoritmos de grafos para otimização de rotas logísticas. " +
            "A aplicação permite visualizar conexões entre Oblasts (províncias), calcular caminhos ótimos " +
            "para abastecimento militar e civil, e analisar a resiliência da rede ferroviária através de " +
            "algoritmos de busca (BFS/DFS) e otimalidade (Kruskal, Prim, Dijkstra)."
        );
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        textArea.setForeground(TEXT_DARK);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setBorder(null);
        
        section.add(titleLabel);
        section.add(Box.createVerticalStrut(18));
        section.add(textArea);
        
        return section;
    }
    
    private JPanel createTechSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG_WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UKRAINE_BLUE, 3),
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 480));
        
        JLabel titleLabel = new JLabel("Tecnologias Utilizadas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(titleLabel);
        section.add(Box.createVerticalStrut(20));
        
        // Subtítulo: Linguagens e Ferramentas
        JLabel techSubtitle = new JLabel("Linguagens e Ferramentas:");
        techSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        techSubtitle.setForeground(TEXT_DARK);
        techSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(techSubtitle);
        section.add(Box.createVerticalStrut(12));
        
        String[] technologies = {
            "• Java - Linguagem de programação principal",
            "• Java Swing - Interface gráfica do utilizador",
            "• Graphics2D - Visualização de grafos e rotas"
        };
        
        for (String tech : technologies) {
            JLabel techLabel = new JLabel(tech);
            techLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
            techLabel.setForeground(TEXT_DARK);
            techLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            section.add(techLabel);
            section.add(Box.createVerticalStrut(8));
        }
        
        section.add(Box.createVerticalStrut(15));
        
        // Subtítulo: Estruturas e Algoritmos
        JLabel algoSubtitle = new JLabel("Estruturas de Dados e Algoritmos:");
        algoSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        algoSubtitle.setForeground(TEXT_DARK);
        algoSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(algoSubtitle);
        section.add(Box.createVerticalStrut(12));
        
        String[] algorithms = {
            "• Matriz de Adjacências (Array 2D) - Representação de conexões",
            "• Listas de Adjacências (ArrayList) - Estrutura otimizada",
            "• BFS (Busca em Largura) - Exploração de caminhos",
            "• DFS (Busca em Profundidade) - Exploração recursiva",
            "• Dijkstra - Caminho mais curto ponderado"
        };
        
        for (String algo : algorithms) {
            JLabel algoLabel = new JLabel(algo);
            algoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
            algoLabel.setForeground(TEXT_DARK);
            algoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            section.add(algoLabel);
            section.add(Box.createVerticalStrut(8));
        }
        
        return section;
    }
    
    private JPanel createTeamSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(BG_WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UKRAINE_BLUE, 3),
            BorderFactory.createEmptyBorder(35, 40, 35, 40)
        ));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 480));
        
        JLabel titleLabel = new JLabel("Equipe de Desenvolvimento");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(titleLabel);
        section.add(Box.createVerticalStrut(25));
        
        JLabel devTitle = new JLabel("Desenvolvedores:");
        devTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        devTitle.setForeground(TEXT_DARK);
        devTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(devTitle);
        section.add(Box.createVerticalStrut(20));
        
        String[] developers = {
            "Clésio António",
            "Dikshy Guinésh",
            "Luana Machel",
            "Nanila Badrú",
            "Purvik Baves"
        };
        
        for (String dev : developers) {
            JLabel devLabel = new JLabel(dev);
            devLabel.setFont(new Font("Segoe UI", Font.PLAIN, 19));
            devLabel.setForeground(TEXT_GRAY);
            devLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            section.add(devLabel);
            section.add(Box.createVerticalStrut(12));
        }
        
        section.add(Box.createVerticalStrut(15));
        
        JLabel workInfo = new JLabel("Trabalho Prático - Algoritmos e Estruturas de Dados II");
        workInfo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
        workInfo.setForeground(UKRAINE_BLUE);
        workInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(workInfo);
        
        section.add(Box.createVerticalStrut(8));
        
        JLabel courseInfo = new JLabel("Licenciatura em Engenharia Informática • 2º Ano");
        courseInfo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        courseInfo.setForeground(TEXT_GRAY);
        courseInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        section.add(courseInfo);
        
        return section;
    }
    
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(LIGHT_GRAY);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, BORDER_GRAY),
            BorderFactory.createEmptyBorder(20, 50, 20, 50)
        ));
        
        JLabel footerText = new JLabel("ISCTEM • Algoritmos e Estruturas de Dados II • 2025");
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        footerText.setForeground(TEXT_GRAY);
        
        footer.add(footerText, BorderLayout.CENTER);
        
        return footer;
    }
}