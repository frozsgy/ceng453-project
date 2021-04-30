package group10.server.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Entity for Password Reset objects.
 */
@Entity
@Table(name = "pending_pw_code")
public class PendingPwCode extends EntityBase {

    /**
     * Field for storing Player information.
     * Since a Player can have multiple password reset requests the relation is ManyToOne.
     */
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    /**
     * Field that stores the password reset code.
     */
    private String code;

    /**
     * Gets Player.
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets Player
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
