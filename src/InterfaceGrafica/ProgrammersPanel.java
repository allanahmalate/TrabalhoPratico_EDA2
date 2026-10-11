import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

// ============== PROFESSIONAL PROGRAMMERS PANEL ==============
class ProgrammersPanel extends JPanel {
    private static final Color UKRAINE_BLUE = new Color(0, 87, 183);
    private static final Color UKRAINE_YELLOW = new Color(255, 213, 0);
    private static final Color BG_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_DARK = new Color(30, 41, 59);
    private static final Color TEXT_GRAY = new Color(100, 116, 139);
    private static final Color LIGHT_GRAY = new Color(248, 250, 252);
    private static final Color BORDER_GRAY = new Color(226, 232, 240);
    
    private List<Programmer> programmers;
    
    public ProgrammersPanel() {
        initProgrammers();
        initUI();
    }
    
    private void initProgrammers() {
        programmers = new ArrayList<>();
        programmers.add(new Programmer("Clésio António", "20230933", "Masculino", 
            "84 123 4567", "clesio.antonio@isctem.ac.mz", "Engenharia Informática"));
        programmers.add(new Programmer("Dikshy Guinésh", "20240213", "Feminino", 
            "87 590 0377", "dikshy.guinesh@isctem.ac.mz", "Engenharia Informática"));
        programmers.add(new Programmer("Luana Machel", "20220329", "Feminino", 
            "84 345 6789", "luana.machel@isctem.ac.mz", "Engenharia Informática"));
        programmers.add(new Programmer("Nanila Badrú", "20240916", "Feminino", 
            "84 789 0123", "nanila.badru@isctem.ac.mz", "Engenharia Informática"));
        programmers.add(new Programmer("Purvik Baves", "20230045", "Masculino", 
            "84 901 2345", "purvik.baves@isctem.ac.mz", "Engenharia Informática"));
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(BG_WHITE);
        
        // Container principal com scroll
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(BG_WHITE);
        
        // Header com borda azul
        JPanel header = createHeader();
        mainContainer.add(header, BorderLayout.NORTH);
        
        // Cards grid
        JPanel cardsPanel = createCardsPanel();
        mainContainer.add(cardsPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footer = createFooter();
        mainContainer.add(footer, BorderLayout.SOUTH);
        
        // ScrollPane apenas vertical
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UKRAINE_BLUE, 4),
            BorderFactory.createEmptyBorder(35, 50, 35, 50)
        ));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BG_WHITE);
        
        JLabel titleLabel = new JLabel("Dados dos Programadores");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titleLabel.setForeground(UKRAINE_BLUE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("made with love");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(TEXT_GRAY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        header.add(titlePanel, BorderLayout.WEST);
        
        return header;
    }
    
    private JPanel createCardsPanel() {
        JPanel outerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        outerContainer.setBackground(BG_WHITE);
        outerContainer.setBorder(BorderFactory.createEmptyBorder(35, 20, 40, 20));
        
        JPanel cardsContainer = new JPanel(new GridBagLayout());
        cardsContainer.setBackground(BG_WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        int cols = 2;
        for (int i = 0; i < programmers.size(); i++) {
            gbc.gridx = i % cols;
            gbc.gridy = i / cols;
            cardsContainer.add(createProgrammerCard(programmers.get(i)), gbc);
        }
        
        outerContainer.add(cardsContainer);
        return outerContainer;
    }
    
    private JPanel createProgrammerCard(Programmer prog) {
        JPanel card = new JPanel() {
            private boolean hovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                // Sombra sofisticada
                if (hovered) {
                    g2d.setColor(new Color(0, 87, 183, 40));
                    g2d.fillRoundRect(8, 8, width - 16, height - 16, 24, 24);
                }
                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fillRoundRect(4, 4, width - 8, height - 8, 24, 24);
                
                // Card background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, width - 8, height - 8, 24, 24);
                
                // Borda com efeito hover
                if (hovered) {
                    g2d.setColor(UKRAINE_BLUE);
                    g2d.setStroke(new BasicStroke(3));
                } else {
                    g2d.setColor(BORDER_GRAY);
                    g2d.setStroke(new BasicStroke(2));
                }
                g2d.drawRoundRect(0, 0, width - 9, height - 9, 24, 24);
                
                // Accent strip no topo
                g2d.setColor(UKRAINE_BLUE);
                g2d.fillRoundRect(0, 0, width - 8, 6, 24, 24);
            }
            
            public void setHovered(boolean h) { hovered = h; repaint(); }
        };
        
        card.setLayout(new BorderLayout(20, 0));
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(400, 145));
        card.setBorder(BorderFactory.createEmptyBorder(28, 28, 25, 28));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Mouse listeners
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method method = card.getClass().getMethod("setHovered", boolean.class);
                    method.invoke(card, true);
                } catch (Exception e) {}
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method method = card.getClass().getMethod("setHovered", boolean.class);
                    method.invoke(card, false);
                } catch (Exception e) {}
            }
        });
        
        // Avatar
        JPanel avatarPanel = createAvatar(prog);
        
        // Info section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(prog.name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(TEXT_DARK);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel codeLabel = new JLabel("Código: " + prog.code);
        codeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        codeLabel.setForeground(TEXT_GRAY);
        codeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(codeLabel);
        infoPanel.add(Box.createVerticalStrut(16));
        
        // Botão
        JButton detailsBtn = new JButton("Ver Detalhes →");
        detailsBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        detailsBtn.setForeground(Color.WHITE);
        detailsBtn.setBackground(UKRAINE_BLUE);
        detailsBtn.setFocusPainted(false);
        detailsBtn.setBorderPainted(false);
        detailsBtn.setPreferredSize(new Dimension(150, 36));
        detailsBtn.setMaximumSize(new Dimension(150, 36));
        detailsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        detailsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                detailsBtn.setBackground(new Color(0, 110, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                detailsBtn.setBackground(UKRAINE_BLUE);
            }
        });
        
        detailsBtn.addActionListener(e -> showProgrammerDetails(prog));
        
        infoPanel.add(detailsBtn);
        
        card.add(avatarPanel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createAvatar(Programmer prog) {
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = 75;
                
                // Gradiente azul
                GradientPaint gp = new GradientPaint(0, 0, UKRAINE_BLUE, size, size, new Color(0, 120, 235));
                g2d.setPaint(gp);
                g2d.fillOval(0, 0, size, size);
                
                // Borda branca sutil
                g2d.setColor(new Color(255, 255, 255, 120));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(2, 2, size - 4, size - 4);
                
                // Iniciais
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 26));
                String initials = getInitials(prog.name);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (size - fm.stringWidth(initials)) / 2;
                int textY = ((size - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(initials, textX, textY);
            }
        };
        
        avatar.setPreferredSize(new Dimension(75, 75));
        avatar.setOpaque(false);
        
        return avatar;
    }
    
    private String getInitials(String name) {
        String[] parts = name.split(" ");
        return parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1);
    }
    private void showProgrammerDetails(Programmer prog) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detalhes do Programador", true);
        dialog.setSize(750, 700);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_WHITE);

        // Header elegante
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, UKRAINE_BLUE, 0, getHeight(), new Color(0, 120, 235));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(255, 255, 255, 25));
                for (int i = 0; i < 12; i++) {
                    int x = i * 90 - 30;
                    g2d.fillOval(x, -25, 70, 70);
                    g2d.fillOval(x + 35, getHeight() - 45, 60, 60);
                }
            }
        };
        headerPanel.setPreferredSize(new Dimension(750, 160));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(35, 45, 30, 45));

        JPanel headerContent = new JPanel();
        headerContent.setLayout(new BoxLayout(headerContent, BoxLayout.Y_AXIS));
        headerContent.setOpaque(false);

        JLabel titleLabel = new JLabel("👨‍💻 Informações do Programador");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 255, 255, 230));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(prog.name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerContent.add(titleLabel);
        headerContent.add(Box.createVerticalStrut(12));
        headerContent.add(nameLabel);

        headerPanel.add(headerContent, BorderLayout.WEST);

        // Painel de detalhes com scroll
        JPanel detailsContainer = new JPanel();
        detailsContainer.setLayout(new GridLayout(0, 1, 0, -42)); // 0 linhas = automático, 1 coluna, 18px de gap vertical
        detailsContainer.setBackground(BG_WHITE);
        detailsContainer.setBorder(BorderFactory.createEmptyBorder(35, 45, 0, 45));

        // Código do estudante
        detailsContainer.add(createDetailCard("Código do Estudante", prog.code, "🎓", UKRAINE_BLUE));
        detailsContainer.add(Box.createVerticalStrut(0));

        // Email
        detailsContainer.add(createDetailCard("Email Institucional", prog.email, "✉️", new Color(59, 130, 246)));
        detailsContainer.add(Box.createVerticalStrut(0));

        // Curso
        detailsContainer.add(createDetailCard("Curso", prog.course, "📚", new Color(16, 185, 129)));
        detailsContainer.add(Box.createVerticalStrut(0));

        // Sexo
        detailsContainer.add(createDetailCard("Sexo", prog.gender, "👤", new Color(139, 92, 246)));
        detailsContainer.add(Box.createVerticalStrut(0));

        // Contacto
        detailsContainer.add(createDetailCard("Contacto", prog.contact, "📱", new Color(236, 72, 153)));

        // JScrollPane para scroll automático
        JScrollPane scrollPane = new JScrollPane(detailsContainer);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);

        // Botão fechar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BG_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JButton closeBtn = new JButton("✕ Fechar");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(UKRAINE_BLUE);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setPreferredSize(new Dimension(150, 42));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeBtn.setBackground(new Color(0, 120, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeBtn.setBackground(UKRAINE_BLUE);
            }
        });

        closeBtn.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeBtn);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    
    private JPanel createDetailCard(String label, String value, String icon, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(18, 0));
        card.setBackground(LIGHT_GRAY);
        card.setMaximumSize(new Dimension(700, 80));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_GRAY, 2),
            BorderFactory.createEmptyBorder(18, 22, 18, 22)
        ));
        
        // Ícone colorido
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradiente colorido
                GradientPaint gp = new GradientPaint(0, 0, accentColor, 55, 55, accentColor.brighter());
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, 55, 55, 14, 14);
                
                // Borda sutil
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, 53, 53, 14, 14);
            }
        };
        iconPanel.setPreferredSize(new Dimension(55, 55));
        iconPanel.setOpaque(false);
        iconPanel.setLayout(new GridBagLayout());
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        iconPanel.add(iconLabel);
        
        // Conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelComp.setForeground(TEXT_GRAY);
        labelComp.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        valueComp.setForeground(TEXT_DARK);
        valueComp.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(Box.createVerticalStrut(4));
        contentPanel.add(labelComp);
        contentPanel.add(Box.createVerticalStrut(6));
        contentPanel.add(valueComp);
        
        card.add(iconPanel, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
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
    
    // ============== PROGRAMMER DATA CLASS ==============
    private static class Programmer {
        String name, code, gender, contact, email, course;
        
        Programmer(String name, String code, String gender, String contact, String email, String course) {
            this.name = name;
            this.code = code;
            this.gender = gender;
            this.contact = contact;
            this.email = email;
            this.course = course;
        }
    }
}