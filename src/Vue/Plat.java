package Vue;

/**
 * Class for representing a dish.
 */
public class Plat {
    private String nom;
    private double prix;
    private String description;
    private String categorie;
    private String imagePath;

    public Plat() {
        // Default constructor for DB operations
    }

    public Plat(String nom, double prix, String description, String categorie, String imagePath) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.categorie = categorie;
        this.imagePath = imagePath;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public String getDescription() {
        return description;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return nom + " - " + prix + "€ (" + description + ")";
    }
}