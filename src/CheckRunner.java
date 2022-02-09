import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class CheckRunner {
    public static final String TITLE = "CASH RECEIPT";
    public static Date date;
    public static ClientInfo clientInfo;
    private static double totalBasketCost;
    private static Supermarket supermarket;

    public static void main(String[] args) {
        if(args.length == 0)
            return;
        supermarket = new Supermarket("Supermarket 123", "12, MILKYWAY Galaxy/ Earth", "Tel: 123-456-7890");
        clientInfo = new ClientInfo(supermarket, args);
        printCheck(clientInfo);
    }

    public static void printCheck(ClientInfo basket) {
        System.out.println("enter the filename to output check");
        PrintStream consoleOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(output);
        System.setOut(stream);
        printCheckData(basket);
        try(FileOutputStream fileOutputStream = new FileOutputStream(Objects.requireNonNull(Supermarket.readFromTerminal()))) {
            output.writeTo(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setOut(consoleOut);
        System.out.println(output);
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printCheckData(ClientInfo basket){
        System.out.printf("%30s\n", TITLE);
        System.out.printf("%32s\n", supermarket.getMarketName());
        System.out.printf("%38s\n", supermarket.getAddress());
        System.out.printf("%33s\n\n", supermarket.getPhoneNumber());
        printDateAndTime();
        printItemBasket(basket);
        printTotal();
    }

    public static void printDateAndTime() {
        date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("%s%25s%s\n",supermarket.getCurrentCashier(), "DATE: ", formatDate.format(date));
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        System.out.printf("%37s%s\n","TIME: ",formatTime.format(date));
    }

    public static void printItemBasket(ClientInfo basket) {
        for (int i = 0; i < 48; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.printf("%s %10s %20s %10s\n", "QTY", "DESCRIPTION", "PRICE", "TOTAL");
        for(Map.Entry<Item, Integer> itemInBasket : basket.itemsBasket.entrySet()) {
            Item item = itemInBasket.getKey();
            int qty = itemInBasket.getValue();
            double price = item.getPrice();
            double itemTotal = itemInBasket.getValue() * price;
            totalBasketCost+= itemTotal;
            System.out.printf("%3d %-26s $%-9.2f $%-10.2f \n", qty, item.getItemName(), price, itemTotal);
            if(qty > 5) makeQtyDiscount(itemTotal);
        }
        for (int i = 0; i < 48; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static void makeQtyDiscount(Double itemTotal) {
        double discount = itemTotal * 10 / 100;
        totalBasketCost-=discount;
        System.out.printf("%-41s $-%.2f\n", "discount = ", discount);
    }

    public static void printTotal() {
        String format = "%-41s $%.2f\n";
        System.out.printf(format, "TOTAL BEFORE DISCOUNT", totalBasketCost);
        double cartDiscount = 0;
        if(getCartDiscount() != 0) {
            cartDiscount = totalBasketCost * getCartDiscount() / 100;
            totalBasketCost -= cartDiscount;
        }
        System.out.printf(format, "CART DISCOUNT", cartDiscount);
        System.out.printf(format, "TOTAL", totalBasketCost);
    }

    public static int getCartDiscount() {
        int discount = 0;
        if(clientInfo.getCard() != null) {
            discount = clientInfo.getCard().getDiscountPercent();
        }
        return discount;
    }
}
