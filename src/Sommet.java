import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Un Sommet est notamment caractérisé par :
 * <ul>
 *     <li>un nom.</li>
 *     <li>une couleur (représentée par un entier).</li>
 *     <li>une liste de ses sommets adjacents.</li>
 * </ul>
 * </p>
 * @see #couleur
 * @see #numero
 * @see #nom
 * @see #aretes
 */
public class Sommet {

    /**
     * Couleur du sommet, représentée par un entier.
     */
    private int couleur = 0;
    /**
     * Numéro du sommet.
     */
    private int numero = 0;
    /**
     * Nom du sommet.
     */
    private String nom = "Default";
    /**
     * Liste des sommets adjacents à ce sommet.
     */
    private List<Sommet> aretes = new ArrayList<>();

    /**
     * Constructeur par défaut du sommet.
     */
    public Sommet () {}

    public Sommet (int num, String name) {
        numero = num;
        nom = name;
    }

    /**
     * Constructeur de Sommet.
     * @param c Couleur du sommet.
     */
    public Sommet (int c) {
        couleur = c;
    }

    /**
     * Constructeur du sommet.
     * @param c Couleur
     * @param ls Liste des sommets adjacents.
     * @param num Numéro.
     * @param name Nom.
     */
    public Sommet (int c, List<Sommet> ls, int num, String name){
        couleur = c;
        aretes = ls;
        numero = num;
        nom = name;
    }

    /**
     * Rajoute un sommet adjacent (s'il n'est pas déjà présent dans la liste).
     * @param s Sommet à rajouter.
     * @see #aretes
     */
    public void rajouterSommet(Sommet s){
        // Si on n'a pas déja le sommet on le rajoute à notre liste.
        if (aretes.size()==0 || !aretes.contains(s)) aretes.add(s);
    }

    /**
     * Récupère les couleurs des sommets adjacents.
     * @return Liste des couleurs des sommets adjacents.
     * @see #aretes
     * @see #couleur
     */
    public List<Integer> couleurVoisin(){
        List<Integer> couleurVoisins = new ArrayList<>();
        for (Sommet arete : aretes) {
            if (!couleurVoisins.contains(arete.getCouleur()))
                couleurVoisins.add(arete.getCouleur());
        }
        return couleurVoisins;
    }

    /**
     * Récupère la plus petite couleur disponible parmi les sommets adjacents.
     * @return Plus petite couleur disponible.
     * @see #aretes
     * @see #couleur
     */
    public int couleurMinimale(){
        List<Integer> voisin = couleurVoisin();
        int j;
        for (j = 1; true; j++) {
            if(!voisin.contains(j)){
                return j;
            }
        }
    }

    /**
     * Renvoie le degré du sommet.
     * @return Degré
     * @see #aretes
     */
    public int degre(){
        return aretes.size();
    }

    /**
     * Récupère le numéro du sommet.
     * @return Numéro du sommet sous forme de String.
     * @see #numero
     */
    public String toString() { return String.valueOf(numero); }

    /**
     * Récupère la couleur du sommet.
     * @return Couleur du sommet.
     * @see #couleur
     */
    public int getCouleur(){
        return couleur;
    }

    /**
     * Récupère la liste des sommets adjacents.
     * @return Liste des sommets adjacents.
     * @see #aretes
     */
    public List<Sommet> getAretes() { return aretes; }

    /**
     * Définit la couleur du sommet.
     * @param couleur Couleur du sommet.
     * @see #couleur
     */
    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}
