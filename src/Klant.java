import java.sql.SQLException;

public class Klant {

    private int klantID;
    private String voornaam;
    private String achternaam;
    private String email;
    private String telefoonnummer;
    private String adres;
    private String rekeningnummer;

    private DBConnection db;

    public Klant(String voornaam, String achternaam, String email, String telefoonnummer, String adres, String rekeningnummer){
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.adres = adres;
        this.rekeningnummer = rekeningnummer;

        this.db = new DBConnection();
    }

    public Klant(int klantID) {
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public String getAdres() {
        return adres;
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }

    private void saveToDB() {
        this.db.open();

        try {
            db.connect.createStatement().execute("INSERT INTO KLANT (VOORNAAM, ACHTERNAAM, EMAIL, TELEFOONNUMMER, STRAAT, HUISNUMMER, POSTCODE, PLAATS, AANMAAK_DATUM) VALUES ('"+this.voornaam+"', )");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.db.close();
    }
}

