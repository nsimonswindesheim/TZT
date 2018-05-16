package route;

import koerier.FietsKoerier;
import koerier.Koerier;
import koerier.TreinKoerier;

public class RouteStep {

    private double kosten;
    private int afstand;
    private int tijd;
    private Koerier koerier;

    public RouteStep(int afstand, int tijd, Koerier koerier) {
        this.afstand = afstand;
        this.tijd = tijd;
        this.koerier = koerier;
        this.kosten = koerier.calculateCost(afstand);
    }

    public double getKosten() {
        return kosten;
    }

    public int getAfstand() {
        return afstand;
    }

    public int getTijd() {
        return tijd;
    }

    public int getGreen() {
        if(this.koerier instanceof FietsKoerier || this.koerier instanceof TreinKoerier) {
            return 1;
        } else {
            return 0;
        }
    }

    private void parseGoogleJson() {

    }
}
