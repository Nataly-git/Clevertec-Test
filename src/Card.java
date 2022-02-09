
public class Card {
    private int cardNumber;
    private int discountPercent;

    public Card(int cardNumber) {
        this.cardNumber = cardNumber;
        this.discountPercent = 0;
    }

    public Card(int cardNumber, int discountPercent) {
        this.cardNumber = cardNumber;
        this.discountPercent = discountPercent;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public static boolean isCardNumber(String num) {
        return num.contains("card-");
    }
}
