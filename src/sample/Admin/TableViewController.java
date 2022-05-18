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
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableViewController extends Navigation implements Initializable  {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView <Livre> LivreTable;
    @FXML
    private TableColumn<Livre,String> idcol;
    @FXML
    private TableColumn<Livre,String> namecol;
    @FXML
    private TableColumn<Livre,String> Auteurcol;
    @FXML
    private TableColumn<Livre,String> categorycol;
    @FXML
    private TableColumn<Livre,String> nb_pagecol;
    @FXML
    private TableColumn<Livre,String> editCol;
    @FXML
    private TextField rechinput;
    @FXML
    private Label username,date;
    String query=null;
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet =null;
    ObservableList <Livre>LivreList= FXCollections.observableArrayList();

    @Override
    public void gesPreView(javafx.scene.input.MouseEvent event) {
        super.gesPreView(event);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTable();
    username.setText(Login_Controller.username);
        date.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    }
    @FXML
    private void close(MouseEvent event){}
    @FXML
    public void getAddView(javafx.scene.input.MouseEvent event){
        try {

            root = FXMLLoader.load(getClass().getResource("AddLivre.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow() ;
            scene=new Scene(root, 700, 400);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void refreshTable(){
        LoadData();
        try {
            LivreList.clear();
            query="SELECT * FROM `livre`";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Livre l=new Livre();
                int id=resultSet.getInt("id");
                l.setId(id);
                String name=resultSet.getString("name");
                l.setName(name);
                String auteur=resultSet.getString("Auteur");
                l.setAuteur(auteur);
                String categ=resultSet.getString("category");
                l.setCategory(categ);
                int nb=resultSet.getInt("nb_pages");
                l.setNb_pages(nb);

                LivreList.add(l);
                LivreTable.setItems(LivreList);
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }
    public void recherche(){
        LoadData();
        try {
            LivreList.clear();
            query="SELECT * FROM livre WHERE name LIKE '%"+rechinput.getText()+"%' OR Auteur LIKE '%"+rechinput.getText()+"%' OR category LIKE '%"+rechinput.getText()+"%'";
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Livre l=new Livre();
                     int id=resultSet.getInt("id");
                    l.setId(id);
                     String name=resultSet.getString("name");
                     l.setName(name);
                     String auteur=resultSet.getString("Auteur");
                     l.setAuteur(auteur);
                     String categ=resultSet.getString("category");
                     l.setCategory(categ);
                     int nb=resultSet.getInt("nb_pages");
                     l.setNb_pages(nb);

                LivreList.add(l);
                LivreTable.setItems(LivreList);
                rechinput.clear();
            }
        }catch (SQLException e){
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE,null,e);
        }

    }

    @FXML
    private void print(MouseEvent event){}
    private void LoadData(){
        DatabaseConnection db=new DatabaseConnection();
        connection= db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        Auteurcol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("category"));
        nb_pagecol.setCellValueFactory(new PropertyValueFactory<>("nb_pages"));

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
                               Livre livre = LivreTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `livre` WHERE id  ="+livre.getId();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();

                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });
                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent  event) -> {

                            Livre livre = LivreTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("update.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddLivre livrecontroler = loader.getController();

                            livrecontroler.setTextField(livre);
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
        LivreTable.setItems(LivreList);


    }


}
