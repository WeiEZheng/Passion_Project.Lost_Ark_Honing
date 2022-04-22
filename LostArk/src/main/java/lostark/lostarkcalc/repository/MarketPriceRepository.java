package lostark.lostarkcalc.repository;

import lostark.lostarkcalc.domain.Item;
import lostark.lostarkcalc.domain.MarketPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the MarketPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {
    Optional<MarketPrice> findOneByItem(Item item);
}
