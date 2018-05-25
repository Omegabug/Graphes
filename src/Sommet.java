import java.lang.String;

public class Sommet {
    Couleur couleur;
    String
    Sommet[] arretes;

    //Crée un sommet générique
    public Sommet (){

        //Blanc est la couleur par défaut
        couleur="Blanc";
    }


    //Crée un sommet à partir d'une couleur donnée et d'une liste de sommet
    public Sommet (String c,Sommet[] ls){
        couleur=c;
        arretes=ls;
    }

    //Crée un sommet à partir d'une couleur donnée;
    public Sommet (String c){
        couleur=c;
    }

    public String getCouleur(){
        return couleur;
    }
    //Renvoit le sommet à position pointée par l'index
    public Sommet getSommet(int index){
        return arretes[index];
    }

}
