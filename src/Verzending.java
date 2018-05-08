public class Verzending {

    private int verzendID;
    private Klant klant;
    private Route route;
    private String afhaalAdres;
    private String bezorgAdres;

    public Verzending(Klant klant, Route route, String afhaalAdres, String bezorgAdres) {
        this.klant = klant;
        this.route = route;
        this.afhaalAdres = afhaalAdres;
        this.bezorgAdres = bezorgAdres;
    }

    public Verzending(int verzendID) {

    }

    public String zoekBestaandeVerzending(int verzendID) {

    }

    public Klant getKlant() {
        return klant;
    }

    public Route getRoute() {
        return route;
    }

    public String getAfhaalAdres() {
        return afhaalAdres;
    }

    public String getBezorgAdres() {
        return bezorgAdres;
    }
}
