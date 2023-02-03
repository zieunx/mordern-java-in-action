package book.action.capter16;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ShopApplication {
    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();

        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");

        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + "msecs");

        doSomethingElse();

        try {
            Double price = futurePrice.get(1, TimeUnit.SECONDS);
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + retrievalTime + "msecs");
    }

    /**
     * 메인스레드가 처리할 다른 일
     */
    private static void doSomethingElse() {
        // something
    }
}
