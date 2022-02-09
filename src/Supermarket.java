import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Supermarket {
    private String marketName;
    private String address;
    private String phoneNumber;
    private List<Cashier> cashiers;
    private Cashier currentCashier;
    private final List<Item> marketItems;
    private static List<Card> clientsCards;

    public Supermarket(String marketName, String address, String phoneNumber) {
        this.marketName = marketName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cashiers = fillCashiersList();
        System.out.println("enter the filename of itemsList");
        marketItems = fillMarketItems();
        System.out.println("enter the filename of cardsList");
        clientsCards = fillClientsCards();
        currentCashier = cashiers.get((int) (Math.random() * cashiers.size()));
    }

    public List<Item> getMarketItems() {
        return marketItems;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public Cashier getCurrentCashier() {
        return currentCashier;
    }

    public void setCurrentCashier(Cashier currentCashier) {
        this.currentCashier = currentCashier;
    }

    public static List<Card> getClientsCards() {
        return clientsCards;
    }

    public static Card interCardNumber(String cardName){
        int cardNumber = parseCardNumber(cardName);
        Card card = null;
        for(Card cardOfList : clientsCards){
            if(cardOfList.getCardNumber() == cardNumber) {
                card = cardOfList;
            }
        }
        return card;
    }

    public static int parseCardNumber(String cardName) {
        String number = cardName.substring(cardName.indexOf("-") + 1);
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.println("data input error");
            return 0;
        }
    }

    public List<Cashier> fillCashiersList() {
        List<Cashier> cashiers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cashiers.add(new Cashier());
        }
        return cashiers;
    }

    public List<Item> fillMarketItems() {
        List<Item> items = new ArrayList<>();
        for(String line : readDataFromFile(readFromTerminal())) {
            Item item = getItem(line);
            if(item != null) {
                items.add(getItem(line));
            }
        }
        return items;
    }

    public Item getItem(String str) {
        String[] itemData = str.split(" ");
        if(itemData.length > 2) return null;
        double price = Double.parseDouble(itemData[1]);
        return new Item(itemData[0], price);
    }

    public List<Card> fillClientsCards() {
        List<Card> cards = new ArrayList<>();
        for(String line : readDataFromFile(readFromTerminal())) {
            Card card = getCard(line);
            if(card != null) {
                cards.add(getCard(line));
            }
        }
        return cards;
    }

    public Card getCard(String str) {
        String[] cardData = str.split(" ");
        if(cardData.length > 2) return null;
        int cardNumber = parseCardNumber(cardData[0]);
        Card card = new Card(cardNumber);
        if(cardData.length > 1) {
            try {
                card.setDiscountPercent(Integer.parseInt(cardData[1]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    public static List<String> readDataFromFile(String fileName) {
        List<String> dataRead = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                dataRead.add(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("File is not found. Try again.");
            dataRead = readDataFromFile(readFromTerminal());
        }
        return dataRead;
    }

    public static String readFromTerminal() {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        try {
            return buffer.readLine();
        } catch (IOException e) {
            System.out.println("Incorrect input data. Try again.");
            return readFromTerminal();
        }
    }
}