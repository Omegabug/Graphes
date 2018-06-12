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

            InputStream in;
            BufferedReader br;
            String line;
            int temp1, temp2;
            int compteurLigne = 0;
            int compteurSommet = 0;

            try {
                System.out.println("Création d'un nouveau graphe...");
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
                System.out.println("Création du graphe " + nom + " réussie !");
                //listeAdjacence();
                br.close();
                in.close();
            } catch (IOException e) {
                System.out.println("Création du graphe avortée !");
                e.printStackTrace();
            }
    }

    // On ordonne les sommets en fonction de leur degré
    private List<Sommet> ordonnerSommets() {
        List<Sommet> sommetsSorted = new ArrayList<>(sommets);
        sommetsSorted.sort(Comparator.comparing(Sommet::degre).reversed());
        return sommetsSorted;
    }

    // On ordonne les sommets en fonction de leur degré
    private List<Sommet> ordonnerSommetsCroissant() {
        List<Sommet> sommetsSorted = new ArrayList<>(sommets);
        sommetsSorted.sort(Comparator.comparing(Sommet::degre));
        return sommetsSorted;
    }

    public void listeAdjacence() {
        System.out.println("Liste d'adjacence :");
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

        List<Integer> listeCouleurs = new ArrayList<>();
        int couleur;
        for (Sommet s : sommets) {
            if (!listeCouleurs.contains((couleur = s.getCouleur()))) {
                listeCouleurs.add(couleur);
            }
        }

        int length = listeCouleurs.size();
        System.out.println("Nombre de couleurs nécessaires pour colorier le graphe : " + length);
        /*for (Integer color : listeCouleurs) {
            System.out.print(color + " ->");
            for (Sommet s : sommets) {
                if ((couleur = s.getCouleur()) == color) {
                    System.out.print(" " + s);
                }
            }
            System.out.print("\n");
        }*/
        return length;
    }

    public void greedyColoringDecroissant(){
        //System.out.println("ALGORITHME GREEDY.");
        reinitialiserCouleur();

        // On ordonne les sommets.
        List<Sommet> fileAttente = ordonnerSommets();

        greedy(fileAttente);
        //this.getColoration();
    }

    public void greedyColoringCroissant(){
        //System.out.println("ALGORITHME GREEDY.");
        reinitialiserCouleur();

        // On ordonne les sommets.
        List<Sommet> fileAttente = ordonnerSommetsCroissant();


        greedy(fileAttente);
        //this.getColoration();
    }

    public void greedyColoring(){
        //System.out.println("ALGORITHME GREEDY.");
        reinitialiserCouleur();

        // On ordonne les sommets.
        List<Sommet> fileAttente =  new ArrayList<>(sommets);
        
        greedy(fileAttente);
        //this.getColoration();
    }

    private void greedy(List<Sommet> fileAttente) {
        Sommet x;
        List<Integer> couleursPresentes;
        int c;
        while (!fileAttente.isEmpty()) {
            x = fileAttente.get(0);
            // On réinitialise la liste des couleurs présentes dans l'entourage du sommet.
            couleursPresentes = x.couleurVoisin();
            c=1;
            while (couleursPresentes.contains(c)) {
                c++;
            }
            x.setCouleur(c);
            fileAttente.remove(x);
        }
    }

    public void welshPowellColoringDecroissant() {
        //System.out.println("ALGORITHME DE WELSH POWELL.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = ordonnerSommets();

        int k = 1;
        weshPowell(fileAttente, k);
        //this.getColoration();
    }

    public void welshPowellColoringCroissant() {
        //System.out.println("ALGORITHME DE WELSH POWELL.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = ordonnerSommetsCroissant();

        int k = 1;
        weshPowell(fileAttente, k);
        //this.getColoration();
    }

    public void welshPowellColoring() {
        //System.out.println("ALGORITHME DE WELSH POWELL.");
        reinitialiserCouleur();

        List<Sommet> fileAttente =new ArrayList<>(sommets) ;

        int k = 1;
        weshPowell(fileAttente, k);
        //this.getColoration();
    }

    private void weshPowell(List<Sommet> fileAttente, int k) {
        Sommet x;
        int i;
        Sommet y;
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
    }

    public void DsaturColoringDecroissant() {
        //System.out.println("ALGORITHME DE DSATUR.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = ordonnerSommets();

        Dsatur(fileAttente);
        //this.getColoration();
    }

    public void DsaturColoringCroissant() {
        //System.out.println("ALGORITHME DE DSATUR.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = ordonnerSommetsCroissant();

        Dsatur(fileAttente);
        //this.getColoration();
    }

    public void DsaturColoring() {
        //System.out.println("ALGORITHME DE DSATUR.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = new ArrayList<>(sommets);


        Dsatur(fileAttente);
        //this.getColoration();
    }

    private void Dsatur(List<Sommet> fileAttente) {
        Sommet elu;
        int maxDSAT;
        int i;
        Sommet y;
        List<Integer> couleursPresentes;
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
    }


    private void reinitialiserCouleur(){
        for (Sommet s: sommets) {
            s.setCouleur(0);
        }
    }

    public String toString() { return nom;}

    public List<Sommet> getSommets() { return sommets; }
}
