package group10.server.repository;

import group10.server.entity.PendingPwCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Repository that is used by PlayerService. Allows communication with the database.
 */
@Repository
public interface PendingPwCodeRepository extends JpaRepository<PendingPwCode, Long> {

    /**
     * Finds the PendingPwCode with the given id.
     *
     * @param id - PwCode id
     * @return PendingPwCode if found.
     */
    Optional<PendingPwCode> findById(long id);


    /**
     * Finds the PendingPwCode with the given code.
     *
     * @param code - PwCode code
     * @return PendingPwCode if found.
     */
    Optional<PendingPwCode> findByCode(String code);
}
