package group10.server.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public long register(String username, String password, String email) {
        // TODO -- returns uid if successful, -1 if fails

        return -1;
    }

    public boolean login(String username, String password) {
        // TODO

        return false;
    }

    public boolean logout(String username) {
        // TODO

        return false;
    }

    public void requestPassword(String username) {
        // TODO
    }

    public void updatePassword(long userId, String password) {
        // TODO
    }
}
