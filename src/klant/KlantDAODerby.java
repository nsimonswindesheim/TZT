package klant;

import general.DBConnection;
import locatie.Locatie;
import locatie.LocatieDAODerby;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class KlantDAODerby implements KlantDAO {

    private DBConnection db = new DBConnection();

    /**
     * With this function you save all of the 'klant.klant' information to the database
     * If the 'klant.klant' already exists that it is a update function
     *
     * @return
     */
    public int saveToDB(Klant klant) {
        if (ifExistDB(klant.getKlantID())) {
            //Update existing klant in database
            try {
                db.open();
                db.statement = db.connect.prepareStatement("UPDATE KLANT SET VOORNAAM = ?, ACHTERNAAM = ?, EMAIL = ?, TELEFOONNUMMER = ?, locatie = ? WHERE \"Klant-ID\" = ?");
                db.statement.setString(1, klant.getVoornaam());
                db.statement.setString(2, klant.getAchternaam());
                db.statement.setString(3, klant.getEmail());
                db.statement.setString(4, klant.getTelefoonnummer());
                db.statement.setInt(5, klant.getLocatie().getId());
                db.statement.setInt(6, klant.getKlantID());
                db.statement.executeUpdate();
                return klant.getKlantID();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            } finally {
                db.close();
            }
        } else {
            //Make a new klant in database
            try {
                db.open();
                db.statement = db.connect.prepareStatement("INSERT INTO KLANT (VOORNAAM, ACHTERNAAM, EMAIL, TELEFOONNUMMER, LOCATIE, AANMAAK_DATUM) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                db.statement.setString(1, klant.getVoornaam());
                db.statement.setString(2, klant.getAchternaam());
                db.statement.setString(3, klant.getEmail());
                db.statement.setString(4, klant.getTelefoonnummer());
                db.statement.setInt(5, klant.getLocatie().getId());
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                db.statement.setTimestamp(6, sqlDate);
                db.statement.executeUpdate();
                db.resultSet = db.statement.getGeneratedKeys();
                if(db.resultSet.next()) {
                    return db.resultSet.getInt(1);
                }
                return 0;
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Geen uniek email");
                return 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
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
            if(db.resultSet.next()) {
                //Define klant information
                Klant result =  new Klant();
                result.setKlantID(db.resultSet.getInt(1));
                result.setVoornaam(db.resultSet.getString(2));
                result.setAchternaam(db.resultSet.getString(3));
                result.setEmail(db.resultSet.getString(4));
                result.setTelefoonnummer(db.resultSet.getString(5));
                result.setLocatie(new LocatieDAODerby().getLocatieById(db.resultSet.getInt(7)));
                return result;
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
