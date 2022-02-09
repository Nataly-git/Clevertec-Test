public class Cashier {
    private static int cashiersCounter = 1;
    private final int ID;

    public Cashier() {
        ID = cashiersCounter++;
    }

    @Override
    public String toString() {
        return "CASHIER: #" + ID;
    }
}
