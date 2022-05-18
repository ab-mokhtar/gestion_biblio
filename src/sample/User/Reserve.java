package sample.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Admin.Livre;
import sample.DatabaseConnection;
import sample.Login_Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;

public class Reserve {
    @FXML
    private Label AddMessageLabel;
    @FXML
    private TextField Nameinput;
    @FXML
    private TextField idinput;
    @FXML
    private TextField auteurinput;
    @FXML
    private DatePicker date_deb;
    @FXML
    private DatePicker date_retour;

    private int id_livre;
    private String nom_livre;
    private String id_adh;

    PreparedStatement preparedStatement=null;
    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connection=ConnectNow.getConnection();
    ResultSet resultSet,resultSet1 =null;
    public void update(ActionEvent event) throws Exception{
        LocalDate d1 = LocalDate.parse(date_deb.getValue().toString());
        LocalDate d2 = LocalDate.parse(date_retour.getValue().toString());
        if (d1.isBefore(d2)&&d1.isAfter(LocalDate.now().minus(Period.ofDays(1)))){
            DatabaseConnection ConnectNow = new DatabaseConnection();
            Connection Connectdb= ConnectNow.getConnection();
            String insert=("INSERT INTO reservs (id_adh,id_livre,nom_livre,date_sortie,date_retour)VALUES('"+ Login_Controller.id +"','"+idinput.getText()+"','"+Nameinput.getText()+"','"+date_deb.getValue().toString()+"','"+date_retour.getValue().toString()+"')");

            if (verifreserv() && verifDispoempr()){
                try {

                    if (verifnbempr() ) {
                        preparedStatement = connection.prepareStatement(insert);
                        preparedStatement.executeUpdate();
                        Nameinput.clear();
                        AddMessageLabel.setText("succes !");
                    }
                    else {
                        Nameinput.clear();
                        AddMessageLabel.setText("vous avez atteint le nombre maximal pour ce livre !");
                    }




                }catch (Exception e){
                    e.printStackTrace();
                    e.getCause();

                }
            }else {AddMessageLabel.setText("livre non Disponible dans cette date !");}
        }


        else
        {
            AddMessageLabel.setText("date incorrect !");
        }
    }
    public void setTextField(Livre lv){

        try {
            idinput.setText(String.valueOf(lv.getId()));
            Nameinput.setText(lv.getName());





        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
    public boolean verifreserv(){
        String verifreserv=("SELECT COUNT(*) AS nb FROM reservs WHERE id_livre="+idinput.getText()+" AND date_retour >'"+date_deb.getValue().toString()+"' AND date_sortie<'"+date_retour.getValue().toString()+"'");
        int x=2;
        try {
            preparedStatement = connection.prepareStatement(verifreserv);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            x=resultSet.getInt("nb");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return (x==0);
    }
    public boolean verifDispoempr(){
        String verifreserv=("SELECT COUNT(*) AS nb FROM emprunts WHERE id_livre="+idinput.getText()+" AND etat = 0 AND date_retour >'"+date_deb.getValue().toString()+"'");
        int x=2;
        try {
            preparedStatement = connection.prepareStatement(verifreserv);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            x=resultSet.getInt("nb");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return (x==0);
    }
    public boolean verifnbempr(){
        String verif=("SELECT COUNT(*) AS nb FROM reservs WHERE id_adh="+Login_Controller.id+" AND id_livre="+idinput.getText());
        int x=2;
        try {
            preparedStatement = connection.prepareStatement(verif);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            x=resultSet.getInt("nb");
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
        return (x<=2);
    }


}
