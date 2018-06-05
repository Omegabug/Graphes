import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Sommet {
    Couleur couleur;



    String nom;
    List<Sommet> arretes;

    //Crée un sommet générique
    public Sommet (){
        arretes=new ArrayList<Sommet>();
        //White est la couleur par défaut
        couleur=Couleur.WHITE;
        nom="Default";

    }

    public Sommet (String n){
        arretes=new ArrayList<Sommet>();
        //White est la couleur par défaut
        couleur=Couleur.WHITE;
        nom=n;

    }


    //Crée un sommet à partir d'une couleur donnée et d'une liste de sommet
    public Sommet (Couleur c, List<Sommet> ls, String n){
        arretes=new ArrayList<Sommet>();
        couleur=c;
        arretes=ls;
        nom=n;
    }

    //Crée un sommet à partir d'une couleur donnée;
    public Sommet (Couleur c){
        arretes=new ArrayList<Sommet>();
        couleur=c;
        nom="Default";
    }

    public Couleur getCouleur(){
        return couleur;
    }
    //Renvoit le sommet à position pointée par l'index
    public Sommet getSommet(int index){
        return arretes.get(index);
    }


    public void rajouterSommet(Sommet s){
        //Si on n'a pas déja le sommet on le rajoute à notre liste
        if(!arretes.contains(s))
            arretes.add(s);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
