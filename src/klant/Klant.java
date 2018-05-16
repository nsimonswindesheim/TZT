package klant;

import general.DBConnection;
import general.Locatie;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class Klant {

    //All of the 'klant.klant' information
    private int klantID;
    private String voornaam;
    private String achternaam;
    private String email;
    private String telefoonnummer;
    private Locatie locatie;

    private DBConnection db;

    /**
     * Create a new 'klant.klant' with all new information
     *
     * @param voornaam
     * @param achternaam
     * @param email
     * @param telefoonnummer
     * @param straat
     * @param huisnummer
     * @param postcode
     * @param plaats
     */
    public Klant(String voornaam, String achternaam, String email, String telefoonnummer, String straat, String huisnummer, String postcode, String plaats) {
        //Create database class, for connection
        this.db = new DBConnection();

        //Define klant information
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.locatie = new Locatie(straat, huisnummer, postcode, plaats);
    }

    public Klant(String achternaam, String email, String telefoonnummer, String straat, String huisnummer, String postcode, String plaats) {
        this(null, achternaam, email, telefoonnummer, straat, huisnummer, postcode, plaats);
    }

    /**
     * Create a existing 'klant.klant'
     * Search in database
     *
     * @param klantID
     */
    public Klant(int klantID) {
        this.db = new DBConnection();
        if (ifExistDB(klantID)) {
            try {
                db.open();
                db.statement = db.connect.prepareStatement("SELECT * FROM KLANT WHERE \"Klant-ID\" = ?");
                db.statement.setInt(1, klantID);
                db.resultSet = db.statement.executeQuery();
                db.resultSet.next();

                //Define klant information
                this.klantID = db.resultSet.getInt(1);
                this.voornaam = db.resultSet.getString(2);
                this.achternaam = db.resultSet.getString(3);
                this.email = db.resultSet.getString(4);
                this.telefoonnummer = db.resultSet.getString(5);
                this.locatie = new Locatie(db.resultSet.getString(6), db.resultSet.getString(7), db.resultSet.getString(8), db.resultSet.getString(9));
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }
    }

    /**
     * With this function you save all of the 'klant.klant' information to the database
     * If the 'klant.klant' already exists that it is a update function
     *
     * @return
     */
    public boolean saveToDB() {
        if (ifExistDB(this.klantID)) {
            //Update existing klant in database
            try {
                db.open();
                db.statement = db.connect.prepareStatement("UPDATE KLANT SET VOORNAAM = ?, ACHTERNAAM = ?, EMAIL = ?, TELEFOONNUMMER = ?, STRAAT = ?, HUISNUMMER = ?, POSTCODE = ?, PLAATS = ? WHERE \"Klant-ID\" = ?");
                db.statement.setString(1, this.voornaam);
                db.statement.setString(2, this.achternaam);
                db.statement.setString(3, this.email);
                db.statement.setString(4, this.telefoonnummer);
                db.statement.setString(5, this.locatie.getStraat());
                db.statement.setString(6, this.locatie.getHuisnummer());
                db.statement.setString(7, this.locatie.getPostcode());
                db.statement.setString(8, this.locatie.getPlaats());
                db.statement.setInt(9, this.klantID);
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
                db.statement.setString(1, this.voornaam);
                db.statement.setString(2, this.achternaam);
                db.statement.setString(3, this.email);
                db.statement.setString(4, this.telefoonnummer);
                db.statement.setString(5, this.locatie.getStraat());
                db.statement.setString(6, this.locatie.getHuisnummer());
                db.statement.setString(7, this.locatie.getPostcode());
                db.statement.setString(8, this.locatie.getPlaats());
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
    private boolean ifExistDB(int id) {
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


    //Below here, are all getter functions
    public String getEmail() {
        return email;
    }

    public int getKlantID() {
        return klantID;
    }

    public Locatie getLocatie() {
        return this.locatie;
    }
}

