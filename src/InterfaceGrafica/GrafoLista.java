import java.util.*;

public class GrafoLista {
    static class Aresta {
        String destino; int peso;
        Aresta(String d, int p) { destino = d; peso = p; }
        public String toString() { return destino + " (" + peso + " km)"; }
    }

    public static void GrafoLista() {
        Map<String, List<Aresta>> grafo = new LinkedHashMap<>();
        String[] c = {"ZHYTOMYR","VINNYTSIA","KYIV","KROPYVNYTSKYI","ODESSA","CHERNIHIV","SUMMY","POLTAVA","KHARKIV","DNIPRO","DONETSK","LUHANSK","MYKOLAIV","KHERSON","ZAPORIZHZHYA"};
        for (String cidade : c) grafo.put(cidade, new ArrayList<>());

        add(grafo, "ZHYTOMYR", "VINNYTSA", 220); add(grafo, "ZHYTOMYR", "KYIV", 120);
        add(grafo, "VINNYTSA", "KYIV", 260); add(grafo, "VINNYTSIA", "KROPYVNYTSKYI", 160); add(grafo, "VINNYTSA", "ODESSA", 270);
        add(grafo, "CHERNIHIV", "SUMMY", 120); add(grafo, "CHERNIHIV", "KYIV", 150); add(grafo, "SUMMY", "KHARKIV", 180);
        add(grafo, "KYIV", "POLTAVA", 290); add(grafo, "POLTAVA", "KHARKIV", 200); add(grafo, "POLTAVA", "KROPYVNYTSKYI", 180);
        add(grafo, "POLTAVA", "DNIPRO", 250); add(grafo, "KHARKIV", "DNIPRO", 300); add(grafo, "KHARKIV", "DONETSK", 240);
        add(grafo, "KHARKIV", "LUHANSK", 200); add(grafo, "KROPYNYTSKYI", "DNIPRO", 200); add(grafo, "KROPYVNYTSKYI", "MYKOLAIV", 250);
        add(grafo, "DNIPRO", "ZAPORIZHZHYA", 100); add(grafo, "DNIPRO", "DONETSK", 260); add(grafo, "MYKOLAIV", "ODESSA", 140);
        add(grafo, "MYKOLAIV", "KHERSON", 150); add(grafo, "KHERSON", "ZAPORIZHZHYA", 300); add(grafo, "ZAPORIZHZHYA", "DONETSK", 200);
        add(grafo, "DONETSK", "LUHANSK", 120);

        System.out.println("=====================================================================================================");
        System.out.println("                                 LISTA DE ADJACÊNCIA                                        ");
        System.out.println("=====================================================================================================");
        for (String cidade : grafo.keySet()) {
            System.out.printf("%-12s -> %s%n", cidade, grafo.get(cidade));
        }
    }

    static void add(Map<String, List<Aresta>> g, String a, String b, int p) {
        g.get(a).add(new Aresta(b, p)); g.get(b).add(new Aresta(a, p));
    }

    public static void main(String[] args) { GrafoLista(); }
}