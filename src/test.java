import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.List;

/**
 * Cette classe permet d'exécuter facilement les algorithmes dans l'idée d'obtenir un temps d'exécution moyen.
 * @see #allAlgoOnGraphMoyenne(String, int)
 * @see #algoOnAllGraphMoyenne(Consumer, int)
 * @see #allAlgoOnAllGraphMoyenne(int)
 */
public class test {

    public static void main(String[] args) {

        //allAlgoOnGraph("src/Fichier/queen5_5.txt");


         allAlgoOnGraphMoyenne("src/Fichier/crown10.txt", 1000);

        // allAlgoOnAllGraphMoyenne(1000);

        // algoOnAllGraphMoyenne(Graphe::greedyColoring, 1000);
    }

    /**
     * Calcule le temps d'exécution d'un algorithme de coloration de graphe.
     * @param g Graphe sur lequel appliquer l'algorithme de coloration.
     * @param method Algorithme de coloration dont on veut le temps d'exécution.
     * @return Temps d'exécution de la méthode en µs.
     */
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

    /**
     * Calcule le temps d'exécution moyen d'un algorithme de coloration de graphe.
     * @param g Graphe sur lequel appliquer la méthode.
     * @param method Algorithme de coloration dont on veut le temps d'exécution.
     * @param nbEchantillons Nombre d'exécutions de l'algorithme à réaliser pour moyenner.
     * @return Temps d'exécution moyen de l'algorithme en µs.
     * @see #clocking(Graphe, Consumer)
     */
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

    /**
     * Applique un algorithme de coloration sur tous les graphes présents dans le dossier "Fichier".
     * @param algo Algorithme de coloration à appliquer.
     * @param nbEchantillons Nombre d'exécutions de l'algorithme à réaliser pour  moyenner.
     * @see #tousLesFichiers(String)
     * @see #moyenneClocking(Graphe, Consumer, int)
     */
    public static void algoOnAllGraphMoyenne(Consumer<Graphe> algo, int nbEchantillons) {
        List<String> files = tousLesFichiers("src/Fichier");
        Graphe g;
        for (String file : files) {
            g = new Graphe();
            g.lectureGraphe(file);
            moyenneClocking(g, algo, nbEchantillons);
        }
    }

    /**
     * Applique tous les algorithmes de coloration sur un graphe.
     * @param file Graphe sur lequel appliquer les algorithmes de coloration.
     * @see #clocking(Graphe, Consumer)
     */
    public static void allAlgoOnGraph(String file) {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoringDecroissant, Graphe::welshPowellColoringDecroissant, Graphe::dsaturColoringDecroissant));
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

    /**
     * Applique un certain nombre de fois tous les algorithmes sur un graphe, tout en moyennant le temps d'exécution des algorithmes.
     * @param file Graphe sur lequel appliquer les algorithmes.
     * @param nbEchantillons Nombre d'exécutions des algorithmes pour moyenner le temps d'exécution
     * @see #moyenneClocking(Graphe, Consumer, int)
     */
    public static void allAlgoOnGraphMoyenne(String file, int nbEchantillons) {
        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoringCroissant, Graphe::welshPowellColoringCroissant, Graphe::dsaturColoringCroissant));
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

    /**
     * Applique tous les algorithmes de coloration sur tous les graphes présents dans le dossier "Fichier".
     */
    public static void allAlgoOnAllGraph() {
        List<String> files = tousLesFichiers("src/Fichier");
        for (String file : files) {
            allAlgoOnGraph(file);
        }
    }

    /**
     * Applique tous les algorithmes de coloration sur tous les graphes présents dans le dossier "Fichier", tout en moyennant les temps d'exécution.
     * @param nbEchantillons Nombre d'exécutions des algorithmes pour moyenner leur temps d'exécution.
     * @see #allAlgoOnGraphMoyenne(String, int)
     */
    public static void allAlgoOnAllGraphMoyenne(int nbEchantillons) {
        List<String> files = tousLesFichiers("src/Fichier");
        for (String file : files) {
            allAlgoOnGraphMoyenne(file, nbEchantillons);
        }
    }

    /**
     * Récupère les adresses des fichiers présents dans un repértoire.
     * @param directory Repértoire dans lequel récupérer les fichiers.
     * @return Liste des adresses des fichiers.
     */
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
