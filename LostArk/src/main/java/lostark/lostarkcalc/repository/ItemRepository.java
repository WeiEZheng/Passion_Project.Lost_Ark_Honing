package lostark.lostarkcalc.repository;

import lostark.lostarkcalc.domain.Item;
import lostark.lostarkcalc.domain.enumeration.MaterialName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Item entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findOneByName(MaterialName materialName);
}
