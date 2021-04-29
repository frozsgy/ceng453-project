package group10.server.service;

import group10.server.config.JWTUtil;
import group10.server.entity.PendingPwCode;
import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.PasswordDTO;
import group10.server.model.PlayerDTO;
import group10.server.repository.PendingPwCodeRepository;
import group10.server.repository.PlayerRepository;
import group10.server.repository.RoleRepository;
import group10.server.util.EmailComposer;
import group10.server.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JavaMailSender javaMailSender;
    private PendingPwCodeRepository pendingPwCodeRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, RoleRepository roleRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender,
                         PendingPwCodeRepository pendingPwCodeRepository) {
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
        this.pendingPwCodeRepository = pendingPwCodeRepository;
    }

    public long register(PlayerDTO dto) throws IllegalArgumentException {
        Player player = new Player();
        player.setUsername(dto.getUsername());
        player.setEmail(dto.getEmail());
        player.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        player.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
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

    public boolean requestPassword(String email) {
        // TODO
        Optional<Player> optUser = playerRepository.findByEmail(email);
        if (optUser.isPresent()) {
            Player player = optUser.get();
            String code = RandomStringGenerator.generate();
            SimpleMailMessage msg = EmailComposer.composeMail(email, RandomStringGenerator.generate());
            try{
                javaMailSender.send(msg);
                PendingPwCode inserted = new PendingPwCode();
                inserted.setPlayer(player);
                inserted.setEmail(email);
                inserted.setCode(code);
                pendingPwCodeRepository.save(inserted);
                return true;
            }catch(MailException e) {
                return false;
            }
        } else {
            return false;
        }
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
