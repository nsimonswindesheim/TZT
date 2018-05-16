package route;

import koerier.Koerier;

import java.util.ArrayList;

public class Route {

    private ArrayList<RouteStep> steps;
    private double kosten;
    private int afstand;
    private int tijd;
    private int greenLvl;

    public void addStep(int afstand, int tijd, Koerier koerier) {
        RouteStep newStep    = new RouteStep(afstand, tijd, koerier);
        this.kosten         += newStep.getKosten();
        this.afstand        += newStep.getAfstand();
        this.tijd           += newStep.getTijd();
        this.greenLvl       += newStep.getGreen();
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

    public int getGreenLvl() {
        return greenLvl;
    }
}
