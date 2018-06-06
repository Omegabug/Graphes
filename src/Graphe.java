import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Graphe {
    public List<Sommet> getEnsembleSommet() {
        return ensembleSommet;
    }

    protected String nom;
    protected boolean oriente;
    protected int nbSommet;
    protected int nbValSommet;
    protected int nbArcs;
    protected int nbValArcs;

    protected List<Sommet> ensembleSommet = new ArrayList<Sommet>();

    public Graphe() {}
    
    public void lectureGraphe(String adresseFichier) throws IOException {


            InputStream in = null;
            BufferedReader br = null;
            String line="";
            int temp1,temp2;
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


                            break;
                        case 1:
                            //On vérifie que notre graphe est orienté
                            if(line.equalsIgnoreCase("oui")){
                                oriente=true;


                            }
                            else{
                                oriente=false;
                            }
                            break;
                        case 2:
                            nbSommet=Integer.parseInt(line);

                            break;
                        case 3:
                            nbValSommet=Integer.parseInt(line);

                            break;
                        case 4:
                            nbArcs=Integer.parseInt(line);

                            break;
                        case 5:
                            nbValArcs=Integer.parseInt(line);

                            break;
                        case 6:
                            break;


                    }
                    if (compteurLigne>6&&compteurLigne<7+nbSommet){
                        //On va jusqu'a l'espace pour avoir le nom du sommet
                        position=line.indexOf(' ')+1;
                        line=line.substring(position);

                        ensembleSommet.add(new Sommet(line));

                    }
                    compteurLigne++;

                    if(compteurLigne>7+nbSommet+1&&compteurLigne<7+nbSommet+2+nbArcs){
                        //On trouve l'espace entre les 2 chiffres

                        temp1=Integer.parseInt(line.substring(0,line.indexOf(' ')));
                        temp2=Integer.parseInt(line.substring(line.indexOf(' ')+1));
                        ensembleSommet.get(temp1).rajouterSommet(ensembleSommet.get(temp2));
                        if(!oriente)
                            ensembleSommet.get(temp2).rajouterSommet(ensembleSommet.get(temp1));
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) br.close();
                if (in != null) in.close();

            }

    }

    @Override
    public String toString() {
        return "Graphe{" +
                "ensembleSommet=" + ensembleSommet +
                ", nom='" + nom + '\'' +
                ", oriente=" + oriente +
                ", nbSommet=" + nbSommet +
                ", nbValSommet=" + nbValSommet +
                ", nbArcs=" + nbArcs +
                ", nbValArcs=" + nbValArcs +
                '}';
    }
    //On ordonne les sommets en fonction de leur degré
    public void ordonnerSommets(){
        /*Collections.sort(ensembleSommet, new Comparator<Sommet>() {
            @Override
            public int compare(Sommet s1,Sommet s2){
                if( s1.degre()>s2.degre()) return 1;
                else {
                    System.out.println(s1.degre());
                    return 0;
                }
            }
        });*/
        ensembleSommet.sort(Comparator.comparing(Sommet::degre));
    }
}
