public class GrafoMatriz {
    static final int INF = 0;

    public GrafoMatriz() {
        String[] cidades = {
                "ZHYTOMYR", "VINNYTSA", "KYIV", "KROPYNYTSKYI", "ODESSA",
                "CHERNIHIV", "SUMMY", "POLTAVA", "KHARKIV", "DNIPRO",
                "DONETSK", "LUHANSK", "MYKOLAIV", "KHERSON", "ZAPORIZHZHYA"
        };

        int n = cidades.length;
        int[][] matriz = new int[n][n];

        addAresta(matriz, 0, 1, 220); addAresta(matriz, 0, 2, 120);
        addAresta(matriz, 1, 2, 260); addAresta(matriz, 1, 3, 160); addAresta(matriz, 1, 4, 270);
        addAresta(matriz, 5, 6, 120); addAresta(matriz, 5, 2, 150); addAresta(matriz, 6, 8, 180);
        addAresta(matriz, 2, 7, 290); addAresta(matriz, 7, 8, 200); addAresta(matriz, 7, 3, 180);
        addAresta(matriz, 7, 9, 250); addAresta(matriz, 8, 9, 300); addAresta(matriz, 8, 10, 240);
        addAresta(matriz, 8, 11, 200); addAresta(matriz, 3, 9, 200); addAresta(matriz, 3, 12, 250);
        addAresta(matriz, 9, 14, 100); addAresta(matriz, 9, 10, 260); addAresta(matriz, 12, 4, 140);
        addAresta(matriz, 12, 13, 150); addAresta(matriz, 13, 14, 300); addAresta(matriz, 14, 10, 200);
        addAresta(matriz, 10, 11, 120);

        System.out.println("=====================================================================================================");
        System.out.println("                                 MATRIZ DE ADJACÊNCIA (km)                                  ");
        System.out.println("=====================================================================================================");
        System.out.print("        ");
        for (String c : cidades) System.out.printf("%12s", c.substring(0, Math.min(5, c.length())));
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("%-8s", cidades[i].substring(0, Math.min(8, cidades[i].length())));
            for (int j = 0; j < n; j++) System.out.printf("%12d", matriz[i][j]);
            System.out.println();
        }
    }

    static void addAresta(int[][] m, int i, int j, int peso) {
        m[i][j] = m[j][i] = peso;
    }

    public static void main(String[] args) { new GrafoMatriz(); }
}