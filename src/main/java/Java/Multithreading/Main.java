package Java.Multithreading;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final int n = 100_000;

    static AtomicInteger threeLettersAtomic = new AtomicInteger(0);
    static AtomicInteger fourLettersAtomic = new AtomicInteger(0);
    static AtomicInteger fiveLettersAtomic = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();

        String[] texts = new String[n];

        for (int i = 0; i < texts.length; i++)
            texts[i] = generateText("abc", 3 + random.nextInt(3));

        TestPalindrom(texts, n);
        TestOneSameLetter(texts, n);
        TestQueueLetter(texts, n);

        System.out.println("Красивых слов с длиной 3: " + threeLettersAtomic.get());
        System.out.println("Красивых слов с длиной 4: " + fourLettersAtomic.get());
        System.out.println("Красивых слов с длиной 5: " + fiveLettersAtomic.get());

    }

    ;

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++)
            text.append(letters.charAt(random.nextInt(letters.length())));
        return text.toString();
    }

    private static void IncrementAndGetMethod(String text) {

        switch (text.length()) {
            case 3:
                threeLettersAtomic.incrementAndGet();
                break;
            case 4:
                fourLettersAtomic.incrementAndGet();
                break;
            case 5:
                fiveLettersAtomic.incrementAndGet();
                break;
        }
    }

    public static void TestPalindrom(String[] texts, int n) throws InterruptedException {

        Runnable palindrom = () -> {

            for (int i = 0; i < n; i++) {
                boolean palindromFlag = true;
                int left = 0;
                int right = texts[i].length() - 1;
                while (right > left) {
                    if (texts[i].charAt(left) != texts[i].charAt(right)) {
                        palindromFlag = false;
                        break;
                    }
                    ;
                    left++;
                    right--;
                }
                if (palindromFlag)
                    IncrementAndGetMethod(texts[i]);

            }
        };

        Thread palindromThread = new Thread(palindrom);
        palindromThread.start();
        palindromThread.join();
    }


    public static void TestOneSameLetter(String[] texts, int n) throws InterruptedException {

        Runnable oneSameLetter = () -> {

            for (int i = 0; i < n; i++) {
                boolean oneLetterFlag = true;
                int current = 0;
                while (current <= texts[i].length() - 2) {
                    if (texts[i].charAt(current) != texts[i].charAt(current + 1)) {
                        oneLetterFlag = false;
                        break;
                    }
                    ;
                    current++;
                }
                if (oneLetterFlag)
                    IncrementAndGetMethod(texts[i]);

            }
        };

        Thread sameLettersThread = new Thread(oneSameLetter);
        sameLettersThread.start();
        sameLettersThread.join();
    }

    public static void TestQueueLetter(String[] texts, int n) throws InterruptedException {
        Runnable queueLetter = () -> {
            for (int i = 0; i < n; i++) {
                boolean queueLetterFlag = true;
                int current = 0;
                while (current < texts[i].length() - 1) {
                    if (texts[i].charAt(current) > texts[i].charAt(current + 1)) {
                        queueLetterFlag = false;
                        break;
                    }
                    ;
                    current++;
                }
                if (queueLetterFlag)
                    IncrementAndGetMethod(texts[i]);

            }
        };
        Thread queueLettersThread = new Thread(queueLetter);
        queueLettersThread.start();
        queueLettersThread.join();
    }
}
