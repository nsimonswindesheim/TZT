package koerier;

public class TreinKoerier extends Koerier {

    public TreinKoerier() {
        super("Treinkoerier", 2);
    }

    @Override
    public double calculateCost(int afstand) {
        return 3;
    }
}
