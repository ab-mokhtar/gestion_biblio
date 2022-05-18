package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.w3c.dom.Document;
import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Login_Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String username;
    public static int id;
    @FXML
    private Button cancelButton;
    @FXML
    private Button registerlink;
    @FXML
    private Button LoginButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private TextField Username_input;
    @FXML
    private PasswordField password_input;
    public void LoginButton(ActionEvent evnet){

       if (Username_input.getText().isEmpty()==false && password_input.getText().isEmpty()==false){
           validatelogin(evnet);

       }else {
           LoginMessageLabel.setText("Please enter username and password");
       }
    }
    public void cancelButton(ActionEvent evnet){
        Username_input.clear();
        password_input.clear();
        LoginMessageLabel.setText("");
    }
    public void validatelogin(ActionEvent event){
        DatabaseConnection ConnectNow = new DatabaseConnection();

        try {
            Connection Connectdb= ConnectNow.getConnection();
            String verifylogin="SELECT * FROM users WHERE username='"+Username_input.getText()+"' and password='"+password_input.getText()+"'";
            Statement statement=Connectdb.createStatement();
            ResultSet queryresult=statement.executeQuery(verifylogin);
           if (queryresult.isBeforeFirst()){
            while (queryresult.next()){

                    username=queryresult.getString("username");
                    id=queryresult.getInt("id");
                    if (queryresult.getString("type").equals("Admin")){
                        loginsucces(event);}
                    else {loginUsers(event);}


            }  } else {
                   LoginMessageLabel.setText("Invalid Username or password please try again");

               }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
    public void registerlink(ActionEvent event){
        try {

             root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }
    public void loginsucces(ActionEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("Admin/Home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }
    public void loginUsers(ActionEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("User/homeadh.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }
}
