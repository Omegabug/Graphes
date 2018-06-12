import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.List;

public class test {

    public static void main(String[] args) {

        //allAlgoOnGraph("src/Fichier/crown10.txt");

        allAlgoOnGraphMoyenne("src/Fichier/crown10.txt", 1000);

        // allAlgoOnAllGraphMoyenne(1000);

        // algoOnAllGraphMoyenne(Graphe::greedyColoring, 1000);
    }

    public static long clocking(Graphe g, Consumer<Graphe> method) {
        long debut = System.nanoTime();
        method.accept(g);
        long fin = System.nanoTime();

        long tempsMicro = (fin - debut)/1000;
        long tempsNano = fin - debut;
        //g.getColoration();
        //System.out.println("Temps d'exécution de la méthode : " + tempsNano + " ns -> " + tempsMicro + " µs -> " + tempsMicro/1000 + " ms.\n");
        return tempsMicro;
    }

    public static long moyenneClocking(Graphe g, Consumer<Graphe> method, int nbEchantillons) {
        long temps = 0;
        long moyenne;
        for (int i = 0; i < nbEchantillons; i++) {
            temps =+ clocking(g, method);
        }
        g.getColoration();
        moyenne = temps / nbEchantillons;
        System.out.println("Moyenne de temps d'exécution : " + temps + " µs.\n");
        return moyenne;
    }

    // Applique un algo sur tous les graphes
    public static void algoOnAllGraphMoyenne(Consumer<Graphe> algo, int nbEchantillons) {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoring, Graphe::welshPowell, Graphe::Dsatur));
        List<String> files = tousLesFichiers("src/Fichier");

        Graphe g;
        for (String file : files) {
            g = new Graphe();
            g.lectureGraphe(file);
            moyenneClocking(g, algo, nbEchantillons);
        }
    }

    // Applique tous les algos sur un graphe.
    public static void allAlgoOnGraph(String file) {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoring, Graphe::welshPowell, Graphe::Dsatur));
        Graphe g = new Graphe();
        g.lectureGraphe(file);

        int i = 1;
        String name = "";
        for (Consumer<Graphe> c : algos) {
            switch(i) {
                case 1:
                    name = "ALGORITHME GREEDY";
                    break;
                case 2:
                    name = "ALGORITHME DE WELSH POWELL";
                    break;
                case 3:
                    name = "ALGORITHME DE DSATUR";
                    break;
            }
            System.out.println(name);
            clocking(g,c);
            i++;
        }
    }

    // Applique tous les algos sur un graphe, en moyennant.
    public static void allAlgoOnGraphMoyenne(String file, int nbEchantillons) {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoring, Graphe::welshPowell, Graphe::Dsatur));
        Graphe g = new Graphe();
        g.lectureGraphe(file);

        int i = 1;
        String name = "";
        for (Consumer<Graphe> c : algos) {
            switch(i) {
                case 1:
                    name = "ALGORITHME GREEDY";
                    break;
                case 2:
                    name = "ALGORITHME DE WELSH POWELL";
                    break;
                case 3:
                    name = "ALGORITHME DE DSATUR";
                    break;
            }
            System.out.println(name);
            moyenneClocking(g,c,nbEchantillons);
            i++;
        }
    }

    public static void allAlgoOnAllGraph() {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoring, Graphe::welshPowell, Graphe::Dsatur));
        List<String> files = tousLesFichiers("src/Fichier");

        for (String file : files) {
            allAlgoOnGraph(file);
        }
    }

    public static void allAlgoOnAllGraphMoyenne(int nbEchantillons) {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoring, Graphe::welshPowell, Graphe::Dsatur));
        List<String> files = tousLesFichiers("src/Fichier");

        for (String file : files) {
            allAlgoOnGraphMoyenne(file, nbEchantillons);
        }
    }

    // On récupère le nom de tous les fichiers dans le repértoire.
    public static List<String> tousLesFichiers(String directory) {
        File dir = new File(directory);
        File[] allFiles = dir.listFiles();
        List<String> files = new ArrayList<>();
        if (allFiles != null) {
            for (File file : allFiles) {
                files.add(file.toString());
            }
        } else {
            System.out.println("Repértoire vide !");
        }
        return files;
    }

    /* NOTES POUR MON JUAN
    Pour chronométrer le temps que met "maMethode" à s'exécuter sur le graphe "monGraphe" :
    clocking(monGraphe, x -> x.maMethode());
    Tu peux mettre 'x' comme tu peux mettre 'y', 'z' ou 'nazi' : on s'en fout totalement, c'est une pure variable muette. <3
     */


}
