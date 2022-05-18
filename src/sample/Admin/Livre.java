package sample.Admin;

public class Livre {
    private int id;
    private String name;
    private String Auteur;
    private String category;
    private int nb_pages;
    private int dispo;

    public Livre() {
    }

    public Livre(int id, String name, String auteur, String category, int nb_pages) {
        this.name = name;
        Auteur = auteur;
        this.category = category;
        this.nb_pages = nb_pages;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Auteur='" + Auteur + '\'' +
                ", category='" + category + '\'' +
                ", nb_page='" + nb_pages + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNb_pages() {
        return nb_pages;
    }

    public void setNb_pages(int nb_page) {
        this.nb_pages = nb_page;
    }
}
