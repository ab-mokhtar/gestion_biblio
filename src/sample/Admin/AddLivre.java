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

public class AddLivre extends Navigation implements Initializable {

    @FXML
    private Label AddMessageLabel;
    @FXML
    private TextField Nameinput;
    @FXML
    private TextField idinput;
    @FXML
    private TextField auteurinput;
    @FXML
    private ChoiceBox catinput;
    @FXML
    private TextField nb_input;
    public void add_btn(ActionEvent evnet){

        if (Nameinput.getText().isEmpty()==false && auteurinput.getText().isEmpty()==false  &&nb_input.getText().isEmpty()==false){
            validatadd(evnet);

        }else {
            AddMessageLabel.setText("Please verify");
        }
    }

    @Override
    public void gesPreView(MouseEvent event) {
        super.gesPreView(event);
    }

    public void validatadd(ActionEvent event){
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection Connectdb= ConnectNow.getConnection();
        String verifyregister=("INSERT INTO livre (name,Auteur,category,nb_pages,dispo)VALUES('"+Nameinput.getText()+"','"+auteurinput.getText()+"','"+catinput.getSelectionModel().getSelectedItem().toString()+"','"+nb_input.getText()+"',0 )");

        try {
            Statement statement=Connectdb.createStatement();
            statement.executeUpdate(verifyregister);
            Nameinput.clear();
            auteurinput.clear();
            nb_input.clear();
            AddMessageLabel.setText("Livre ajouté !");



        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
    public void update(ActionEvent event){
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection Connectdb= ConnectNow.getConnection();
        String verifyregister=("UPDATE  `livre` SET name='"+Nameinput.getText()+"',Auteur='"+auteurinput.getText()+"',category='"+catinput.getSelectionModel().getSelectedItem().toString()+"',nb_pages='"+nb_input.getText()+"' WHERE id ='"+idinput.getText()+"'");

        try {
            Statement statement=Connectdb.createStatement();
            statement.executeUpdate(verifyregister);
            Nameinput.clear();
            auteurinput.clear();
            nb_input.clear();
            AddMessageLabel.setText("Livre modifié !");




        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
    public void setTextField(Livre lv){

        try {
            idinput.setText(String.valueOf(lv.getId()));
            Nameinput.setText(lv.getName());
            auteurinput.setText(lv.getAuteur());
            nb_input.setText(String.valueOf(lv.getNb_pages()));
switch (lv.getCategory())
{
    case "narratif":
        catinput.getSelectionModel().selectFirst();
        break;
    case "théâtral":
        catinput.getSelectionModel().select(1);
        break;
    case "poétique":
        catinput.getSelectionModel().select(2);
        break;
    case "argumentatif":
        catinput.getSelectionModel().select(2);
        break;
    case "épistolaire":
        catinput.getSelectionModel().select(3);
        break;
}


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
        String[]genre={"narratif","théâtral","poétique","argumentatif","épistolaire"};
        catinput.getItems().addAll(genre);
        catinput.getSelectionModel().selectFirst();
    }
}
