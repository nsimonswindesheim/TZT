package route;

import general.Config;
import koerier.FietsKoerier;
import koerier.Koerier;
import koerier.TreinKoerier;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class RouteStep {

    private double kosten;
    private int afstand;
    private int tijd;
    private Koerier koerier;
    private int status = 0;

    public RouteStep(Koerier koerier, String googleResult) {
        parseGoogleJson(googleResult);
        this.koerier = koerier;
        this.kosten = koerier.calculateCost(this.afstand);
    }

    public RouteStep() {

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

    public Koerier getKoerier() {
        return koerier;
    }

    public int getGreen() {
        if(this.koerier instanceof FietsKoerier || this.koerier instanceof TreinKoerier)
            return 1;
         else
            return 0;
    }

    public void setAfstand(int afstand) {
        this.afstand = afstand;
    }

    public void setTijd(int tijd) {
        this.tijd = tijd;
    }

    public void setKosten(double kosten) {
        this.kosten = kosten;
    }

    public void setKoerier(Koerier koerier) {
        this.koerier = koerier;
    }

    private void parseGoogleJson(String res) {
        try {
            JSONObject jObject = new JSONObject(res).getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
            int afs     = jObject.getJSONObject("distance").getInt("value");
            int tijd    = jObject.getJSONObject("duration").getInt("value");
            setAfstand(afs);
            setTijd(tijd);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }
}
