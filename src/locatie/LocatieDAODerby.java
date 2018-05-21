package locatie;

import general.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;

public class LocatieDAODerby implements LocatieDAO {

    private DBConnection db = new DBConnection();

    @Override
    public Locatie newLocatie(String straat, String huisnummer, String postcode, String plaats) {
        int existID = locatieExist(straat, huisnummer, postcode, plaats);
        if(existID != 0) {
            return new Locatie(straat, huisnummer, postcode, plaats, existID);
        } else {
            int newLocID = saveLocatie(straat, huisnummer, postcode, plaats);
            return new Locatie(straat, huisnummer, postcode, plaats, newLocID);
        }
    }

    @Override
    public int saveLocatie(String straat, String huisnummer, String postcode, String plaats) {
        try {
            db.open();
            db.statement = db.connect.prepareStatement("INSERT INTO LOCATIE (STRAAT, HUISNUMMER, POSTCODE, PLAATS) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            db.statement.setString(1, straat);
            db.statement.setString(2, huisnummer);
            db.statement.setString(3, postcode);
            db.statement.setString(4, plaats);
            db.statement.executeUpdate();
            db.resultSet = db.statement.getGeneratedKeys();
            if(db.resultSet.next()) {
                return db.resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            db.close();
        }
    }

    private int locatieExist(String straat, String huisnummer, String postcode, String plaats) {
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT ID FROM LOCATIE WHERE UPPER(STRAAT) = UPPER(?) AND UPPER(HUISNUMMER) = UPPER(?) AND UPPER(POSTCODE) = UPPER(?) AND UPPER(PLAATS) = UPPER(?)");
            db.statement.setString(1, straat);
            db.statement.setString(2, huisnummer);
            db.statement.setString(3, postcode);
            db.statement.setString(4, plaats);
            db.resultSet = db.statement.executeQuery();
            if(db.resultSet.next()) {
                return db.resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            db.close();
        }
    }

    @Override
    public Locatie getLocatieById(int id) {
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM LOCATIE WHERE ID = ?");
            db.statement.setInt(1, id);
            db.resultSet = db.statement.executeQuery();
            if(db.resultSet.next()) {
                return new Locatie(db.resultSet.getString(2), db.resultSet.getString(3), db.resultSet.getString(4), db.resultSet.getString(5), db.resultSet.getInt(1));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
}
