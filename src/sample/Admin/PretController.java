package sample.Admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.DatabaseConnection;
import sample.Login_Controller;
import sample.User.Emprunt;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PretController extends Navigation implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Emprunt> LivreTable;
    @FXML
    private TableColumn<Emprunt,String> idcol;
    @FXML
    private TableColumn<Emprunt,String> id_lv;
    @FXML
    private TableColumn<Emprunt,String> namelvcol;
    @FXML
    private TableColumn<Emprunt,String> id_adhcol;
    @FXML
    private TableColumn<Emprunt,String> nameadhcol;
    @FXML
    private TableColumn<Emprunt,String> datesortie;
    @FXML
    private TableColumn<Emprunt,String> dateretour;

    @FXML
    private TextField rechinput;
    @FXML
    private Label username,date;
    String query=null;
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet,nameSet =null;
    ObservableList<Emprunt> EmpruntList= FXCollections.observableArrayList();

    @Override
    public void HomeView(javafx.scene.input.MouseEvent event) {
        super.HomeView(event);
    }
    @Override
    public void AdhView(javafx.scene.input.MouseEvent event) {
        super.AdhView(event);
    }



    @Override
    public void logout(javafx.scene.input.MouseEvent event) {
        super.logout(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getemprunts();
        username.setText(Login_Controller.username);
        date.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    }



    @Override
    public void gesPreView(javafx.scene.input.MouseEvent event) {
        super.gesPreView(event);
    }

    @FXML
    private void close(MouseEvent event){}

    public void getemprunts(){
        LoadData();
        try {
            EmpruntList.clear();
            query="SELECT * FROM `emprunts` WHERE etat="+0;
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Emprunt l=new Emprunt();
                int id=resultSet.getInt("id");
                l.setId(id);
                int id_lv=resultSet.getInt("id_livre");
                l.setId_livre(id_lv);
                int id_adh=resultSet.getInt("id_adh");
                l.setId_adh(id_adh);
               String queryname="SELECT nom FROM `adherent`WHERE id="+id_adh;
                preparedStatement=connection.prepareStatement(queryname);
                nameSet=preparedStatement.executeQuery();
                while ( nameSet.next()){

                String name_adh=nameSet.getString(1);
                l.setNom_adh(name_adh);}
                String name=resultSet.getString("nom_livre");
                l.setNom_liv(name);
                String date_sor=resultSet.getString("date_sortie");
                l.setDate_sortie(date_sor);
                String date_retour=resultSet.getString("date_retour");
                l.setDate_retour(date_retour);


                EmpruntList.add(l);
                LivreTable.setItems(EmpruntList);
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    public void getreserv(){
        LoadData();
        try {
            EmpruntList.clear();
            query="SELECT * FROM `reservs` ";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Emprunt l=new Emprunt();
                int id=resultSet.getInt("id");
                l.setId(id);
                int id_lv=resultSet.getInt("id_livre");
                l.setId_livre(id_lv);
                int id_adh=resultSet.getInt("id_adh");
                l.setId_adh(id_adh);
                String queryname="SELECT first_name FROM `users`WHERE id="+id_adh;
                preparedStatement=connection.prepareStatement(queryname);
                nameSet=preparedStatement.executeQuery();
                nameSet.next();
                String name_adh=nameSet.getString(1);
                l.setNom_adh(name_adh);
                String name=resultSet.getString("nom_livre");
                l.setNom_liv(name);
                String date_sor=resultSet.getString("date_sortie");
                l.setDate_sortie(date_sor);
                String date_retour=resultSet.getString("date_retour");
                l.setDate_retour(date_retour);


                EmpruntList.add(l);
                LivreTable.setItems(EmpruntList);
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    public void recherche(){
        LoadData();
        try {
            EmpruntList.clear();
            query="SELECT * FROM emprunts WHERE nom_livre LIKE '%"+rechinput.getText()+"%' OR date_sortie LIKE '%"+rechinput.getText()+"%' OR date_retour LIKE '%"+rechinput.getText()+"%'";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Emprunt l=new Emprunt();
                int id=resultSet.getInt("id");
                l.setId(id);
                int id_lv=resultSet.getInt("id_livre");
                l.setId_livre(id_lv);
                String nom_livre=resultSet.getString("nom_livre");
                l.setNom_liv(nom_livre);
                String date_sortie=resultSet.getString("date_sortie");
                l.setDate_sortie(date_sortie);
                String date_retour=resultSet.getString("date_retour");
                l.setDate_retour(date_retour);

                EmpruntList.add(l);
                LivreTable.setItems(EmpruntList);
                rechinput.clear();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

    }

    @FXML
    private void print(MouseEvent event){}
    private void LoadData(){
        DatabaseConnection db=new DatabaseConnection();
        connection= db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_lv.setCellValueFactory(new PropertyValueFactory<>("id_livre"));
        namelvcol.setCellValueFactory(new PropertyValueFactory<>("nom_liv"));
        id_adhcol.setCellValueFactory(new PropertyValueFactory<>("id_adh"));
        nameadhcol.setCellValueFactory(new PropertyValueFactory<>("nom_adh"));
        datesortie.setCellValueFactory(new PropertyValueFactory<>("date_sortie"));
        dateretour.setCellValueFactory(new PropertyValueFactory<>("date_retour"));


        LivreTable.setItems(EmpruntList);


    }





}

