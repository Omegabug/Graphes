import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.IOException;

public class test {
    public static void main(String[] args) {
        Graphe g=new Graphe("hfeuhbhuebviurhv");
        String line;
        try {
            line=g.lectureGraphe("crown10.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
