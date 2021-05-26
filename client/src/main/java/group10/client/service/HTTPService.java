package group10.client.service;

import com.google.gson.Gson;
import group10.client.constants.ServerFolders;
import group10.client.model.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

@Service
public class HTTPService {

    @Value("${spring.application.apiAddress}")
    private String apiAddress;
    private RestTemplate restTemplate;
    private Gson gson;

    private static HTTPService instance;

    private HTTPService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }
    public static HTTPService getInstance() {
        if (instance == null) {
            return new HTTPService();
        }
        return instance;
    }

    public boolean login(Player player) {
        String json = gson.toJson(player);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        try {
            /*
                TODO
                for some reasons, this.apiAddress not injected. for now, I placed url hardcoded to test the client.
             */
            ResponseEntity<?> response =  restTemplate.exchange("http://localhost:8080/api/user/login", HttpMethod.POST, entity, String.class);
        } catch (HttpServerErrorException e) {
            // invalid credientials.
            // debug data
            System.out.println("Invalid credientials");
            return false;
        }
        System.out.println("Success!");
        return true;
    }
}
