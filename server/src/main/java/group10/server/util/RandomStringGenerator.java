package group10.server.util;

import java.util.Random;

public class RandomStringGenerator {

    private final static int STRING_LENGTH = 10;
    private final static String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final static String NUMBERS = "0123456789";
    private final static String PUNCTS = "!@#$%^&*()_+=-.,;";
    private final static String ALL = UPPER + LOWER + NUMBERS + PUNCTS;

    public static String generate() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < STRING_LENGTH; i++) {
            int index = random.nextInt(ALL.length());
            char randChar = ALL.charAt(index);
            sb.append(randChar);
        }
        return sb.toString();
    }
}
