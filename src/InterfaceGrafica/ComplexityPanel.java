import javax.swing.*;
import java.awt.*;
import java.util.List;

// ============== COMPLEXITY PANEL ==============
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ComplexityPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    
    public ComplexityPanel() {
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
        
        mainPanel.add(createButtonPanel(), BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        
        contentPanel.add(createAlgorithmPanel("BFS"), "BFS");
        contentPanel.add(createAlgorithmPanel("DFS"), "DFS");
        contentPanel.add(createAlgorithmPanel("Dijkstra"), "Dijkstra");
        contentPanel.add(createAlgorithmPanel("Kruskal"), "Kruskal");
        contentPanel.add(createAlgorithmPanel("Prim"), "Prim");
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        cardLayout.show(contentPanel, "BFS");
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel title = new JLabel("Análise de Complexidades");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        
        JLabel desc = new JLabel("Comparação de Algoritmos de Grafos");
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
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        String[] algorithms = {"BFS", "DFS", "Dijkstra", "Kruskal", "Prim"};
        
        for (String algo : algorithms) {
            JButton btn = createAlgorithmButton(algo);
            btn.addActionListener(e -> cardLayout.show(contentPanel, algo));
            panel.add(btn);
            panel.add(Box.createVerticalStrut(12));
        }
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JButton createAlgorithmButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(150, 45));
        btn.setMaximumSize(new Dimension(150, 45));
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
    
    private JPanel createAlgorithmPanel(String algorithm) {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Complexidade do Algoritmo " + algorithm);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        panel.add(title, BorderLayout.NORTH);
        
        Object[][] data = getComplexityData(algorithm);
        String[] columns = {"Caso", "Complexidade de Tempo", "Complexidade de Espaço"};
        
        javax.swing.JTable table = new javax.swing.JTable(data, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(50);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(200, 220, 255));
        table.setEnabled(false);
        
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);
        
        JTextArea description = new JTextArea(getAlgorithmDescription(algorithm));
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBackground(new Color(240, 248, 255));
        description.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(description, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private Object[][] getComplexityData(String algorithm) {
        switch (algorithm) {
            case "BFS":
                return new Object[][] {
                    {"Melhor Caso", "O(V + E)", "O(V)"},
                    {"Caso Médio", "O(V + E)", "O(V)"},
                    {"Pior Caso", "O(V + E)", "O(V)"}
                };
            case "DFS":
                return new Object[][] {
                    {"Melhor Caso", "O(V + E)", "O(V)"},
                    {"Caso Médio", "O(V + E)", "O(V)"},
                    {"Pior Caso", "O(V + E)", "O(V)"}
                };
            case "Dijkstra":
                return new Object[][] {
                    {"Melhor Caso", "O(V log V)", "O(V)"},
                    {"Caso Médio", "O((V + E) log V)", "O(V)"},
                    {"Pior Caso", "O((V + E) log V)", "O(V)"}
                };
            case "Kruskal":
                return new Object[][] {
                    {"Melhor Caso", "O(E log E)", "O(V)"},
                    {"Caso Médio", "O(E log E)", "O(V)"},
                    {"Pior Caso", "O(E log E)", "O(V)"}
                };
            case "Prim":
                return new Object[][] {
                    {"Melhor Caso", "O(V log V)", "O(V)"},
                    {"Caso Médio", "O((V + E) log V)", "O(V)"},
                    {"Pior Caso", "O((V + E) log V)", "O(V)"}
                };
            default:
                return new Object[][] {};
        }
    }
    
    private String getAlgorithmDescription(String algorithm) {
        switch (algorithm) {
            case "BFS":
                return "BFS (Busca em Largura) explora o grafo nível por nível, visitando todos os vizinhos de um vértice antes de avançar. " +
                       "É ideal para encontrar o caminho mais curto em grafos não ponderados.\n\n" +
                       "V = número de vértices | E = número de arestas";
            case "DFS":
                return "DFS (Busca em Profundidade) explora o grafo seguindo um caminho o mais profundo possível antes de retroceder. " +
                       "É útil para detectar ciclos e realizar ordenação topológica.\n\n" +
                       "V = número de vértices | E = número de arestas";
            case "Dijkstra":
                return "Dijkstra encontra o caminho mais curto de um vértice fonte para todos os outros em grafos com pesos positivos. " +
                       "Usa uma fila de prioridade para processar vértices de forma eficiente.\n\n" +
                       "V = número de vértices | E = número de arestas";
            case "Kruskal":
                return "Kruskal constrói uma Árvore Geradora Mínima ordenando todas as arestas por peso e adicionando-as uma a uma, " +
                       "evitando ciclos usando estrutura Union-Find.\n\n" +
                       "V = número de vértices | E = número de arestas";
            case "Prim":
                return "Prim constrói uma Árvore Geradora Mínima expandindo a árvore um vértice por vez, " +
                       "sempre escolhendo a aresta de menor peso que conecta a árvore a um novo vértice.\n\n" +
                       "V = número de vértices | E = número de arestas";
            default:
                return "";
        }
    }
}

