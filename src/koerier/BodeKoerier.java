package koerier;

public class BodeKoerier extends Koerier {

    public BodeKoerier() {
        super("Bodekoerier", 3);
    }

    @Override
    public double calculateCost(int afstand) {
        afstand = afstand/1000;
        double cost;

        if(afstand < 40) {
            cost = 12.50;
        } else {
            cost = afstand*.40;
        }
        return cost;
    }
}
