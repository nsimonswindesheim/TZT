package route;

import koerier.FietsKoerier;
import koerier.Koerier;
import koerier.TreinKoerier;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteStep {

    private double kosten;
    private int afstand;
    private int tijd;
    private Koerier koerier;

    public RouteStep(Koerier koerier, String googleResult) {
        parseGoogleJson(googleResult);
        this.koerier = koerier;
        this.kosten = koerier.calculateCost(this.afstand);
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

    private void setAfstand(int afstand) {
        this.afstand = afstand;
    }

    private void setTijd(int tijd) {
        this.tijd = tijd;
    }

    private void parseGoogleJson(String res) {
        try {
            JSONObject jObject = new JSONObject(res).getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
            setAfstand(jObject.getJSONObject("distance").getInt("value"));
            setTijd(jObject.getJSONObject("duration").getInt("value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
