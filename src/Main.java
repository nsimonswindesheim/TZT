import klant.Klant;
import koerier.FietsKoerier;
import koerier.TreinKoerier;
import route.Route;

public class Main {

    public static void main(String[] args) {
//        klant.klant k = new klant.klant("Nick", "Simons", "nick@twesq.com", "0655194576", "Entoshof", "23", "7462VV", "Rijssen");
//        System.out.println(k.getKlantID());


        Klant k1 = new Klant(101);

        //verzending.Verzending v = new verzending.Verzending(k1, new general.Locatie("Goudvink", "44", "7462VV", "Amsterdam"));

        Route route = new Route();
        route.addStep(1000,10, new FietsKoerier());
        route.addStep(2000,10, new TreinKoerier());
        route.addStep(500,5, new FietsKoerier());

        System.out.println("Afstand: "+route.getAfstand());
        System.out.println("Kosten: "+route.getKosten());
        System.out.println("Tijd: "+route.getTijd());
        System.out.println("Green lvl: "+route.getGreenLvl());
    }
}

