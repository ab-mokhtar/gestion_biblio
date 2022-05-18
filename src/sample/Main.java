package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("My Biblio");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        Timer timer=new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 02);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();


        int nb=1000 * 60 * 60 * 24;
        timer.schedule(new VerfiEmprunt(), time,nb);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
