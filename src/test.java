import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.List;

public class test {

    public static long clocking(Graphe g, Consumer<Graphe> method) {
        long debut = System.nanoTime();
        method.accept(g);
        long fin = System.nanoTime();

        long tempsMicro = (fin - debut)/1000;
        long tempsNano = fin - debut;
        //System.out.println("Temps d'exécution de la méthode : " + tempsNano + " ns -> " + tempsMicro + " µs -> " + tempsMicro/1000 + " ms.\n");
        return tempsMicro;
    }

    public static long moyenneClocking(Graphe g, Consumer<Graphe> method, int nbEchantillons) {
        long temps = 0;
        long moyenne;
        for (int i = 0; i < nbEchantillons; i++) {
            temps =+ clocking(g, method);
        }
        moyenne = temps / nbEchantillons;
        System.out.println("Moyenne de temps d'éxécution : " + temps + " µs.");
        return moyenne;
    }

    public static void main(String[] args) {
        Graphe g = new Graphe();
        g.lectureGraphe("crown10.txt");

        List<Consumer<Graphe>> algos = new ArrayList<>(Arrays.asList(Graphe::greedyColoring, Graphe::welshPowell, Graphe::Dsatur));
        for (Consumer<Graphe> c : algos) {
            moyenneClocking(g, c, 10);
        }
    }

    /* NOTES POUR MON JUAN
    Pour chronométrer le temps que met "maMethode" à s'exécuter sur le graphe "monGraphe" :
    clocking(monGraphe, x -> x.maMethode());
    Tu peux mettre 'x' comme tu peux mettre 'y', 'z' ou 'nazi' : on s'en fout totalement, c'est une pure variable muette. <3
     */


}
