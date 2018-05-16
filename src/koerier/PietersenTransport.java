package koerier;

public class PietersenTransport extends Koerier {

    public PietersenTransport() {
        super("Pietersen Transport B.V.", 4);
    }

    @Override
    public double calculateCost(int afstand) {
        afstand = afstand/1000;
        double cost;

        if(afstand < 25) {
            cost = 10;
        } else {
            cost = afstand*.39;
        }
        return cost;
    }
}
