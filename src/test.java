import java.io.IOException;
import java.util.function.Consumer;

public class test {

    public static void clocking(Graphe g, Consumer<Graphe> method) {
        long debut = System.nanoTime();
        method.accept(g);
        long fin = System.nanoTime();
        System.out.println("Temps d'exécution de la méthode : " + (fin - debut)/1000000 + " ms");
    }

    public static void main(String[] args) {
        Graphe g = new Graphe();
        g.lectureGraphe("crown10.txt");
        // clocking(g, x -> x.lectureGraphe("crown10.txt"));
        // g.ordonnerSommets();
    }

    /* NOTES POUR MON JUAN
    Pour chronométrer le temps que met "maMethode" à s'exécuter sur le graphe "monGraphe" :
    clocking(monGraphe, x -> x.maMethode());
    Tu peux mettre 'x' comme tu peux mettre 'y', 'z' ou 'nazi' : on s'en fout totalement, c'est une pure variable muette. <3
     */
}
