import java.util.HashMap;
import java.util.Map;

public class ClientInfo {
    public Map<Item, Integer> itemsBasket;
    private Card card;
    Supermarket superMarket;

    public ClientInfo(Supermarket superMarket, String[] array) {
        this.superMarket = superMarket;
        fillItemsBasket(array);
    }

    private void fillItemsBasket(String[] array){
        itemsBasket = new HashMap<>();
        for (String s : array) {
            if (!Card.isCardNumber(s)) {
                try {
                    putItemInBasket(itemsBasket, getItem(s), getQuantity(s));
                } catch (NumberFormatException e) {
                    System.out.println("data input error");
                }
            } else {
                card = Supermarket.interCardNumber(s);
                break;
            }
        }
    }

    private void putItemInBasket(Map<Item, Integer> basket, Item item, Integer quantity) {
        if(basket.containsKey(item)) {
            basket.replace(item, basket.get(item) + quantity);
        } else basket.put(item,quantity);
    }

    public Item getItem(String line) {
        String itemId = line.substring(0, line.indexOf("-"));
        return superMarket.getMarketItems().get(Integer.parseInt(itemId));
    }

    private static Integer getQuantity(String line) {
        String itemId = line.substring(line.indexOf("-") + 1);
        return Integer.valueOf(itemId);
    }

    public Card getCard() {
        return card;
    }
}
