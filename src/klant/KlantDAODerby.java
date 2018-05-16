package klant;

import general.DBConnection;
import general.Locatie;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class KlantDAODerby implements KlantDAO {

    private DBConnection db = new DBConnection();

    /**
     * With this function you save all of the 'klant.klant' information to the database
     * If the 'klant.klant' already exists that it is a update function
     *
     * @return
     */
    public boolean saveToDB(Klant klant) {
        if (ifExistDB(klant.getKlantID())) {
            //Update existing klant in database
            try {
                db.open();
                db.statement = db.connect.prepareStatement("UPDATE KLANT SET VOORNAAM = ?, ACHTERNAAM = ?, EMAIL = ?, TELEFOONNUMMER = ?, STRAAT = ?, HUISNUMMER = ?, POSTCODE = ?, PLAATS = ? WHERE \"Klant-ID\" = ?");
                db.statement.setString(1, klant.getVoornaam());
                db.statement.setString(2, klant.getAchternaam());
                db.statement.setString(3, klant.getEmail());
                db.statement.setString(4, klant.getTelefoonnummer());
                db.statement.setString(5, klant.getLocatie().getStraat());
                db.statement.setString(6, klant.getLocatie().getHuisnummer());
                db.statement.setString(7, klant.getLocatie().getPostcode());
                db.statement.setString(8, klant.getLocatie().getPlaats());
                db.statement.setInt(9, klant.getKlantID());
                db.resultSet = db.statement.executeQuery();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        } else {
            //Make a new klant in database
            try {
                db.open();
                db.statement = db.connect.prepareStatement("INSERT INTO KLANT (VOORNAAM, ACHTERNAAM, EMAIL, TELEFOONNUMMER, STRAAT, HUISNUMMER, POSTCODE, PLAATS, AANMAAK_DATUM) VALUES (?,?,?,?,?,?,?,?,?)");
                db.statement.setString(1, klant.getVoornaam());
                db.statement.setString(2, klant.getAchternaam());
                db.statement.setString(3, klant.getEmail());
                db.statement.setString(4, klant.getTelefoonnummer());
                db.statement.setString(5, klant.getLocatie().getStraat());
                db.statement.setString(6, klant.getLocatie().getHuisnummer());
                db.statement.setString(7, klant.getLocatie().getPostcode());
                db.statement.setString(8, klant.getLocatie().getPlaats());
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                db.statement.setTimestamp(9, sqlDate);
                db.statement.executeUpdate();
                System.out.println("DB DATA: " + db.statement);
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Geen uniek email");
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        }
    }

    /**
     * Search in database if 'klant.klant' with that id exists
     *
     * @param id
     * @return true|false
     */
    public boolean ifExistDB(int id) {
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM KLANT WHERE \"Klant-ID\" = ?");
            db.statement.setInt(1, id);
            db.resultSet = db.statement.executeQuery();
            if (db.resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    /**
     * Create a existing 'klant.klant'
     * Search in database
     * @param klantID
     * @return Klant
     */
    public Klant getKlantById(int klantID) {
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM KLANT WHERE \"Klant-ID\" = ?");
            db.statement.setInt(1, klantID);
            db.resultSet = db.statement.executeQuery();
            db.resultSet.next();

            //Define klant information
            Klant result =  new Klant();
            result.setKlantID(db.resultSet.getInt(1));
            result.setVoornaam(db.resultSet.getString(2));
            result.setAchternaam(db.resultSet.getString(3));
            result.setEmail(db.resultSet.getString(4));
            result.setTelefoonnummer(db.resultSet.getString(5));
            result.setLocatie(new Locatie(db.resultSet.getString(6), db.resultSet.getString(7), db.resultSet.getString(8), db.resultSet.getString(9)));
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
}
