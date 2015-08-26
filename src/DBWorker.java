import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBWorker {

    private Statement statement;

    public DBWorker(String db, String user, String password) {
        try {
            String url = "jdbc:mysql://";
            Connection connection = DriverManager.getConnection(url + db, user, password);
            statement = connection.createStatement();
            GUI.status.setText("Соединение с БД установлено");
        } catch (SQLException e) {
            GUI.status.setText("<html>Ошибка подключения к БД,<br> проверьте правильность введённых данных,<br>" +
                    "а также наличие подключения к сети");
            e.printStackTrace();
        }
    }

    public void createAndClearTable() {

        String createQuery = "CREATE TABLE IF NOT EXISTS schedule(class varchar(20) DEFAULT NULL," +
                "dayofweek varchar(11) DEFAULT NULL, auditory varchar(20) DEFAULT NULL," +
                "lecture varchar(45) DEFAULT NULL, lecturer varchar(45) DEFAULT NULL," +
                "time varchar(5) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        String truncateQuery = "TRUNCATE TABLE schedule";
        try {
            statement.executeUpdate(createQuery);
            statement.executeUpdate(truncateQuery);
        } catch (SQLException e) {
            GUI.status.setText("Ошибка создания/очистки " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fillTable(ArrayList<Data> arrayList) {

        for (Data data : arrayList) {
            String query = "INSERT INTO schedule VALUES('" + data.getGroup() + "','"
                    + data.getDayOfWeek() + "','" + data.getAuditory() + "','" + data.getLecture() + "','"
                    + data.getLecturer() + "','" + data.getTime() + "')";
            try {
                statement.executeUpdate(query);
                GUI.status.setText("Таблицы успешно заполнены");
            } catch (SQLException e) {
                GUI.status.setText("Ошибка заполнения " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}