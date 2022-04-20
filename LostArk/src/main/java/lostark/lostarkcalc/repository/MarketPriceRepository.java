package lostark.lostarkcalc.repository;

import lostark.lostarkcalc.domain.MarketPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MarketPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {}
