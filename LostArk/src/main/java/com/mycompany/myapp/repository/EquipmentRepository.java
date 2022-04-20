package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Equipment;
import java.util.List;
import java.util.Optional;
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
        value = "select distinct equipment from Equipment equipment left join fetch equipment.characters",
        countQuery = "select count(distinct equipment) from Equipment equipment"
    )
    Page<Equipment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct equipment from Equipment equipment left join fetch equipment.characters")
    List<Equipment> findAllWithToOneRelationships();

    @Query("select equipment from Equipment equipment left join fetch equipment.characters where equipment.id =:id")
    Optional<Equipment> findOneWithToOneRelationships(@Param("id") Long id);
}
