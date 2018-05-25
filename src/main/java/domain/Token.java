package domain;

import java.util.Random;

public class Token {

    private final static int PARTS = 4;
    private final static int MAX_VALUE_FOR_PART = 9999;
    private final static int MAX_LENGTH_FOR_PART = Integer.toString(MAX_VALUE_FOR_PART).length();
    private final static String SEPARATOR = "-";

    public static String generateToken(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < PARTS; i++) {
            builder.append(generateTokenPart());
            if (i != PARTS -1){
                builder.append(SEPARATOR);
            }
        }
        return builder.toString();
    }

    private static String generateTokenPart() {
        Random rand = new Random();
        int number = rand.nextInt(MAX_VALUE_FOR_PART) + 1;
        StringBuilder builder = new StringBuilder(Integer.toString(number));
        for (int i = builder.length(); i < MAX_LENGTH_FOR_PART; i++) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }
}
