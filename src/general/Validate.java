package general;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Validate {

    /**
     * Dit is de validatie class, deze kan handig zijn bij het valideren van input velden.
     * De parameter is een array dat er bijvoorbeeld zo uit ziet:
     * String[][] arrays = {
         {"email", "nick@twesq.com", "required,email"},
         {"voornaam", "Nick", "required,max:2"},
         {"achternaam", "", "required"}
     };
     * Dit geeft een arraylist met errors terug, wanneer deze leeg is, is alles goed
     * Als hij niet gepassed is dan kun je alle errors laten zien doormiddel van te loopen door de terug gekregen array
     */
    public static ArrayList<String> multiple(String[][] toValidate) {
        //Item[0] = De naam van het veld of value
        //Item[1] = De value
        //Item[2] = De rules in een array
        //Rule[0] = De rule
        //Rule[1] = De parameter van de rule, kan leeg zijn
        ArrayList<String> errors = new ArrayList<>();
        for (String[] item:toValidate) {
            String[] rules = item[2].split(",");
            for (String rule1:rules) {
                String[] rule = rule1.split(":");
                switch (rule[0]) {
                    case "required":
                        if(!required(item[1]))
                            errors.add(item[0].substring(0, 1).toUpperCase() + item[0].substring(1) + " is een verplicht veld");
                        break;
                    case "email":
                        if(!isEmail(item[1]))
                            errors.add(item[0].substring(0, 1).toUpperCase() + item[0].substring(1) + " moet een geldig e-mailadres zijn");
                        break;
                    case "min":
                        if(min_length(item[1], Integer.parseInt(rule[1])))
                            errors.add(item[0].substring(0, 1).toUpperCase() + item[0].substring(1) + " moet minimaal " + rule[1] +  " karakters bevatten");
                        break;
                    case "max":
                        if(max_length(item[1], Integer.parseInt(rule[1])))
                            errors.add(item[0].substring(0, 1).toUpperCase() + item[0].substring(1) + " mag maximaal " + rule[1] +  " karakters bevatten");
                        break;
                }
            }
        }
        return errors;
    }

    public static boolean required(String val) {
        return (val.length() > 0);
    }

    public static boolean isEmail(String val) {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        if (val == null)
            return false;
        return pat.matcher(val).matches();
    }

    public static boolean min_length(String val, int param) {
        return (val.length() < param);
    }

    public static boolean max_length(String val, int param) {
        return (val.length() > param);
    }

}
