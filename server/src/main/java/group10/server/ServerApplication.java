package group10.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring boot application that contains the runnable method.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@SpringBootApplication
public class ServerApplication {

    /**
     * A bean that creates password encoder to inject to player service.
     *
     * @return Password encoder to be injected to player service.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Main method where execution starts. It starts the springboot application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
