import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DRIVER      = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL    = "jdbc:derby:DBTZT;create=true";

    public Connection conn;

    public DBConnection() {
        try {
            Class.forName(DRIVER).newInstance();
            this.conn = DriverManager.getConnection(JDBC_URL);
            if(this.conn != null) {
                System.out.println("Connected to database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query(String q) {
        try {
            this.conn.createStatement().execute("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
