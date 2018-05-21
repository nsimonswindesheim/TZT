package verzending;

import klant.Klant;
import locatie.Locatie;
import route.Route;
import route.RouteCalculator;

public class Verzending {

    private int verzendID = 0;
    private Klant klant;
    private Locatie origin;
    private Locatie destination;
    private Route route;


    public Verzending(Klant klant, Locatie destination) {
        this.klant          = klant;
        this.origin         = klant.getLocatie();
        this.destination    = destination;
    }
    public Verzending() {}

    public void setOrigin(Locatie origin) {
        this.origin = origin;
    }

    public boolean calcRoute() {
        this.route = RouteCalculator.berekenRoute(origin, destination);
        System.out.println("V "+this.route);
        if(this.route != null) {
            return true;
        } else {
            return false;
        }
    }

    public Route getRoute() {
        return route;
    }

    public int getVerzendID() {
        return verzendID;
    }

    public Klant getKlant() {
        return klant;
    }

    public Locatie getOrigin() {
        return origin;
    }

    public Locatie getDestination() {
        return destination;
    }

    public void setVerzendID(int verzendID) {
        this.verzendID = verzendID;
    }

    public void setOriginNoCalc(Locatie origin) {
        this.origin = origin;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public void setDestination(Locatie destination) {
        this.destination = destination;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
