package route;

import general.DBConnection;
import general.HttpRequest;
import general.Locatie;
import koerier.BodeKoerier;
import koerier.FietsKoerier;
import koerier.PietersenTransport;
import koerier.TreinKoerier;
import org.json.JSONArray;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.*;

public class RouteCalculator {

    public static Route berekenRoute(Locatie origin, Locatie destination) {
        ArrayList<Route> mogelijkheden = new ArrayList<>();
        Route tijdelijkeRoute;
        if(fietsMogelijk(origin.getPlaats(), destination.getPlaats())) {
            //route.RouteCalculator met fiets naar eind
            String fullFietsRes = HttpRequest.getGoogleMatrix(origin, destination, 2);
            tijdelijkeRoute = new Route();
            tijdelijkeRoute.addStep(new FietsKoerier(), fullFietsRes);
            mogelijkheden.add(tijdelijkeRoute);
        }

        ArrayList<Locatie> stations = berekenStation(origin, destination);
        if(!stations.isEmpty()) {
            Locatie station1 = stations.get(0);
            Locatie station2 = stations.get(1);
            System.out.println("Begin station: " + station2.getFullAddress());
            System.out.println("Eind station: " + station1.getFullAddress());

            String deel1Auto    = HttpRequest.getGoogleMatrix(origin, station1, 1);
            String deel1Fiets   = HttpRequest.getGoogleMatrix(origin, station1, 2);
            String deel2        = HttpRequest.getGoogleMatrix(station1, station2, 3);
            String deel3Auto    = HttpRequest.getGoogleMatrix(station2, destination, 1);
            String deel3Fiets   = HttpRequest.getGoogleMatrix(station2, destination, 2);
            boolean deel1FietMogelijk = fietsMogelijk(origin.getPlaats(), station1.getPlaats());
            boolean deel3FietMogelijk = fietsMogelijk(station2.getPlaats(), destination.getPlaats());

            //Fiets Trein Fiets
            if(deel1FietMogelijk && deel3FietMogelijk) {
                tijdelijkeRoute = new Route();
                tijdelijkeRoute.addStep(new FietsKoerier(), deel1Fiets);
                tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
                tijdelijkeRoute.addStep(new FietsKoerier(), deel3Fiets);
                mogelijkheden.add(tijdelijkeRoute);
            }

            //Fiets Trein Auto
            if(deel1FietMogelijk) {
                tijdelijkeRoute = new Route();
                tijdelijkeRoute.addStep(new FietsKoerier(), deel1Fiets);
                tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
                tijdelijkeRoute.addStep(new BodeKoerier(), deel3Auto);
                mogelijkheden.add(tijdelijkeRoute);
                tijdelijkeRoute = new Route();
                tijdelijkeRoute.addStep(new FietsKoerier(), deel1Fiets);
                tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
                tijdelijkeRoute.addStep(new PietersenTransport(), deel3Auto);
                mogelijkheden.add(tijdelijkeRoute);
            }

            //Auto Trein Auto
            tijdelijkeRoute = new Route();
            tijdelijkeRoute.addStep(new BodeKoerier(), deel1Auto);
            tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
            tijdelijkeRoute.addStep(new BodeKoerier(), deel3Auto);
            mogelijkheden.add(tijdelijkeRoute);
            tijdelijkeRoute = new Route();
            tijdelijkeRoute.addStep(new BodeKoerier(), deel1Auto);
            tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
            tijdelijkeRoute.addStep(new PietersenTransport(), deel3Auto);
            mogelijkheden.add(tijdelijkeRoute);
            tijdelijkeRoute = new Route();
            tijdelijkeRoute.addStep(new PietersenTransport(), deel1Auto);
            tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
            tijdelijkeRoute.addStep(new PietersenTransport(), deel3Auto);
            mogelijkheden.add(tijdelijkeRoute);
            tijdelijkeRoute = new Route();
            tijdelijkeRoute.addStep(new PietersenTransport(), deel1Auto);
            tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
            tijdelijkeRoute.addStep(new BodeKoerier(), deel3Auto);
            mogelijkheden.add(tijdelijkeRoute);

            //Auto Trein Fiets
            if(deel3FietMogelijk) {
                tijdelijkeRoute = new Route();
                tijdelijkeRoute.addStep(new BodeKoerier(), deel1Auto);
                tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
                tijdelijkeRoute.addStep(new FietsKoerier(), deel3Fiets);
                mogelijkheden.add(tijdelijkeRoute);
                tijdelijkeRoute = new Route();
                tijdelijkeRoute.addStep(new PietersenTransport(), deel1Auto);
                tijdelijkeRoute.addStep(new TreinKoerier(), deel2);
                tijdelijkeRoute.addStep(new FietsKoerier(), deel3Fiets);
                mogelijkheden.add(tijdelijkeRoute);
            }
        }

        //route.RouteCalculator met auto
        String fullCarRes = HttpRequest.getGoogleMatrix(origin, destination, 1);
        tijdelijkeRoute = new Route();
        tijdelijkeRoute.addStep(new BodeKoerier(), fullCarRes);
        mogelijkheden.add(tijdelijkeRoute);
        tijdelijkeRoute = new Route();
        tijdelijkeRoute.addStep(new PietersenTransport(), fullCarRes);
        mogelijkheden.add(tijdelijkeRoute);

        Collections.sort(mogelijkheden);

        if(!mogelijkheden.isEmpty()) {
            return mogelijkheden.get(0);
        } else {
            return null;
        }
    }

    private static boolean fietsMogelijk(String pl1, String pl2) {
        if(pl1.equalsIgnoreCase(pl2)) {
            try {
                DBConnection db = new DBConnection();
                db.open();
                db.statement = db.connect.prepareStatement("SELECT * FROM FIETSPLAATS WHERE UPPER(PLAATS) = UPPER(?)");
                db.statement.setString(1, pl1);
                db.resultSet = db.statement.executeQuery();
                if(db.resultSet.next()) {
                    db.close();
                    return true;
                } else {
                    db.close();
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    private static ArrayList<Locatie> berekenStation(Locatie origin, Locatie destination) {//Station mogelijkheden
        ArrayList<Locatie> stationPlaatsen = new ArrayList<>();
        try {
            DBConnection db = new DBConnection();
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM STATIONPLAATS");
            db.resultSet = db.statement.executeQuery();
            while(db.resultSet.next()) {
                stationPlaatsen.add(new Locatie(db.resultSet.getString(2), db.resultSet.getString(3), db.resultSet.getString(4), db.resultSet.getString(5), db.resultSet.getInt(1)));
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Locatie> stationVerEind = new ArrayList<>();
        stationVerEind.add(origin);
        stationVerEind.add(destination);
        String res = HttpRequest.getGoogleMatrix(stationVerEind, stationPlaatsen, 1);

        return sortStationRoute(parseStationGoogleRes(res), stationPlaatsen);
    }

    private static ArrayList<HashMap> parseStationGoogleRes(String res) {
        try {
            JSONArray jsonArr = new JSONArray("["+res+"]");
            //JSONObject ts = new JSONObject(res);
            jsonArr = jsonArr.getJSONObject(0).getJSONArray("rows");

            JSONArray beginAdr = jsonArr.getJSONObject(0).getJSONArray("elements");
            JSONArray eindAdr = jsonArr.getJSONObject(1).getJSONArray("elements");

            //Sorting begin adres
            HashMap<Integer,Integer> unsortedMap1 = new HashMap<Integer, Integer>();
            HashMap<Integer,Integer> unsortedMap2 = new HashMap<Integer, Integer>();
            for (int i = 0; i < beginAdr.length(); i++) {
                try {
                    int j1 = beginAdr.getJSONObject(i).getJSONObject("distance").getInt("value");
                    unsortedMap1.put(i, j1);
                    int j2 = eindAdr.getJSONObject(i).getJSONObject("distance").getInt("value");
                    unsortedMap2.put(i, j2);
                } catch (JSONException e) {
                    unsortedMap1 = null;
                    unsortedMap2 = null;
                    break;
                }
            }
            ArrayList<HashMap> result = new ArrayList<>();
            result.add(unsortedMap1);
            result.add(unsortedMap2);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<HashMap>();
        }
    }
    private static ArrayList<Locatie> sortStationRoute(ArrayList<HashMap> parseResult, ArrayList<Locatie> stationPlaatsen) {
        ArrayList<Locatie> stationPlaatsBeginEind = new ArrayList<>();
        try {
            HashMap<Integer,Integer> unsortedMap1 = parseResult.get(0);
            HashMap<Integer,Integer> unsortedMap2 = parseResult.get(1);
            if(unsortedMap1 != null && unsortedMap2 != null) {
                Set<Map.Entry<Integer, Integer>> set1 = unsortedMap1.entrySet();
                List<Map.Entry<Integer, Integer>> list1 = new ArrayList<Map.Entry<Integer, Integer>>(set1);
                Collections.sort(list1, new Comparator<Map.Entry<Integer, Integer>>() {
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return -o2.getValue().compareTo(o1.getValue());
                    }
                });

                //Sorting eind adres
                Set<Map.Entry<Integer, Integer>> set2 = unsortedMap2.entrySet();
                List<Map.Entry<Integer, Integer>> list2 = new ArrayList<Map.Entry<Integer, Integer>>(set2);
                Collections.sort(list2, new Comparator<Map.Entry<Integer, Integer>>() {
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return -o2.getValue().compareTo(o1.getValue());
                    }
                });
                stationPlaatsBeginEind.add(stationPlaatsen.get(list1.get(0).getKey()));
                stationPlaatsBeginEind.add(stationPlaatsen.get(list2.get(0).getKey()));
                return stationPlaatsBeginEind;
            } else {
                return stationPlaatsBeginEind;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return stationPlaatsBeginEind;
        }
    }


}
