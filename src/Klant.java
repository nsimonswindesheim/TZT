public class Klant {

    private int klantID;
    private String voornaam;
    private String achternaam;
    private String email;
    private String telefoonnummer;
    private String adres;
    private String rekeningnummer;

    public Klant(String voornaam, String achternaam, String email, String telefoonnummer, String adres, String rekeningnummer){
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.adres = adres;
        this.rekeningnummer = rekeningnummer;
    }

    public Klant(int klantID) {
    }

    public String zoekBestaandeKlant(int klantID) {

    }

    public getVoornaam() {
        return voornaam;
    }

    public getAchternaam() {
        return achternaam;
    }

    public getEmail() {
        return email;
    }

    public getTelefoonnummer() {
        return telefoonnummer;
    }

    public getAdres() {
        return adres;
    }

    public getRekeningnummer() {
        return rekeningnummer;
    }
}

