package koerier;

public abstract class Koerier {

    private String naam;
    private int id;

    public Koerier(String naam, int id) {
        this.naam = naam;
        this.id = id;
    }

    @Override
    public String toString() {
        return naam;
    }

    public abstract double calculateCost(int afstand);

    public int getId() {
        return id;
    }

    public static Koerier getKoerierById(int id) {
        switch (id) {
            case 1:
                return new FietsKoerier();
            case 2:
                return new TreinKoerier();
            case 3:
                return new BodeKoerier();
            case 4:
                return new PietersenTransport();
            default:
                return null;
        }
    }
}
