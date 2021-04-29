package group10.server.service;

import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.PasswordDTO;
import group10.server.model.PlayerDTO;
import group10.server.repository.PlayerRepository;
import group10.server.config.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JavaMailSender javaMailSender;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         JavaMailSender javaMailSender) {
        this.playerRepository = playerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
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
        // tested and working. Other logic must be implemented.
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("alperen.0311@gmail.com");
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
        javaMailSender.send(msg);
    }

    @Transactional
    public void updatePassword(long userId, PasswordDTO password) {
        String encryptedPassword = this.bCryptPasswordEncoder.encode(password.getPassword());
        playerRepository.updatePassword(userId, encryptedPassword);
    }

    public Player getLoggedInPlayer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        Optional<Player> playerByUsername = playerRepository.findByUsername(username);
        return playerByUsername.orElse(null);
    }
}
