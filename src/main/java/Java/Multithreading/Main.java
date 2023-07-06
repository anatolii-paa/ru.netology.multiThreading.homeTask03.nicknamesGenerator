package Java.Multithreading;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static int threeLettersCounter = 0, fourLetterCounter = 0, fiveLettersCounter = 0;
    public static final int n = 100000;

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        AtomicInteger threeLettersAtomic = new AtomicInteger(threeLettersCounter);
        AtomicInteger fourLettersAtomic = new AtomicInteger(fourLetterCounter);
        AtomicInteger fiveLettersAtomic = new AtomicInteger(fiveLettersCounter);


        String[] texts = new String[n];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
//            System.out.println(texts[i]);
        }

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
                if (palindromFlag == true) {
                    if (texts[i].length() == 3) {
                        threeLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is threeLettersPalindrom");
                    }
                    if (texts[i].length() == 4) {
                        fourLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is fourLettersPalindrom");
                    }
                    if (texts[i].length() == 5) {
                        fiveLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is fiveLettersPalindrom");
                    }
                } else {
//                    System.out.println(texts[i] + " is not palindrom");
                }
                ;
            }
        };

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
                if (oneLetterFlag == true) {
                    if (texts[i].length() == 3) {
                        threeLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is threeSameLetters text");
                    }
                    if (texts[i].length() == 4) {
                        fourLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is fourSameLetters text");
                    }
                    if (texts[i].length() == 5) {
                        fiveLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is fiveSameLetters");
                    }
                } else {
//                    System.out.println(texts[i] + " is not sameLetters text");
                }
                ;

            }

        };

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
                if (queueLetterFlag == true) {
                    if (texts[i].length() == 3) {
                        threeLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is threeQueueLetters text");
                    }
                    if (texts[i].length() == 4) {
                        fourLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is fourQueueLetters text");
                    }
                    if (texts[i].length() == 5) {
                        fiveLettersAtomic.incrementAndGet();
//                        System.out.println("! " + texts[i] + " is fiveQueueLetters");
                    }
                } else {
//                    System.out.println(texts[i] + " is not QueueLetters text");
                }
                ;

            }

        };
        Thread palindromThread = new Thread(palindrom);
        Thread sameLettersThread = new Thread(oneSameLetter);
        Thread queueLettersThread = new Thread(queueLetter);
        palindromThread.start();
        sameLettersThread.start();
        palindromThread.join();
        sameLettersThread.join();
        queueLettersThread.start();
        queueLettersThread.join();

        System.out.println("Красивых слов с длиной 3: " + threeLettersAtomic.get());
        System.out.println("Красивых слов с длиной 4: " + fourLettersAtomic.get());
        System.out.println("Красивых слов с длиной 5: " + fiveLettersAtomic.get());

    }

    ;


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


}