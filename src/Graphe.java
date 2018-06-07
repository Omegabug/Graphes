import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Graphe {

    private String nom;
    private boolean oriente;
    private int nbSommets;
    private int nbValSommet;
    private int nbArcs;
    private int nbValArcs;
    private List<Sommet> sommets = new ArrayList<>();

    public Graphe() {}

    public void lectureGraphe(String adresseFichier) {

            InputStream in = null;
            BufferedReader br = null;
            String line;
            int temp1, temp2;
            int compteurLigne = 0;
            int compteurSommet = 0;

            try {
                in = new FileInputStream(adresseFichier);
                br = new BufferedReader(new InputStreamReader(in, "UTF-8"), 2048);

                int position, i;
                while ((line = br.readLine()) != null) {
                    position = line.indexOf(':')+1;
                    line = line.substring(position);
                    // On enlève les espaces inutiles
                    for (i=0; line.charAt(i) == ' '; i++);
                    line = line.substring(i);

                    switch (compteurLigne) {
                        case 0:
                            nom = line;
                            break;
                        case 1:
                            oriente = line.equalsIgnoreCase("oui");
                            break;
                        case 2:
                            nbSommets = Integer.parseInt(line);
                            break;
                        case 3:
                            nbValSommet = Integer.parseInt(line);
                            break;
                        case 4:
                            nbArcs = Integer.parseInt(line);
                            break;
                        case 5:
                            nbValArcs = Integer.parseInt(line);
                            break;
                    }

                    if (compteurLigne > 6 && compteurLigne < 7+ nbSommets) {
                        // On va jusqu'à l'espace pour avoir le nom du sommet
                        position = line.indexOf(' ') + 1;
                        line = line.substring(position);
                        sommets.add(new Sommet(compteurSommet, line));
                        compteurSommet++;
                    }

                    compteurLigne++;
                    if(compteurLigne > 7+nbSommets+1 && compteurLigne < 7+ nbSommets +2+nbArcs) {
                        // On trouve l'espace entre les 2 chiffres
                        temp1 = Integer.parseInt(line.substring(0, line.indexOf(' ')));
                        temp2 = Integer.parseInt(line.substring(line.indexOf(' ') + 1));
                        sommets.get(temp1).rajouterSommet(sommets.get(temp2));
                        if (!oriente) sommets.get(temp2).rajouterSommet(sommets.get(temp1));
                    }
                }
                listeAdjacence();
                br.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    // On ordonne les sommets en fonction de leur degré
    public void ordonnerSommets(){
        /*Collections.sort(sommets, new Comparator<Sommet>() {
            @Override
            public int compare(Sommet s1,Sommet s2){
                if( s1.degre()>s2.degre()) return 1;
                else {
                    System.out.println(s1.degre());
                    return 0;
                }
            }
        });*/
        sommets.sort(Comparator.comparing(Sommet::degre));
    }

    public void listeAdjacence() {
        System.out.println("Liste d'adjacence du graphe " + this + " :");
        for (Sommet s : sommets) {
            if (s.degre() != 0) {
                System.out.print(s + " ->");
                for (Sommet arete : s.getAretes()) {
                    System.out.print(" " + arete);
                }
                System.out.print("\n");
            }
        }
        System.out.println("\n");
    }

    public String toString() { return nom;}

    public List<Sommet> getSommets() { return sommets; }
}
