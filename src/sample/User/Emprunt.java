package sample.User;

import java.util.Date;

public class Emprunt {
    private  int id;
    private int id_adh;
    private String nom_adh;
    private int id_livre;
    private String nom_liv;
    private String date_sortie;
    private String date_retour;

    public Emprunt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_adh() {
        return nom_adh;
    }

    public void setNom_adh(String nom_adh) {
        this.nom_adh = nom_adh;
    }

    public int getId_adh() {
        return id_adh;
    }

    public void setId_adh(int id_adh) {
        this.id_adh = id_adh;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public String getNom_liv() {
        return nom_liv;
    }

    public void setNom_liv(String nom_liv) {
        this.nom_liv = nom_liv;
    }

    public String getDate_sortie() {
        return date_sortie;
    }

    public void setDate_sortie(String date_sortie) {
        this.date_sortie = date_sortie;
    }

    public String getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(String date_retour) {
        this.date_retour = date_retour;
    }
}
