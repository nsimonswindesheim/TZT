package route;

import general.DBConnection;
import general.HttpRequest;
import general.Locatie;
import org.json.JSONArray;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.*;

public class RouteCalculator {

    public static Route berekenRoute(Locatie origin, Locatie destination) {
        ArrayList<Route> mogelijkheden = new ArrayList<>();
        if(fietsMogelijk(origin.getPlaats(), destination.getPlaats())) {
            //route.RouteCalculator met fiets naar eind
            System.out.println("ja");
        } else {
            System.out.println("nee");
        }

        ArrayList<String> stations = berekenStation(origin, destination);
        if(!stations.isEmpty()) {
            System.out.println("Begin station: "+stations.get(0));
            System.out.println("Eind station: "+stations.get(1));
        } else {
            System.out.println("Geen station");
        }
        //route.RouteCalculator naar station
        //Trein route
        //route.RouteCalculator van station naar eind


        //route.RouteCalculator met auto


        return new Route();
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

    private static ArrayList<String> berekenStation(Locatie origin, Locatie destination) {//Station mogelijkheden
        ArrayList<String> stationPlaatsen = new ArrayList<>();
        ArrayList<String> stationPlaatsBeginEind = new ArrayList<>();
        try {
            DBConnection db = new DBConnection();
            db.open();
            db.statement = db.connect.prepareStatement("SELECT * FROM FIETSPLAATS");
            db.resultSet = db.statement.executeQuery();
            while(db.resultSet.next()) {
                stationPlaatsen.add(db.resultSet.getString(1));
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(stationPlaatsen);
        String res = HttpRequest.getGoogleMatrix(new String[]{"Entoshof 23", "1781 SW, Den Helder"}, stationPlaatsen, 1);
//        String res = "{" +
//                "   \"destination_addresses\" : [ \"Amsterdam, Nederland\", \"Rotterdam, Nederland\", \"Enschede, Nederland\" ]," +
//                "   \"origin_addresses\" : [ \"Entoshof 23, 7462 VV Rijssen, Nederland\", \"Dordrecht, Nederland\" ]," +
//                "   \"rows\" : [" +
//                "      {" +
//                "         \"elements\" : [" +
//                "            {" +
//                "               \"distance\" : {" +
//                "                  \"text\" : \"134 km\"," +
//                "                  \"value\" : 133597" +
//                "               }," +
//                "               \"duration\" : {" +
//                "                  \"text\" : \"1 uur 33 min.\"," +
//                "                  \"value\" : 5562" +
//                "               }," +
//                "               \"status\" : \"OK\"" +
//                "            }," +
//                "            {" +
//                "               \"distance\" : {" +
//                "                  \"text\" : \"165 km\"," +
//                "                  \"value\" : 165452" +
//                "               }," +
//                "               \"duration\" : {" +
//                "                  \"text\" : \"1 uur 45 min.\"," +
//                "                  \"value\" : 6292" +
//                "               }," +
//                "               \"status\" : \"OK\"" +
//                "            }," +
//                "            {" +
//                "               \"distance\" : {" +
//                "                  \"text\" : \"37,0 km\"," +
//                "                  \"value\" : 37046" +
//                "               }," +
//                "               \"duration\" : {" +
//                "                  \"text\" : \"33 min.\"," +
//                "                  \"value\" : 1961" +
//                "               }," +
//                "               \"status\" : \"OK\"" +
//                "            }" +
//                "         ]" +
//                "      }," +
//                "      {" +
//                "         \"elements\" : [" +
//                "            {" +
//                "               \"distance\" : {" +
//                "                  \"text\" : \"93,9 km\"," +
//                "                  \"value\" : 93935" +
//                "               }," +
//                "               \"duration\" : {" +
//                "                  \"text\" : \"1 uur 11 min.\"," +
//                "                  \"value\" : 4283" +
//                "               }," +
//                "               \"status\" : \"OK\"" +
//                "            }," +
//                "            {" +
//                "               \"distance\" : {" +
//                "                  \"text\" : \"26,2 km\"," +
//                "                  \"value\" : 26159" +
//                "               }," +
//                "               \"duration\" : {" +
//                "                  \"text\" : \"29 min.\"," +
//                "                  \"value\" : 1745" +
//                "               }," +
//                "               \"status\" : \"OK\"" +
//                "            }," +
//                "            {" +
//                "               \"distance\" : {" +
//                "                  \"text\" : \"191 km\"," +
//                "                  \"value\" : 191328" +
//                "               }," +
//                "               \"duration\" : {" +
//                "                  \"text\" : \"1 uur 59 min.\"," +
//                "                  \"value\" : 7136" +
//                "               }," +
//                "               \"status\" : \"OK\"" +
//                "            }" +
//                "         ]" +
//                "      }" +
//                "   ]," +
//                "   \"status\" : \"OK\"" +
//                "}";

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
        } catch (JSONException e) {
            e.printStackTrace();
            return stationPlaatsBeginEind;
        }
    }

    private static void parseGoogleReq() {

    }
    private static void sortRoute() {

    }


}
