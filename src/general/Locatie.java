package general;

public class Locatie {

    private String straat;
    private String huisnummer;
    private String postcode;
    private String plaats;
    private int id;

    public Locatie(String straat, String huisnummer, String postcode, String plaats, int id) {
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
        this.plaats = plaats;
        this.id = id;
    }
    public Locatie(String straat, String huisnummer, String postcode, String plaats) {
        this(straat, huisnummer, postcode, plaats, 0);
    }

    public String getFullAddress() {
        return straat +" "+ huisnummer +", "+ postcode +" "+ plaats;
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getPlaats() {
        return plaats;
    }
}
