import javax.swing.*;
import java.awt.*;

public class GraphViewPanel extends JPanel {
    private Graph graph;
    private GraphPanel graphPanel;
    
    public GraphViewPanel(Graph graph) {
        this.graph = graph;
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UkraineGraphSystem.FUNDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel title = new JLabel("Grafo da Linha Ferroviária da Ucrânia");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(UkraineGraphSystem.AZUL_UCRANIA);
        
        JLabel desc = new JLabel("Visualização Interativa");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        desc.setForeground(UkraineGraphSystem.CINZA_TEXTO);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(desc);
        
        header.add(textPanel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
        
        // GraphPanel
        graphPanel = new GraphPanel(graph);
        JPanel canvasContainer = new JPanel(new BorderLayout());
        canvasContainer.setBackground(Color.WHITE);
        canvasContainer.setBorder(BorderFactory.createLineBorder(UkraineGraphSystem.AZUL_UCRANIA, 2));
        canvasContainer.add(graphPanel, BorderLayout.CENTER);
        
        add(canvasContainer, BorderLayout.CENTER);
    }
}