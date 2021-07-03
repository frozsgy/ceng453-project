package group10.server.util;

import java.util.Random;

/**
 * Utility class that generates password reset code, which is a
 * random string
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class RandomStringGenerator {

    /**
     * Password reset code length
     */
    private final static int STRING_LENGTH = 10;
    /**
     * Constant that contains upper case letters.
     */
    private final static String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Constant that contains lower case letters.
     */
    private final static String LOWER = "abcdefghijklmnopqrstuvwxyz";
    /**
     * Constant that contains numbers.
     */
    private final static String NUMBERS = "0123456789";
    /**
     * Constant that contains punctuation symbols.
     */
    private final static String PUNCTS = "!@#$%^&*()_+=-.,;";
    /**
     * Constant that holds the mix of lower case, upper case letters, numbers and punctuation symbols
     */
    private final static String ALL = UPPER + LOWER + NUMBERS + PUNCTS;

    /**
     * Static method to generate password reset code.
     *
     * @return Password reset code.
     */
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
