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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Login_Controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Recu {
    Stage stage;
    @FXML
    private Label nomadh,idadh,idlv,datesortie,dateretour,nomlv;
Emprunt emprunt;
    public void setTextField(Emprunt emp){
this.emprunt=emp;
        try {
            nomadh.setText(Login_Controller.username);
            idadh.setText(String.valueOf(Login_Controller.id));
            idlv.setText(String.valueOf(emp.getId_livre()));
            datesortie.setText(emp.getDate_sortie());
            dateretour.setText(emp.getDate_retour());
            nomlv.setText(emp.getNom_liv());





        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
    public void imprimerrecu(Event event)throws FileNotFoundException {
Emprunt emrunt=this.emprunt;

        try
        {
            String path="C:\\Users\\abbes\\IdeaProjects\\test\\src\\recu\\recu"+Math.round(Math.random()*1000)+"_"+LocalDate.now()+".pdf";
            PdfWriter pdfWriter = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A4);

            float [] columnWidthHeader  = {520f};
            Table headerTable = new Table(columnWidthHeader);
            headerTable.setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(com.itextpdf.kernel.color.Color.WHITE);

            headerTable.addCell(new com.itextpdf.layout.element.Cell().add("Reçu"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(30f)
                    .setMarginBottom(30f)
                    .setFontSize(30f)
                    .setBorder(Border.NO_BORDER);


            float [] columnWidthInformation  = {100f,100f,100f,100f,100f,100f};
            Table InformationTable = new Table(columnWidthInformation);


            float [] columnWidth  = {100f,100f,100f,100f,100f,100f};
            Table table = new Table(columnWidth);
            InformationTable.addCell(new com.itextpdf.layout.element.Cell().add("Nom Adhérent").setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(com.itextpdf.kernel.color.Color.WHITE));
            InformationTable.addCell(new com.itextpdf.layout.element.Cell().add("Id Adhérent").setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(com.itextpdf.kernel.color.Color.WHITE));
            InformationTable.addCell(new com.itextpdf.layout.element.Cell().add("Nom Livre").setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(com.itextpdf.kernel.color.Color.WHITE));
            InformationTable.addCell(new com.itextpdf.layout.element.Cell().add("Id Livre").setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(com.itextpdf.kernel.color.Color.WHITE));
            InformationTable.addCell(new com.itextpdf.layout.element.Cell().add("Date Sortie").setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(com.itextpdf.kernel.color.Color.WHITE));
            InformationTable.addCell(new com.itextpdf.layout.element.Cell().add("Date Retour").setBackgroundColor(new DeviceRgb(0 ,5,68)).setFontColor(Color.WHITE));

            float [] columnWidthMnt = {100f};


            table.addCell(new com.itextpdf.layout.element.Cell().add(Login_Controller.username));
            table.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(Login_Controller.id)));
            table.addCell(new com.itextpdf.layout.element.Cell().add(emprunt.getNom_liv()));
            table.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(emprunt.getId_livre())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(emprunt.getDate_sortie()));
            table.addCell(new Cell().add(emprunt.getDate_retour()));
            document.add(headerTable);
            document.add(new Paragraph("\n\n"));
            document.add(InformationTable);
            document.add(new Paragraph("\n\n"));
            document.add(table);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("MERCI À BIENTÔT")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(280f));


            document.close();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }










}
