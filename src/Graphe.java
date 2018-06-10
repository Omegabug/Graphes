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
    public List<Sommet> ordonnerSommets() {
        List<Sommet> sommetsSorted = new ArrayList<>(sommets);
        sommetsSorted.sort(Comparator.comparing(Sommet::degre).reversed());
        return sommetsSorted;
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
        System.out.print("\n");
    }

    public int getColoration() {

        /*
        System.out.println("La coloration du graphe " + this + " :");
        for (Sommet s : sommets) {
            System.out.println("La couleur du sommet " + s + " est " + s.getCouleur() + ".");
        }
        System.out.println("\n");
        */

        List<Integer> listeCouleurs = new ArrayList<>();
        int couleur;
        for (Sommet s : sommets) {
            if (!listeCouleurs.contains((couleur = s.getCouleur()))) {
                listeCouleurs.add(couleur);
            }
        }
        int length = listeCouleurs.size();
        System.out.println("Nombre de couleurs nécessaires pour colorier le graphe : " + length);
        for (Integer color : listeCouleurs) {
            System.out.print(color + " ->");
            for (Sommet s : sommets) {
                if ((couleur = s.getCouleur()) == color) {
                    System.out.print(" " + s);
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        return length;
    }

    public void greedyColoring(){
        // On ordonne les sommets.
        List<Sommet> fileAttente = ordonnerSommets();
        List<Integer> couleursPresentes;
        Sommet x;
        int c;
        while (!fileAttente.isEmpty()) {
            x = fileAttente.get(0);
            // On réinitialise la liste des couleurs présentes dans l'entourage du sommet.
            couleursPresentes = x.couleurVoisin();
            c=0;
            while (couleursPresentes.contains(c)) {
                c++;
            }
            x.setCouleur(c);
            fileAttente.remove(x);
        }
        //TODO: Préciser le tri fait.
        System.out.println("Test Greedy avec tri.");
        this.getColoration();
    }

    public void welshPowell(){
        List<Sommet> fileAttente = ordonnerSommets();
        Sommet x, y;
        int i;
        int k = 1;
        reinitialiserCouleur();
        while(!fileAttente.isEmpty()){
            x = fileAttente.get(0);
            x.setCouleur(k);
            fileAttente.remove(x);
            for (i = 0; i < fileAttente.size(); i++) {
                y = fileAttente.get(i);
                if (!y.couleurVoisin().contains(k)) {
                    y.setCouleur(k);
                    fileAttente.remove(y);
                    i--;
                }
            }
            k++;
        }
        System.out.println("Test de Welsh Powell.");
        this.getColoration();
    }

    public void Dsatur() {
        List<Sommet> fileAttente = ordonnerSommets();
        List<Integer> couleursPresentes;
        Sommet y;
        int i, maxDSAT;
        Sommet elu;
        reinitialiserCouleur();

        while(!fileAttente.isEmpty()){
            elu=fileAttente.get(0);
            maxDSAT=0;
            // Calcule de DSAT
            for (i = 0; i < fileAttente.size(); i++) {
                y = fileAttente.get(i);
                couleursPresentes=y.couleurVoisin();
                if (couleursPresentes.size()>maxDSAT || (couleursPresentes.size() == maxDSAT && y.degre()>elu.degre())) {
                    elu = y;
                    maxDSAT = couleursPresentes.size();
                }
            }
            elu.setCouleur(elu.couleurMinimale());
            fileAttente.remove(elu);
        }
        System.out.println("Test de Dsatur.");
        this.getColoration();
    }


    public void reinitialiserCouleur(){
        for (Sommet s: sommets) {
            s.setCouleur(0);
        }
    }

    public String toString() { return nom;}

    public List<Sommet> getSommets() { return sommets; }
}
