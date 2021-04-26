package group10.server.service;

import group10.server.entity.User;
import group10.server.model.LoginDTO;
import group10.server.model.UserDTO;
import group10.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User register(UserDTO dto) throws IllegalArgumentException {
        // TODO
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
//        String hashedPassword = this.hashPassword(dto.getPassword());
        user.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        return userRepository.save(user);
    }

    public boolean login(LoginDTO loginData) {
        Optional<User> optUser = userRepository.findByUsername(loginData.getUsername());
        if (!optUser.isEmpty()) {
            User user = optUser.get();
            if (this.checkPassword(loginData.getPassword(), user.getPassword())){
                return true;
            }
        }
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
    private boolean checkPassword(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)){
            return true;
        }
        return false;
    }
}
