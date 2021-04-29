package group10.server.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "pending_pw_codes")
public class PendingPwCodes extends EntityBase {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    private String code;
    private String email;
}
