package route;

import general.DBConnection;
import klant.KlantDAODerby;
import locatie.LocatieDAODerby;
import verzending.Verzending;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RouteDAODerby implements RouteDAO {

    private DBConnection db = new DBConnection();

    /**
     * Deze functie slaat de route op in de db,
     * wanneer dit is gelukt wordt de functie van de routestep aangeroepen.
     * deze slaat alle route steps op in de db.
     * Als is gelukt is dan wordt het opgeslagen routeid terug gegeven.
     * Wanneer er een fout is wordt er een 0 terug gegeven als id
     * @param route
     * @return routeId
     */
    @Override
    public int saveRoute(Route route) {
        if(route.getId() == 0) {
            int savedRouteId = 0;
            try {
                db.open();
                db.statement = db.connect.prepareStatement("INSERT INTO ROUTE (AFSTAND, TIJD, GREENLVL, KOSTEN) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                db.statement.setInt(1, route.getAfstand());
                db.statement.setInt(2, route.getTijd());
                db.statement.setInt(3, route.getGreenLvl());
                db.statement.setDouble(4, route.getKosten());
                db.statement.executeUpdate();
                db.resultSet = db.statement.getGeneratedKeys();
                if(db.resultSet.next()) {
                    savedRouteId = db.resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(savedRouteId != 0) {
                boolean stepResult = new RouteStepDAODerby().saveRouteSteps(route.getSteps(), savedRouteId);
                if(stepResult) {
                    return savedRouteId;
                } else {
                    return 0;
                }
            }
        } else {
            System.out.println("Route Bestaat al!");
        }
        return 0;
    }

    /**
     * Deze functie zoekt een route bij een verzending. en geeft deze als route object terug.
     * Wanneer er een fout optreed wordt er een null terug gegeven
     * @return route
     */
    @Override
    public Route getRouteById(int id) {
        ArrayList<RouteStep> stepResult = new RouteStepDAODerby().getRouteSteps(id);
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM ROUTE WHERE ID = ?");
            db.statement.setInt(1, id);
            db.resultSet = db.statement.executeQuery();
            if (db.resultSet.next()) {
                Route result =  new Route();
                result.setAfstand(db.resultSet.getInt(2));
                result.setTijd(db.resultSet.getInt(3));
                result.setGreenLvl(db.resultSet.getInt(4));
                result.setKosten(db.resultSet.getDouble(5));
                result.setSteps(stepResult);
                return result;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
}
