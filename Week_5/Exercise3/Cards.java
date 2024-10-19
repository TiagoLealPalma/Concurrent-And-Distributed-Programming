package Week_5.Exercise3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cards {
    private final List<String> numbers = new ArrayList<>();
    private final List<String> suits = new ArrayList<>();
    private final Random rand = new Random();

    public Cards() {
        numbers.add("A");
        for (int i = 2; i < 11; i++) {
            numbers.add(i + "");
        }
        numbers.add("J");
        numbers.add("Q");
        numbers.add("K");

        suits.add("♠");
        suits.add("♡");
        suits.add("♢");
        suits.add("♣");
    }
    public String getCard(){
        String result = "";
        result+=numbers.get(rand.nextInt(numbers.size()));
        result+=suits.get(rand.nextInt(suits.size()));
        return result;
    }

    private static final Cards cardsInstance = new Cards();

    public static String getRandomCard(){
        return cardsInstance.getCard();
    }
}
