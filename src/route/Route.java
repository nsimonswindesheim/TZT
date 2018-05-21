package route;

import general.Config;
import koerier.Koerier;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.round;

public class Route implements Comparable<Route> {

    private ArrayList<RouteStep> steps = new ArrayList<>();
    private double kosten;
    private int afstand;
    private int tijd;
    private int greenLvl;
    private int id = 0;

    public void addStep(Koerier koerier, String googleResult) {
        RouteStep newStep    = new RouteStep(koerier, googleResult);
        if(newStep.getAfstand() != 0 && newStep.getTijd() != 0) {
            this.kosten += newStep.getKosten() * Config.COST_PERCENTAGE;
            this.afstand += newStep.getAfstand();
            this.tijd += newStep.getTijd();
            this.greenLvl += newStep.getGreen();
            this.steps.add(newStep);
        }
    }

    public double getKosten() {
        return kosten;
    }

    public static String getKostenText(double kost) {
        double kosten = kost;
        DecimalFormat fm = new DecimalFormat(".##");
        return "€ " + fm.format(kosten);
    }

    public int getAfstand() {
        return afstand;
    }

    public static String getAfstandText(int afstand) {
        return Integer.toString(afstand / 1000) + " KM";
    }

    public int getTijd() {
        return tijd;
    }

    public static String getTijdText(int tijd) {
        int p2 = tijd / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;

        if(p2 == 0 && p3 != 0) {
            return p3 + "  minuten";
        } else if(p2 != 0 && p3 == 0) {
            return p2 + " uur";
        } else if(p2 != 0 && p3 != 0) {
            return p2 + " uur " + p3 + "  minuten";
        } else {
            return "-";
        }
    }

    public int getGreenLvl() {
        return greenLvl;
    }

    public ArrayList<RouteStep> getSteps() {
        return steps;
    }

    public int getId() {
        return id;
    }

    public void setSteps(ArrayList<RouteStep> steps) {
        this.steps = steps;
    }

    public void setKosten(double kosten) {
        this.kosten = kosten;
    }

    public void setAfstand(int afstand) {
        this.afstand = afstand;
    }

    public void setTijd(int tijd) {
        this.tijd = tijd;
    }

    public void setGreenLvl(int greenLvl) {
        this.greenLvl = greenLvl;
    }

    public void setId(int id) {
        this.id = id;
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
