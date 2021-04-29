package group10.server.util;

import org.springframework.mail.SimpleMailMessage;

public class EmailComposer {

    private static final String SUBJECT = "Your Password Code Request";
    private static final String BODY = "Your code: ";

    public static SimpleMailMessage composeMail(String email, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(SUBJECT);
        msg.setText(BODY + code);
        return msg;
    }
}
