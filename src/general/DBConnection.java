package general;

import java.sql.*;


public class DBConnection {

    //Database variables for connection and statements
    public Connection connect = null;
    public PreparedStatement statement = null;
    public ResultSet resultSet = null;

    /**
     * Open/ connect to database
     */
    public void open() {
        try {
            Class.forName(Config.DRIVER);
            connect = DriverManager.getConnection(Config.JDBC_URL);
            if(this.connect != null) {
                //System.out.println("Connected to database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set all database variables to null
     * The database connection is now closed
     */
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
            //System.out.println("Disconnected from database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSql(String tName, String colm) throws SQLException {
        try {
            statement = connect.prepareStatement("SELECT ? FROM ?");
            statement.setString(1, colm);
            statement.setString(2, tName);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
