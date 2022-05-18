package sample.Admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigation {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void HomeView(javafx.scene.input.MouseEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void AdhView(javafx.scene.input.MouseEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("liste_adhérent.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void gesPreView(javafx.scene.input.MouseEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("gestion_pret.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void logout(javafx.scene.input.MouseEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("/sample/Login.fxml"));
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
