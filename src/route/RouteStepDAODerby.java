package route;

import general.DBConnection;
import klant.KlantDAODerby;
import koerier.Koerier;
import locatie.LocatieDAODerby;
import verzending.Verzending;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RouteStepDAODerby implements RouteStepDAO {

    private DBConnection db = new DBConnection();

    /**
     * Deze functie slaat de route steps op in de db,
     * De functie verwacht een arraylist met alle steps en een route id
     * Als dit gelukt is dan wordt het opgeslagen true terug gegeven.
     * Wanneer er een fout is wordt er een false terug gegeven
     * Het kan zijn dat er meerdere steps zijn, daarom een arraylist
     * @param steps arraylist
     * @param routeId
     * @return boolean
     */
    @Override
    public boolean saveRouteSteps(ArrayList<RouteStep> steps, int routeId) {
        if(!steps.isEmpty()) {
            for(RouteStep step:steps) {
                try {
                    db.open();
                    db.statement = db.connect.prepareStatement("INSERT INTO ROUTESTEP (ROUTE_ID, KOSTEN, AFSTAND, TIJD, KOERIER,GREENLVL, STATUS) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    db.statement.setInt(1, routeId);
                    db.statement.setDouble(2, step.getKosten());
                    db.statement.setInt(3, step.getAfstand());
                    db.statement.setDouble(4, step.getTijd());
                    db.statement.setDouble(5, step.getKoerier().getId());
                    db.statement.setDouble(6, step.getGreen());
                    db.statement.setInt(7, 0);
                    db.statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Deze functie geeft alle routesteps terug die horen bij een route.
     * als het fout gaat dan is de arraylist leeg.
     * @param routeId
     * @return arraylist routestep
     */
    @Override
    public ArrayList<RouteStep> getRouteSteps(int routeId) {
        ArrayList<RouteStep> result = new ArrayList<>();
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM ROUTESTEP WHERE ROUTE_ID = ?");
            db.statement.setInt(1, routeId);
            db.resultSet = db.statement.executeQuery();
            while (db.resultSet.next()) {
                RouteStep tijdelijkeStep = new RouteStep();
                tijdelijkeStep.setKosten(db.resultSet.getDouble(3));
                tijdelijkeStep.setTijd(db.resultSet.getInt(5));
                tijdelijkeStep.setAfstand(db.resultSet.getInt(4));
                tijdelijkeStep.setKoerier(Koerier.getKoerierById(db.resultSet.getInt(7)));
                result.add(tijdelijkeStep);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
}
