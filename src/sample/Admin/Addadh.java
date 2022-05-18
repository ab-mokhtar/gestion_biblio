package sample.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Addadh extends Navigation implements Initializable {

    @FXML
    private Label AddMessageLabel;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField tel;
    @FXML
    private TextField adresse;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField Confirmpassword;
    @FXML
    private TextField idinput;
    @FXML
    private ChoiceBox typebox;
    public void add_btn(ActionEvent evnet){

        if (nom.getText().isEmpty()==false && prenom.getText().isEmpty()==false&& username.getText().isEmpty()==false && tel.getText().isEmpty()==false &&adresse.getText().isEmpty()==false&password.getText().isEmpty()==false){
           if (password.getText().toString().equals(Confirmpassword.getText().toString())){
            validatadd(evnet);}
           else {
               AddMessageLabel.setText("les mot de passes sont différentes");
           }

        }else {
            AddMessageLabel.setText("Please verify");
        }
    }

    public void validatadd(ActionEvent event){
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection Connectdb= ConnectNow.getConnection();
        String verifyregister=("INSERT INTO users (first_name,last_name,username,password,tel,adresse,type)VALUES('"+nom.getText()+"','"+prenom.getText()+"','"+username.getText()+"','"+password.getText()+"','"+tel.getText()+"','"+adresse.getText()+"','"+typebox.getSelectionModel().getSelectedItem().toString()+"')");

        try {
            Statement statement=Connectdb.createStatement();
            statement.executeUpdate(verifyregister);
            nom.clear();
            prenom.clear();
            tel.clear();
            adresse.clear();
            AddMessageLabel.setText("Adhérent ajouté !");



        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
            AddMessageLabel.setText("Erreur !");

        }
    }

    @Override
    public void gesPreView(MouseEvent event) {
        super.gesPreView(event);
    }

    public void update(ActionEvent event){
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection Connectdb= ConnectNow.getConnection();
        String verifyregister=("UPDATE  `users` SET first_name='"+nom.getText()+"',last_name='"+prenom.getText()+"',type='"+typebox.getSelectionModel().getSelectedItem().toString()+"',adresse='"+adresse.getText()+"' WHERE id ='"+idinput.getText()+"'");

        try {
            Statement statement=Connectdb.createStatement();
            statement.executeUpdate(verifyregister);
            nom.clear();
            prenom.clear();
            tel.clear();
            adresse.clear();
            AddMessageLabel.setText("Adhérent modifié !");



        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }

    @Override
    public void HomeView(MouseEvent event) {
        super.HomeView(event);
    }

    @Override
    public void AdhView(MouseEvent event) {
        super.AdhView(event);
    }

    @Override
    public void logout(MouseEvent event) {
        super.logout(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        typebox.getItems().add("Etudiant");
        typebox.getItems().add("Ensegniant");
        typebox.getSelectionModel().selectFirst();


    }
    public void setTextField(Adherent adh){

        try {
            idinput.setText(String.valueOf(adh.getId()));
            nom.setText(adh.getNom());
            prenom.setText(adh.getPrenom());
            adresse.setText(adh.getAdresse());
            tel.setText(String.valueOf(adh.getNuméro_tel()));
            switch (adh.getType())
            {
                case "Etudiant":
                   typebox.getSelectionModel().selectFirst();
                    break;
                case "Ensegniant":
                    typebox.getSelectionModel().select(1);
                    break;

            }


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }

}
