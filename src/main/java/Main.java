import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger threeWordCounter = new AtomicInteger(0);
        AtomicInteger fourWordCounter = new AtomicInteger(0);
        AtomicInteger fiveWordCounter = new AtomicInteger(0);
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread check1 = new Thread(() -> {
            for (String name : texts) {
                char[] temp = name.toCharArray();
                if (temp.length == 3 && temp[0] == temp[2]) {
                    threeWordCounter.getAndIncrement();
                } else if (temp.length == 4 && temp[0] == temp[3] && temp[1] == temp[2]) {
                    fourWordCounter.getAndIncrement();
                } else if (temp.length == 5 && temp[0] == temp[4] && temp[1] == temp[3]) {
                    fiveWordCounter.getAndIncrement();
                }
            }
        });

        Thread check2 = new Thread(() -> {
            for (String name : texts) {
                char[] temp = name.toCharArray();
                if (temp.length == 3 && temp[0] == temp[1] && temp[0] == temp[2]) {
                    threeWordCounter.getAndIncrement();
                } else if (temp.length == 4 && temp[0] == temp[1] && temp[0] == temp[2] && temp[0] == temp[3]) {
                    fourWordCounter.getAndIncrement();
                } else if (temp.length == 5 && temp[0] == temp[1] && temp[0] == temp[2] && temp[0] == temp[3]
                        && temp[0] == temp[4]) {
                    fiveWordCounter.getAndIncrement();
                }
            }
        });

        Thread check3 = new Thread(() -> {
            for (String name : texts) {
                char[] temp = name.toCharArray();
                if (temp.length == 3 && temp[0] <= temp[1] && temp[0] <= temp[2]) {
                    threeWordCounter.getAndIncrement();
                } else if (temp.length == 4 && temp[0] <= temp[1] && temp[0] <= temp[2] && temp[0] <= temp[3]) {
                    fourWordCounter.getAndIncrement();
                } else if (temp.length == 5 && temp[0] <= temp[1] && temp[0] <= temp[2] && temp[0] <= temp[3]
                        && temp[0] <= temp[4]) {
                    fiveWordCounter.getAndIncrement();
                }
            }
        });
        check1.start();
        check2.start();
        check3.start();
        check3.join();
        check2.join();
        check1.join();

        System.out.println("Красивых слов с длиной 3: " + threeWordCounter);
        System.out.println("Красивых слов с длиной 4: " + fourWordCounter);
        System.out.println("Красивых слов с длиной 5: " + fiveWordCounter);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
