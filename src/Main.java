import klant.Klant;
import klant.KlantDAODerby;
import koerier.BodeKoerier;
import koerier.FietsKoerier;
import koerier.PietersenTransport;
import koerier.TreinKoerier;
import route.Route;
import route.RouteStep;

public class Main {

    public static void main(String[] args) {
//        klant.klant k = new klant.klant("Nick", "Simons", "nick@twesq.com", "0655194576", "Entoshof", "23", "7462VV", "Rijssen");
//        System.out.println(k.getKlantID());


        Klant k1 = new KlantDAODerby().getKlantById(101);

        //verzending.Verzending v = new verzending.Verzending(k1, new general.Locatie("Cor Kieboomplein", "501", "3077MK", "Rotterdam"));
        verzending.Verzending v = new verzending.Verzending(k1, new general.Locatie("Campus", "2", "8017CA", "Zwolle"));

        System.out.println("Afstand: "+v.getRoute().getAfstand());
        System.out.println("Kosten: "+v.getRoute().getKosten());
        System.out.println("Tijd: "+v.getRoute().getTijd());
        System.out.println("Green lvl: "+v.getRoute().getGreenLvl());

        for (RouteStep s:v.getRoute().getSteps()) {
            System.out.println("Step Afstand: "+s.getAfstand());
            System.out.println("Step Kosten: "+s.getKosten());
            System.out.println("Step Tijd: "+s.getTijd());
            System.out.println("Step Koerier: "+s.getKoerier());
        }

        String res = "{" +
                "   \"destination_addresses\" : [ \"Goudvink 44, 7463 BE Rijssen, Nederland\" ]," +
                "   \"origin_addresses\" : [ \"Entoshof 23, 7462 VV Rijssen, Nederland\" ]," +
                "   \"rows\" : [" +
                "      {" +
                "         \"elements\" : [" +
                "            {" +
                "               \"distance\" : {" +
                "                  \"text\" : \"2,2 km\"," +
                "                  \"value\" : 42937" +
                "               }," +
                "               \"duration\" : {" +
                "                  \"text\" : \"5 min.\"," +
                "                  \"value\" : 309" +
                "               }," +
                "               \"status\" : \"OK\"" +
                "            }" +
                "         ]" +
                "      }" +
                "   ]," +
                "   \"status\" : \"OK\"" +
                "}";
        
        Route route = new Route();
        route.addStep(new BodeKoerier(), res);

        System.out.println("Afstand: "+route.getAfstand());
        System.out.println("Kosten: "+route.getKosten());
        System.out.println("Tijd: "+route.getTijd());
        System.out.println("Green lvl: "+route.getGreenLvl());

    }
}

