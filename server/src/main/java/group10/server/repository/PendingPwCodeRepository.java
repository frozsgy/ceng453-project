package group10.server.repository;

import group10.server.entity.PendingPwCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingPwCodeRepository extends JpaRepository<PendingPwCode, Long> {

    Optional<PendingPwCode> findById(long id);

}
