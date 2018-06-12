import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Un graphe est principalement caractérisé par :
 * <ul>
 *     <li>un nom.</li>
 *     <li>un booléen indiquant s'il est orienté.</li>
 *     <li>la liste de ses sommets.</li>
 * </ul>
 * Pour construire un graphe, il ne faut pas seulement instancier l'objet : il faut faire
 * appel à la méthode lectureGraphe(String fileName) qui va permettre de mettre à jour
 * les attributs en fonction du contenu du fichier.
 * </p>
 * @see #nom
 * @see #oriente
 * @see #nbSommets
 * @see #sommets
 * @see #lectureGraphe(String)
 */
public class Graphe {

    /**
     * Nom du graphe.
     */
    private String nom;
    /**
     * Indique si le graphe est orienté (true) ou non (false).
     */
    private boolean oriente;
    /**
     * Nombre de sommets du graphe.
     */
    private int nbSommets;
    private int nbValSommet;
    /**
     * Nombre d'arcs du graphe.
     */
    private int nbArcs;
    private int nbValArcs;
    /**
     * Liste des sommets du graphe. Lorsqu'un graphe est mis à jour par la lectur  d'un fichier,
     * ils sont "triés" par ordre d'arrivée de lecture.
     */
    private List<Sommet> sommets = new ArrayList<>();

    /**
     * Constructeur de Graphe.
     */
    public Graphe() {}

    /**
     * Permet de définir l'objet graphe en fonction du contenu d'un fichier.
     * @param adresseFichier Fichier à lire.
     */
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

    /**
     * Affiche la liste d'adjacence du graphe dans la console.
     */
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

    /**
     * Ordonner les sommets du graphe par degré décroissant.
     * @return Liste des sommets triés.
     * @see #sommets
     */
    private List<Sommet> ordonnerSommets() {
        List<Sommet> sommetsSorted = new ArrayList<>(sommets);
        sommetsSorted.sort(Comparator.comparing(Sommet::degre).reversed());
        return sommetsSorted;
    }

    /**
     * Ordonner les sommets du graphe par degré croissant.
     * @return Liste des sommets triés.
     * @see #sommets
     */
    private List<Sommet> ordonnerSommetsCroissant() {
        List<Sommet> sommetsSorted = new ArrayList<>(sommets);
        sommetsSorted.sort(Comparator.comparing(Sommet::degre));
        return sommetsSorted;
    }

    /**
     * Applique l'algorithme de coloration greedy au graphe.
     * @see #greedy
     */
    public void greedyColoring(){
        reinitialiserCouleur();
        greedy(new ArrayList<>(sommets));
    }

    /**
     * Applique l'algorithme de coloration greedy au graphe, en triant les sommets
     * par degré décroissant.
     * @see #greedy
     * @see #ordonnerSommets()
     */
    public void greedyColoringDecroissant(){
        reinitialiserCouleur();
        // On ordonne les sommets.
        List<Sommet> fileAttente = ordonnerSommets();
        greedy(fileAttente);
    }

    /**
     * Applique l'algorithme de coloration greedy au graphe, en triant les sommets
     * par degré croissant.
     * @see #greedy
     * @see #ordonnerSommetsCroissant()
     */
    public void greedyColoringCroissant(){
        reinitialiserCouleur();
        // On ordonne les sommets.
        List<Sommet> fileAttente = ordonnerSommetsCroissant();
        greedy(fileAttente);
    }

    /**
     * Applique l'algorithme de coloration greedy sur une liste de sommets.
     * @param fileAttente Liste de sommets sur laquelle appliquer l'algorithme.
     */
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

    /**
     * Applique l'algorithme de Welsh Powell sur un graphe.
     * @see #welshPowellColoring()
     */
    public void welshPowellColoring() {
        reinitialiserCouleur();
        weshPowell(new ArrayList<>(sommets));
    }

    /**
     * Applique l'algorithme de Welsh Powell sur un graphe, en triant les sommets
     * par degré décroissant.
     * @see #welshPowellColoring()
     */
    public void welshPowellColoringDecroissant() {
        reinitialiserCouleur();
        List<Sommet> fileAttente = ordonnerSommets();
        weshPowell(fileAttente);
    }

    /**
     * Applique l'algorithme de Welsh Powell sur un graphe, en triant les sommets
     * par degré croissant.
     * @see #welshPowellColoring()
     */
    public void welshPowellColoringCroissant() {
        reinitialiserCouleur();
        List<Sommet> fileAttente = ordonnerSommetsCroissant();
        weshPowell(fileAttente);
    }

    /**
     * Applique l'algorithme de Welsh Powell sur une liste de sommets.
     */
    private void weshPowell(List<Sommet> fileAttente) {
        Sommet x;
        int i;
        Sommet y;
        int k = 1;
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

    /**
     * Applique l'algorithme de coloration de Dsatur au graphe.
     * @see #dsatur(List)
     */
    public void dsaturColoring() {
        //System.out.println("ALGORITHME DE DSATUR.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = new ArrayList<>(sommets);


        dsatur(fileAttente);
        //this.getColoration();
    }

    /**
     * Applique l'algorithme de coloration de Dsatur au graphe, en triant les sommets
     * par degré décroissant.
     * @see #dsatur(List)
     */
    public void dsaturColoringDecroissant() {
        //System.out.println("ALGORITHME DE DSATUR.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = ordonnerSommets();

        dsatur(fileAttente);
        //this.getColoration();
    }

    /**
     * Applique l'algorithme de coloration de Dsatur au graphe, en triant les sommets
     * par degré croissant.
     * @see #dsatur(List)
     */
    public void dsaturColoringCroissant() {
        //System.out.println("ALGORITHME DE DSATUR.");
        reinitialiserCouleur();

        List<Sommet> fileAttente = ordonnerSommetsCroissant();

        dsatur(fileAttente);
        //this.getColoration();
    }

    /**
     * Applique l'algorithme de coloration de Dsatur sur une liste de sommets.
     */
    private void dsatur(List<Sommet> fileAttente) {
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

    /**
     * Réiniatialise les couleurs des sommets du graphe.
     */
    private void reinitialiserCouleur(){
        for (Sommet s: sommets) {
            s.setCouleur(0);
        }
    }

    /**
     * Affiche la coloration du graphe obtenue après l'application d'un algorithme.
     * @return Nombre de couleurs du graphe.
     * @see Sommet#couleur
     */
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

    /**
     * Récupère le nom du graphe.
     * @return Nom du graphe.
     * @see #nom
     */
    public String toString() { return nom;}

    /**
     * Récupère la liste des sommets du graphe.
     * @return Liste des sommets du graphe.
     * @see #sommets
     */
    public List<Sommet> getSommets() { return sommets; }
}
