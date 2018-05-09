import java.sql.*;

public class DBConnection {

    private static final String DRIVER      = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL    = "jdbc:derby:DBTZT;create=true";

    public Connection connect = null;
    public Statement statement = null;
    public ResultSet resultSet = null;

    public void open() {
        try {
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(JDBC_URL);
            if(this.connect != null) {
                System.out.println("Connected to database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
