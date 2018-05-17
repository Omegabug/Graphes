import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Graphe {
    Sommet[] ensembleSommet;
    String nom;
    //Vrais si le graphe est orienté
    boolean oriente;
    public Graphe(String fichier){
        FileInputStream f=null;
        int nbSommets;
        //On ouvre le fichier donné en argument
        try {
             f= new FileInputStream(new File(fichier));


        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        try {

            if (f != null)

                f.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
