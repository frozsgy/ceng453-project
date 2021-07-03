package group10.server.util;

import org.springframework.mail.SimpleMailMessage;

/**
 * Utility class that composes password reset code.
 * It does not implement mail sending.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class EmailComposer {

    /**
     * Subject of the password reset code mail.
     */
    private static final String SUBJECT = "Your Password Code Request";
    /**
     * Constant body of the password reset code mail.
     */
    private static final String BODY = "Your code: ";

    /**
     * Static function that composes mail that contains password reset code
     * for the given email.
     *
     * @param email Email address that is going to receive the reset code.
     * @param code  Pre-generated reset code that is going to be mailed.
     * @return Mail that is going to be sent.
     */
    public static SimpleMailMessage composeMail(String email, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(SUBJECT);
        msg.setText(BODY + code);
        return msg;
    }
}
