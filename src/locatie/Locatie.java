package locatie;

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
        String adres = "";
        if(straat == null)
            straat = "";
        if(huisnummer == null)
            huisnummer = "";
        if(postcode == null)
            postcode = "";
        if(plaats == null)
            plaats = "";

        if(!straat.equals(""))
            adres += straat;
        if(!straat.equals("") && !huisnummer.equals(""))
            adres += " ";
        if(!huisnummer.equals(""))
            adres += huisnummer;
        if(!straat.equals("") || !huisnummer.equals(""))
            adres += ", ";
        if(!postcode.equals(""))
            adres += postcode;
        if(!postcode.equals("") && !plaats.equals(""))
            adres += " ";
        if(!plaats.equals(""))
            adres += plaats;

        return adres;
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

    public int getId() {
        return id;
    }
}
