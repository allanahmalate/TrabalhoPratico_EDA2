package InterfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SidebarPanel extends JPanel {
    private UkraineGraphSystem mainFrame;

    public SidebarPanel(UkraineGraphSystem mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));

        // Header
        JPanel header = createHeader();
        contentPanel.add(header);

        // Menu SATU
        addMenuItem(contentPanel, "Home", "home");
        addMenuItem(contentPanel, "Grafo Inicial", "graph");
        //addMenuItem(contentPanel, "Representação do Grafo", "representation");

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createSeparator());
        //contentPanel.add(Box.createVerticalStrut(10));

        // Seção Funcionalidades
        JLabel funcLabel = new JLabel("Funcionalidades");
        funcLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        funcLabel.setForeground(Color.WHITE);
        funcLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        funcLabel.setBorder(BorderFactory.createEmptyBorder(12, 30, 6, 30));
        contentPanel.add(funcLabel);

        //addMenuItem(contentPanel, "Matriz de Distâncias", "distance");

        addMenuItem(contentPanel, "Representação do Grafo", "representation");
        addMenuItem(contentPanel, "Algoritmo de Busca", "search");
        addMenuItem(contentPanel, "Árvore de Cobertura", "mst");
        addMenuItem(contentPanel, "Caminho Mínimo", "dijkstra");
        addMenuItem(contentPanel, "Complexidades", "complexity");
        addMenuItem(contentPanel, "Gerar Relatório PDF", null, this::generatePDF);

        contentPanel.add(Box.createVerticalStrut(4));
        contentPanel.add(createSeparator());
        contentPanel.add(Box.createVerticalStrut(4));

        // Seção Extras
        JLabel extrasLabel = new JLabel("Extras");
        extrasLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        extrasLabel.setForeground(Color.WHITE);
        extrasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        extrasLabel.setBorder(BorderFactory.createEmptyBorder(11, 30, 8, 30));
        contentPanel.add(extrasLabel);

        addMenuItem(contentPanel, "Programadores", "programmers");
        addMenuItem(contentPanel, "Sobre", "about");
        addMenuItem(contentPanel, "Ajuda", "help");

        contentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(24);
        scrollPane.setPreferredSize(new Dimension(310, 0));

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(310, 0));
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 18, 20));

        JLabel title = new JLabel("SATU");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(UkraineGraphSystem.AMARELO_UCRANIA);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("OBlast");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(16));
        header.add(createSeparator());

        return header;
    }

    private void addMenuItem(JPanel parent, String text, String panelName) {
        addMenuItem(parent, text, panelName, null);
    }

    private void addMenuItem(JPanel parent, String text, String panelName, Runnable customAction) {
        JLabel item = new JLabel(text);
        item.setFont(new Font("Segoe UI", Font.BOLD, 15));
        item.setForeground(UkraineGraphSystem.AMARELO_UCRANIA);
        item.setAlignmentX(Component.CENTER_ALIGNMENT);
        item.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.setOpaque(true);
        item.setBackground(UkraineGraphSystem.AZUL_UCRANIA);

        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (customAction != null) {
                    customAction.run();
                } else if (panelName != null) {
                    mainFrame.showPanel(panelName);
                }
            }

            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(0, 110, 200));
            }

            public void mouseExited(MouseEvent e) {
                item.setBackground(UkraineGraphSystem.AZUL_UCRANIA);
            }
        });

        parent.add(item);
        parent.add(Box.createVerticalStrut(2));
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 150, 255));
        sep.setMaximumSize(new Dimension(280, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        return sep;
    }

    private void generatePDF() {
        // Chama o gerador de PDF existente
        PDFReportPanel pdfPanel = new PDFReportPanel(mainFrame.getGraph());
        pdfPanel.generateReport((JFrame) SwingUtilities.getWindowAncestor(this));
    }
}
