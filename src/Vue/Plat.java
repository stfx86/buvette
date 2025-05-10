package Vue;

/**
 * Class for representing a dish.
 */
public class Plat {
    String nom;
    double prix;
    String description;
    String categorie;
    String imagePath;

    public Plat(String nom, double prix, String description, String categorie, String imagePath) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.categorie = categorie;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return nom + " - " + prix + "€ (" + description + ")";
    }
}