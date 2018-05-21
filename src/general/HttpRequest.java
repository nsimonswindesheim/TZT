package general;

import locatie.Locatie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest {

    public static String getGoogleMatrix(ArrayList<Locatie> origins, ArrayList<Locatie> destinations, int mode) {
        String modeText = "driving";
        if(mode == 2) {
            modeText = "bicycling";
        } else if(mode ==  3) {
            modeText = "transit";
        }

        String originString = "";
        String destinationString = "";

        for(Locatie loc:origins) {
            originString += loc.getFullAddress()+"|";
        }
        for(Locatie loc:destinations) {
            destinationString += loc.getFullAddress()+"|";
        }


        originString         = originString.replaceAll(" ", "+");
        destinationString    = destinationString.replaceAll(" ", "+");
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+ originString +"&destinations="+ destinationString +"&mode="+ modeText +"&language="+ Config.REQ_LANG +"&key="+Config.APIKEY;

        return googleRequest(url);
    }

    public static String getGoogleMatrix(Locatie origins, Locatie destinations, int mode) {
        ArrayList<Locatie> originArray = new ArrayList<>();
        ArrayList<Locatie> destinationArray = new ArrayList<>();
        originArray.add(origins);
        destinationArray.add(destinations);
        return getGoogleMatrix(originArray, destinationArray, mode);
    }

    private static String googleRequest(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
