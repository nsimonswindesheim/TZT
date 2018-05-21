package klant;

import general.DBConnection;
import locatie.Locatie;
import locatie.LocatieDAODerby;

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
        if(plaats != null) {
            this.locatie = new LocatieDAODerby().newLocatie(straat, huisnummer, postcode, plaats);
        }
    }
    public Klant(String achternaam, String email, String telefoonnummer, String straat, String huisnummer, String postcode, String plaats) {
        this(null, achternaam, email, telefoonnummer, straat, huisnummer, postcode, plaats);
    }
    public Klant() {
        this(null, null, null, null, null, null, null, null);
    }


    public String getVoornaam() {
        if(voornaam != null) {
            return voornaam;
        } else {
            return "";
        }
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
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

    public void setKlantID(int klantID) {
        this.klantID = klantID;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public void setLocatie(Locatie locatie) {
        this.locatie = locatie;
    }
}

