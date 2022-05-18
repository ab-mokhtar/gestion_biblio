package sample.Admin;

public class Adherent {
    private int id;
    private String nom;
    private String prenom;
    private int numéro_tel;
    private String adresse;
    private String type;

    public Adherent() {
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Adherent{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numéro_tel=" + numéro_tel +
                ", adresse='" + adresse + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Adherent(int id, String nom, String prenom, int numéro_tel, String adresse, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numéro_tel = numéro_tel;
        this.adresse = adresse;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNuméro_tel() {
        return numéro_tel;
    }

    public void setNuméro_tel(int numéro_tel) {
        this.numéro_tel = numéro_tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
