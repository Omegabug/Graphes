import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Graphe {
    protected List<Sommet> ensembleSommet;
    protected String nom;
    //Vrais si le graphe est orienté
    protected boolean oriente;
    protected int nbSommet;
    protected int nbValSommet;
    protected int nbArcs;
    protected int nbValArcs;
    public Graphe(String fichier){
        FileInputStream f=null;
        ensembleSommet=new ArrayList<Sommet>();
        //On ouvre le fichier donné en argument
        /*try {

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        */
    }


    public String lectureGraphe(String adresseFichier) throws IOException {


            InputStream in = null;
            BufferedReader br = null;
            String line="";

            int compteurLigne=0;
            try {
                in = new FileInputStream(adresseFichier);
                br = new BufferedReader(new InputStreamReader(in, "UTF-8"), 2048);
                int position,i;

                while ((line = br.readLine()) != null){
                    position=line.indexOf(':')+1;
                    line=line.substring(position);
                    //On enlève les espaces inutiles
                    for(i=0;line.charAt(i)==' ';i++);
                    line = line.substring(i);
                    switch (compteurLigne) {
                        case 0:

                            nom=line;
                            System.out.println(line);

                            break;
                        case 1:
                            //On vérifie que notre graphe est orienté
                            if(line.equalsIgnoreCase("oui")){
                                oriente=true;
                                System.out.println(line);

                            }
                            else{
                                oriente=false;
                            }
                            break;
                        case 2:
                            nbSommet=Integer.parseInt(line);
                            System.out.println(nbSommet);
                            break;
                        case 3:
                            nbValSommet=Integer.parseInt(line);
                            System.out.println(nbValSommet);
                            break;
                        case 4:
                            nbArcs=Integer.parseInt(line);
                            System.out.println(nbArcs);
                            break;
                        case 5:
                            nbValArcs=Integer.parseInt(line);
                            System.out.println(nbValArcs);
                            break;
                        case 6:
                            break;


                    }
                    if (compteurLigne>6&&compteurLigne<6+nbSommet){
                        //On va jusqu'a l'espace pour avoir le nom du sommet
                        position=line.indexOf(' ')+1;
                        line=line.substring(position);

                        ensembleSommet.add(new Sommet(line));
                    }
                    compteurLigne++;

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) br.close();
                if (in != null) in.close();

            }
            return line;
    }
}
