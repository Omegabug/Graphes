import java.io.IOException;

public class test {
    public static void main(String[] args) {
        Graphe g = new Graphe();
        try {
            g.lectureGraphe("crown10.txt");
            // g.ordonnerSommets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
