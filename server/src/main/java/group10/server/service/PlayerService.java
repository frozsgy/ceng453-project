package group10.server.service;

import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.PasswordDTO;
import group10.server.model.PlayerDTO;
import group10.server.repository.PlayerRepository;
import group10.server.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.playerRepository = playerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public long register(PlayerDTO dto) throws IllegalArgumentException {
        Player player = new Player();
        player.setUsername(dto.getUsername());
        player.setEmail(dto.getEmail());
        player.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        long userId = -1;
        try {
            Player saved = playerRepository.save(player);
            userId = saved.getId();
        } catch (Exception ignored) {
        }
        return userId;
    }

    public String login(LoginDTO loginData) {
        Optional<Player> optUser = playerRepository.findByUsername(loginData.getUsername());
        if (optUser.isPresent()) {
            Player player = optUser.get();
            System.out.println(player.getPassword());
            if (this.bCryptPasswordEncoder.matches(loginData.getPassword(), player.getPassword())) {
                return JWTUtil.getJWTToken(player.getUsername(), player.getId());
            }
        }
        return "Wrong username/password";
    }

    public boolean logout(String username) {
        // TODO

        return false;
    }

    public void requestPassword(String username) {
        // TODO
    }

    @Transactional
    public void updatePassword(long userId, PasswordDTO password) {
        String encryptedPassword = this.bCryptPasswordEncoder.encode(password.getPassword());
        playerRepository.updatePassword(userId, encryptedPassword);
    }
}
