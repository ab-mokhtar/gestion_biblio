package sample;

import javafx.event.ActionEvent;
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

import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button cancelButton;
    @FXML
    private Button register_btn;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField Username_input;
    @FXML
    private TextField FistNameinput;
    @FXML
    private TextField LastNameinput;
    @FXML
    private PasswordField password_input;
    public void register_btn(ActionEvent evnet){

        if (Username_input.getText().isEmpty()==false && password_input.getText().isEmpty()==false && FistNameinput.getText().isEmpty()==false &&LastNameinput.getText().isEmpty()==false){
            validaterefister(evnet);

        }else {
            registerMessageLabel.setText("Please verify");
        }
    }
    public void cancelButton(ActionEvent evnet){
        Stage stage= (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void validaterefister(ActionEvent event){
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection Connectdb= ConnectNow.getConnection();
        String verifyregister=("INSERT INTO users (first_name,last_name,username,password)VALUES('"+FistNameinput.getText()+"','"+LastNameinput.getText()+"','"+Username_input.getText()+"','"+password_input.getText()+"')");

        try {
            Statement statement=Connectdb.createStatement();
            statement.executeUpdate(verifyregister);
             registerMessageLabel.setText("register done !");



        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
    public void loginlink(ActionEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }
}
