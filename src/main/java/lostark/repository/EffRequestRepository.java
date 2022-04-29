package lostark.repository;

import lostark.domain.EffRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EffRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EffRequestRepository extends JpaRepository<EffRequest, Long> {}
