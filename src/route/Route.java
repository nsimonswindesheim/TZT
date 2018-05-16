package route;

import general.Config;
import koerier.Koerier;

import java.util.ArrayList;

public class Route implements Comparable<Route> {

    private ArrayList<RouteStep> steps = new ArrayList<>();
    private double kosten;
    private int afstand;
    private int tijd;
    private int greenLvl;

    public void addStep(Koerier koerier, String googleResult) {
        RouteStep newStep    = new RouteStep(koerier, googleResult);
        this.kosten         += newStep.getKosten();
        this.afstand        += newStep.getAfstand();
        this.tijd           += newStep.getTijd();
        this.greenLvl       += newStep.getGreen();
        this.steps.add(newStep);
    }

    public double getKosten() {
        return (kosten * Config.COST_PERCENTAGE);
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

    public ArrayList<RouteStep> getSteps() {
        return steps;
    }

    @Override
    public int compareTo(Route o) {
        double otherAmount = o.getKosten();
        double glvl = o.getGreenLvl();

        if (kosten == otherAmount)
            return 0;
        else if (kosten > otherAmount && greenLvl > glvl)
            return 2;
        else if (kosten > otherAmount)
            return 1;
        else
            return -1;
    }
}
