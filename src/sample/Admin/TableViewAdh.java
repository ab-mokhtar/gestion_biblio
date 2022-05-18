package sample.Admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.DatabaseConnection;
import sample.Login_Controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
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

public class TableViewAdh extends Navigation implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label username,date;
    @FXML
    private TableView<Adherent> adhernet;
    @FXML
    private TableColumn<Adherent,String> idcol;
    @FXML
    private TableColumn<Adherent,String> nomcol;
    @FXML
    private TableColumn<Adherent,String> prencol;
    @FXML
    private TableColumn<Adherent,String> numcol;
    @FXML
    private TableColumn<Adherent,String> adresscol;
    @FXML
    private TableColumn<Adherent,String> typecol;
    @FXML
    private TableColumn<Livre,String> editCol;
    @FXML
    private TextField rechinput;

    String query=null;
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet =null;
    ObservableList<Adherent> AdhList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTable();
        username.setText(Login_Controller.username);
        date.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    }
    @FXML
    private void close(MouseEvent event){}

    @Override
    public void gesPreView(javafx.scene.input.MouseEvent event) {
        super.gesPreView(event);
    }

    @FXML
    public void getAddView(javafx.scene.input.MouseEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("AddAdh.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void recherche(){
        LoadData();
        try {
            AdhList.clear();
            query="SELECT * FROM users WHERE first_name LIKE '%"+rechinput.getText()+"%' OR last_name LIKE '%"+rechinput.getText()+"%' OR adresse LIKE '%"+rechinput.getText()+"%'";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Adherent l=new Adherent();
                int id=resultSet.getInt("id");
                l.setId(id);
                String name=resultSet.getString("first_name");
                l.setNom(name);
                String prenom=resultSet.getString("last_name");
                l.setPrenom(prenom);
                int tel=resultSet.getInt("tel");
                l.setNuméro_tel(tel);
                String adresse=resultSet.getString("adresse");
                l.setAdresse(adresse);
                String type=resultSet.getString("type");
                l.setType(type);

                AdhList.add(l);
                adhernet.setItems(AdhList);
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    public void refreshTable(){
        LoadData();
        try {
            AdhList.clear();
            query="SELECT * FROM `users`";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Adherent l=new Adherent();
                int id=resultSet.getInt("id");
                l.setId(id);
                String name=resultSet.getString("first_name");
                l.setNom(name);
                String prenom=resultSet.getString("last_name");
                l.setPrenom(prenom);
                int tel=resultSet.getInt("tel");
                l.setNuméro_tel(tel);
                String adresse=resultSet.getString("adresse");
                l.setAdresse(adresse);
                String type=resultSet.getString("type");
                l.setType(type);

                AdhList.add(l);
                adhernet.setItems(AdhList);
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    public void LoadBann(){
    LoadBlockeradh();
    try {
            AdhList.clear();
            query="SELECT * FROM `users` WHERE id IN(SELECT id_adh  FROM block WHERE  date_fin >'"+LocalDate.now()+"') ";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Adherent l=new Adherent();
                int id=resultSet.getInt("id");
                l.setId(id);
                String name=resultSet.getString("first_name");
                l.setNom(name);
                String prenom=resultSet.getString("last_name");
                l.setPrenom(prenom);
                int tel=resultSet.getInt("tel");
                l.setNuméro_tel(tel);
                String adresse=resultSet.getString("adresse");
                l.setAdresse(adresse);
                String type=resultSet.getString("type");
                l.setType(type);

                AdhList.add(l);
                adhernet.setItems(AdhList);
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    @FXML
    private void print(MouseEvent event){}
    private void LoadBlockeradh(){
        DatabaseConnection db=new DatabaseConnection();
        connection= db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prencol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        numcol.setCellValueFactory(new PropertyValueFactory<>("numéro_tel"));
        adresscol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));

        //add cell of button edit
        Callback<TableColumn<Livre, String>, TableCell<Livre, String>> cellFoctory = (TableColumn<Livre, String> param) -> {
            // make cell containing buttons
            final TableCell<Livre, String> cell = new TableCell<Livre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIcon deleteIcon =new FontAwesomeIcon();
                        deleteIcon.setIcon(FontAwesomeIcons.UNLOCK);


                        deleteIcon.setStyle(
                                "-fx-font-family: FontAwesome;"+
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:#ff1744;"
                        );

                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent  event) -> {

                            try {
                                Adherent Adh = adhernet.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `block` WHERE id_adh  ="+Adh.getId()+" AND date_fin >'"+LocalDate.now()+"'";
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();

                                LoadBann();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });


                        HBox managebtn = new HBox( deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        adhernet.setItems(AdhList);



    }
    private void LoadData(){
        DatabaseConnection db=new DatabaseConnection();
        connection= db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prencol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        numcol.setCellValueFactory(new PropertyValueFactory<>("numéro_tel"));
        adresscol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));

        //add cell of button edit
        Callback<TableColumn<Livre, String>, TableCell<Livre, String>> cellFoctory = (TableColumn<Livre, String> param) -> {
            // make cell containing buttons
            final TableCell<Livre, String> cell = new TableCell<Livre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIcon deleteIcon =new FontAwesomeIcon();
                        deleteIcon.setIcon(FontAwesomeIcons.TRASH);
                        FontAwesomeIcon editIcon = new FontAwesomeIcon();
                        editIcon.setIcon(FontAwesomeIcons.EDIT);

                        deleteIcon.setStyle(
                                "-fx-font-family: FontAwesome;"+
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                "-fx-font-family: FontAwesome;"+
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent  event) -> {

                            try {
                                Adherent Adh = adhernet.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `adherent` WHERE id  ="+Adh.getId();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();

                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });
                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent  event) -> {
                            Adherent adh = adhernet.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("updateadh.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            Addadh adhcon = loader.getController();

                            adhcon.setTextField(adh);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();




                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        adhernet.setItems(AdhList);



    }

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

}
