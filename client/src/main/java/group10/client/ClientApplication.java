package group10.client;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application that contains the runnable method.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@SpringBootApplication
public class ClientApplication {

    /**
     * Entry point for the application
     *
     * @param args argument list
     */
    public static void main(String[] args) {
        Application.launch(UiApplication.class, args);
    }

}
