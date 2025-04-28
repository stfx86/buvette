
package Vue;

class Plat {
    private String nom;
    private String prix;
    private String description;
    private String imagePath;

    public Plat(String nom, String prix, String description, String imagePath) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getNom() {
        return nom;
    }

    public String getPrix() {
        return prix;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }
}