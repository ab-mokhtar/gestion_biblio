package sample.User;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.Admin.Livre;
import sample.Admin.TableViewController;
import sample.DatabaseConnection;
import sample.Login_Controller;

import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class HistoriqueController  extends Navigation implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Emprunt> LivreTable;
    @FXML
    private TableColumn<Livre, String> idcol;
    @FXML
    private TableColumn<Livre, String> namecol;
    @FXML
    private TableColumn<Livre, String> datesortie;
    @FXML
    private TableColumn<Livre, String> dateretour;

    @FXML
    private TableColumn<Livre, String> editCol;
    @FXML
    private TextField rechinput;
    @FXML
    private Label username, date;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<Emprunt> EmpruntList = FXCollections.observableArrayList();

    @Override
    public void HomeView(javafx.scene.input.MouseEvent event) {
        super.HomeView(event);
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
    public void Histori(javafx.scene.input.MouseEvent event) {
        super.Histori(event);
    }

    @FXML
    private void close(MouseEvent event) {
    }

    public void getOldemprunts() {
        printHistoryData();
        try {
            EmpruntList.clear();
            query = "SELECT * FROM `emprunts` WHERE id_adh =" + Login_Controller.id + " AND etat=" + 1;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Emprunt l = new Emprunt();
                int id = resultSet.getInt("id");
                l.setId(id);
                int idlv = resultSet.getInt("id_livre");
                l.setId_livre(idlv);
                String name = resultSet.getString("nom_livre");
                l.setNom_liv(name);
                String date_sor = resultSet.getString("date_sortie");
                l.setDate_sortie(date_sor);
                String date_retour = resultSet.getString("date_retour");
                l.setDate_retour(date_retour);


                EmpruntList.add(l);
                LivreTable.setItems(EmpruntList);
            }
        } catch (SQLException e) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void getemprunts() {
        EmpruntList.clear();
        printData();
        try {
            EmpruntList.clear();
            query = "SELECT * FROM `emprunts` WHERE id_adh =" + Login_Controller.id + " AND etat=" + 0;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Emprunt l = new Emprunt();
                int id = resultSet.getInt("id");
                l.setId(id);
                int idlv = resultSet.getInt("id_livre");
                l.setId_livre(idlv);
                String name = resultSet.getString("nom_livre");
                l.setNom_liv(name);
                String date_sor = resultSet.getString("date_sortie");
                l.setDate_sortie(date_sor);
                String date_retour = resultSet.getString("date_retour");
                l.setDate_retour(date_retour);


                EmpruntList.add(l);
                LivreTable.setItems(EmpruntList);
            }
        } catch (SQLException e) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void refreshTable() {
        EmpruntList.clear();
        LoadData();
        try {
            EmpruntList.clear();
            query = "SELECT * FROM `reservs` WHERE id_adh =" + Login_Controller.id;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Emprunt l = new Emprunt();
                int id = resultSet.getInt("id");
                l.setId(id);
                int idlv = resultSet.getInt("id_livre");
                l.setId_livre(idlv);
                String name = resultSet.getString("nom_livre");
                l.setNom_liv(name);
                String date_sor = resultSet.getString("date_sortie");
                l.setDate_sortie(date_sor);
                String date_retour = resultSet.getString("date_retour");
                l.setDate_retour(date_retour);


                EmpruntList.add(l);
                LivreTable.setItems(EmpruntList);
            }
        } catch (SQLException e) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void recherche() {
        printHistoryData();
        try {
            EmpruntList.clear();
            query = "SELECT * FROM `emprunts` WHERE nom_livre LIKE '%" + rechinput.getText() + "%' OR date_sortie LIKE '%" + rechinput.getText() + "%' OR date_retour LIKE '%" + rechinput.getText() + "%' ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Emprunt l = new Emprunt();
                int id = resultSet.getInt("id");
                l.setId(id);
                int idlv = resultSet.getInt("id_livre");
                l.setId_livre(idlv);
                String name = resultSet.getString("nom_livre");
                l.setNom_liv(name);
                String date_sor = resultSet.getString("date_sortie");
                l.setDate_sortie(date_sor);
                String date_retour = resultSet.getString("date_retour");
                l.setDate_retour(date_retour);

            }
        } catch (SQLException e) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    private void print(MouseEvent event) {
    }

    private void LoadData() {
        DatabaseConnection db = new DatabaseConnection();
        connection = db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("nom_liv"));
        datesortie.setCellValueFactory(new PropertyValueFactory<>("date_sortie"));
        dateretour.setCellValueFactory(new PropertyValueFactory<>("date_retour"));

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

                        FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
                        deleteIcon.setIcon(FontAwesomeIcons.TRASH_ALT);
                        FontAwesomeIcon imprimer = new FontAwesomeIcon();
                        imprimer.setIcon(FontAwesomeIcons.PRINT);


                        deleteIcon.setStyle(
                                "-fx-font-family: FontAwesome;" +
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:red;"

                        );
                        imprimer.setStyle(
                                "-fx-font-family: FontAwesome;" +
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"


                        );
                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            try {
                                Emprunt reserv = LivreTable.getSelectionModel().getSelectedItem();
                                query = " DELETE FROM reservs WHERE id  =" + reserv.getId();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();

                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        imprimer.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            Emprunt emp = LivreTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/sample/User/recu.fxml"));
                            try {
                                loader.load();
//                                    imprimerrecu(emp);

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(ex.getStackTrace());
                            }
                            Recu rc = loader.getController();
                            rc.setTextField(emp);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();


                        });


                        HBox managebtn = new HBox(deleteIcon, imprimer);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(imprimer, new Insets(2, 2, 0, 3));


                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        LivreTable.setItems(EmpruntList);


    }

    private void printHistoryData() {
        DatabaseConnection db = new DatabaseConnection();
        connection = db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("nom_liv"));
        datesortie.setCellValueFactory(new PropertyValueFactory<>("date_sortie"));
        dateretour.setCellValueFactory(new PropertyValueFactory<>("date_retour"));

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

                        FontAwesomeIcon imprimer = new FontAwesomeIcon();
                        imprimer.setIcon(FontAwesomeIcons.PRINT);



                        imprimer.setStyle(
                                "-fx-font-family: FontAwesome;" +
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"


                        );

                        imprimer.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            Emprunt emp = LivreTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/sample/User/recu.fxml"));
                            try {
                                loader.load();
//                                    imprimerrecu(emp);

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(ex.getStackTrace());
                            }
                            Recu rc = loader.getController();
                            rc.setTextField(emp);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();


                        });


                        HBox managebtn = new HBox(imprimer);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(imprimer, new Insets(2, 2, 0, 3));


                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        LivreTable.setItems(EmpruntList);


    }

    private void printData() {
        DatabaseConnection db = new DatabaseConnection();
        connection = db.getConnection();
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("nom_liv"));
        datesortie.setCellValueFactory(new PropertyValueFactory<>("date_sortie"));
        dateretour.setCellValueFactory(new PropertyValueFactory<>("date_retour"));

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

                        FontAwesomeIcon returnicon = new FontAwesomeIcon();
                        returnicon.setIcon(FontAwesomeIcons.CHECK_SQUARE_ALT);
                        FontAwesomeIcon imprimer = new FontAwesomeIcon();
                        imprimer.setIcon(FontAwesomeIcons.PRINT);

                        imprimer.setStyle(
                                "-fx-font-family: FontAwesome;" +
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"


                        );


                        returnicon.setStyle(
                                "-fx-font-family: FontAwesome;" +
                                        " -fx-cursor: hand ;"
                                        + "-fx-font-size: 25px;"
                                        + "-fx-fill:green;"

                        );
                        returnicon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            try {
                                Emprunt emprunt = LivreTable.getSelectionModel().getSelectedItem();
                                query = " UPDATE `emprunts` SET etat = 1 WHERE id =" + emprunt.getId();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                query = ("UPDATE `livre` SET dispo = 0 WHERE id =" + emprunt.getId_livre());
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();

                                getemprunts();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        imprimer.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            Emprunt emp = LivreTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/sample/User/recu.fxml"));
                            try {
                                loader.load();
//                                    imprimerrecu(emp);

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(ex.getStackTrace());
                            }
                            Recu rc = loader.getController();
                            rc.setTextField(emp);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();


                        });



                        HBox managebtn = new HBox(returnicon,imprimer);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(returnicon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(imprimer, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        LivreTable.setItems(EmpruntList);


    }

}