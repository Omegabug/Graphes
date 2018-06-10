import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Sommet {

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    private int couleur = 0;
    private int numero = 0;

    public String getNom() {
        return nom;
    }

    private String nom = "Default";
    private List<Sommet> aretes = new ArrayList<>();

    // Crée un sommet générique.
    public Sommet () {}

    public Sommet (int num, String name) {
        numero = num;
        nom = name;
    }

    // Crée un sommet à partir d'une couleur donnée.
    public Sommet (int c) {
        couleur = c;
    }

    // Crée un sommet à partir d'une couleur donnée et d'une liste de sommets
    public Sommet (int c, List<Sommet> ls, int num, String name){
        couleur = c;
        aretes = ls;
        numero = num;
        nom = name;
    }

    public void rajouterSommet(Sommet s){
        // Si on n'a pas déja le sommet on le rajoute à notre liste.
        if (aretes.size()==0 || !aretes.contains(s)) aretes.add(s);
    }

    // Retourne le degré du sommet
    public int degre(){
        return aretes.size();
    }


    public String toString() { return String.valueOf(numero); }

    public int getCouleur(){
        return couleur;
    }

    public List<Sommet> getAretes() { return aretes; }

    public Sommet getSommet(int index){
        return aretes.get(index);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Integer> couleurVoisin(){
        List<Integer> couleurVoisin=new ArrayList<>();

        for (int i = 0; i < aretes.size(); i++) {
            if(!couleurVoisin.contains(aretes.get(i).getCouleur()))
                couleurVoisin.add(aretes.get(i).getCouleur());

        }
        return couleurVoisin;
    }
    public int couleurMinimale(){
        int max;
        List<Integer> voisin=couleurVoisin();
        boolean recherche=true;
        int j;
        max=0;
        for ( j = 1; recherche; j++) {

             if(!voisin.contains(j)){
                 return j;
             }


        }


        return j;
    }
}
