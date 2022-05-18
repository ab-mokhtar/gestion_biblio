package sample.User;

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
import sample.Admin.Livre;
import sample.Admin.TableViewController;
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
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeUserController extends Navigation implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label MessageLabel;

    @FXML
    private TableView<Livre> LivreTable;
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
    private Label username;
    @FXML
    private Label date;
    String query=null;
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet =null;
    ObservableList<Livre> LivreList= FXCollections.observableArrayList();

    @Override
    public void HomeView(javafx.scene.input.MouseEvent event) {
        super.HomeView(event);
    }

    @Override
    public void Histori(javafx.scene.input.MouseEvent event) {
        super.Histori(event);
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
                int dispo=resultSet.getInt("dispo");
                l.setDispo(dispo);

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
                int dispo=resultSet.getInt("dispo");
                l.setDispo(dispo);
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

                        FontAwesomeIcon empruntIcon =new FontAwesomeIcon();
                        empruntIcon.setIcon(FontAwesomeIcons.BOOK);
                        FontAwesomeIcon resericon = new FontAwesomeIcon();
                        resericon.setIcon(FontAwesomeIcons.CALENDAR);

                        resericon.setStyle(
                                "-fx-font-family: FontAwesome;"+
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:#00E676;"
                        );
                        empruntIcon.setStyle(
                                "-fx-font-family: FontAwesome;"+
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:blue;"

                        );
                        empruntIcon.setOnMouseClicked((javafx.scene.input.MouseEvent  event) -> {
                            if (verifBanne(Login_Controller.id)){
                                try {
                                Livre livre = LivreTable.getSelectionModel().getSelectedItem();
                                emprunt(livre);

                                refreshTable();

                            } catch (Exception ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }}

                            else {
                                MessageLabel.setText("Vous êtes bloqué");
                            }




                        });
                        resericon.setOnMouseClicked((javafx.scene.input.MouseEvent  event) -> {
                            if (verifBanne(Login_Controller.id)){
                                Livre livre = LivreTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader ();
                                loader.setLocation(getClass().getResource("/sample/User/reserv.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    System.out.println("error");
                                }
                                Reserve reserve = loader.getController();

                                reserve.setTextField(livre);
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            }
                         else {
                                MessageLabel.setText("Vous êtes bloqué");
                            }



                        });

                        HBox managebtn = new HBox(resericon, empruntIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(empruntIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(resericon, new Insets(2, 3, 0, 2));

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

private void emprunt(Livre livre){

            query = "SELECT COUNT(*) AS nb FROM emprunts WHERE id_adh =" + Login_Controller.id + " AND etat =" + 0;
            try {
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (resultSet.getInt("nb") < 3) {
                        if (livre.getDispo() != 1 && Dispoliv_for_emprunt(livre)) {
                            query = ("INSERT INTO emprunts (id_adh,id_livre,nom_livre,date_sortie,date_retour,etat)VALUES('" + Login_Controller.id + "','" + livre.getId() + "','" + livre.getName() + "','" + LocalDate.now() + "','" + LocalDate.now().plus(Period.ofWeeks(1)) + "',0)");

                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.execute();
                            query = ("UPDATE livre SET dispo = 1 WHERE id = " + livre.getId());
                            preparedStatement = connection.prepareStatement(query);
                            preparedStatement.execute();
//                    Timer tr=new Timer();
//                    tr.schedule();
                            MessageLabel.setText("succes");

                        } else {
                            MessageLabel.setText("livre non disponible");
                        }

                    } else {
                        MessageLabel.setText("vous avez atteindre le nb max");
                    }

                }
            } catch (Exception e) {

            }


}
private boolean Dispoliv_for_emprunt(Livre livre){
    String verifreserv=("SELECT COUNT(*) AS nb FROM reservs WHERE id_livre="+livre.getId()+" AND date_sortie <'"+LocalDate.now().plus(Period.ofWeeks(1))+"'");
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
    private boolean verifBanne(int id){
        String verifreserv=("SELECT COUNT(*) AS nb FROM block WHERE id_adh="+ id+" AND date_fin >'"+LocalDate.now()+"'");
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
}

