package book.action.capter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {
    private static final Random random = new Random();
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    /**
     * 동기 메서드
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * getPrice(String) 의 비동기 메서드로 전환
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> { // 실제 가격을 계산할 다른 스레드

            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e); // 발생한 에러를 포함하여 Future 종료
            }
        }).start();
        return futurePrice;
    }

    private double calculatePrice(String product) {
        if (product.equals("ex")) {
            throw new RuntimeException("product not available");
        }
        delay(); // Thread sleep 으로 강제 대기시간 부여
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    /**
     * 1초 지연을 흉내 내는 메서드
     */
    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
