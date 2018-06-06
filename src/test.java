import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.IOException;

public class test {
    public static void main(String[] args) {
        Graphe g=new Graphe("hfeuhbhuebviurhv");
        String line;
        try {
            g.lectureGraphe("crown10.txt");
            System.out.println(g);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
