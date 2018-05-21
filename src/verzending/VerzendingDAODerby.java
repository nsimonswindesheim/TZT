package verzending;

import general.DBConnection;
import klant.Klant;
import klant.KlantDAODerby;
import locatie.LocatieDAODerby;
import route.Route;
import route.RouteDAODerby;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class VerzendingDAODerby implements VerzendingDAO {

    private DBConnection db = new DBConnection();

    /**
     * Opslaan van een verzending in de db
     * De verzending bestaat uit route met route stappen en klantid
     * Als er iets fout gaat met het opslaan, wordt er een false terug gegeven.
     * @param verzending
     * @return boolean
     */
    @Override
    public boolean saveVerzending(Verzending verzending) {
        boolean saveStatus = false;
        if(verzending.getVerzendID() == 0) {
            int routeId = new RouteDAODerby().saveRoute(verzending.getRoute());
            if(routeId != 0) {
                try {
                    db.open();
                    db.statement = db.connect.prepareStatement("INSERT INTO VERZENDING (KLANT_ID, AFHAAL_LOCATIE, BEZORG_LOCATIE, ROUTE, REGISTREER_DATUM) VALUES (?,?,?,?,?)");
                    db.statement.setInt(1, verzending.getKlant().getKlantID());
                    db.statement.setInt(2, verzending.getOrigin().getId());
                    db.statement.setInt(3, verzending.getDestination().getId());
                    db.statement.setInt(4, routeId);
                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
                    db.statement.setTimestamp(5, sqlDate);
                    db.statement.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    db.close();
                }
            } else {
                System.out.println("1: "+saveStatus);
                return saveStatus;
            }
        } else {
            System.out.println("Verzending bestaat al!");
        }
        return saveStatus;
    }

    /**
     * Deze functie geeft alle verzendingen terug die hoort bij een klant.
     * @param id
     * @return ArrayList met verzendingen
     */
    @Override
    public ArrayList<Integer> getAllVerzendingFromKlant(int id) {
        ArrayList<Integer> result = new ArrayList<>();
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT ID FROM VERZENDING WHERE KLANT_ID = ?");
            db.statement.setInt(1, id);
            db.resultSet = db.statement.executeQuery();
            while (db.resultSet.next()) {
                result.add(db.resultSet.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            db.close();
        }
    }

    @Override
    public Verzending getAllVerzendingById(int id) {
        try {
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM VERZENDING WHERE ID = ?");
            db.statement.setInt(1, id);
            db.resultSet = db.statement.executeQuery();
            if (db.resultSet.next()) {
                Route route = new RouteDAODerby().getRouteById(db.resultSet.getInt(5));
                if(route != null) {
                    Verzending result =  new Verzending();
                    result.setVerzendID(db.resultSet.getInt(1));
                    result.setKlant(new KlantDAODerby().getKlantById(db.resultSet.getInt(2)));
                    result.setOriginNoCalc(new LocatieDAODerby().getLocatieById(db.resultSet.getInt(3)));
                    result.setDestination(new LocatieDAODerby().getLocatieById(db.resultSet.getInt(4)));
                    result.setRoute(route);
                    return result;
                } else {
                    return null;
                }
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
