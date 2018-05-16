package verzending;

import klant.Klant;
import general.Locatie;
import route.Route;
import route.RouteCalculator;

public class Verzending {

    private int verzendID;
    private Klant klant;
    private Locatie origin;
    private Locatie destination;
    private Route route;


    public Verzending(Klant klant, Locatie destination) {
        this.klant          = klant;
        this.origin         = klant.getLocatie();
        this.destination    = destination;

        this.route = RouteCalculator.berekenRoute(origin, destination);
    }

    public Verzending(int verzendID) {
        //TODO: verzending zoeken op basis van id
    }

    public void setOrigin(Locatie destination) {
        this.destination = destination;

        this.route = RouteCalculator.berekenRoute(origin, destination);
    }
}
