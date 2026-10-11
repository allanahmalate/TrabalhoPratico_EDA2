import java.util.*;

public class Prim {
    static final int INF = 0;

    public static List<Aresta> getMST() {
        String[] cidades = {
                "ZHYTOMYR","VINNYTSA","KYIV","KROPYNYTSKYI","ODESSA",
                "CHERNIHIV","SUMMY","POLTAVA","KHARKIV","DNIPRO",
                "DONETSK","LUHANSK","MYKOLAIV","KHERSON","ZAPORIZHZHYA"
        };
        int n = cidades.length;
        int[][] matriz = new int[n][n];
        addArestas(matriz);

        boolean[] visitado = new boolean[n];
        int[] menorDist = new int[n];
        int[] pai = new int[n];
        Arrays.fill(menorDist, Integer.MAX_VALUE);
        Arrays.fill(pai, -1);
        menorDist[0] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDist(menorDist, visitado);
            visitado[u] = true;
            for (int v = 0; v < n; v++) {
                if (matriz[u][v] != INF && !visitado[v] && matriz[u][v] < menorDist[v]) {
                    menorDist[v] = matriz[u][v];
                    pai[v] = u;
                }
            }
        }

        List<Aresta> mst = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (pai[i] != -1) mst.add(new Aresta(pai[i], i, matriz[i][pai[i]]));
        }
        return mst;
    }

    public static void main(String[] args) {
        List<Aresta> mst = getMST();
        String[] c = {
                "ZHYTOMYR","VINNYTSA","KYIV","KROPYNYTSKYI","ODESSA",
                "CHERNIHIV","SUMMY","POLTAVA","KHARKIV","DNIPRO",
                "DONETSK","LUHANSK","MYKOLAIV","KHERSON","ZAPORIZHZHYA"
        };
        System.out.println("=== PRIM ===");
        for (Aresta a : mst) System.out.println(c[a.origem] + " - " + c[a.destino] + " = " + a.peso + " km");
        System.out.println("Custo total: " + mst.stream().mapToInt(a -> a.peso).sum() + " km");
    }

    static int minDist(int[] d, boolean[] v) {
        int min = Integer.MAX_VALUE, idx = -1;
        for (int i = 0; i < d.length; i++)
            if (!v[i] && d[i] < min) { min = d[i]; idx = i; }
        return idx;
    }

    static void addAresta(int[][] m, int i, int j, int p) { m[i][j] = m[j][i] = p; }
    static void addArestas(int[][] m) {
        addAresta(m, 0,1,220); addAresta(m,0,2,120); addAresta(m,1,2,260); addAresta(m,1,3,160); addAresta(m,1,4,270);
        addAresta(m,5,6,120); addAresta(m,5,2,150); addAresta(m,6,8,180); addAresta(m,2,7,290); addAresta(m,7,8,200);
        addAresta(m,7,3,180); addAresta(m,7,9,250); addAresta(m,8,9,300); addAresta(m,8,10,240); addAresta(m,8,11,200);
        addAresta(m,3,9,200); addAresta(m,3,12,250); addAresta(m,9,14,100); addAresta(m,9,10,260); addAresta(m,12,4,140);
        addAresta(m,12,13,150); addAresta(m,13,14,300); addAresta(m,14,10,200); addAresta(m,10,11,120);
    }

    static class Aresta { int origem, destino, peso; Aresta(int o, int d, int p) { origem=o; destino=d; peso=p; } }
}