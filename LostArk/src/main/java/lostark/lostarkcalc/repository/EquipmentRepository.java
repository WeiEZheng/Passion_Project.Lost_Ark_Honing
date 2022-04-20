package lostark.lostarkcalc.repository;

import java.util.List;
import java.util.Optional;
import lostark.lostarkcalc.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Equipment entity.
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("select equipment from Equipment equipment where equipment.belongTo.login = ?#{principal.username}")
    List<Equipment> findByBelongToIsCurrentUser();

    default Optional<Equipment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Equipment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Equipment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct equipment from Equipment equipment left join fetch equipment.belongTo",
        countQuery = "select count(distinct equipment) from Equipment equipment"
    )
    Page<Equipment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct equipment from Equipment equipment left join fetch equipment.belongTo")
    List<Equipment> findAllWithToOneRelationships();

    @Query("select equipment from Equipment equipment left join fetch equipment.belongTo where equipment.id =:id")
    Optional<Equipment> findOneWithToOneRelationships(@Param("id") Long id);
}
