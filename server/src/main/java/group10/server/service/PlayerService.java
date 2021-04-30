package group10.server.service;

import group10.server.config.JWTUtil;
import group10.server.entity.PendingPwCode;
import group10.server.entity.Player;
import group10.server.model.LoginDTO;
import group10.server.model.PasswordResetDTO;
import group10.server.model.PlayerDTO;
import group10.server.repository.PendingPwCodeRepository;
import group10.server.repository.PlayerRepository;
import group10.server.repository.RoleRepository;
import group10.server.util.EmailComposer;
import group10.server.util.RandomStringGenerator;
import org.json.JSONException;
import org.json.JSONObject;
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

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Service class that is responsible of Player.
 */
@Service
public class PlayerService {

    /**
     * Auto-wired player repository. Responsible for interacting with Player table.
     */
    private PlayerRepository playerRepository;
    /**
     * Auto-wired role repostiory. Responsible for interacting with Role table.
     */
    private RoleRepository roleRepository;
    /**
     * Auto-wired password encoder.
     * Generates salted and hashed password.
     * Also, verifies password for login.
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * Auto-wired mail sender.
     * Sends e-mail to given address.
     */
    private JavaMailSender javaMailSender;
    /**
     * Auto-wired repository that is responsible for interacting with pending_pw_code table.
     */
    private PendingPwCodeRepository pendingPwCodeRepository;

    /**
     * Constructor for player service.
     * @param playerRepository Autowired player repository
     * @param roleRepository Autowired role repository.
     * @param bCryptPasswordEncoder Autowired password encoder.
     * @param javaMailSender Autowired mail sender.
     * @param pendingPwCodeRepository Autowired pending pw code repository.
     */
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

    /**
     * Registers (adds) a user to Player table.
     * @param dto DTO object that registered player is created from.
     * @return Player
     * @throws IllegalArgumentException if any of the dto fields is invalid.
     * @see Player
     * @see PlayerDTO
     */
    public Player register(PlayerDTO dto) throws IllegalArgumentException {
        Player player = new Player();
        player.setUsername(dto.getUsername());
        player.setEmail(dto.getEmail());
        player.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        player.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return playerRepository.save(player);
    }

    /**
     *
     * Logs in a registered player.
     * @param loginData DTO object that contains username and password.
     * @return Token
     * @throws IllegalArgumentException if username and password do not match.
     */
    public String login(LoginDTO loginData) throws IllegalArgumentException {
        Optional<Player> optUser = playerRepository.findByUsername(loginData.getUsername());
        if (optUser.isPresent()) {
            Player player = optUser.get();
            if (this.bCryptPasswordEncoder.matches(loginData.getPassword(), player.getPassword())) {
                return JWTUtil.getJWTToken(player.getUsername(), player.getId());
            }
        }
        throw new IllegalArgumentException("Wrong username/password");
    }

    /**
     * Given an email address, if it is registered, creates a password reset code, mails it and
     * saves it to pending_pw_code table.
     * @param emailJSON JSON object that contains the password that the mail is going to be sent.
     * @return true if user is found and mail is sent successfully, false otherwise.
     */
    public boolean requestPwCode(JSONObject emailJSON) {
        try{
            String email = emailJSON.getString("email");
            Optional<Player> optUser = playerRepository.findByEmail(email);
            if (optUser.isPresent()) {
                Player player = optUser.get();
                String code = RandomStringGenerator.generate();
                SimpleMailMessage msg = EmailComposer.composeMail(email, code);
                try{
                    javaMailSender.send(msg);
                    PendingPwCode inserted = new PendingPwCode();
                    inserted.setPlayer(player);
                    inserted.setCode(code);
                    pendingPwCodeRepository.save(inserted);
                    return true;
                } catch(MailException e) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Given username, password reset code and a new password, if credentials are valid, sets the user's password
     * to new password.
     * @param passwordResetDTO DTO that contains, username, new password and password reset code.
     * @return true if password is updated successfully, false otherwise.
     */
    @Transactional
    public boolean updatePassword(PasswordResetDTO passwordResetDTO) {
        Optional<PendingPwCode> optionalPending = pendingPwCodeRepository.findByCode(passwordResetDTO.getResetCode());
        if (optionalPending.isPresent()) {
            PendingPwCode pending = optionalPending.get();
            if (pending.getPlayer().getUsername().equals(passwordResetDTO.getUsername())) {
                String encryptedPassword = this.bCryptPasswordEncoder.encode(passwordResetDTO.getPassword());
                playerRepository.updatePassword(pending.getPlayer().getId(), encryptedPassword);
                pendingPwCodeRepository.delete(pending);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Given token, gets the Player
     * @return Player
     */
    public Player getLoggedInPlayer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        Optional<Player> playerByUsername = playerRepository.findByUsername(username);
        return playerByUsername.orElse(null);
    }
}
