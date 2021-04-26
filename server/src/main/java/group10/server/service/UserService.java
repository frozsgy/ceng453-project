package group10.server.service;

import group10.server.entity.User;
import group10.server.model.UserDTO;
import group10.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserDTO dto) throws IllegalArgumentException {
        // TODO
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        String hashedPassword = this.hashPassword(dto.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
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

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
