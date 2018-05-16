package koerier;

public class FietsKoerier extends Koerier {

    public FietsKoerier() {
        super("Fietskoerier", 1);
    }

    @Override
    public double calculateCost(int afstand) {
        afstand = afstand/1000;
        double cost = 0;

        if(afstand < 4) {
            cost = 9;
        } else if(afstand < 8) {
            cost = 14;
        } else if(afstand < 12) {
            cost = 19;
        } else if(afstand > 11) {
            cost = 15 + (afstand*.56);
        }
        return cost;
    }
}
