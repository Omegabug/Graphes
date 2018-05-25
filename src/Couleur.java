public enum Couleur {

    WHITE("White"),
    BLACK("Black"),
    YELLOW("Yellow"),
    BLUE("Blue"),
    RED("RED"),
    GREEN("Green"),
    PINK("Pink");

    private String couleur;

    Couleur(String couleur) {
        this.couleur = couleur;
    }

    public Couleur nextCouleur() {
        return Couleur.values()[(this.ordinal()+1)%(Couleur.values().length)];
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String toString() {
        return this.couleur;
    }
}
