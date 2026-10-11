import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class PDFReportPanel {
    private Graph graph;

    public PDFReportPanel(Graph graph) {
        this.graph = graph;
    }

    public void generateReport(JFrame parent) {
        try {
            String filename = "Relatorio_UA_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new java.util.Date()) + ".pdf";
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

            MinimumSpanningTree mst = new MinimumSpanningTree(graph);
            java.util.List<Edge> kruskalEdges = mst.getMSTKruskal();
            int custoK = mst.getCost(kruskalEdges);
            document.add(new Paragraph("3. ÁRVORE GERADORA MÍNIMA (KRUSKAL)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Custo total: " + custoK + " km", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            for (Edge e : kruskalEdges) {
                document.add(new Paragraph("• " + e.getFrom().getName() + " — " + e.getTo().getName() + " = " + e.getWeight() + " km"));
            }
            document.add(Chunk.NEWLINE);

            java.util.List<Edge> primEdges = mst.getMSTPrim();
            int custoP = mst.getCost(primEdges);
            document.add(new Paragraph("4. ÁRVORE GERADORA MÍNIMA (PRIM)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Custo total: " + custoP + " km", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            for (Edge e : primEdges) {
                document.add(new Paragraph("• " + e.getFrom().getName() + " — " + e.getTo().getName() + " = " + e.getWeight() + " km"));
            }
            document.add(Chunk.NEWLINE);

            int startIdx = graph.getNodeIndex("KYIV");
            int endIdx = graph.getNodeIndex("ODESSA");
            java.util.List<Node> path = ShortestPath.getShortestPathByIndex(graph, startIdx, endIdx);

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

            java.util.List<Node> bfsOrder = GraphSearch.bfs(graph, startIdx);
            java.util.List<Node> dfsOrder = GraphSearch.dfs(graph, startIdx);

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

            JOptionPane.showMessageDialog(parent,
                "Relatório gerado com sucesso!\n\nSalvo como:\n" + new java.io.File(filename).getAbsolutePath(),
                "PDF Gerado", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Erro ao gerar PDF: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        g.setColor(UkraineGraphSystem.AZUL_UCRANIA);
        g.fillRect(0, 0, w, h/2);
        g.setColor(UkraineGraphSystem.AMARELO_UCRANIA);
        g.fillRect(0, h/2, w, h/2);
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        return baos.toByteArray();
    }
}