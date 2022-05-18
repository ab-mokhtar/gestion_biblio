package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimerTask;

public class VerfiEmprunt extends TimerTask {
    PreparedStatement preparedStatement=null;
    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connection=ConnectNow.getConnection();
    ResultSet resultSet,resultSet1 =null;
    @Override
    public void run() {
        String verifreserv=("SELECT COUNT(*) AS nb FROM emprunts WHERE etat=0 AND date_retour <'"+ LocalDate.now()+"'");
        int x=0;
        try {
            preparedStatement = connection.prepareStatement(verifreserv);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getInt("nb")>0){
                String banne=("SELECT * FROM emprunts WHERE etat=0 AND date_retour <'"+ LocalDate.now()+"'");
                preparedStatement = connection.prepareStatement(banne);
                resultSet = preparedStatement.executeQuery();
                int i=0;
                while (resultSet.next()){
                   banne(resultSet.getInt("id_adh"));

                }
            }

        }catch (Exception e){
            System.out.println("hné");
            System.out.println(e.getMessage());
        }

    }
    public void banne(int id){

        LocalDate d1;
        try {
            String verifreserv=("SELECT COUNT(*) AS nb FROM block WHERE id_adh="+ id+" AND date_fin >'"+LocalDate.now()+"'");
            preparedStatement = connection.prepareStatement(verifreserv);
            resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            int nb=resultSet1.getInt("nb");
            if (nb==0) {
                String test=("SELECT COUNT(*) AS nb FROM block WHERE id_adh="+id);
                preparedStatement = connection.prepareStatement(test);
                resultSet1 = preparedStatement.executeQuery();
                resultSet1.next();
                int x=resultSet1.getInt("nb");
                switch (x) {
                    case 0:
                        d1 = LocalDate.now().plusMonths(1);
                        break;
                    case 1:
                        d1 = LocalDate.now().plusMonths(3);
                        break;
                    case 2:
                        d1 = LocalDate.now().plusMonths(6);
                        break;
                    default:
                        d1 = LocalDate.now().plusMonths(6);
                }
                String banne = ("INSERT INTO `block`(`id_adh`, `date_deb`, `date_fin`) VALUES ('" + id + "','" + LocalDate.now() + "','" + d1 + "')");
                preparedStatement = connection.prepareStatement(banne);
                preparedStatement.execute();
            }

        }catch (Exception e){

            System.out.println(e.getMessage());
        }
    }
}
