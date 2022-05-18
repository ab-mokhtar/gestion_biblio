package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String db_name="bibliotheque";
        String db_user="root";
        String db_password=" ";
        String url="jdbc:mysql://localhost/"+db_name;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink=DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque","root","");
        }catch (Exception e){
            System.out.println("probléme cnx");
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
